<template>
  <div class="yhglobal-account-detail" id="yhglobal-account-detail">
    <div class="supplier-frozen-count-form">
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
    <div class="supplier-frozen-discount-input account-list">
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

    <div class="yhglobal-account-detail-flow-table">
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
    name: "YhglobalAccountDetail",
    data() {
      return {
        dataForm: {
          accountType: 0,
          accountFlow: '',
          accountDate: ''
        },
        accountType: [{accountCode: 8, label: '返利实收账户'}, {accountCode: 9, label: '代垫实收账户'},{accountCode: 10, label: '返利过账账户'}, {accountCode: 11, label: '代垫过账账户'}],
        accountFlow: [{flowNo: 306, label: '收入'}, {flowNo: 305, label: '支出'}],
        discount: [{title: "返利实收账户", accountType: 8, discountNumber: 0}, {title: "代垫实收账户", accountType: 9, discountNumber: 0}, {title: "返利过账账户", accountType: 10, discountNumber: 0}, {title: "代垫过账账户", accountType: 11, discountNumber: 0}],
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
        this.$http.get('/payment/yhglobal/flow', {
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
        this.$http.get('/payment/yhglobal/accountAmount', {
          params: amountParams,
        }).then((res) => {
          let {data: {data, message, returnCode}} = res;
          if (returnCode === 0) {
            this.discount[0].discountNumber = data['couponAmountDoubleStr'];
            this.discount[1].discountNumber = data['prepaidAmountDoubleStr'];
            this.discount[2].discountNumber = data['couponBufferAmountDoubleStr'];
            this.discount[3].discountNumber = data['prepaidBufferAmountDoubleStr'];
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
        this.$http.get('/payment/yhglobal/getSubtotal', {
          params: queryParams
        }).then((res) => {
          let {data:{data,message,returnCode},status} = res;
          if(returnCode === 0 && status === 200){
            this.amountCount = [{number:data['incomeQuantity'],amount:data['incomeAmountStr']},{number:data['expenditureQuantity'],amount:data['expenditureAmountStr']}];
          }else{
            this.$showMessage.showMessage({message});
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
