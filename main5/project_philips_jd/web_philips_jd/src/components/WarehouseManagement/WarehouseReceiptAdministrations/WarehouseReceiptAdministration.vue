<template>
  <div class="entry-warehouse" id="entry-warehouse">
    <div class="entry-warehouse-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
        <el-form-item label="入库单号">
          <el-input v-model="dataForm.gonxiaoInboundNo" placeholder="入库单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="批次号">
          <el-input v-model="dataForm.batchNo" placeholder="批次号" clearable></el-input>
        </el-form-item>
        <el-form-item label="订单类型">
          <el-select v-model="dataForm.orderType" placeholder="请选择">
            <el-option label="采购入库" value="shanghai"></el-option>
            <el-option label="退货入库" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库名称">
          <el-select v-model="dataForm.warehouseId" placeholder="仓库名称">
            <el-option :label="item.warehouseName" :value="item.warehouseId" v-for="(item,index) in warehouseList"
                       :key="index">{{item.warehouseName}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商">
          <el-select v-model="dataForm.supplier" placeholder="供应商">
            <el-option :label="item.supplierName" :value="item.supplierId" v-for="(item,index) in supplierList"
                       :key="index">{{item.supplierName}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入库通知单号">
          <el-input v-model="dataForm.wmsInboundOrderNo" placeholder="入库通知单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
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
      <el-button>导出</el-button>
    </div>
    <!--表格-->
    <div class="entry-warehouse-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="gongxiaoInboundOrderNo" label="入库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchaseOrderNo" label="采购单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="batchNo" label="批次号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inboundType" label="单据类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="收货仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inStockQuantity" label="实际入库数量"></el-table-column>
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
          gonxiaoInboundNo: '',
          batchNo: '',
          orderType: '',
          createTimeBegin: '',
          createTimeTo: '',
          warehouseId: '',
          supplier: '',
          wmsInboundOrderNo: ''
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
        'warehouseList',
        'supplierList'
      ])
    },
    methods: {
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
          gonxiaoInboundNo: this.dataForm.gonxiaoInboundNo,
          batchNo: this.dataForm.batchNo,
          orderType: this.dataForm.orderType,
          warehouseId: this.dataForm.warehouseId,
          supplier: this.dataForm.supplier,
          wmsInboundOrderNo: this.dataForm.wmsInboundOrderNo,
//          createTimeBegin: this.dataForm.createTimeBegin,
//          createTimeTo: this.dataForm.createTimeTo,
          createTimeBegin: "",
          createTimeTo: "",
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/storage/warehousereceipt/selectInboundOrder', {params:queryParams}).then((res) => {
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
