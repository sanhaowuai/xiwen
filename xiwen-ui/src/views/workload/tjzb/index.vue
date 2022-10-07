<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68p0">
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="success" size="mini" @click="downloadFile('1')" >导出</el-button>
      </el-form-item>
    </el-form>

    <div>
      <table>
        <tr >
          <td>考核分类</td>
          <template v-for="(val1,index) in headerList">
            <td v-if="val1.pid === '-1'"></td>
          </template>
        </tr>
      </table>
    </div>

    <el-table v-loading="loading" :data="khfsbList">



      <el-table-column label="考核分类" align="center" prop="yhxm" />
      <el-table-column label="" align="center" prop="bmmc" />
      <el-table-column label="" align="center" prop="nd" />
      <template v-for="(val1,index) in headerList">
        <el-table-column :label="val1.khxmc" align="center" prop="zfs" />
      </template>
    </el-table>
  </div>
</template>

<script>
  import { listKhfsb} from "@/api/workload/khfsb";
  import { getNdList} from "@/api/workload/ndgl";
  import { getTableHeader } from "@/api/workload/wftjgl";

  export default {
    name: "Khfsb",
    data() {
      return {
        // 遮罩层
        loading: true,
        headers: { "Content-Type" : "multipart/form-data;charset=UTF-8" },
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
        headerList: [],
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
      this.getTableHeaderv();
    },
    methods: {
      getTableHeaderv(){
        getTableHeader({}).then(res => {
          this.headerList = res.data.hList
        });
      },
      downloadFile(lx) {
        downloadFileSer({lx: lx} ).then(res => {
          const link = document.createElement("a");
          let blob = new Blob([res], { type: "application/vnd.ms-excel" });  //文件流处理
          link.style.display = "none";  //去除a标签的样式
          // 设置连接
          link.href = URL.createObjectURL(blob);
          link.download = '人员初始分导入.xlsx';
          document.body.appendChild(link);
          // 模拟点击事件
          link.click();
          // 下载完成移除元素
          document.body.removeChild(link)
        })
      },
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
