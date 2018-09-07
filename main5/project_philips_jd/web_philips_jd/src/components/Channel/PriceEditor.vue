<template>
  <div class="project-management" id="project-management">
    <div class="project-management-form" id="project-management-form">
      <el-row>
        <el-form :inline="true" :model="dataForm" labelWidth="100px">
          <div label-position="right">
          <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
          <div class="essentialInfo" id="essentialInfo" style="padding-left: 7%;">
            <el-row>
              <el-form-item label="采购指导价">
                <el-input v-model="dataForm.EASCode" placeholder="采购指导价" disabled clearable></el-input>
              </el-form-item>
              <el-form-item label="税率">
                <el-input v-model="dataForm.projectName" placeholder="税率" disabled clearable></el-input>
              </el-form-item>
              <el-form-item label="税收分类编码">
                <el-input v-model="dataForm.companyBody" placeholder="税收分类编码" disabled clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="销售指导价" required>
                <el-input v-model="dataForm.EASCode" placeholder="销售指导价" clearable></el-input>
              </el-form-item>
              <el-form-item label="实销返点">
                <el-input v-model="dataForm.projectName" placeholder="实销返点" clearable></el-input>
              </el-form-item>
              <el-form-item label="应销返点">
                <el-input v-model="dataForm.companyBody" placeholder="应销返点" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="厂送返利点">
                <el-input v-model="dataForm.EASCode" placeholder="厂送返利点" clearable></el-input>
              </el-form-item>
              <el-form-item label="YH支持折扣">
                <el-input v-model="dataForm.projectName" placeholder="YH支持折扣" clearable></el-input>
              </el-form-item>
              <el-form-item label="直送客户返点">
                <el-input v-model="dataForm.companyBody" placeholder="直送客户返点" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="" prop="yn" style="padding-left: 73px;">
                <el-checkbox v-model="dataForm.companyBody">是否向仓库发送通知单</el-checkbox>
              </el-form-item>
            </el-row>
          </div>
        </div>
          <div clasee="exportBtn block" id="btn" style="padding-left: 26%; padding-top: 20px;">
            <el-row>
              <el-button type="primary" @click="onSubmit()">提交</el-button>
              <el-button type="button" @click="onSaveAndSubmit()">取消</el-button>
              <el-button type="button" @click="onCancel()">作废</el-button>
            </el-row>
          </div>
        </el-form>
      </el-row>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import eventBus from '../../assets/js/eventBus.js';
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {ElRow},
    data() {
      return {
        dataForm: {
          EASCode: '',
          projectName: '',
          companyBody: ''
        },
        pagination: {
          // 每页显示数量
          pageSize: 60,
          // 页码
          pageNumber: 1,
          // 页码list
          pageList: [5, 10, 20],
          // 总条数
          total: 0
        },
        tableData: [],
        changedProjectId: 222,
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo'
      ])
    },
    methods: {
    },
    watch: {
    },
    mounted() {
    },
  }
</script>
