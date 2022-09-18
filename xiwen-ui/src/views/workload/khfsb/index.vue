<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68p0">
      <el-form-item label="姓名" prop="yhid">
        <el-input style="width: 180px"
          v-model="queryParams.yhxm"
          placeholder="姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年度" prop="nd">
        <el-select v-model="queryParams.nd" placeholder="请选择年度" style="width: 180px">
          <el-option
            v-for="nd in ndList"
            :key="nd.nd"
            :label="nd.nd"
            :value="nd.nd"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="分数" prop="yhid">
        <el-input style="width: 85px"
                  v-model="queryParams.minfs"
                  clearable
                  @keyup.enter.native="handleQuery"
        />
        至
        <el-input style="width: 85px"
                  v-model="queryParams.maxfs"
                  clearable
                  @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="khfsbList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="姓名" align="center" prop="yhxm" />
      <el-table-column label="部门" align="center" prop="bmmc" />
      <el-table-column label="年度" align="center" prop="nd" />
      <el-table-column label="总分数" align="center" prop="zfs" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workload:khfsb:edit']"
          >管理</el-button>
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

    <!-- 添加或修改人员考核分数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @close="handleQuery">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-divider content-position="center">{{form.yhxm}} 考核分数详情信息</el-divider>-->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddKhfsxqb">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteKhfsxqb">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="khfsxqbList" height="400px" :row-class-name="rowKhfsxqbIndex" @selection-change="handleKhfsxqbSelectionChange" ref="khfsxqb">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="创建时间" prop="createtimeg" width="100" />
          <el-table-column label="创建人" prop="createUserName" width="90" />
          <el-table-column label="修改时间" prop="updatetimeg" width="100" />
          <el-table-column label="修改人" prop="updateUserName" width="90" />
          <el-table-column label="分数类型" prop="fslxmc" width="90" />
          <el-table-column label="分数" prop="fs" width="80" />
          <el-table-column label="备注" prop="bz" width="300" :show-overflow-tooltip="true" >
          </el-table-column>
        </el-table>
      </el-form>
    </el-dialog>

    <!-- 添加或修改人员考核分数 详情 对话框 -->
    <el-dialog title="添加" :visible.sync="openXq" width="500px;" append-to-body @close="handleUpdateClose">
      <el-form ref="form" :model="xQform" label-width="80px" style="border: 1px solid #bcbcbc;padding: 10px;">
        <el-form-item label="姓名" >
          <el-input v-model="xQform.yhxm" style="width: 200px" disabled></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="xQform.fslx" placeholder="请选择类型" style="width: 200px">
            <el-option  key="1" label="加分" value="1" />
            <el-option  key="2" label="减分" value="2" />
<!--            <el-option  key="3" label="初始化" value="3" />-->
          </el-select>
        </el-form-item>
        <el-form-item label="分数">
          <el-input-number v-model="xQform.fs" :precision="2" :min="0.00" style="width: 200px"></el-input-number>
        </el-form-item>
        <el-form-item label="备注">
          <textarea class="el-textarea__inner" v-model="xQform.bz" style="height: 140px"></textarea>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="primary" @click="submitFormXq">提 交</el-button>
        <el-button @click="cancelXq">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listKhfsb, getKhfsb, delKhfsxqb,getKhfsbXq,delKhfsb, addKhfsb, updateKhfsb,updateKhfsbXq } from "@/api/workload/khfsb";
  import { getNdList} from "@/api/workload/ndgl";

  export default {
    name: "Khfsb",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 子表选中数据
        checkedKhfsxqb: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 人员考核分数表格数据
        khfsbList: [],
        // 人员考核分数详情表格数据
        khfsxqbList: [],
        // 弹出层标题
        title: "",
        openXq: false,
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          yhxm: null,
          nd: null,
          maxfs: '',
          minfs: '',
          zfs: null
        },
        ndList:[],
        xqids:[],
        dqnd:'',
        tempRowId:'',
        tempRowYhid:'',
        // 表单参数
        form: {},
        // 详情表单参数
        xQform: {},
        // 表单校验
        rules: {
        }
      };
    },
    created() {
      this.getNdList();
    },
    methods: {
      //查询年度
      getNdList(){
        getNdList({}).then(res => {
          this.ndList = res.data
          let tempList = this.ndList.filter(nd => nd.sfdqnd === '1')
          this.dqnd = tempList[0].nd
          this.queryParams.nd = this.dqnd
          this.getList();
        })
      },
      /** 查询人员考核分数列表 */
      getList() {
        this.loading = true;
        listKhfsb(this.queryParams).then(response => {
          this.khfsbList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 取消按钮
      cancelXq() {
        this.openXq = false;
        this.xQform.fslx = '1';
        this.xQform.fs = '0';
        this.xQform.bz = '';
        this.xQform.rykhfsbid = '';
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
          yhid: null,
          nd: null,
          zfs: null
        };
        this.khfsxqbList = [];
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
        this.title = "添加人员考核分数";
      },
      /** 管理按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id
        this.tempRowId = id
        this.tempRowYhid = row.yhid
        getKhfsbXq(id).then(response => {
          this.form = row;
          this.khfsxqbList = response.data;
          this.open = true;
          this.title = "管理 "+ row.yhxm +" 分数";
        });
      },
      /** 管理按钮操作关闭执行 */
      handleUpdateClose() {
        getKhfsbXq(this.tempRowId).then(response => {
          this.khfsxqbList = response.data;
        });
      },
      /** 详情提交按钮 */
      submitFormXq() {
        if(this.xQform.fslx === null || this.xQform.fslx === ''){
          this.$modal.msgWarning("分数类型不能为空！")
          return
        }
        if(this.xQform.fs === null || this.xQform.fs === '' || this.xQform.fs === '0' || this.xQform.fs === 0){
          this.$modal.msgWarning("分数不能为空并且不可为0！")
          return
        }
        if(this.xQform.bz === null || this.xQform.bz === '' || this.xQform.bz.length < 10){
          this.$modal.msgWarning("备注不能为空，并且字数不能少于10个字！")
          return
        }
        this.xQform.rykhfsbid = this.tempRowId
        this.xQform.yhid = this.tempRowYhid
        this.xQform.nd = this.queryParams.nd
        this.xQform.id = ''
        updateKhfsbXq(this.xQform).then(response1 => {
          this.form.id= response1.data.rykhfsbid
          this.tempRowId=response1.data.rykhfsbid
          this.$modal.msgSuccess("操作成功");
          this.openXq = false;
          getKhfsbXq(this.tempRowId).then(response => {
            this.khfsxqbList = response.data;
          });
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.khfsxqbList = this.khfsxqbList;
            if (this.form.id != null) {
              updateKhfsb(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addKhfsb(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除人员考核分数编号为"' + ids + '"的数据项？').then(function() {
          return delKhfsb(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 人员考核分数详情序号 */
      rowKhfsxqbIndex({ row, rowIndex }) {
        row.index = rowIndex + 1;
      },
      /** 人员考核分数详情添加按钮操作 */
      handleAddKhfsxqb() {
        this.xQform.bz = ''
        this.xQform.ywid = ''
        this.xQform.fs = 0
        this.xQform.fslx = '1'
        this.xQform.yhxm = this.form.yhxm
        this.xQform.rykhfsbid = this.form.id
        this.openXq = true
      },
      /** 人员考核分数详情删除按钮操作 */
      handleDeleteKhfsxqb() {
        const ids = this.xqids;
        this.$modal.confirm('是否确认选中的数据项？').then(function() {
          delKhfsxqb(ids).then(response => {
          });
        }).then(() => {
          this.handleUpdateClose();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 复选框选中数据 */
      handleKhfsxqbSelectionChange(selection) {
        this.xqids = selection.map(item => item.id)
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('workload/khfsb/export', {
          ...this.queryParams
        }, `khfsb_${new Date().getTime()}.xlsx`)
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
</style>
