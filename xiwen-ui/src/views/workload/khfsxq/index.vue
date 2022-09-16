<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考核分数表ID wf_rykhfsb.id" prop="rykhfsbid">
        <el-input
          v-model="queryParams.rykhfsbid"
          placeholder="请输入考核分数表ID wf_rykhfsb.id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分数类型  1加2减3初始化" prop="fslx">
        <el-input
          v-model="queryParams.fslx"
          placeholder="请输入分数类型  1加2减3初始化"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分数" prop="fs">
        <el-input
          v-model="queryParams.fs"
          placeholder="请输入分数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务ID" prop="ywid">
        <el-input
          v-model="queryParams.ywid"
          placeholder="请输入业务ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['workload:Khfsxqb:add']"
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
          v-hasPermi="['workload:Khfsxqb:edit']"
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
          v-hasPermi="['workload:Khfsxqb:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workload:Khfsxqb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="KhfsxqbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="乐观锁" align="center" prop="verss" />
      <el-table-column label="是否删除" align="center" prop="isdel" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createuse" />
      <el-table-column label="修改时间" align="center" prop="updatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="updateuse" />
      <el-table-column label="考核分数表ID wf_rykhfsb.id" align="center" prop="rykhfsbid" />
      <el-table-column label="分数类型  1加2减3初始化" align="center" prop="fslx" />
      <el-table-column label="分数" align="center" prop="fs" />
      <el-table-column label="业务ID" align="center" prop="ywid" />
      <el-table-column label="备注" align="center" prop="bz" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workload:Khfsxqb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workload:Khfsxqb:remove']"
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

    <!-- 添加或修改人员考核分数详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="乐观锁" prop="verss">
          <el-input v-model="form.verss" placeholder="请输入乐观锁" />
        </el-form-item>
        <el-form-item label="创建人" prop="createuse">
          <el-input v-model="form.createuse" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="修改人" prop="updateuse">
          <el-input v-model="form.updateuse" placeholder="请输入修改人" />
        </el-form-item>
        <el-form-item label="考核分数表ID wf_rykhfsb.id" prop="rykhfsbid">
          <el-input v-model="form.rykhfsbid" placeholder="请输入考核分数表ID wf_rykhfsb.id" />
        </el-form-item>
        <el-form-item label="分数类型  1加2减3初始化" prop="fslx">
          <el-input v-model="form.fslx" placeholder="请输入分数类型  1加2减3初始化" />
        </el-form-item>
        <el-form-item label="分数" prop="fs">
          <el-input v-model="form.fs" placeholder="请输入分数" />
        </el-form-item>
        <el-form-item label="业务ID" prop="ywid">
          <el-input v-model="form.ywid" placeholder="请输入业务ID" />
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" type="textarea" placeholder="请输入内容" />
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
  import { listKhfsxqb, getKhfsxqb, delKhfsxqb, addKhfsxqb, updateKhfsxqb } from "@/api/workload/Khfsxqb";

  export default {
    name: "Khfsxqb",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 人员考核分数详情表格数据
        KhfsxqbList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          rykhfsbid: null,
          fslx: null,
          fs: null,
          ywid: null,
          bz: null
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
      /** 查询人员考核分数详情列表 */
      getList() {
        this.loading = true;
        listKhfsxqb(this.queryParams).then(response => {
          this.KhfsxqbList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
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
          rykhfsbid: null,
          fslx: null,
          fs: null,
          ywid: null,
          bz: null
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
        this.title = "添加人员考核分数详情";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getKhfsxqb(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改人员考核分数详情";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateKhfsxqb(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addKhfsxqb(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除人员考核分数详情编号为"' + ids + '"的数据项？').then(function() {
          return delKhfsxqb(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('workload/Khfsxqb/export', {
          ...this.queryParams
        }, `Khfsxqb_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
