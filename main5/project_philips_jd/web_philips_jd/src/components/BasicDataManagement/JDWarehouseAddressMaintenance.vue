<template>
  <div class="jd-warehouse-addresss" id="jd-warehouse-addresss">
    <div class="jd-warehouse-addresss-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
        <el-form-item label="仓库编码">
          <el-input v-model="dataForm.warehouseID" placeholder="仓库编码" clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="仓库名称">
          <el-input v-model="dataForm.warehouse" placeholder="仓库名称" clearable
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getTableData">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--导出-->
    <div class="fx-button">
      <el-button>导入</el-button>
    </div>
    <!--表格-->
    <div class="storage-notice-entry-table">
      <!--<el-table :data="tableData" v-loading="loading">-->
      <el-table :data="tableData">
        <el-table-column align="center" header-align="center" prop="WarehouseID" label="仓库编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="Warehouse" label="仓库名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="ReceivingAddress" label="仓库地址"></el-table-column>
        <el-table-column align="center" header-align="center" prop="DistributionCenter" label="到站"></el-table-column>
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
          warehouseID: '',
          warehouse: ''
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
        tableData: [{}],
        changedProjectId: 222,
        loading: true
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo',
        'warehouseList'
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
          warehouseID: this.dataForm.warehouseID,
          warehouse: this.dataForm.warehouse,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/jdwarehouse/address/selectAllWarehouseAddress', {params: queryParams}).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            [this.tableData, this.pagination.total] = [list, total];
            this.loading = false;
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
//      eventBus.$on('loadingDefaultProjectId', () => {
////        this.getTableData();
//      });
    },
    activated() {
//      if (!this.tableData) {
//        this.getTableData();
//      }
    }
  }
</script>
