<template>
  <div class="inventory-check" id="inventory-check">
    <div class="inventory-check-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
        <el-row>
          <el-form-item label="货品编码">
            <el-input v-model="dataForm.productCode" placeholder="货品编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="货品名称">
            <el-input v-model="dataForm.productName" placeholder="货品名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
    <!-- 导出 -->
    <div class="fx-button">
      <el-button @click="exportFile()">导出</el-button>
    </div>
    <!-- 列表 -->
    <div class="inventory-check-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="dateTime" label="日期" :formatter="transformTime"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productCode" label="货品编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="仓库名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="status" label="状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="fenxiaoPerfectQuantity"
                         label="分销良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="wmsPerfectQuantity" label="仓库良品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="perfectDifferent" label="良品差异"></el-table-column>
        <el-table-column align="center" header-align="center" prop="fenxiaoImperfectQuantity"
                         label="分销残品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="wmsImperfectQuantity"
                         label="仓库残品"></el-table-column>
        <el-table-column align="center" header-align="center" prop="imPerfectDifferent" label="残次品差异"></el-table-column>
        <el-table-column align="center" header-align="center" prop="fenxiaoEngineDamage" label="分销机损"></el-table-column>
        <el-table-column align="center" header-align="center" prop="wmsEngineDamage" label="仓库机损"></el-table-column>
        <el-table-column align="center" header-align="center" prop="engineDamageDifferent"
                         label="机损差异"></el-table-column>
        <el-table-column align="center" header-align="center" prop="fenxiaoBoxDamage" label="分销箱损"></el-table-column>
        <el-table-column align="center" header-align="center" prop="wmsBoxDamage" label="仓库箱损"></el-table-column>
        <el-table-column align="center" header-align="center" prop="boxDamageDifferent" label="箱损差异"></el-table-column>
        <el-table-column align="center" header-align="center" prop="fenxiaoFrozenStock" label="分销冻结"></el-table-column>
        <el-table-column align="center" header-align="center" prop="wmsFrozenStock" label="仓库冻结"></el-table-column>
        <el-table-column align="center" header-align="center" prop="frozenStockDifferent"
                         label="冻结差异"></el-table-column>
      </el-table>
      <!-- 分页 -->
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
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import eventBus from '../../assets/js/eventBus.js';

  export default {
    data() {
      return {
        dataForm: {
          productCode: '',
          productName: ''
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
        'getProjectInfo'
      ])
    },
    methods: {
      transformTime(row,column,cellValue,index){
        //
        return this.$globalEvent.transformTime({time:cellValue})
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
          productCode: this.dataForm.productCode,
          productName: this.dataForm.productName,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/inventory/center/selectBatchInventoryReport', {params: queryParams}).then((res) => {
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
      if (!this.tableData.length) {
        this.getTableData();
      }
    }
  }
</script>


