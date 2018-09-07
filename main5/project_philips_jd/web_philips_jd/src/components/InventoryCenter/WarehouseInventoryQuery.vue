<template>
  <div class="warehouse-inventory" id="warehouse-inventory">
    <div class="warehouse-inventory-form">
      <el-row>
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="货品编码">
            <el-input v-model="dataForm.productCodes" placeholder="货品编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="货品名称">
            <el-input v-model="dataForm.descriptionOfGoods" placeholder="货品名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库">
            <el-select v-model="dataForm.warehouseId" placeholder="仓库">
              <el-option :label="item.warehouseName" :value="item.warehouseId" v-for="(item,index) in warehouseList" :key="index">{{item.warehouseName}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <!--导出-->
    <div class="fx-button">
      <el-button @click="exportFile()">导出</el-button>
    </div>
    <!--表格-->
    <div class="warehouse-inventory-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="productCode" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="仓库名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="status" label="库存状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="batchNo" label="批次号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchaseType" label="采购方式"></el-table-column>
        <el-table-column align="center" header-align="center" prop="availableQuantity" label="数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="occupancyQuantity" label="占用数量"></el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <!--<div class="fx-pagination">-->
      <!--<span class="pagination-total">共 {{ this.pagination.total }} 条</span>-->
      <!--<el-pagination-->
        <!--@size-change="handleSizeChange"-->
        <!--@current-change="handleCurrentChange"-->
        <!--:current-page.sync="pagination.pageNumber"-->
        <!--:page-sizes="pagination.pageList"-->
        <!--:page-size="pagination.pageSize"-->
        <!--layout="sizes, prev, pager, next"-->
        <!--:total="pagination.total">-->
      <!--</el-pagination>-->
    <!--</div>-->
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
          warehouseId: ''
        },
//        pagination: {
//          // 每页显示数量
//          pageSize: 60,
//          // 页码
//          pageNumber: 1,
//          // 页码list
//          pageList: [5, 10, 20],
//          // 总条数
//          total: 0
//        },
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
//      handleSizeChange(size) {
//        this.pagination.pageSize = size;
//        this.getTableData();
//      },
//      handleCurrentChange(currentPage) {
//        this.pagination.currentPage = currentPage;
//        this.getTableData()
//      },
      exportFile(){
        console.log("导出功能")
      },
      getTableData() {
        let queryParams = {
          productCodes: this.dataForm.productCodes,
          descriptionOfGoods: this.dataForm.descriptionOfGoods,
          warehouse: this.dataForm.warehouseId
//          pageNumber: this.pagination.pageNumber,
//          pageSize: this.pagination.pageSize
        };
        this.$http.get('/inventory/center/selectWarehouseInventory', {params: queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.tableData = data;
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
      if (!this.tableData.length) {
        this.getTableData();
      }
    }
  }
</script>
