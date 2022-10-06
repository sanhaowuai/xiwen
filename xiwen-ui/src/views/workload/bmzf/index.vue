<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68p0">
      <el-form-item label="部门" prop="yhid">
        <treeselect style="width: 180px" v-model="queryParams.deptid" :options="deptOptions"
                    :clearable="false" :normalizer="normalizer" placeholder="选择部门" />
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">统计</el-button>
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      </el-form-item>
    </el-form>


    <div >
      <div>部门年度分数</div>
      <div ref="pie" style="width: 100%; height: 450px"></div>
    </div>

  </div>
</template>

<script>
  import { getMrbmxx,getBmzfList } from "@/api/workload/wftjgl";
  import echarts from 'echarts'
  import { getNdList} from "@/api/workload/ndgl";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import { listDept } from "@/api/system/dept";

  export default {
    name: "bmzf",
    components: { Treeselect },
    data() {
      return {
        // 遮罩层
        loading: true,
        ndList: [],
        dqnd:'',
        xzDataList: [],
        yzDataList: [],
        yzDataName: [],
        yzData: [],
        // 部门树选项
        deptOptions: [],
        pieData: [], //柱状
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          yhxm: null,
          nd: null,
          maxfs: '',
          minfs: '',
          deptid: '',
          zfs: null
        },
      };
    },
    created() {
      this.getNdList();
    },
    mounted () {

    },
    methods: {


      // 地域饼状图
      inOrigin () {
        let myChart = echarts.init(this.$refs.pie);
        let selft = this;
        // 地域分布---圆形饼状图
        let optionPie = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              dataView: { show: false, readOnly: false },
              magicType: { show: true, type: ['line', 'bar'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          legend: {
            data: ['1-12月']
          },
          xAxis: [
            {
              type: 'category',
              data: selft.yzDataName,
              axisPointer: {
                type: 'shadow'
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '总分',
              // axisLabel:{
              //   formatter: function (value) {
              //     console.log(JSON.stringify(selft.yzDataList))
              //     return selft.getYzDataList();
              //   }
              // }
              // min: 0,
              // max: 250,
              // interval: 50
            }
          ],
          series: [
            {
              name: '1-12月',
              type: 'bar',
              data: selft.yzData
            }
          ]
        };
        //图标自适应
        let listener = function () {
          myChart.resize()
        }
        window.addEventListener('resize', listener)
        //图标自适应
        myChart.setOption(optionPie);
      },
      getYzDataList(){
        getBmzfList({bmid:this.queryParams.deptid,nd:this.queryParams.nd}).then(res => {
          return res.data.yzList
        })
      },
      getMrbmxxv(){
        getMrbmxx().then(res => {
          this.queryParams.deptid = res.data.deptid
          this.getBmzfListv();
        })
      },
      getBmzfListv(){
        getBmzfList({bmid:this.queryParams.deptid,nd:this.queryParams.nd}).then(res => {
          this.yzDataList = res.data.bmList
          this.xzDataList = res.data.yzList
          this.yzData = res.data.yzData
          this.yzDataName = res.data.yzDataName
          this.inOrigin()
        })
      },
      /** 转换部门数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.deptId,
          label: node.deptName,
          children: node.children
        };
      },


      //查询年度
      getNdList(){
        getNdList({}).then(res => {
          this.ndList = res.data
          let tempList = this.ndList.filter(nd => nd.sfdqnd === '1')
          this.dqnd = tempList[0].nd
          this.queryParams.nd = this.dqnd
          listDept().then(response => {
            this.deptOptions = this.handleTree(response.data, "deptId");
            this.getMrbmxxv();
          });
        })

      },

      /** 搜索按钮操作 */
      handleQuery() {
        console.log(JSON.stringify(this.queryParams))
        this.getBmzfListv();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },

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
