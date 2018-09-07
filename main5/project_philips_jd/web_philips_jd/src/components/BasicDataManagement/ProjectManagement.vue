<template>
  <div class="project-management" id="project-management">
    <div class="project-management-form" id="project-management-form">
      <el-row>
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="EAS项目编码">
            <el-input v-model="dataForm.EASCode" placeholder="EAS项目编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="dataForm.projectName" placeholder="项目名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="公司主体">
            <el-input v-model="dataForm.companyBody" placeholder="公司主体" clearable></el-input>
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
        <el-table-column align="center" header-align="center" prop="projectCode" label="项目ID"></el-table-column>
        <el-table-column align="center" header-align="center" prop="projectName" label="项目名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="company" label="公司主体"></el-table-column>
        <el-table-column align="center" header-align="center" prop="monthCouponRate"
                         label="月度应收返利比例"></el-table-column>
        <el-table-column align="center" header-align="center" prop="quarterCouponRate"
                         label="季度应收返利比例"></el-table-column>
        <el-table-column align="center" header-align="center" prop="annualCouponRate" label="年度应收返利比例"></el-table-column>
        <!--<el-table-column align="center" header-align="center" prop="proportionOfRebateUse" label="返利使用比例"></el-table-column>-->
        <!--<el-table-column align="center" header-align="center" prop="proportionOfSubstitute" label="代垫使用比例"></el-table-column>-->
        <!--<el-table-column align="center" header-align="center" prop="operator" label="操作人"></el-table-column>-->
        <el-table-column align="center" header-align="center" prop="lastModificationTime"
                         label="最后修改时间"></el-table-column>
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
      purchaseOrderNumberClick({row}) {
        let {purchaseOrderNumber} = row;
        console.log(purchaseOrderNumber)
      },
      tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'waring-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return ''
      },
      handleSizeChange(size) {
        this.pagination.pageSize = size;
        this.getTableData();
      },
      handleCurrentChange(currentPage) {
        this.pagination.currentPage = currentPage;
        this.getTableData()
      },
      getTableData() {
        let queryParams = {
          EASCode: this.dataForm.productCodes,
          projectName: this.dataForm.projectName,
          companyBody: this.dataForm.companyBody,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/base/project/selectProject?EASCode', {queryParams}).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            [this.tableData, this.pagination.total] = [list, total];
          } else {
            console.log(message);
          }
        }).catch((err) => {
          console.log(err);
        });
      }
    },
    watch: {
      getProjectInfo(newProjectId) {
        this.changedProjectId = newProjectId;
      }
    },
    mounted() {
      eventBus.$on('loadingDefaultProjectId', () => {
        this.getTableData();
      });
    },
  }
</script>
