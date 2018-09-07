<template>
  <div class="entry-deposit-account" id="entry-deposit-account">
    <div class="entry-deposit-account-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">

          <el-form-item label="货品编码">
            <el-input v-model="dataForm.productCodes" placeholder="货品编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="货品名称">
            <el-input v-model="dataForm.productName" placeholder="货品名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="收货仓库">
          <el-select v-model="dataForm.warehouseId" placeholder="收货仓库">
            <el-option :label="item.warehouseName" :value="item.warehouseId" v-for="(item,index) in warehouseList" :key="index">{{item.warehouseName}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="要求到货日期">
          <el-date-picker v-model="dataForm.date" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>

      </el-form>
    </div>
    <!--导出-->
    <div class="fx-button">
      <el-button @click="exportFile()">导出</el-button>
    </div>
    <!--表格-->
    <div class="entry-deposit-account">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="dateTime" label="要求到货时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productId" label="货品ID"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productModel" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="仓库名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="beginTotalAmount" label="期初总量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="beginNonDefective" label="期初良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="beginDefective" label="期初次品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inStockTotalAmount" label="入库总量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inStockNonDefectiveAmount" label="入库良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inStockDefectiveAmount" label="入库次品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="outStockTotalAmount" label="出库总量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="nonDefectiveSaleAmount" label="良品销售发货"></el-table-column>
        <el-table-column align="center" header-align="center" prop="nonDefectiveOtherAmount" label="良品其它出库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="endTotalAmount" label="期末总量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="endNonDefectiveAmount" label="期末良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="endDefectiveAmount" label="期末次品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="onPurchaseAmount" label="采购在途"></el-table-column>
        <el-table-column align="center" header-align="center" prop="onTransferInAmount" label="调拨在途"></el-table-column>
        <el-table-column align="center" header-align="center" prop="onTransferOutAmount" label="退仓调拨在途"></el-table-column>
        <el-table-column align="center" header-align="center" prop="nonDefectiveProfitkAmount" label="良品盘盈"></el-table-column>
        <el-table-column align="center" header-align="center" prop="defectiveProfitAmount" label="次品盘盈"></el-table-column>
        <el-table-column align="center" header-align="center" prop="defectiveOutAmount" label="次品出库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="nonDefectiveLossAmount" label="良品盘亏"></el-table-column>
        <el-table-column align="center" header-align="center" prop="defectiveLossAmount" label="次品盘亏"></el-table-column>
        <el-table-column align="center" header-align="center" prop="adjustmentAccountTotalOut" label="调账出库总量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="adjustmentAccountNonDefectiveOut" label="调账出库良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="adjustmentAccountDefectiveOut" label="调账出库非良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="adjustmentAccountTotalIn" label="调整出库总量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="adjustmentAccountNonDefectiveIn" label="调整出库良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="modifyDefectiveAmountIn" label="调整出库非良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="modifyTotalAmountIn" label="调账入库总量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="modifyNonDefectiveAmountIn" label="调账入库良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="adjustmentAccountDefectiveIn" label="调账入库非良品"></el-table-column>
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
          productCodes: '',
          productName: '',
          warehouseId: '',
          endDate: ''
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
          productCodes: this.dataForm.productCodes,
          descriptionOfGoods: this.dataForm.productName,
          warehouse: this.dataForm.warehouseId,
          startDate: this.dataForm.startDate,
          endDate: this.dataForm.endDate,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/inventory/center/selectInvoicingLedgers',{params : queryParams}).then((res) => {
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
      eventBus.$on('loadingDefaultProjectId', () => {
        this.getTableData();
      });
    },
    activated(){
      this.getTableData();
    }
  }
</script>
