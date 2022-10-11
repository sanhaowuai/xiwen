<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="60px">
      <el-form-item label="申报人" prop="sqr">
        <el-input style="width: 140px"
                  v-model="queryParams.sqr"
                  placeholder="请输入申报人"
                  clearable
                  @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="sqlx">
        <el-select v-model="queryParams.sqlx" style="width: 150px" @change="getTreeselect()"
                   placeholder="请选择申报类型" clearable>
          <el-option
            v-for="dict in dict.type.workload_khxlx"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考核项" prop="ksxid">
        <treeselect style="width: 240px" v-model="queryParams.ksxid" :options="khxglOptions" :normalizer="normalizer" placeholder="请选择考核项" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['workload:sbgl:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['workload:sbgl:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['workload:sbgl:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sbglList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="checkedSelectRow"/>
      <el-table-column label="申报时间" align="center" prop="sbsj" width="97">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sbsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申报流程" align="center" prop="lcmc" width="100" :show-overflow-tooltip="true" />
      <el-table-column label="申报人" align="center" prop="sqrxm" width="100"/>
      <el-table-column label="类型" align="center" prop="sqlxmc" width="80" />
      <el-table-column label="考核项" align="center" prop="khxmc" width="120" :show-overflow-tooltip="true" />
      <el-table-column label="工作时长" align="center" prop="gzsc" width="100"/>
      <el-table-column label="工作简述" align="center" prop="gzjs" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="申请分值" align="center" prop="sqfz" width="100"/>
      <el-table-column label="审核状态" align="center" prop="shztmc" width="100" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.shzt == '0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workload:sbgl:edit']"
          >修改</el-button>
          <el-button v-if="scope.row.shzt == '0'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workload:sbgl:remove']"
          >删除</el-button>
          <el-button v-if="scope.row.shzt != '0'" size="mini" type="text" icon="el-icon-s-operation" @click="lcck(scope.row)"
          >流程</el-button>
          <el-button v-if="scope.row.shzt != '0'"
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleView(scope.row)"
            v-hasPermi="['workload:sbgl:remove']"
          >查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 选择参与人 -->
    <el-dialog title="选择参与人" :visible.sync="showCyrDialogSf" width="720px" append-to-body :close-on-press-escape="false" :close-on-click-modal="false">
      <div>
        <h3>已选择：</h3>
        <el-tag
          :key="item[UID]"
          v-for="(item, index) in cyryTempList"
          closable
          @close="removeItemByIndex(index, item)"
        >
          {{ item.yhxm }}
        </el-tag>
      </div>
      <div style="margin-bottom: 5px;text-align: right;">
        姓名：<el-input type="text" style="width: 160px;" size="small" v-model="queryCyryParams.queryCyrXm" ></el-input>
        <el-button style="margin-left: 10px;margin-top: 2px;" type="primary" icon="el-icon-search" size="small" @click="queryCyryTableList">搜索</el-button>
        <el-button style="margin-left: 10px;margin-top: 2px;" type="success" icon="el-icon-user" size="small" @click="saveCyry">确定</el-button>
      </div>
      <div style="margin-top: 10px;margin-bottom: 21px;">
        <el-table height="400" ref="cyryMultipleTable" :row-key="getRowKeyYhid"
                  :data="cyryTableList" @select="selectChange"
                  @select-all="selectAllChange">
          <el-table-column type="selection" width="55" align="center" :reserve-selection="true"/>
          <el-table-column label="人员姓名" width="130" align="center" prop="yhxm" />
          <el-table-column label="性别" width="100" align="center" prop="xbmc" />
          <el-table-column label="年龄" width="100" align="center" prop="nl" />
          <el-table-column label="部门" align="center" prop="bmmc" />
        </el-table>
        <pagination
          v-show="totalCyry>0"
          :total="totalCyry"
          :page.sync="queryCyryParams.pageNum"
          :limit.sync="queryCyryParams.pageSize"
          @pagination="getCyryList"
        />
      </div>
      <div>

      </div>
    </el-dialog>
    <!-- 添加或修改申报管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body :close-on-press-escape="false" :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="100px" style="padding: 5px 15px;">
        <el-form-item label="类型" prop="sqlx" class="el-form-item-margin">
          <el-select v-model="form.sqlx" placeholder="请选择考核类型" style="width: 200px" @change="getCyryTreeselect()">
            <el-option
              v-for="dict in dict.type.workload_khxlx"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="考核项" prop="ksxid" class="el-form-item-margin">
          <treeselect style="width: 200px" v-model="form.ksxid" :options="khxglOptionsCyry" :clearable="false"
                      :disable-branch-nodes="true"
                     @input="checkedKhxdyfs" :normalizer="normalizerCyry" placeholder="请选择考核项" />
        </el-form-item>
        <el-form-item label="开始时间" prop="gzkssj" class="el-form-item-margin">
          <el-date-picker @change="checkedGzkssj('1')"
            style="width: 200px"
            v-model="form.gzkssj"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            placeholder="请选择工作开始时间"
            align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="gzjssj" class="el-form-item-margin">
          <el-date-picker @change="checkedGzkssj('2')"
            style="width: 200px"
            v-model="form.gzjssj"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            placeholder="请选择工作结束时间"
            align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工作时长" prop="gzsc" class="el-form-item-margin">
          <el-input v-model="form.gzsc" placeholder="请输入工作时长" style="width: 160px"/>小时
        </el-form-item>
        <el-form-item label="总分值" prop="sqfz" class="el-form-item-margin">
          <el-input v-model="form.sqfz" placeholder="请输入申请分值" style="width: 130px"/>预设<span style="color: red;">{{form.tempSqfz}}</span>分
        </el-form-item>
        <el-form-item label="参与人"  class="el-form-item-margin">
          <slot>
            <el-button type="primary" plain icon="el-icon-plus" style="padding: 6px 9px;" title="添加参与人"
                       @click="showCyrDialog" size="mini" ></el-button>
            <el-button type="primary" plain style="padding: 6px 9px;" title="计算每人得分，不能平均分配的，请手动修改分值分配"
                       @click="avgCyryFz" size="mini" >总分值平均分配</el-button>
          </slot>
          <table border="1" style="border-collapse: collapse;text-align: center;">
            <tr>
              <td style="width:80px">姓名</td>
              <td style="width:110px">得分</td>
              <td style="width:295px">工作分配</td>
              <td style="width:50px"></td>
            </tr>
            <template v-for="(item, index) in cyryList">
              <tr>
                <td>{{item.yhxm}}</td>
                <td><el-input-number size="mini" v-model="item.df" style="width: 100px;" :min="0.01"></el-input-number></td>
                <td><el-input v-model="item.gznr" placeholder="请输入工作内容" style="width: 295px"/></td>
                <td><el-button type="danger" plain icon="el-icon-delete" style="padding: 6px 9px;"
                               @click="handleDeleteCyry(item,index)" size="mini" ></el-button></td>
              </tr>
            </template>
          </table>
        </el-form-item>
        <el-form-item label="工作内容" prop="gzjs" class="el-form-item-margin">
          <el-input v-model="form.gzjs" type="textarea" placeholder="请输入工作内容" style="width: 520px" :autosize="{ minRows: 4, maxRows: 6}"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('0')">保 存</el-button>
        <el-button type="primary" @click="submitForm('1')">提 交</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看 -->
    <el-dialog title="查看详情" :visible.sync="openXq" width="720px" append-to-body :close-on-press-escape="false" :close-on-click-modal="false">
      <el-divider content-position="center"></el-divider>
      <div>
        <div style="margin-left: 67px;margin-bottom: 15px;">
          <label style="width: 80px;">类型：</label><div style="width: 240px;display: inline-block;">{{xqform.sqlxmc}}</div>
          <label style="margin-left: 15px;">考核项：</label><div style="width: 240px;display: inline-block;">{{xqform.khxmc}}</div>
        </div>
        <div style="margin-left: 40px;margin-bottom: 15px;">
          <label>开始时间：</label><div style="width: 240px;display: inline-block;">{{xqform.gzkssj}}</div>
          <label>结束时间：</label><div style="width: 240px;display: inline-block;">{{xqform.gzjssj}}</div>
        </div>
        <div style="margin-left: 40px;margin-bottom: 15px;">
          <label>工作时长：</label><div style="width: 240px;display: inline-block;">{{xqform.gzsc}}</div>
          <label style="margin-left: 15px;">总分值：</label><div style="width: 240px;display: inline-block;">{{xqform.sqfz}}</div>
        </div>
        <div style="margin-left: 40px;margin-bottom: 15px;">
          <div style="font-weight: 700;margin-left: 14px;width: 57px;float: left">参与人：</div>
          <div style="width: 510px;display: inline-block;min-height: 100px;">
            <table border="1" style="border-collapse: collapse;text-align: center;">
              <tr>
                <td style="width:80px;line-height: 30px;">姓名</td>
                <td style="width:110px;line-height: 30px;">得分</td>
                <td style="width:390px;line-height: 30px;">工作分配</td>
              </tr>
              <template v-for="(item, index) in cyryList">
                <tr>
                  <td style="line-height: 30px;">{{item.yhxm}}</td>
                  <td style="line-height: 30px;">{{item.df}}</td>
                  <td style="text-align: left;line-height: 30px;" :title="item.gznr">{{item.gznr}}</td>
                </tr>
              </template>
            </table>
          </div>
        </div>
        <div style="margin-left: 40px;margin-bottom: 15px;">
          <label>工作内容：</label>
          <div style="width: 510px;display: inline-block;border: 1px solid;min-height: 100px;">{{xqform.gzjs}}</div>
        </div>
      </div>
    </el-dialog>


    <!-- 查看流程 -->
    <el-dialog title="流程详情" :visible.sync="openLcXq" width="720px" append-to-body >
      <el-divider content-position="center"></el-divider>
      <div>
        <div style="margin-left: 20px;margin-right: 20px;margin-bottom: 20px;margin-top: 25px;">
          <el-steps  :active="lcjdjd" finish-status="success">
            <el-step v-for="itemlcjd in lcjdList" :title="itemlcjd.jsmc"></el-step>
          </el-steps>
        </div>
        <div style="margin-left: 20px;margin-right: 20px;margin-bottom: 20px;margin-top: 25px;">
          <div class="block">
            <el-timeline>
              <el-timeline-item v-for="itemlcsh in lcshjlList" :timestamp="itemlcsh.czsj" placement="top">
                <el-card>
                  <h4>{{itemlcsh.yhxm}}  {{itemlcsh.shztmc}}</h4>
                  <p>{{itemlcsh.shyj}}</p>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </div>
        </div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listSbgl, getSbgl, delSbgl, addSbgl, updateSbgl,
    queryCyryList,getUserByDlr,querySbcyryList,getWfConfig } from "@/api/workload/sbgl";
  import { getLcjdxx,getShdqjd,getShjlList } from "@/api/workload/sbsh";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import { listKhxglZs, getKhxgl } from "@/api/workload/khxgl";
  export default {
    name: "Sbgl",
    dicts: ['workload_khxlx'],
    components: { Treeselect },
    data() {
      return {
        pickerOptions: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        //详情form
        xqform: {},
        // 非单个禁用
        single: true,
        // 选择参与人员是否显示
        showCyrDialogSf: false,
        //参与人员选择临时
        cyryTempList:[],
        //参与人员选择table数据
        cyryTableList:[],
        // 参与人员
        cyryList: [],
        // 考核项管理树选项
        khxglOptions: [],
        // 考核项管理树选项 参与人员表单
        khxglOptionsCyry: [],
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 唯一标识符
        UID: "yhid",
        totalCyry: 0,
        // 申报管理表格数据
        sbglList: [],
        lcjdList: [],
        lcshjlList: [],
        lcjdjd: 0,
        openLcXq: false,
        // 弹出层标题
        title: "",
        // 限制天数
        sbxzts: '1',
        sbxztsDqsj: null,
        // 是否显示弹出层
        open: false,
        // 是否显示弹出层 查看
        openXq: false,
        // 查询参数
        queryCyryParams: {
          pageNum: 1,
          pageSize: 10,
          queryCyrXm: null,
          sqid: ''
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          sqr: null,
          sqlx: null,
          ksxid: null,
          gzjs: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          sqlx: [{ required: true, message: "类型不能为空", trigger: "change" }],
          ksxid: [{ required: true, message: "考核项不能为空", trigger: "change" }],
          gzkssj: [{ required: true, message: "开始时间不能为空", trigger: "blur" }],
          gzjssj: [{ required: true, message: "结束时间不能为空", trigger: "blur" }],
          gzsc: [{ required: true, message: "工作时长不能为空", trigger: "blur" }],
          sqfz: [{ required: true, message: "总分值不能为空", trigger: "blur" }],
          gzjs: [{ required: true, message: "工作内容不能为空", trigger: "blur" }]
        }
      };
    },
    created() {
      this.getList();
      this.getTreeselect();
      this.getCyryTreeselect();
      this.getWfConfig();
    },
    methods: {

      /** 流程查看 */
      async lcck(row) {
        getLcjdxx(row.id).then(res => {
          this.lcjdList = res.data;
          getShdqjd(row.id).then(res1 => {
            let dqjdpx = res1.data.px
            if(dqjdpx === 99){
              this.lcjdjd = this.lcjdList.length
            }else{
              this.lcjdjd = dqjdpx
            }
            getShjlList(row.id).then(res2 => {
              this.lcshjlList = res2.data;
              this.openLcXq = true
            })
          })
        })
      },

      getWfConfig(){
        getWfConfig('WF_SB_TSXZ').then(res => {
          this.sbxzts = parseInt(res.data.sfky)
          if('0' === this.sbxzts || 0 === this.sbxzts){
            this.sbxztsDqsj = new Date(res.data.dqsj)
          }
        })
      },
      //申报表单中  判断开始结束时间大小
      checkedGzkssj(type){
        if(this.form.gzkssj !== null && this.form.gzjssj !== null && this.form.gzkssj !== '' && this.form.gzjssj !== ''){
          let date1 = new Date(this.form.gzkssj)
          let date2 = new Date(this.form.gzjssj)
          if(date1 > date2){
            this.$modal.msgWarning("时间选择错误，结束时间应大于开始时间！")
            this.form.gzjssj = ''
            this.form.gzsc = ''
            return
          }
          let gzscTemp = Math.ceil(parseInt(date2 - date1) / 1000 / 60 / 60)
          this.form.gzsc = gzscTemp
        }
      },
      //判断主列表的多选按钮是否禁用
      checkedSelectRow(row,index){
        if (row.shzt == '0') {
          return true //不可勾选
        } else {
          return false; //可勾选
        }
      },
      //表单中考核项改变时触发
      checkedKhxdyfs(value,state){
        if(value !== null && value !== '' && value !== '0' && value !== 0){
          getKhxgl(value).then(response => {
            let tempObj = response.data;
            this.form.sqfz = tempObj.ysfz
            this.form.tempSqfz = tempObj.ysfz
          });
        }
      },
      //表单中考核项改变时触发 修改是使用
      checkedKhxdyfsByUpd(value){
        getKhxgl(value).then(response => {
          let tempObj = response.data;
          this.form.sqfz = tempObj.ysfz
          this.form.tempSqfz = tempObj.ysfz
        });
      },
      //选择参与人员后确定
      saveCyry(){
        this.cyryTempList.forEach((row) => {
          let tempRow = JSON.parse(JSON.stringify(row))
          tempRow.yhdm = tempRow.yhid
          tempRow.xm = tempRow.ryxm
          tempRow.df = 0.01
          let tempList = this.cyryList.filter(t => t.yhid === row.yhid)
          if(tempList.length < 1){
            this.cyryList.push(tempRow)
          }
        })
        if(this.form.sqfz !== null && this.form.sqfz !== '' && this.cyryList.length > 0){
          let tempList = this.average(parseFloat(this.form.sqfz),this.cyryList.length,2)
          for(let i=0; i<tempList.length;i++){
            this.cyryList[i].df = tempList[i]
          }
        }
        this.showCyrDialogSf = false
      },
      //平均分配按钮
      avgCyryFz(){
        if(this.cyryList.length < 1){
          this.$modal.msgWarning("请先选择参与人！")
          return
        }
        if(this.form.sqfz !== null && this.form.sqfz !== ''){
          let tempList = this.average(parseFloat(this.form.sqfz),this.cyryList.length,2)
          for(let i=0; i<tempList.length;i++){
            this.cyryList[i].df = tempList[i]
          }
        }else{
          this.$modal.msgWarning("总分值不为空时执行！")
        }
      },
      //计算平均分 value 总分   amount 总数  point 小数点位数
      average(value,amount,point){
        if(!point){
          point = 2
        }
        let list = []
        let first = (Math.floor((value/amount)*Math.pow(10,point))/Math.pow(10,point)).toFixed(point)
        let last = (value - first*(amount - 1)).toFixed(point)
        for(let i = 0;i<amount-1;i++){
          list.push(first)
        }
        list.push(last)
        return list
      },

      getRowKeyYhid(row){
        return row.yhid
      },
      // 单行前的勾选状态切换
      selectChange(selectedRows, row) {
        // true为选中, 0或false为取消选中
        let selected = selectedRows.length && selectedRows.indexOf(row) !== -1;
        if (selected) {
          this.addItem(row);
        } else {
          this.removeItem(row);
        }
      },
      // 全选/取消全选
      selectAllChange(selectedRows) {
        let selectedMarkList = this.cyryTempList.map((item) => item[this.UID]);
        // 当前页选中行的标记列表
        let pageSelectedMarkList = Array.isArray(selectedRows)
          ? selectedRows.map((item) => item[this.UID])
          : [];

        this.cyryTableList.forEach((row) => {
          if (pageSelectedMarkList.includes(row[this.UID])) {
            if (!selectedMarkList.includes(row[this.UID])) {
              this.addItem(row);
            }
          } else if (selectedMarkList.includes(row[this.UID])) {
            this.removeItem(row);
          }
        });
      },
      // 更新勾选标记
      updateMark() {
        let selectedMarkList = this.cyryTempList.map((item) => item.yhid);
        this.cyryTableList.forEach((row) => {
          if (selectedMarkList.includes(row.yhid)) {
            // toggleRowSelection 需在$nextTick中使用！
            this.$nextTick(() => {
              this.$refs.cyryMultipleTable.toggleRowSelection(row,true);
            });
          }else{
            this.$nextTick(() => {
              this.$refs.cyryMultipleTable.toggleRowSelection(row,false);
            });
          }
        });
      },

      // 新增选中项
      addItem(item) {
        this.cyryTempList.push(item);
      },
      // 移除选中项
      removeItem(item) {
        for (let [index, itemTemp] of this.cyryTempList.entries()) {
          if (itemTemp[this.UID] === item[this.UID]) {
            this.removeItemByIndex(index);
            break;
          }
        }
      },
      // 根据下标移除选中项
      removeItemByIndex(index, item) {
        this.cyryTempList.splice(index, 1);
        // 若有item,则是点击标签上的关闭按钮，移除选中项
        if (item) {
          this.$nextTick(() => {
            this.$refs.cyryMultipleTable.toggleRowSelection(
              // 此处必须在 cyryTableList 中查找对应的数据，否则 toggleRowSelection 会失效
              this.cyryTableList.find((row) => {
                return row[this.UID] === item[this.UID];
              }),
              false
            );
          });
        }
      },

      //查询参与人员table list
      queryCyryTableList(){
        this.showCyrDialogSf = true
        this.queryCyryParams.pageNum = 1;
        this.getCyryList()
      },
      async getCyryList(){
        await queryCyryList(this.queryCyryParams).then(response => {
          this.cyryTableList = response.rows;
          // this.cyryTempList.forEach(row => {
          //   this.$refs.cyryMultipleTable.toggleRowSelection(row);
          // });
          this.totalCyry = response.total;
        });
        this.updateMark();
      },
      //弹出参与人dialog
      showCyrDialog(){
        this.showCyrDialogSf = true
        this.cyryTempList = []
        this.cyryList.forEach((item) => {
          let tempRow = JSON.parse(JSON.stringify(item))
          tempRow.yhid =
          this.cyryTempList.push(item)
        })
        this.queryCyryTableList()
      },

      /** 查询申报管理列表 */
      getList() {
        this.loading = true;
        listSbgl(this.queryParams).then(response => {
          this.sbglList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询参与人员表单考核项管理下拉树结构 */
      getCyryTreeselect() {
        let val = this.form.sqlx
        if(val !== null && val !== '' && val !== undefined && val !== 'undefined'){
          let data = {
            sjflx: val
          }
          listKhxglZs(data).then(response => {
            this.khxglOptionsCyry = [];
            const data = { id: 0, khxmc: '顶级节点', children: [] };
            data.children = this.handleTree(response.data, "id", "pid");
            this.khxglOptionsCyry.push(data);
          });
        }else{
          this.khxglOptionsCyry = [];
          const data = { id: 0, khxmc: '顶级节点', children: [] };
          this.khxglOptionsCyry.push(data);
          this.form.ksxid = 0
        }
      },
      /** 查询考核项管理下拉树结构 */
      getTreeselect() {
        let data = {
          sjflx: this.queryParams.sqlx
        }
        listKhxglZs(data).then(response => {
          this.khxglOptions = [];
          const data = { id: 0, khxmc: '顶级节点', children: [] };
          data.children = this.handleTree(response.data, "id", "pid");
          this.khxglOptions.push(data);
        });
      },
      /** 转换菜单数据结构  参与人员表单*/
      normalizerCyry(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.id + '',
          label: node.khxmc,
          children: node.children
        };
      },
      /** 转换菜单数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.id,
          label: node.khxmc,
          children: node.children
        };
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          verss: null,
          isdel: null,
          createtime: null,
          createuse: null,
          updatetime: null,
          updateuse: null,
          lcid: null,
          sqr: null,
          sqlx: null,
          ksxid: 0,
          gzkssj: null,
          gzjssj: null,
          gzsc: null,
          tempSqfz: '',
          cyryTList:[],
          gzjs: null,
          sqfz: null,
          shzt: null
        };
        this.resetForm("form");
      },
      // 表单重置
      resetXq() {
        this.xqform = {
          id: null,
          verss: null,
          isdel: null,
          sqlxmc: null,
          khxmc: null,
          lcid: null,
          sqr: null,
          sqlx: null,
          ksxid: 0,
          gzkssj: null,
          gzjssj: null,
          gzsc: null,
          tempSqfz: '',
          cyryTList:[],
          gzjs: null,
          sqfz: null,
          shzt: null
        };
        this.resetForm("xqform");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.cyryList = []
        getUserByDlr().then((res) => {
          let tempObj = res.data
          if(this.form.sqfz !== null && this.form.sqfz !== ''){
            tempObj.df = this.this.form.sqfz
          }else{
            tempObj.df = 0.01
          }
          this.cyryList.push(tempObj)
        })
        this.title = "添加申报";
      },
      /** 查看 */
      async handleView(row) {
        this.reset();
        const id = row.id
        this.queryCyryParams.sqid = id
        await getSbgl(id).then(response => {
          this.xqform = response.data;
          querySbcyryList({id:id}).then(res => {
            res.data.forEach(t => {
              let tempRow = JSON.parse(JSON.stringify(t))
              tempRow.df = parseFloat(t.df)
              this.cyryList.push(tempRow)
            })
            this.cyryList = res.data
          });
          this.openXq = true;
        });
      },
      /** 修改按钮操作 */
      async handleUpdate(row) {
        this.reset();
        const id = row.id
        this.queryCyryParams.sqid = id
        await getSbgl(id).then(response => {
          this.form = response.data;
          this.getCyryTreeselect();
          this.checkedKhxdyfsByUpd(this.form.ksxid);
          querySbcyryList({id:id}).then(res => {
            res.data.forEach(t => {
              let tempRow = JSON.parse(JSON.stringify(t))
              tempRow.df = parseFloat(t.df)
              this.cyryList.push(tempRow)
            })
            this.cyryList = res.data
          });
          this.open = true;
          this.title = "修改申报";
        });
      },
      /** 提交按钮 */
      async submitForm(sftj) {
        let self = this
        this.$refs["form"].validate(valid => {
          if (valid) {
            //判断是否可申报 this.sbxzts  form.gzkssj
            if('0' === this.sbxzts || 0 === this.sbxzts){
              let date1 = new Date(this.form.gzkssj)
              let date2 = this.sbxztsDqsj
              if(date1 < date2){
                this.$modal.msgWarning("当前工作已过系统设置天数，不可申报上月的工作量！")
                throw SyntaxError();
              }
            }
            let tempSumFs = 0.00 //实际分配总分数
            let tempYsfz = 0.00 //考核项预设分值
            let tempZfz = 0.00 // 输入的总分值
            // debugger
            if(this.form.ksxid !== null && this.form.ksxid !== '' && this.form.ksxid !== '0' && this.form.ksxid !== 0){// 判断分数
              getKhxgl(this.form.ksxid).then(response => {
                let tempObj = response.data;
                tempYsfz = parseFloat(tempObj.ysfz)
                tempZfz = parseFloat(this.form.sqfz)
                 if(tempZfz > tempYsfz){
                   this.$modal.msgWarning('总分值不能大于考核项预设分值!');
                   throw SyntaxError();
                   return
                 }else{
                   if(this.cyryList.length < 1){//判断参与人
                     this.$modal.msgWarning('参与人不能为空，请先选择参与人!');
                     throw SyntaxError();
                     return
                   }else{
                     let tempSumFs = 0.00
                     this.cyryList.forEach(t => {
                       tempSumFs += parseFloat(t.df)
                     })
                     if(tempSumFs !== parseFloat(this.form.sqfz)){
                       this.$modal.msgWarning('分配分数:' + tempSumFs + '和总分值:' + parseFloat(this.form.sqfz) + '不相同，请修改!');
                       throw SyntaxError();
                       return
                     }else{
                       if(tempZfz < tempYsfz){
                         this.$modal.confirm('当前申报的分配总分（' + tempZfz + '）小于预设分值（' + tempYsfz + '），是否继续操作？').then(function() {
                           self.updDataSer(sftj)
                         }).catch(() => {});
                       }else{
                         self.updDataSer(sftj)
                       }
                     }
                   }
                 }
              });
            }else{
              this.$modal.msgWarning('请选择正确的考核项!');
              throw SyntaxError();
              return
            }
          }
        });
      },
      //调用保存修改接口
      updDataSer(sftj){
        this.form.cyryTList = this.cyryList;
        this.form.shzt = sftj
        if (this.form.id != null) {
          updateSbgl(this.form).then(response => {
            this.$modal.msgSuccess(response.data.msg);
            this.open = false;
            this.getList();
          });
        } else {
          updateSbgl(this.form).then(response => {
            this.$modal.msgSuccess(response.data.msg);
            this.open = false;
            this.getList();
          });
        }
      },

      /** 删除参与人员按钮操作 */
      handleDeleteCyry(row,index) {
        this.cyryList.splice(index, 1);
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除当前数据项？').then(function() {
          return delSbgl(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('workload/sbgl/export', {
          ...this.queryParams
        }, `sbgl_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
<style>
  .el-form-item-margin{
    margin-bottom: 15px;
    margin-right: 5px;
  }
  .el-dialog__header {
    padding: 12px;
    padding-bottom: 5px;
  }
  .el-dialog__body {
    padding: 10px 20px;
  }
  .el-dialog__title {
    font-size: 17px;
  }
  .el-divider--horizontal {
    margin-bottom: 10px;
    margin-top: 10px;
  }
</style>
