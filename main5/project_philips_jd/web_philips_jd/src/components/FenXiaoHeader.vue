<template>
  <div class="fx-header clearfix" id="fx-header">
    <h2>分销协同平台</h2>
    <div class="shape-box">
      <el-select v-model="projectId" clearable placeholder="请选择" id="project-list" @change="projectListChange">
        <el-option
          v-for="item in projectList"
          :key="item.value"
          :label="item['projectName']"
          :value="item['projectId']"
        >
        </el-option>
      </el-select>

      <!--<img src="../assets/shape.png" alt="user-img" width="27" height="27">-->
      <!--<span>admin.yhglsttbal.com</span>-->
      <a href="javascript:void(0);">注销</a>
    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'
  import eventBus from '../assets/js/eventBus';

  export default {
    name: "FenXiaoHeader",
    data() {
      return {
        projectList: [{}],
        projectId: 0
      }
    },
    methods: {
      ...mapActions([
        'changeProjectInfo'
      ]),
      getProjectData() {
        // 后台获取数据的接口
        this.$http.get('http://localhost:9000/getProjectList')
          .then((res) => {
            // let {data:{data:{list, total}, message, returnCode}, status} = res;
            let {data:{list, message, returnCode}} = res;
            if(returnCode === 0){
              this.projectList = list;
              this.projectId = list[0]['projectId'];
              this.changeProjectInfo(this.projectId);
              eventBus.$emit("loadingDefaultProjectId");
            }else{
              console.log(message);
            }
          })
          .catch(function (err) {
            console.log(err);
          })
      },
      projectListChange() {
        this.changeProjectInfo(this.projectId);
      },
    },
    created() {
      this.getProjectData();
    }
  }
</script>

<style scoped>
  .fx-header {
    height: 50px;
    line-height: 50px;
    background-color: #fff;
    border-bottom: 1px solid #e8eaed;

  }

  .fx-header > h2 {
    height: 50px;
    line-height: 50px;
    margin-top: 0;
    margin-bottom: 0;
    color: #27303A;
    font-size: 22px;
    padding-left: 10px;
    float: left;
  }

  .fx-header .shape-box {
    float: right;
    padding-right: 20px;
  }

  .fx-header .shape-box > img {
    display: inline-block;
  }

  .fx-header .shape-box > span {
    margin-left: 10px;
    margin-right: 14px;
  }

  .fx-header .shape-box > span, .fx-header .shape-box > a {
    font-size: 14px;
    color: #96a4b4;
    display: inline-block;
  }
</style>
