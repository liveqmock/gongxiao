<template>
  <div class="pay-confirm-book" id="pay-confirm-book">
    <div class="pay-confirm-book-form">
      <el-form :inline="true" :model="purchaseInfo" class="">
        <el-form-item label="采购单号">
          <el-input v-model="purchaseInfo.purchaseOrderNumber" placeholder="采购单号"></el-input>
        </el-form-item>
        <el-form-item label="供应商单号">
          <el-input v-model="purchaseInfo.supplierOrderNumber" placeholder="采购单号"></el-input>
        </el-form-item>
        <el-form-item label="供应商">
          <el-select v-model="purchaseInfo.supplierId" placeholder="供应商">
            <el-option :label="item.supplierName" :value="item.supplierId"  v-for="(item,index) in supplierList" :key="index">{{item.supplierName}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库">
          <el-select v-model="purchaseInfo.warehouseId" placeholder="仓库">
            <el-option :label="item.warehouseName" :value="item.warehouseId"  v-for="(item,index) in warehouseList" :key="index">{{item.warehouseName}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="purchaseInfo.createTime"
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
    <!--表格-->
    <div class="pay-confirm-book-table">
      <el-table
        :data="tableData"
      >
        <el-table-column
          align="center"
          stripe
          header-align="center"
          :row-class-name="tableRowClassName"
          prop="purchaseOrderNumber"
          label="采购单号"
        >
          <template slot-scope="scope">
            <router-link :to="{path:'/ProjectInfo/'+scope.row.purchaseOrderNumber}">{{ scope.row.purchaseOrderNumber
              }}
            </router-link>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="supplierOrderNo"
          label="供应商订单号"
        >
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="contractReferenceOrderNo"
          label="合同参考号">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="supplierName"
          label="供应商">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="orderAmount"
          label="订单金额">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="warehouseName"
          label="收货仓库">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="purchaseStatus"
          label="订单状态">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="purchaseNumber"
          label="总数量">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="businessDate"
          label="创建时间">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="createPerson"
          label="创建人">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="checkClick(scope)"
            >查看
            </el-button>
            <el-button
              type="text"
              size="small"
              @click.native.prevent="deleteRow(scope.$index, tableData)"
            >删除
            </el-button>
          </template>
        </el-table-column>
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
        purchaseInfo: {
          purchaseOrderNumber: "",
          supplierOrderNumber: "",
          supplierId: "",
          warehouseId: "",
          createTime: ""
        },
        warehouseList:[{}],
        supplierList:[{}],
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
      checkClick(scope) {
        let {purchaseOrderNumber} = scope;
        console.log(purchaseOrderNumber);
      },
      deleteRow(index, rows) {
        rows.splice(index, 1);
      },
      getTableData() {
        this.changedProjectId = this.getProjectInfo;
        let purchaseQueryParams = {
          projectId: this.changedProjectId,
          purchaseOrderNumber: this.purchaseInfo.purchaseOrderNumber,
          supplierOrderNumber: this.purchaseInfo.supplierOrderNumber,
          supplierId: this.purchaseInfo.supplierId,
          warehouseId: this.purchaseInfo.warehouseId,
          createTime: this.purchaseInfo.createTime,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/getPurchaseList').then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            [this.tableData, this.pagination.total] = [list, total];
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
    }
  }
</script>

<style scoped lang="less">

</style>
