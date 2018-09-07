<template>
  <div class="management-outer-notice" id="management-outer-notice">
    <div class="management-outer-notice-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
          <el-form-item label="客户">
            <el-input v-model="dataForm.customer" placeholder="客户" clearable></el-input>
          </el-form-item>
          <el-form-item label="供应商">
            <el-input v-model="dataForm.supplier" placeholder="供应商" clearable></el-input>
          </el-form-item>
          <el-form-item label="发货仓库">
            <el-select v-model="dataForm.warehouseId" placeholder="发货仓库">
              <el-option :label="item.warehouseName" :value="item.warehouseId" v-for="(item,index) in warehouseList" :key="index">{{item.warehouseName}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="出库单号">
            <el-input v-model="dataForm.gongxiaoOutNo" placeholder="出库单号" clearable></el-input>
          </el-form-item>
          <el-form-item label="销售单号">
            <el-input v-model="dataForm.salseNo" placeholder="销售单号" clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="货品编码">
            <el-input v-model="dataForm.productCode" placeholder="货品编码" clearable
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
      </el-form>
    </div>
    <!--导出-->
    <div class="fx-button">
      <router-link :to="{path:'/NewAnnouncementList'}" tag="el-button">新增</router-link>
      <el-button>导入</el-button>
    </div>
    <!--表格-->
    <div class="management-outer-notice-table">
      <el-table  :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="gongxiaoOutboundOrderNo" label="出库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="salesOrderNo" label="销售单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="outStorageType" label="单据类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="orderStatus" label="单据状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="deliverWarehouse" label="发货仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="plantQuantity" label="计划入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="actualQuantity" label="实际入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="differentQuantity" label="差异数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplier" label="供应商"></el-table-column>
        <el-table-column align="center" header-align="center" prop="customer" label="客户"></el-table-column>
        <el-table-column align="center" header-align="center" prop="createTime" label="创建时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <div class="fx-pagination">
      <span class="pagination-total">共 {{ this.pagination.total }} 条</span>
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
          salseNo: '',
          productCode: '',
          createTimeBeging: '',
          createTimeLast: '',
          warehouseId: '',
          finishTimeBegin: '',
          finishTimeLast: '',
          supplier: '',
          customer:''
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
        loading: true,
        // warehouseList: [{}],
        changedProjectId: 222,
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
          // 仓库ID. 出库单号
          gonxiaoInboundNo: this.tableData.gongxiaoOutboundOrderNo,
          warehouseId: this.tableData.warehouseId
        };
        this.$http.get('/storage/outstorenotice/cancelOutboundOrder', {params:queryParams}).then((res) => {
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
      //关闭
      closeInboundOrder() {
        let queryParams = {
          // 仓库ID. 出库单号
          gonxiaoInboundNo: this.tableData.gongxiaoOutboundOrderNo,
          warehouseId: this.tableData.warehouseId
        };
        this.$http.get('/storage/outstorenotice/closeOutboundOrder', {queryParams}).then((res) => {
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
      // 列表
      getTableData() {
        let queryParams = {
          gongxiaoOutNo: this.dataForm.gongxiaoOutNo,
          salseNo: this.dataForm.salseNo,
          productCode: this.dataForm.productCode,
//          createTimeBeging: this.dataForm.createTimeBeging,
//          createTimeLast: this.dataForm.createTimeLast,
          createTimeBeging: "",
          createTimeLast: "",
          warehouseId: this.dataForm.warehouseId,
//          finishTimeBegin: this.dataForm.finishTimeBegin,
//          finishTimeLast: this.dataForm.finishTimeLast,
          finishTimeBegin: "",
          finishTimeLast: "",
          supplier: this.dataForm.supplier,
          customer: this.dataForm.customer,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/storage/outstorenotice/selectOutboundOrder', {params:queryParams}).then((res) => {
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
