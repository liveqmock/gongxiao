<template>
  <div class="batch-inventory-report" id="batch-inventory-report">
    <div class="batch-inventory-report-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
        <el-form-item label="批次号">
          <el-input v-model="dataForm.batchNumber" placeholder="批次号" clearable></el-input>
        </el-form-item>
        <el-form-item label="入库单号">
          <el-input v-model="dataForm.warehouseNumber" placeholder="入库单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="出库单号">
          <el-input v-model="dataForm.outWarehouseSingleNumber" placeholder="出库单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="批次状态">
          <el-select v-model="dataForm.status" placeholder="请选择">
            <el-option label="正常" value="normal"></el-option>
            <el-option label="关闭" value="close"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生成日期">
          <el-date-picker
            v-model="dataForm.createTime"
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
      <el-button>导入</el-button>
    </div>
    <!--表格-->
    <div class="batch-inventory-report-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="status" label="批次状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="batchNo" label="批次号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productCode" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inboundQuantity" label="入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchaseOrderNo" label="采购订单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inboundOrderNo" label="入库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="createTime" label="入库日期" :formatter="transformTime"></el-table-column>
        <el-table-column align="center" header-align="center" prop="receiveFinishTime" label="收货完成日期" :formatter="transformTime"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchaseGuidPrice" label="采购指导价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchasePrice" label="采购价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="costPrice" label="成本价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="batchInboundAmount" label="批次进货金额"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inventoryAge" label="库存库龄"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inventoryTurnover" label="库存周转率"></el-table-column>
        <el-table-column align="center" header-align="center" prop="restAmount" label="剩余数量"></el-table-column>
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
          batchNumber: '',
          warehouseNumber: '',
          outWarehouseSingleNumber: '',
          status: '',
          createTime: ''
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
        warehouseList: [{}],
        changedProjectId: 222,
        loading: true
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo'
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
      transformTime(row,column,cellValue,index){
        return this.$globalEvent.transformTime({time:cellValue,seconds:"seconds"})
      },
      getTableData() {
        let queryParams = {
          batchNumber: this.dataForm.batchNumber,
          warehouseNumber: this.dataForm.warehouseNumber,
          outWarehouseSingleNumber: this.dataForm.outWarehouseSingleNumber,
          startDate: this.dataForm.createTime[0],
          endDate: this.dataForm.createTime[1],
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/inventory/center/selectDailyInventoryReport', {params: queryParams}).then((res) => {
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
    activated() {
      if(!this.tableData.length){
        this.getTableData();
      }
    }
  }
</script>
