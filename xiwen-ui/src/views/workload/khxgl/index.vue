<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="60px">
      <el-form-item label="类型" prop="sjflx">
        <el-select v-model="queryParams.sjflx" placeholder="请选择考核项类型" clearable>
          <el-option
            v-for="dict in dict.type.workload_khxlx"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="khxmc">
        <el-input
          v-model="queryParams.khxmc"
          placeholder="考核项名称"
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
          v-hasPermi="['workload:khxgl:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="khxglList"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="名称" align="left" prop="khxmc"/>
      <el-table-column label="类型" align="center" prop="sjflx" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.workload_khxlx" :value="scope.row.sjflx"/>
        </template>
      </el-table-column>
      <el-table-column label="加分/减分" align="center" prop="fzlx" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.workload_fzlx" :value="scope.row.fzlx"/>
        </template>
      </el-table-column>
      <el-table-column label="分值" align="center" prop="ysfz" />
      <el-table-column label="申报流程" align="center" prop="lcid" >
        <template slot-scope="scope">
          <template v-for="item in lcList">
            <template v-if="item.id == scope.row.lcid">
              {{item.lcmc}}
            </template>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workload:khxgl:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['workload:khxgl:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workload:khxgl:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改考核项管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px"  append-to-body>
      <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="100px">
        <el-form-item label="父级考核项" prop="pid">
          <treeselect style="width: 240px" v-model="form.pid" :options="khxglOptions" :normalizer="normalizer" placeholder="请选择父考核项id" />
        </el-form-item>
        <el-form-item label="考核项类型" prop="sjflx">
          <el-select v-model="form.sjflx" placeholder="请选择考核项类型" style="width: 240px">
            <el-option
              v-for="dict in dict.type.workload_khxlx"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核项名称" prop="khxmc">
          <el-input style="width: 590px" v-model="form.khxmc" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="分值类型" prop="fzlx">
          <el-select v-model="form.fzlx" placeholder="请选择分值类型" style="width: 240px">
            <el-option
              v-for="dict in dict.type.workload_fzlx"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预设分值" prop="ysfz">
          <el-input type="number" v-model="form.ysfz" placeholder="请输入预设分值" style="width: 240px"/>
        </el-form-item>
        <el-form-item label="考核细则说明" prop="ksxz">
          <el-input v-model="form.ksxz" type="textarea" placeholder="请输入内容" style="width: 590px"/>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="form.bz" type="textarea" placeholder="请输入内容" style="width: 590px"/>
        </el-form-item>
        <el-form-item label="申报流程" prop="lcid">
          <el-select v-model="form.lcid" placeholder="请选择申报流程" style="width: 590px">
            <el-option
              v-for="dict in lcList"
              :key="dict.id"
              :label="dict.lcmc"
              :value="dict.id"
            ></el-option>
          </el-select>
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
  import { listKhxgl, getKhxgl, delKhxgl, addKhxgl, updateKhxgl } from "@/api/workload/khxgl";
  import { listLcglBySb } from "@/api/workload/lcgl";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Khxgl",
    dicts: ['workload_khxlx','workload_fzlx'],
    components: {
      Treeselect
    },
    data() {
      return {
        // 遮罩层
        loading: true,
        // 显示搜索条件
        showSearch: true,
        // 考核项管理表格数据
        khxglList: [],
        // 考核项管理树选项
        khxglOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否展开，默认全部展开
        isExpandAll: true,
        // 重新渲染表格状态
        refreshTable: true,
        // 申报流程list
        lcList: [],
        // 查询参数
        queryParams: {
          sjflx: null,
          khxmc: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          khxmc: [{ required: true, message: "考核项不能为空", trigger: "blur" }],
          ysfz: [{ required: true, message: "预设分值不能为空", trigger: "blur" }],
          sjflx: [{ required: true, message: "类型不能为空", trigger: "change" }],
          fzlx: [{ required: true, message: "分值不能为空", trigger: "blur" }],
          lcid: [{ required: true, message: "申报流程不能为空", trigger: "change" }]
        }
      };
    },
    created() {
      this.getList();
      this.getLcList();
    },
    methods: {
      /** 查询申报流程list */
      getLcList() {
        listLcglBySb().then(response => {
          this.lcList = response.data;
        });
      },
      /** 查询考核项管理列表 */
      getList() {
        this.loading = true;
        listKhxgl(this.queryParams).then(response => {
          this.khxglList = this.handleTree(response.data, "id", "pid");
          this.loading = false;
        });
      },
      /** 转换考核项管理数据结构 */
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
      /** 查询考核项管理下拉树结构 */
      getTreeselect() {
        listKhxgl().then(response => {
          this.khxglOptions = [];
          const data = { id: 0, khxmc: '顶级节点', children: [] };
          data.children = this.handleTree(response.data, "id", "pid");
          this.khxglOptions.push(data);
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
          kyf: null,
          px: null,
          sjflx: null,
          fzlx: null,
          ysfz: null,
          khxmc: null,
          lcid: null,
          pid: null,
          ksxz: null,
          bz: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 新增按钮操作 */
      handleAdd(row) {
        this.reset();
        this.getTreeselect();
        if (row != null && row.id) {
          this.form.pid = row.id;
        } else {
          this.form.pid = 0;
        }
        this.open = true;
        this.title = "添加考核项管理";
      },
      /** 展开/折叠操作 */
      toggleExpandAll() {
        this.refreshTable = false;
        this.isExpandAll = !this.isExpandAll;
        this.$nextTick(() => {
          this.refreshTable = true;
        });
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.getTreeselect();
        if (row != null) {
          this.form.pid = row.id;
        }
        getKhxgl(row.id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改考核项管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateKhxgl(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addKhxgl(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除考核项管理编号为"' + row.khxmc + '"的数据项？').then(function() {
          return delKhxgl(row.id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      }
    }
  };
</script>
