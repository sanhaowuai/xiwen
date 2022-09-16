<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="yhid">
        <el-input
          v-model="queryParams.yhid"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总分数" prop="zfs">
        <el-input
          v-model="queryParams.zfs"
          placeholder="请输入总分数"
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
          v-hasPermi="['workload:khfsb:add']"
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
          v-hasPermi="['workload:khfsb:edit']"
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
          v-hasPermi="['workload:khfsb:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workload:khfsb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="khfsbList" @selection-change="handleSelectionChange">
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
      <el-table-column label="用户ID" align="center" prop="yhid" />
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
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workload:khfsb:remove']"
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

    <!-- 添加或修改人员考核分数对话框 -->
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
        <el-form-item label="用户ID" prop="yhid">
          <el-input v-model="form.yhid" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="总分数" prop="zfs">
          <el-input v-model="form.zfs" placeholder="请输入总分数" />
        </el-form-item>
        <el-divider content-position="center">人员考核分数详情信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddKhfsxqb">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteKhfsxqb">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="khfsxqbList" :row-class-name="rowKhfsxqbIndex" @selection-change="handleKhfsxqbSelectionChange" ref="khfsxqb">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="乐观锁" prop="verss" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.verss" placeholder="请输入乐观锁" />
            </template>
          </el-table-column>
          <el-table-column label="创建时间" prop="createtime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.createtime" type="date" value-format="yyyy-MM-dd" placeholder="请选择创建时间" />
            </template>
          </el-table-column>
          <el-table-column label="创建人" prop="createuse" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.createuse" placeholder="请输入创建人" />
            </template>
          </el-table-column>
          <el-table-column label="修改时间" prop="updatetime" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.updatetime" type="date" value-format="yyyy-MM-dd" placeholder="请选择修改时间" />
            </template>
          </el-table-column>
          <el-table-column label="修改人" prop="updateuse" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.updateuse" placeholder="请输入修改人" />
            </template>
          </el-table-column>
          <el-table-column label="分数类型  1加2减3初始化" prop="fslx" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.fslx" placeholder="请输入分数类型  1加2减3初始化" />
            </template>
          </el-table-column>
          <el-table-column label="分数" prop="fs" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.fs" placeholder="请输入分数" />
            </template>
          </el-table-column>
          <el-table-column label="业务ID" prop="ywid" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.ywid" placeholder="请输入业务ID" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listKhfsb, getKhfsb, delKhfsb, addKhfsb, updateKhfsb } from "@/api/workload/khfsb";

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
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          yhid: null,
          nd: null,
          zfs: null
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
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getKhfsb(id).then(response => {
          this.form = response.data;
          this.khfsxqbList = response.data.khfsxqbList;
          this.open = true;
          this.title = "修改人员考核分数";
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
        let obj = {};
        obj.verss = "";
        obj.isdel = "";
        obj.createtime = "";
        obj.createuse = "";
        obj.updatetime = "";
        obj.updateuse = "";
        obj.fslx = "";
        obj.fs = "";
        obj.ywid = "";
        obj.bz = "";
        this.khfsxqbList.push(obj);
      },
      /** 人员考核分数详情删除按钮操作 */
      handleDeleteKhfsxqb() {
        if (this.checkedKhfsxqb.length == 0) {
          this.$modal.msgError("请先选择要删除的人员考核分数详情数据");
        } else {
          const khfsxqbList = this.khfsxqbList;
          const checkedKhfsxqb = this.checkedKhfsxqb;
          this.khfsxqbList = khfsxqbList.filter(function(item) {
            return checkedKhfsxqb.indexOf(item.index) == -1
          });
        }
      },
      /** 复选框选中数据 */
      handleKhfsxqbSelectionChange(selection) {
        this.checkedKhfsxqb = selection.map(item => item.index)
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
