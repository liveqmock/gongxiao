<template>
  <div class="goods-inventory" id="goods-inventory">
    <div class="goods-inventory-form">
      <el-row>
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="货品编码">
            <el-input v-model="dataForm.productCodes" placeholder="货品编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="货品名称">
            <el-input v-model="dataForm.descriptionOfGoods" placeholder="货品名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <div class="fx-button">
      <el-button @click="exportFile()">导出</el-button>
    </div>
    <!--表格-->
    <div class="goods-inventory-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="productCode" label="货品编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="availableQuantity" label="实物良品数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="occupancyQuantity" label="占用数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="defectiveQuantity" label="残次数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="physicalInventory" label="实物库存"></el-table-column>
        <el-table-column align="center" header-align="center" prop="onWayQuantity" label="在途数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="frozenQuantity" label="冻结数量"></el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <!--<div class="fx-pagination">-->
    <!--<span class="pagination-total">共 {{this.pagination.total}} 条</span>-->
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
          descriptionOfGoods: ''
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
        'getProjectInfo'
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
          descriptionOfGoods: this.dataForm.descriptionOfGoods
//          pageNumber: this.pagination.pageNumber,
//          pageSize: this.pagination.pageSize
        };
        this.$http.get('/inventory/center/selectProductInventoryInfo', {params: queryParams}).then((res) => {
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
      if(!this.tableData.length){
        this.getTableData();
      }
    }
  }
</script>
