<template>
  <div class="product-added" id="product-added">
    <div label-position="right">
      <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">订单信息</h4>
      <div class="essentialInfo" id="essentialInfo" style="padding-left: 7%;">
      </div>
    </div>

    <el-form :model="productManagement" :rules="rules" label-width="100px" class="demo-ruleForm" clearable>
      <div clasee="exportBtn" id="exportBtn">
        <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">导出明细</el-button>
        <el-form-item label="发货仓库">
          <el-select v-model="productManagement.taxRate" placeholder="请选择" style="width: 125px;">
            <el-option label="请选择" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="test"></el-option>
            <el-option label="飞利浦厨卫京东2" value="test"></el-option>
          </el-select>
        </el-form-item>
      </div>

      <div class="project-management-table" id="project-management-table">
        <el-table height="250" style="width: 100%" :data="tableData">
          <el-table-column align="center" header-align="center" prop="goodsName" label="京东SKU编码"></el-table-column>
          <el-table-column align="center" header-align="center" prop="model" label="货品名称"></el-table-column>
          <el-table-column align="center" header-align="center" prop="WMSCoding" label="型号"></el-table-column>
          <el-table-column align="center" header-align="center" prop="project" label="销售指导价"></el-table-column>
          <el-table-column align="center" header-align="center" prop="state" label="良品库存"></el-table-column>
          <el-table-column align="center" header-align="center" prop="goodsName" label="北京仓"></el-table-column>
          <el-table-column align="center" header-align="center" prop="model" label="望亭仓"></el-table-column>
          <el-table-column align="center" header-align="center" prop="WMSCoding" label="武汉仓"></el-table-column>
          <el-table-column align="center" header-align="center" prop="project" label="订购数量"></el-table-column>
          <el-table-column align="center" header-align="center" prop="state" label="确认数量"></el-table-column>
          <el-table-column align="center" header-align="center" prop="goodsName" label="京东采购价"></el-table-column>
          <el-table-column align="center" header-align="center" prop="model" label="出货小计"></el-table-column>
          <el-table-column align="center" header-align="center" prop="WMSCoding" label="不满足原因"></el-table-column>
        </el-table>
      </div>
      <div style="float: right;">
        <span>出货金额:1000000</span>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">客户收货信息</h4>
        <div class="taxRate" id="taxRate" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="收货人" prop="purchasingGuidancePrice">
              <el-input v-model="productManagement.purchasingGuidancePrice" clearable style="width: 150px;"></el-input>
            </el-form-item>
            <el-form-item label="手机" style="margin-left: -9px;" prop="taxRate">
              <el-input v-model="productManagement.taxRate" clearable style="width: 150px;"></el-input>
            </el-form-item>
            <el-form-item label="发货仓" style="padding-left: 5px;" prop="taxonomyTaxRate">
              <el-input v-model="productManagement.taxonomyTaxRate" clearable style="width: 300px;"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="地址" prop="salesGuidancePrice" required>
              <el-input v-model="productManagement.salesGuidancePrice" clearable style="width: 390px;"></el-input>
            </el-form-item>
            <el-form-item label="收货公司" style="margin-left: 6px;" prop="realSalesRebate">
              <el-input v-model="productManagement.realSalesRebate" clearable style="width: 300px;"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="详细地址" prop="factorySendRebatePoint" required>
              <el-input v-model="productManagement.factorySendRebatePoint" clearable style="width: 796px;"></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div clasee="exportBtn block" id="btn1" style="padding-left: 26%; padding-top: 20px;">
        <el-row>
          <el-button type="primary" @click="onSubmit()">修改收货信息</el-button>
          <el-button type="primary" @click="onSaveAndSubmit()">审核</el-button>
          <el-button type="primary" @click="onSaveAndSubmit()">驳回</el-button>
          <el-button type="button" @click="onCancel()">返回</el-button>
        </el-row>
      </div>
    </el-form>

    <div label-position="right">
      <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">操作日志</h4>
      <div class="essentialInfo" id="essentialInfo5" style="padding-left: 7%;">
        <el-table :data="tableData" style="width: 61%">
          <el-table-column prop="operator" label="操作人" align="center"></el-table-column>
          <el-table-column prop="describe" label="操作功能" align="center"></el-table-column>
          <el-table-column prop="operationTime" label="操作时间" align="center"></el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
  import ElSpinner from "../../../node_modules/element-ui/packages/spinner/src/spinner.vue";
  import ElRow from "element-ui/packages/row/src/row";
  import ElForm from "../../../node_modules/element-ui/packages/form/src/form.vue";

  export default {
    data() {
      return {
        productManagement: {
          goodsName: '',
          specificationMode: '',
          EASCode: '',
          WMSCoding: '',
          customerSKUCoding: '',
          customerCommodityCoding: '',
          purchasingGuidancePrice: '',
          taxRate: '',
          taxonomyTaxRate: '',
          salesGuidancePrice: '',
          realSalesRebate: '',
          pinBackPoint: '',
          factorySendRebatePoint: '',
          costPrice: '',
          exportPrice: '',
          salesSupportDiscounts: '',
          generatingRebatesPurchasePrice: '',
          salelong: '',
          salewide: '',
          salehigh: '',
          saleweight: '',
          transportlong: '',
          transportwide: '',
          transporthigh: '',
          transportweight: '',
          transportBoxNumber: ''
        },
        tableData: [{
          describe: '更新货品',
          operationTime: '2017-06-30 17:58:10',
          operator: '黄佳林'
        }, {
          describe: '更新货品',
          operationTime: '2017-06-30 17:58:10',
          operator: '黄佳林'
        }]
      }
    },
    methods: {
      onReturn() {
      }
    }
  }
</script>
