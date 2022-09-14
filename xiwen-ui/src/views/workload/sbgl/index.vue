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
        <el-select v-model="queryParams.sqlx" style="width: 150px"
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['workload:sbgl:edit']"
        >修改</el-button>
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
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workload:sbgl:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sbglList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申报时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申报流程" align="center" prop="lcid" />
      <el-table-column label="申报人" align="center" prop="sqr" />
      <el-table-column label="类型" align="center" prop="sqlx">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.workload_khxlx" :value="scope.row.sqlx"/>
        </template>
      </el-table-column>
      <el-table-column label="考核项" align="center" prop="ksxid" />
      <el-table-column label="工作时长" align="center" prop="gzsc" />
      <el-table-column label="工作简述" align="center" prop="gzjs" />
      <el-table-column label="申请分值" align="center" prop="sqfz" />
      <el-table-column label="审核状态" align="center" prop="shzt" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workload:sbgl:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workload:sbgl:remove']"
          >删除</el-button>
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
          <el-select v-model="form.sqlx" placeholder="请选择考核类型" style="width: 200px">
            <el-option
              v-for="dict in dict.type.workload_khxlx"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="考核项" prop="ksxid" class="el-form-item-margin">
          <el-input v-model="form.ksxid" placeholder="请选择考核项" style="width: 200px"/>
        </el-form-item>
        <el-form-item label="开始时间" prop="gzkssj" class="el-form-item-margin">
          <el-date-picker
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
          <el-date-picker
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
          <el-input v-model="form.gzsc" placeholder="请输入工作时长(小时)" style="width: 200px"/>
        </el-form-item>
        <el-form-item label="总分值" prop="sqfz" class="el-form-item-margin">
          <el-input v-model="form.sqfz" placeholder="请输入申请分值" style="width: 200px"/>
        </el-form-item>
        <el-form-item label="参与人" prop="sqfz" class="el-form-item-margin">
          <slot>
            <el-button type="primary" plain icon="el-icon-plus" style="padding: 6px 9px;" title="添加参与人"
                       @click="showCyrDialog" size="mini" ></el-button>
          </slot>
          <table border="1" style="border-collapse: collapse;text-align: center;">
            <tr>
              <td style="width:80px">姓名</td>
              <td>得分</td>
              <td>工作分配</td>
              <td></td>
            </tr>
            <template v-for="item in cyryList">
              <tr>
                <td>{{item.yhxm}}</td>
                <td><el-input-number size="mini" v-model="item.df" style="width: 100px;" :min="0.01"></el-input-number></td>
                <td><el-input v-model="item.gznr" placeholder="请输入工作内容" style="width: 295px"/></td>
                <td><el-button type="danger" plain icon="el-icon-delete" style="padding: 6px 9px;"
                               @click="handleDelete" size="mini" ></el-button></td>
              </tr>
            </template>
          </table>
        </el-form-item>
        <el-form-item label="工作内容" prop="gzjs" class="el-form-item-margin">
          <el-input v-model="form.gzjs" type="textarea" placeholder="请输入工作内容" style="width: 520px" :autosize="{ minRows: 4, maxRows: 6}"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listSbgl, getSbgl, delSbgl, addSbgl, updateSbgl, queryCyryList } from "@/api/workload/sbgl";
  import { listKhxglZs } from "@/api/workload/khxgl";
  export default {
    name: "Sbgl",
    dicts: ['workload_khxlx'],
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
        // 非单个禁用
        single: true,
        // 选择参与人员是否显示
        showCyrDialogSf: false,
        //参与人员选择临时
        cyryTempList:[],
        //参与人员选择table数据
        cyryTableList:[],
        // 参与人员
        cyryList: [{
          yhid:'2',
          yhxm:'张三风',
          bmmc:'A部门',
          nl:'45',
          xbmc:'男',
          df:12,
          gznr:'23sdff'
        },
          {
            yhid:'3',
            yhxm:'李四',
            bmmc:'B部门',
            nl:'36',
            xbmc:'男',
            df:2,
            gznr:'23222'
          }],
        // 考核项管理树选项
        khxglOptions: [],
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
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryCyryParams: {
          pageNum: 1,
          pageSize: 10,
          queryCyrXm: null
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
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      //选择参与人员后确定
      saveCyry(){
        this.cyryList = []
        this.cyryTempList.forEach((row) => {
          let tempRow = JSON.parse(JSON.stringify(row))
          tempRow.yhdm = tempRow.yhid
          tempRow.xm = tempRow.ryxm
          tempRow.df = 0.01
          this.cyryList.push(tempRow)
        })
      },
      //计算平均分
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
          ksxid: null,
          gzkssj: null,
          gzjssj: null,
          gzsc: null,
          gzjs: null,
          sqfz: null,
          shzt: null
        };
        this.resetForm("form");
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
        this.title = "添加申报管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getSbgl(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改申报管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateSbgl(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addSbgl(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除申报管理编号为"' + ids + '"的数据项？').then(function() {
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
    margin-bottom: 10px;
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
</style>
