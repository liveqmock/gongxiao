<template>
  <div class="supplier-reserved-account" id="supplier-reserved-account">
    <div class="supplier-reserved-count-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">

        <el-form-item label="账户类型">
          <el-select v-model="dataForm.accountType" placeholder="请选择">
            <el-option
              v-for="item in accountType"
              :key="item.accountCode"
              :label="item.label"
              :value="item.accountCode">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="资金流向">
          <el-select v-model="dataForm.accountFlow" placeholder="请选择">
            <el-option
              v-for="item in accountFlow"
              :key="item.flowNo"
              :label="item.label"
              :value="item.flowNo">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="时间">
          <el-date-picker
            v-model="dataForm.accountDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="getTableData">查询</el-button>
          <el-button @click="">重置</el-button>
        </el-form-item>

      </el-form>
    </div>

    <!--账户金额显示-->
    <div class="supplier-reserved-discount-input account-list">
      <ul class="clear-float">
        <li v-for="(discountItem,index) in discount" :key="discountItem.accountType">
          <h3>{{discountItem.title}}</h3>
          <p>
            <input type="text" :value="discountItem.discountNumber" disabled>
          </p>
        </li>
      </ul>
    </div>

    <!--操作-->
    <div class="fx-button">
      <el-button @click="exportFlow()">导出</el-button>
      <el-button @click="getSubtotal()" type="info">统计金额</el-button>
      <!--统计金额-->
      <ul class="clear-float">
        <li v-for="(item,index) in amountCount" :key="index">已{{item.text}}{{item.number}}笔,共计{{item.amount}}元</li>
      </ul>
    </div>

    <div class="supplier-reserved-account-flow-table">
      <el-table :data="tableData">
        <el-table-column
          align="center"
          header-align="center"
          prop="createTime"
          label="时间"
        >
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="transactionAmountStr"
          label="收入/支出">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="currencyCode"
          label="币种">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="amountAfterTransactionStr"
          label="账户余额">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="typeStr"
          label="流水类型">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="createByName"
          label="操作人">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="remark"
          label="备注">
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
    name: "SupplierReservedAccount",
    data() {
      return {
        dataForm: {
          accountType: 0,
          accountFlow: '',
          accountDate: ''
        },
        accountType: [{accountCode: 3, label: '销售差价'},{accountCode: 4, label: '单价折扣'}, {accountCode: 5, label: '采购预留'}],
        accountFlow: [{flowNo: 306, label: '收入'}, {flowNo: 305, label: '支出'}],
        discount: [{title: "销售差价", accountType: 3, discountNumber: 0},{title: "单价折扣", accountType: 4, discountNumber: 0}, {title: "采购预留", accountType: 5, discountNumber: 0}],
        transfer: {amount: 0, remark: '', accountType: 0},
        amountCount:[{number:0,amount:0,text:'收入'},{number:0,amount:0,text:'支出'}],
        dialogVisible: false,
        dialogTitle: '',
        changedProjectId: 0,
        tableData: [],
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
      };
    },
    computed: {
      ...mapGetters([
        'getProjectInfo'
      ])
    },
    methods: {
      switchDialog(title, accountType) {
        this.dialogVisible = true;
        this.dialogTitle = title;
        this.transfer.accountType = accountType;
      },
      handleSizeChange(size) {
        this.pagination.pageSize = size;
        this.getTableData();
      },
      handleCurrentChange(currentPage) {
        this.pagination.currentPage = currentPage;
        this.getTableData()
      },
      getParams() {
        return {
          accountType: this.dataForm.accountType,
          flowType: this.dataForm.accountFlow,
          projectId: this.changedProjectId,
          dateStartStr: this.dataForm.accountDate[0],
          dateEndStr: this.dataForm.accountDate[1],
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
      },
      // 表格流水数据
      getTableData() {
        let queryParams = this.getParams();
        this.$http.get('/supplier/reserved/flow', {
          params: queryParams
        }).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (returnCode === 0 && status === 200) {
            this.tableData = list;
            this.pagination.total = total;
          }
        }).catch(function (err) {
          console.log(err);
        })
      },
      // 账户余额
      getAccountAmount() {
        let amountParams = {
          projectId: this.changedProjectId,
        };
        this.$http.get('/supplier/reserved/accountAmount', {
          params: amountParams,
        }).then((res) => {
          let {data: {data, message, returnCode}} = res;
          if (returnCode === 0) {
            this.discount[0].discountNumber = data['reservedSalesDifferenceAccountStr'];
            this.discount[1].discountNumber = data['reservedDiscountAccountStr'];
            this.discount[2].discountNumber = data['reservedPurchaseAccountStr'];
          } else {
            console.log(message)
          }
        }).catch(function (err) {
          console.log(err);
        })
      },
      // 统计流水金额
      getSubtotal() {
        let queryParams = this.getParams();
        this.$http.get('/supplier/reserved/getSubtotal', {
          params: queryParams
        }).then((res) => {
          let {data:{data,message,returnCode},status} = res;
          if(returnCode === 0 && status === 200){
            this.amountCount = [{number:data['incomeQuantity'],amount:data['incomeAmountStr']},{number:data['expenditureQuantity'],amount:data['expenditureAmountStr']}];
          }else{
            this.$globalEvent.showMessage({message});
          }
        }).catch(function (err) {
          console.log(err);
        })
      },
      // 导出查询流水
      exportFlow() {

      }
    },
    watch: {
      getProjectInfo(newProjectId) {
        this.changedProjectId = newProjectId;
      }
    },
    mounted(){
      this.dataForm.accountType = this.accountType[0].accountCode;
    },
    activated() {
      eventBus.$on('loadingDefaultProjectId', () => {
        this.changedProjectId = this.getProjectInfo;
        if (this.changedProjectId) {
          this.getTableData();
          this.getAccountAmount();
        }
      });
    }
  }
</script>


