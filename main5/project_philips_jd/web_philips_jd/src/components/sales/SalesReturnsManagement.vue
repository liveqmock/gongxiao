<template>
  <div class="purchase-management" id="purchase-management">
    <div class="purchase-management-form">
      <el-form :inline="true" :model="salesInfo" class="" label-width="100px">
        <el-row>
          <el-form-item label="退货单类型" prop="region">
            <el-select v-model="salesInfo.region" prop="whichLocated2" style="width: 222px;">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="退回仓库" prop="region">
            <el-select v-model="salesInfo.region" prop="whichLocated2">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
        </el-row>

        <el-row>
          <el-form-item label="创建时间">
            <el-date-picker v-model="salesInfo.createtime" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="至">
            <el-date-picker v-model="salesInfo.createtime1" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
        </el-row>

        <el-row>
          <el-form-item label="退回仓库" prop="contactInformation">
            <el-input v-model="salesInfo.createtime" style="width: 222px;"></el-input>
          </el-form-item>
          <el-form-item style="margin-left: 100px;">
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-row>

      </el-form>
    </div>

    <!--2：导出-->
    <div clasee="exportBtn block" id="exportBtn">
      <!--<div>{{全部 (57)}}</div>-->
      <div style="font-size: 14px; color: #909399; padding-left: 40px; background-color: whitesmoke">
        <el-row>
          <el-button type="text" plain @click="onAudited">全部</el-button>
          <span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onSubmission">新建</el-button>
          <span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onUnsubmitted">等待退货到仓</el-button>
          <span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onReject">退货完成</el-button>
          <span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onAudited">仓库拒单</el-button>
          <span style="color: coral;">(100)</span>
        </el-row>
      </div>
      <hr style="margin-top: 0px; margin-bottom: 0px; color: #E4E4E4;">
      <div clasee="exportBtn block" id="exportBtns" style="background-color: #F5F5F5;">
        <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">创建退货</el-button>
        <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">创建换货</el-button>
        <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">审批</el-button>
        <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">导出</el-button>
      </div>
    </div>

    <!--表格-->
    <div class="purchase-management-table">
      <el-table :data="tableData">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" stripe header-align="center" :row-class-name="tableRowClassName"
                         prop="Customer purchase number" label="退货单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="oddNumbers" label="销售订单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="orderAttribute" label="退货单金额"></el-table-column>
        <el-table-column align="center" header-align="center" prop="orderStatus" label="退货状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="amountOfMoney" label="退货单类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="customerInformation" label="发货仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="orderTime" label="退回仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="collectionTime" label="客户信息"></el-table-column>
        <el-table-column align="center" header-align="center" prop="quantityRemainingShipments"
                         label="退单创建时间"></el-table-column>
        <el-table-column align="center" header-align="center" prop="settlementMethod" label="结算单状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="accountNumber" label="结算单号"></el-table-column>
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

  export default {
    data() {
      return {
        salesInfo: {
          orderNo: "",
          createtime: "",
          createtime1:"",
          region:""
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
        tableData: [{}]
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo'
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
      checkClick(scope, row) {
        console.log(scope, row);
      },
      deleteRow(index, rows) {
        rows.splice(index, 1);
      },
      getTableData() {
      }
    },
    watch: {
      getProjectInfo(newProjectId) {
        this.projectId = newProjectId;
        this.getTableData();
      }
    },
    created() {

    },
    mounted() {

    }
  }
</script>

<style scoped lang="less">

</style>
