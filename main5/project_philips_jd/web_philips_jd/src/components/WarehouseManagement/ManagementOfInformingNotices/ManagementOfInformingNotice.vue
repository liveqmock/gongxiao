<template>
  <div class="storage-notice-entry" id="storage-notice-entry">
    <div class="storage-notice-entry-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
          <el-form-item label="入库单号">
            <el-input v-model="dataForm.gonxiaoInboundNo" placeholder="入库单号" clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="采购单号">
            <el-input v-model="dataForm.purchaseNo" placeholder="采购单号" clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="货品编码">
            <el-input v-model="dataForm.productCode" placeholder="货品编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="dataForm.dateTime" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="收货仓库">
            <el-select v-model="dataForm.warehouseId" placeholder="收货仓库">
              <el-option :label="item.warehouseName" :value="item.warehouseId" v-for="(item,index) in warehouseList" :key="index">{{item.warehouseName}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
      </el-form>
    </div>
    <!--导出-->
    <div class="fx-button">
      <router-link :to="{path:'/NewEntryNotice'}" tag="el-button">新增</router-link>
      <el-button>导入</el-button>
    </div>
    <!--表格-->
    <div class="storage-notice-entry-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="gongxiaoInboundOrderNo" label="入库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchaseOrderNo" label="采购单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inboundType" label="单据类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="orderStatus" label="单据状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="收货仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="planInStockQuantity" label="计划入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="actualInStockQuantity" label="实际入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="differentQuantity" label="差异数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="estimateArriveTime" label="预计到仓时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="createTime" label="创建时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
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
          purchaseNo: '',
          productCode: '',
          dateTime: '',
          warehouseId: ''
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
      //取消
      cancelInboundOrder() {
        let queryParams = {
          // 仓库ID. 入库单号
          inboundNo: this.dataForm.inboundNo,
          warehouseId: this.dataForm.warehouseId
        };
        this.$http.get('/storage/informingNotice/cancelInboundOrder', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.tableData = data;
          } else {
            console.log(message);
          }
        }).catch((err) => {
          console.log(err);
        });
      },
      //关闭
      closeInboundOrder() {
        let queryParams = {
          // 仓库ID. 入库单号
          inboundNo: this.dataForm.inboundNo,
          warehouseId: this.dataForm.warehouseId
        };
        this.$http.get('/storage/informingNotice/closeInboundOrder', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.tableData = data;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
      },
      getTableData() {
        let queryParams = {
          gonxiaoInboundNo: this.dataForm.gonxiaoInboundNo,
          purchaseNo: this.dataForm.purchaseNo,
          productCode: this.dataForm.productCode,
          dateTime: this.dataForm.dateTime,
          warehouseId: this.dataForm.warehouseId,
          goodCode: "",
          supplier: "",
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/storage/informingNotice/selectInboundOrder', {params:queryParams}).then((res) => {
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
