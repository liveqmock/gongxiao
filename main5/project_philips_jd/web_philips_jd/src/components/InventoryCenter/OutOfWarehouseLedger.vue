<template>
  <div class="warehouse-ledger" id="warehouse-ledger">
    <div class="warehouse-ledger-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
          <el-form-item label="货品编码">
            <el-input v-model="dataForm.productCodes" placeholder="货品编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="货品名称">
            <el-input v-model="dataForm.productName" placeholder="货品名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库">
            <el-select v-model="dataForm.warehouseId" placeholder="仓库">
              <el-option :label="item.warehouseName" :value="item.warehouseId" v-for="(item,index) in warehouseList"
                         :key="index">{{item.warehouseName}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单号">
            <el-input v-model="dataForm.oddNumbers" placeholder="单号" clearable></el-input>
          </el-form-item>
          <el-form-item label="开始日期">
            <el-date-picker v-model="dataForm.startDate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="结束日期">
            <el-date-picker v-model="dataForm.endDate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
      </el-form>
    </div>
    <!-- 导出 -->
    <div class="fx-button">
      <el-button @click="exportFile()">导出</el-button>
    </div>
    <!-- 表格 -->
    <div class="warehouse-ledger-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="orderNo" label="出/入库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="batchNo" label="批次号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productModel" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="仓库名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="createTime" label="出入库时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="orderType" label="单据类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inventoryFlowType" label="库存类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="amountBeforeTransaction"
                         label="流水发生前的数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="transactionAmount" label="出入数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="perfectQuantity" label="良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="imperfectQuantity" label="残次品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="amountAfterTransaction"
                         label="结存数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="relatedOrderNo" label="采购/销售单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inventoryFlowId" label="外部流水号"></el-table-column>
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
          descriptionOfGoods: '',
          warehouseId: '',
          oddNumbers: '',
          startDate: '',
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
      getWarehouseList() {
        this.$http.get('/warehouse/selectAllWarehouse').then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.warehouseList = data;
          } else {
            console.log(message);
          }
        }).catch((err) => {
          console.log(err)
        })
      },
      getTableData() {
        let queryParams = {
          productCodes: this.dataForm.productCodes,
          descriptionOfGoods: this.dataForm.productName,
          warehouse: this.dataForm.warehouseId,
          oddNumbers: this.dataForm.oddNumbers,
          startDate: this.dataForm.startDate,
          endDate: this.dataForm.endDate,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/inventory/center/selectOutgoingAccount', {params: queryParams}).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            [this.tableData, this.pagination.total] = [list, total];
            this.loading = false
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
    activated() {
      if (!this.tableData.length) {
        this.getTableData();
      }
    }
  }
</script>
