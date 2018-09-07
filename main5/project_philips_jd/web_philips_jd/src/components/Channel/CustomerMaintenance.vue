<template>
  <div class="project-management" id="project-management">
    <div class="project-management-form" id="project-management-form">
      <el-row>
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="EAS客户编码">
            <el-input v-model="dataForm.EASCode" placeholder="EAS客户编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="客户名称">
            <el-input v-model="dataForm.projectName" placeholder="客户名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <!--导出-->
    <div clasee="exportBtn" id="exportBtn">
      <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">导出</el-button>
    </div>
    <!--表格-->
    <div class="project-management-table" id="project-management-table">
      <el-table  :data="tableData">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" header-align="center" prop="projectCode" label="渠道名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="projectName" label="EAS客户编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="company" label="客户名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="monthCouponRate" label="客户账号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="quarterCouponRate" label="密码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="annualCouponRate" label="操作人"></el-table-column>
        <el-table-column align="center" header-align="center" prop="proportionOfRebateUse" label="最后修改时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="proportionOfSubstitute" label="操作"></el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <div class="fx-pagination">
      <span class="pagination-total">共 {{this.pagination.total}} 条</span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="pagination.pageNumber"
        :page-sizes="pagination.pageList"
        :page-size="pagination.pageSize"
        layout="sizes, prev, pager, next"
        :total="pagination.total">
      </el-pagination>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import eventBus from '../../assets/js/eventBus.js';

  export default {
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
    }
  }
</script>
