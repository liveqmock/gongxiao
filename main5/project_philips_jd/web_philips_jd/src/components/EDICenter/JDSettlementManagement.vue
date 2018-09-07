<template>
  <div class="project-management" id="project-management">
    <div class="product-audit-management-form" id="product-audit-management-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
        <el-row>
          <el-form-item label="京东结算单号">
            <el-input v-model="dataForm.goodCode" placeholder="京东结算单号" clearable></el-input>
          </el-form-item>
          <el-form-item label="对账状态">
            <el-select v-model="dataForm.warehouseId" placeholder="对账状态"></el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>

    <div clasee="exportBtn" id="exportBtn">
      <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">导入</el-button>
      <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">开票申请</el-button>
    </div>

    <!--表格-->
    <div class="jd-purchase-order-table">
      <el-table :data="tableData">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="京东结算单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="申请日期"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="对账状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="对账时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="京东应收合计"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="京东应付合计"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="京东应结合计"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="京东开票金额"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="税差"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="供应商主体"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="合同主体"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="导入时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="开票状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="开票日期"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="收款状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="收款日期"></el-table-column>
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

