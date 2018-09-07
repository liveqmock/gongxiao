<template>
  <div class="outbound-notice" id="outbound-notice">
    <div class="outbound-notice-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">

          <el-form-item label="出库单号">
            <el-input v-model="dataForm.gongxiaoOutNo" placeholder="出库单号" clearable></el-input>
          </el-form-item>
          <el-form-item label="销售单号">
            <el-input v-model="dataForm.salesNo" placeholder="销售单号" clearable></el-input>
          </el-form-item>
          <el-form-item label="单据类型">
            <el-select v-model="dataForm.orderType" placeholder="请选择">
              <el-option label="采购入库" value="shanghai"></el-option>
              <el-option label="退货入库" value="beijing"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="客户">
            <el-input v-model="dataForm.customer" placeholder="客户" clearable></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dataForm.createTimeBeging"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>

      </el-form>
    </div>
    <!--导出-->
    <div class="fx-button">
      <el-button>导出</el-button>
    </div>
    <!--表格-->
    <div class="outbound-notice-table">
      <el-table  :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="gongxiaoOutboundOrderNo" label="出库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="salesOrderNo" label="销售单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="outboundType" label="单据类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="发货仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="outStockQuantity" label="出库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="customer" label="客户"></el-table-column>
        <el-table-column align="center" header-align="center" prop="createTime" label="创建时间"></el-table-column>
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
  import eventBus from '../../../assets/js/eventBus.js';

  export default {
    data() {
      return {
        dataForm: {
          gongxiaoOutNo: '',
          salesNo: '',
          orderType: '',
          customer: '',
          createTimeBeging: '',
          createTimeLast: ''
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
      //列表
      getTableData() {
        let queryParams = {
          gongxiaoOutNo: this.dataForm.gongxiaoOutNo,
          salesNo: this.dataForm.salesNo,
          orderType: this.dataForm.orderType,
          customer: this.dataForm.customer,
          createTimeBeging: this.dataForm.createTimeBeging,
          createTimeLast: this.dataForm.createTimeLast,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/storage/outboundorder/selectOutboundOrder', {params:queryParams}).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            [this.tableData, this.pagination.total] = [list, total];
            this.loading = false;
          } else {
            console.log(message);
          }
          console.log(res)
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
    activated() {
      if(!this.tableData.length){
        this.getTableData();
      }
    }
  }
</script>
