<template>
  <div class="project-management" id="project-management">
    <div class="product-audit-management-form" id="product-audit-management-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
        <el-row>
          <el-form-item label="京东采购单号">
            <el-input v-model="dataForm.goodCode" placeholder="京东采购单号" clearable></el-input>
          </el-form-item>
          <el-form-item label="导入状态">
            <el-select v-model="dataForm.warehouseId" placeholder="导入状态"></el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="项目">
            <el-select v-model="dataForm.warehouseId" placeholder="项目"></el-select>
          </el-form-item>
          <el-form-item label="订单属性">
            <el-select v-model="dataForm.warehouseId" placeholder="订单属性"></el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="开始日期">
            <el-date-picker v-model="dataForm.startDate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="结束日期">
            <el-date-picker v-model="dataForm.endDate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>

    <div clasee="exportBtn" id="exportBtn">
      <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">重新导入</el-button>
    </div>

    <!--表格-->
    <div class="jd-purchase-order-table">
      <el-table :data="tableData">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="京东采购单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="业务属性"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="文件名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="创建时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="项目"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="导入状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="描述"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="操作"></el-table-column>
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
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {ElRow},
    data() {
      return {
        dataForm: {
          purchaseOrderNumber: "",
          supplierOrderNumber: "",
          supplierId: "",
          warehouseId: "",
          createTime: "",
          startDate: "",
          endDate: ""
        },
        warehouseList: [{}],
        supplierList: [{}],
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
        'getProjectInfo'
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
      }
    },
    watch: {
      getProjectInfo(newProjectId) {
        this.changedProjectId = newProjectId;
      }
    },
    mounted() {
      eventBus.$on('loadingDefaultProjectId', () => {
      });
    },
    created() {
    }
  }
</script>

