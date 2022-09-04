<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 16px">
          {{tzggObj.noticeTitle}}
          <h5 class="" style="color: black;margin-left: 15px;" v-html="tzggObj.noticeContent">
          </h5>
        </blockquote>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="24">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>通知公告</span>
          </div>
          <el-collapse accordion>
            <template v-for="(item,index) in tzggList">
              <el-collapse-item :title="item.noticeTitle">
              <ol v-html="item.noticeContent">
              </ol>
            </el-collapse-item>
            </template>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { tzggList } from "@/api/homePage";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.6.0",
      //通知公告list
      tzggList:[],
      //最近的一条通知公告信息
      tzggObj:{},
    };
  },
  created() {
    this.getTzggList();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    //查询通知公告
    getTzggList() {
      let data = {
        number: 10
      }
      tzggList(data).then((res) => {
        this.tzggList = res.rows
        console.log(JSON.stringify(res))
        this.tzggObj = this.tzggList[0]
      })
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

