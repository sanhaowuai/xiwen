<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="性别" prop="lx">
        <el-select v-model="queryParams.lx" placeholder="请选择类型" clearable>
          <el-option key="0" label="男" value="0" />
          <el-option key="1" label="女" value="1" />
        </el-select>
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
          v-hasPermi="['workload:jqfsz:add']"
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
          v-hasPermi="['workload:jqfsz:edit']"
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
          v-hasPermi="['workload:jqfsz:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workload:jqfsz:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jqfszList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="是否删除" align="center" prop="isdel">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isdel ? scope.row.isdel.split(',') : []"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="性别" align="center" prop="lx">
        <template slot-scope="scope">
          <template v-if="scope.row.lx === '0'">
            男
          </template>
          <template v-if="scope.row.lx === '1'">
            女
          </template>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="nl" />
      <el-table-column label="加权倍数" align="center" prop="jqbs" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workload:jqfsz:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workload:jqfsz:remove']"
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

    <!-- 添加或修改加权分设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="性别">
          <el-radio-group v-model="form.lx">
            <el-radio key="0" label="0" >男</el-radio>
            <el-radio key="1" label="1" >女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="nl">
          <el-input-number v-model="form.nl" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="加权倍数" prop="jqbs">
          <el-input-number v-model="form.jqbs" :min="1" placeholder="请输入加权倍数" />
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
  import { listJqfsz, getJqfsz, delJqfsz, addJqfsz, updateJqfsz } from "@/api/workload/jqfsz";

  export default {
    name: "Jqfsz",
    dicts: ['sys_yes_no', 'sys_user_sex'],
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
        // 加权分设置表格数据
        jqfszList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          isdel: null,
          lx: null,
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
      /** 查询加权分设置列表 */
      getList() {
        this.loading = true;
        listJqfsz(this.queryParams).then(response => {
          this.jqfszList = response.rows;
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
          isdel: [],
          createtime: null,
          createuse: null,
          updatetime: null,
          updateuse: null,
          lx: "1",
          nl: 0,
          jqbs: 0
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
        this.title = "添加加权分";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getJqfsz(id).then(response => {
          this.form = response.data;
          this.form.isdel = this.form.isdel.split(",");
          this.open = true;
          this.title = "修改加权分";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.isdel = this.form.isdel.join(",");
            if (this.form.id != null) {
              updateJqfsz(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addJqfsz(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除？').then(function() {
          return delJqfsz(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('workload/jqfsz/export', {
          ...this.queryParams
        }, `jqfsz_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
