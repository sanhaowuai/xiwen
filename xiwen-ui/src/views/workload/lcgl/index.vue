<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="流程名称" prop="lcmc">
        <el-input
          v-model="queryParams.lcmc"
          placeholder="请输入流程名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:role:add']"
        >新增</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="lcList" @selection-change="handleSelectionChange">
      <el-table-column label="流程名称" prop="lcmc" width="170" show-overflow-tooltip/>
      <el-table-column label="是否可驳回" prop="sfkbh" :show-overflow-tooltip="true" width="120" >
        <template slot-scope="scope">
          <template v-if="scope.row.sfkbh === 1">是</template>
          <template v-else>否</template>
        </template>
      </el-table-column>
      <el-table-column label="驳回方式" prop="bhlx" :show-overflow-tooltip="true" width="150" >
        <template slot-scope="scope">
          <template v-if="scope.row.bhlx === '1'">驳回至申请人</template>
          <template v-if="scope.row.bhlx === '2'">驳回至上一节点</template>
        </template>
      </el-table-column>
      <el-table-column label="流程说明" prop="lcsm" show-overflow-tooltip/>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.kyf"
            active-value="1"
            inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="140" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.roleId !== 1">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:role:edit']"
          >修改</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:role:edit']">
            <span class="el-dropdown-link">
              <i class="el-icon-d-arrow-right el-icon--right"></i>更多
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleDelete" icon="el-icon-delete">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- 添加或修改流程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" class="demo-form-inline">
        <el-form-item label="流程名称" prop="lcmc">
          <el-input v-model="form.lcmc" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.kyf">
            <el-radio
              v-for="dict in sftyList"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
          <label style="margin-left: 60px;">是否可驳回</label>
          <el-radio-group v-model="form.sfkbh" style="margin-left: 10px;" @change="sfbhChange">
            <el-radio
              v-for="dict in sfList"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
          <el-select v-show="bhxxDisable" v-model="form.bhlx" style="margin-left: 20px;">
            <el-option
              v-for="item in bhScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申报节点">
          <el-input  placeholder="所有人员" disabled style="width: 50%"/>
          <el-button type="primary" plain icon="el-icon-plus"  @click="addLcjd" style="margin-left: 20px;"
          >新增节点</el-button>
        </el-form-item>
        <template v-for="(item,index) in form.jdList">
          <template v-if="item.shjs != -1">
            <el-form-item :label="'流程节点' + (index + 1)">
              <el-select style="width: 50%" v-model="item.shjs">
                <el-option
                  v-for="item in selectRoleList"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                ></el-option>
              </el-select>
              <el-button type="danger" plain icon="el-icon-minus"  @click="delLcjd(index)"
                         style="margin-left: 20px;"
              >删除节点</el-button>
            </el-form-item>
          </template>
        </template>
        <el-form-item  label="流程说明" >
          <el-input type="textarea" v-model="form.lcsm" placeholder="请输入流程说明" />
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
  import { updateLcxx, addLcxx, listLcgl,deleteLcxx,changeLcStatus,getLcxxById } from "@/api/workload/lcgl";
  import { listRole, getRole, delRole, addRole, updateRole, dataScope, changeRoleStatus, optionselect } from "@/api/system/role";
  import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";
  import { treeselect as deptTreeselect, roleDeptTreeselect } from "@/api/system/dept";

  export default {
    name: "Role",
    dicts: ['sys_normal_disable','sys_yes_no'],
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
        // 流程表格数据
        lcList: [],
        // 流程节点下拉框角色表格数据
        selectRoleList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否显示弹出层（数据权限）
        openDataScope: false,
        menuExpand: false,
        bhxxDisable: false,
        menuNodeAll: false,
        deptExpand: true,
        deptNodeAll: false,
        // 日期范围
        dateRange: [],
        sftyList: [{value: "1",label: "正常"},{value: "0",label: "停用"}],
        sfList: [{value: "1",label: "是"},{value: "0",label: "否"}],
        // 数据范围选项
        dataScopeOptions: [
          {
            value: "1",
            label: "全部数据权限"
          },
          {
            value: "2",
            label: "自定数据权限"
          },
          {
            value: "3",
            label: "本部门数据权限"
          },
          {
            value: "4",
            label: "本部门及以下数据权限"
          },
          {
            value: "5",
            label: "仅本人数据权限"
          }
        ],
        // 驳回选项
        bhScopeOptions: [
          {
            value: "1",
            label: "驳回至申请人"
          },
          {
            value: "2",
            label: "驳回至上一节点"
          }
        ],
        // 菜单列表
        menuOptions: [],
        // 部门列表
        deptOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          lcmc: undefined,
          roleKey: undefined,
          status: undefined
        },
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 表单校验
        rules: {
          lcmc: [
            { required: true, message: "流程名称不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getListLcgl();
      /** 查询字典下拉列表 */
      optionselect().then(response => {
        this.selectRoleList = response.data;
      });
    },
    methods: {

      getListLcgl() {
        let data = {}
        listLcgl(data).then((res) => {
          console.log("===================================")
          console.log(JSON.stringify(res))
          console.log("===================================")
        })
      },

      /** 删除流程节点 */
      delLcjd(index) {
        this.form.jdList.splice(index,1)
      },
      /** 添加流程节点 */
      addLcjd() {
        this.form.jdList.push({shjs:1,jdid:''})
      },
      /** 是否可驳回 */
      sfbhChange(val) {
        if(val === "1"){
          this.bhxxDisable = true
          this.form.bhlx = '1'
        }else{
          this.bhxxDisable = false
          this.form.bhlx = ''
        }
      },
      /** 查询角色列表 */
      getList() {
        this.loading = true;
        listLcgl(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          console.log(JSON.stringify(response.rows))
            this.lcList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      /** 查询菜单树结构 */
      getMenuTreeselect() {
        menuTreeselect().then(response => {
          this.menuOptions = response.data;
        });
      },
      /** 查询部门树结构 */
      getDeptTreeselect() {
        deptTreeselect().then(response => {
          this.deptOptions = response.data;
        });
      },
      // 所有菜单节点数据
      getMenuAllCheckedKeys() {
        // 目前被选中的菜单节点
        let checkedKeys = this.$refs.menu.getCheckedKeys();
        // 半选中的菜单节点
        let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
        checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
        return checkedKeys;
      },
      // 所有部门节点数据
      getDeptAllCheckedKeys() {
        // 目前被选中的部门节点
        let checkedKeys = this.$refs.dept.getCheckedKeys();
        // 半选中的部门节点
        let halfCheckedKeys = this.$refs.dept.getHalfCheckedKeys();
        checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
        return checkedKeys;
      },
      /** 根据角色ID查询部门树结构 */
      getRoleDeptTreeselect(roleId) {
        return roleDeptTreeselect(roleId).then(response => {
          this.deptOptions = response.depts;
          return response;
        });
      },
      // 角色状态修改
      handleStatusChange(row) {
        let text = row.kyf === '1' ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '""' + row.lcmc + '"流程吗？').then(function() {
          row.kyf = row.kyf === '1' ? '0' : '1';
          return changeLcStatus(row.id, row.kyf);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(function() {
          row.kyf = row.kyf === '1' ? '1' : '0';
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 取消按钮（数据权限）
      cancelDataScope() {
        this.openDataScope = false;
        this.reset();
      },
      // 表单重置
      reset() {
        if (this.$refs.menu != undefined) {
          this.$refs.menu.setCheckedKeys([]);
        }
        this.menuExpand = false,
          this.menuNodeAll = false,
          this.deptExpand = true,
          this.deptNodeAll = false,
          this.form = {
            roleId: undefined,
            lcmc: undefined,
            roleKey: undefined,
            roleSort: 0,
            status: "0",
            lcsm: "",
            kyf: "1",
            lcid: undefined,
            sfkbh: "0",
            bhlx: '',
            jdList: [],
            deptIds: [],
            remark: undefined
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
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.roleId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      // 更多操作触发
      handleCommand(command, row) {
        switch (command) {
          case "handleDataScope":
            this.handleDataScope(row);
            break;
          case "handleAuthUser":
            this.handleAuthUser(row);
            break;
          case "handleDelete":
            this.handleDelete(row);
            break;
          default:
            break;
        }
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.getMenuTreeselect();
        this.open = true;
        this.title = "添加流程";
        this.bhxxDisable = false
        this.form.sfkbh = "0"
        this.sfbhChange("0")
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id
        getLcxxById(id).then(response => {
          this.form = response.data;
          if(this.form.sfkbh === "1"){
            this.bhxxDisable = true
          }else{
            this.bhxxDisable = false
          }
          this.form.jdList.forEach(t => {
            t.shjs = parseInt(t.shjs)
          })
          this.open = true;
          this.title = "修改流程";
        });
      },
      /** 选择角色权限范围触发 */
      dataScopeSelectChange(value) {
        if(value !== '2') {
          this.$refs.dept.setCheckedKeys([]);
        }
      },
      /** 分配数据权限操作 */
      handleDataScope(row) {
        this.reset();
        const roleDeptTreeselect = this.getRoleDeptTreeselect(row.roleId);
        getRole(row.roleId).then(response => {
          this.form = response.data;
          this.openDataScope = true;
          this.$nextTick(() => {
            roleDeptTreeselect.then(res => {
              this.$refs.dept.setCheckedKeys(res.checkedKeys);
            });
          });
          this.title = "分配数据权限";
        });
      },
      /** 分配用户操作 */
      handleAuthUser: function(row) {
        const roleId = row.roleId;
        this.$router.push("/system/role-auth/user/" + roleId);
      },
      /** 提交按钮 */
      submitForm: function() {
        if(this.form.jdList.length < 1){
          this.$modal.msgWarning("审核节点不能为空");
          return;
        }else{
          this.form.jdList.unshift({shjs:-1,jdid:''});
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined && this.form.id != "") {
              updateLcxx(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addLcxx(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 提交按钮（数据权限） */
      submitDataScope: function() {
        if (this.form.roleId != undefined) {
          this.form.deptIds = this.getDeptAllCheckedKeys();
          dataScope(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.openDataScope = false;
            this.getList();
          });
        }
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const id = row.id;
        this.$modal.confirm('是否确认删除流程名为"' + row.lcmc + '"的数据项？').then(function() {
          return deleteLcxx(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/role/export', {
          ...this.queryParams
        }, `role_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
