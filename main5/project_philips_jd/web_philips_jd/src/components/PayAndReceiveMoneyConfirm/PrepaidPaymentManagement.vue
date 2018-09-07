<template>
  <div class="supplier-frozen-account" id="supplier-frozen-account">
    <el-form  label-width="100px" class="demo-ruleForm clearfix">
      <el-form-item label="账户类型">
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="资金流向">
        <el-select v-model="value2" placeholder="请选择">
          <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="时间" >
        <el-col :span="11">
          <el-form-item >
            <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-form-item >
            <el-time-picker type="fixed-time" placeholder="选择时间" v-model="ruleForm.date2" style="width: 100%;"></el-time-picker>
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm2')">查询</el-button>
        <el-button @click="resetForm('ruleForm2')">重置</el-button>
      </el-form-item>
    </el-form>


    <div>
      <div>
        <button @click="exportFlow()">新增</button>
      </div>
    </div>
    <div class="supplier-frozen-account-flow-table" id="supplier-frozen-account-flow-table">
      <el-table
        :data="tableData"
      >
        <el-table-column
          align="center"
          header-align="center"
          prop="prepaidNo"
          label="代垫付款单号"
        >
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="infoStatus"
          label="状态"
        >
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="standardCurrencyAmount"
          label="本位币金额">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="receivables"
          label="收款方">
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
          prop="createTime"
          label="创建时间">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="createdById"
          label="创建人">
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="remark"
          label="操作">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SupplierFrozenAccount",
    data() {
      return {
        tableData: [],
        options: [{
          value: '选项1',
          accountCode:0,
          label: '单价折扣'
        }, {
          value: '选项2',
          accountCode:1,
          label: '采购预留'
        }],
        value:'',
        options2: [{
          value: '选项1',
          flowNo:306,
          label: '收入'
        }, {
          value: '选项2',
          flowNo:305,
          label: '支出'
        }],
        value2: '',
        ruleForm: {
          date1: '',
          date2: ''
        },
        ruleForm2: {
          pass: '',
          checkPass: '',
          age: ''
        },
        discount: [
          {
            title: "单价折扣",
            accountCode:0,
            discountNumber: 0,
            discountTransOut: "转出"
          }, {
            title: "采购预留",
            accountCode:1,
            discountNumber: 0,
            discountTransOut: "转出"
          }
        ],
        dialogVisible: false,
        amountValue: 0
      };
    },
    methods: {
      confirmAmountTransforOut(){
        this.dialogVisible = false;
        console.log(this.amountValue);
        // 冻结账户转出
        alert("账户转出了");
        this.$http.get('/supplier/frozen/transferout', {
          params: {
            amountValue:this.amountValue,
            projectId:this.projectId,
            accountCode:this.accountCode
          }
        })
      },
      getSubtotal(){
        // 统计流水金额
        var _this = this;
        this.$http.get('/supplier/frozen/getSubtotal', {
          params: {
            accountCode:this.accountCode,
            flowNo:this.flowNo,
            projectId:this.projectId,
            dateStart:this.dateStart,
            dateEnd:this.dateEnd,
            pageNumber:this.pageIndex,
            pageSize: this.pageSize
          }
        }).then(function (res) {

        }).catch(function (err) {
          console.log(err);
        })
      },
      getTableData() {
        // 加载页面时查询流水填充表格
        var _this = this;
        this.$http.get('/supplier/frozen/flow', {
          params: {
            accountCode:this.accountCode,
            flowNo:this.flowNo,
            projectId:this.projectId,
            dateStart:this.dateStart,
            dateEnd:this.dateEnd,
            pageNumber:this.pageIndex,
            pageSize: this.pageSize
          }
        }).then(function (res) {
          console.log(res.data)
          if (res.data.code === 0) {
            _this.tableData = res.data.result.list;
          }
        }).catch(function (err) {
          console.log(err);
        })
      },
      exportFlow(){
        // 导出查询流水
      },
      getAccountAmount() {
        // 页面加载时获取账户余额
        var _this = this;
        this.$http.get('/supplier/frozen/accountAmount').then(function (res) {
          if (res.data.code === 0) {
            // 把账户余额加载到字段中

          }
        }).catch(function (err) {
          console.log(err);
        })
      }
    },
    mounted: function () {
      this.getTableData();
      this.getAccountAmount();
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

  }
</script>

<style scoped lang="less">
  .clearfix {
    *zoom: 1
  }

  .clearfix:after {
    display: block;
    overflow: hidden;
    clear: both;
    height: 0;
    visibility: hidden;
    content: "."
  }

  .supplier-frozen-account {
    margin-top: 20px;
  }

  .el-form-item {
    float: left;
    .el-input {
      width: 200px;
    }
  }

  .supplier-frozen-discount-input li {
    list-style: none;
    margin-left: 20px;
    width: 300px;
    height: 200px;
    float: left;
    border: 1px solid red;
  }
</style>


