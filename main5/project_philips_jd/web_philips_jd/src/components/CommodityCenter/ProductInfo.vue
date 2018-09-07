<template>
  <div class="product-added" id="product-added">
    <el-form :model="productManagement" :rules="rules" label-width="100px" class="demo-ruleForm" clearable>
      <!--1.基本信息-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
        <div class="essentialInfo" id="essentialInfo" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="货品名称" prop="goodsName">
              <el-input v-model="productManagement.goodsName" clearable style="width: 246%;" disabled
                        placeholder="请填写'型号+一个空格+货品名称'，例如：'PQ216/18飞利浦电动剃须刀'"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="规格型号" prop="specificationMode">
              <el-input v-model="productManagement.specificationMode" disabled clearable
                        placeholder="飞利浦项目务必填写12NC"></el-input>
            </el-form-item>
            <el-form-item label="EAS物料编码" prop="EASCode">
              <el-input v-model="productManagement.EASCode" clearable placeholder="EAS物料编码" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="WMS编码" prop="WMSCoding">
              <el-input v-model="productManagement.WMSCoding" clearable style="width: 246%;" disabled
                        placeholder="对应仓库的物料编码"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="客户SKU编码" prop="customerSKUCoding">
              <el-input v-model="productManagement.customerSKUCoding" style="width: 246%;" clearable disabled
                        placeholder="销售端对接的SKU编码,例如京东SKU编码，如果一个型号对应多个京东SKU编码，用英文逗号隔开"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="客户商品编码" prop="customerCommodityCoding">
              <el-input v-model="productManagement.customerCommodityCoding" style="width: 246%;" clearable
                        disabled
                        placeholder="销售端Excel导入的商品编码,例如京东商品编码，如果一个型号对应多个京东商品编码，用英文逗号隔开"></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>
      <!--2.价格品牌税率-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">价格品牌税率</h4>
        <div class="taxRate" id="taxRate" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="采购指导价" prop="purchasingGuidancePrice">
              <el-input v-model="productManagement.purchasingGuidancePrice" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="税率" style="margin-left: -9px;" prop="taxRate">
              <el-input v-model="productManagement.taxRate" disabled clearable style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="税率分类编码" style="padding-left: 5px;" prop="taxonomyTaxRate">
              <el-input v-model="productManagement.taxonomyTaxRate" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="销售指导价" prop="salesGuidancePrice">
              <el-input v-model="productManagement.salesGuidancePrice" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="实销返点" style="margin-left: -9px;" prop="realSalesRebate">
              <el-input v-model="productManagement.realSalesRebate" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="应销返点" style="padding-left: 5px;" prop="pinBackPoint">
              <el-input v-model="productManagement.pinBackPoint" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="厂送返利点" prop="factorySendRebatePoint">
              <el-input v-model="productManagement.factorySendRebatePoint" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="成本价" style="margin-left: -9px;" prop="costPrice">
              <el-input v-model="productManagement.costPrice" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="出货价" style="padding-left: 5px;" prop="exportPrice">
              <el-input v-model="productManagement.exportPrice" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="销售支持折扣" prop="salesSupportDiscounts">
              <el-input v-model="productManagement.salesSupportDiscounts" disabled clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="" prop="generatingRebatesPurchasePrice">
              <el-checkbox v-model="productManagement.generatingRebatesPurchasePrice" disabled
                           style="width: 110px;">按采购价生成返利
              </el-checkbox>
            </el-form-item>
          </el-row>
        </div>
      </div>
      <!--3.货品属性-销售单元-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">货品属性-销售单元</h4>
        <div class="essentialInfo" id="essentialInfo3" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="长" prop="salelong">
              <el-input v-model="productManagement.salelong" disabled placeholder="mm" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="宽" style="margin-left: -9px;" prop="salewide">
              <el-input v-model="productManagement.salewide" disabled placeholder="mm" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="高" style="padding-left: 5px;" prop="salehigh">
              <el-input v-model="productManagement.salehigh" disabled placeholder="mm" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="重量" prop="saleweight">
              <el-input v-model="productManagement.saleweight" disabled placeholder="g" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>
      <!--4.货品属性-运输单元-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">货品属性-运输单元</h4>
        <div class="essentialInfo" id="essentialInfo4" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="长" prop="transportlong">
              <el-input v-model="productManagement.transportlong" disabled placeholder="mm" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="宽" style="margin-left: -9px;" prop="transportwide">
              <el-input v-model="productManagement.transportwide" disabled placeholder="mm" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="高" style="padding-left: 5px;" prop="transporthigh">
              <el-input v-model="productManagement.transporthigh" disabled placeholder="mm" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="重量" prop="transportweight">
              <el-input v-model="productManagement.transportweight" disabled placeholder="g" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
            <el-form-item label="箱装数" style="margin-left: -9px;" prop="transportBoxNumber">
              <el-input v-model="productManagement.transportBoxNumber" disabled placeholder="mm" clearable
                        style="width: 110px;"></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>
      <!--5.操作日志-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">操作日志</h4>
        <div class="essentialInfo" id="essentialInfo5" style="padding-left: 7%;">
          <el-table :data="tableData" style="width: 61%">
            <el-table-column prop="describe" label="描述" align="center"></el-table-column>
            <el-table-column prop="operationTime" label="操作时间" align="center"></el-table-column>
            <el-table-column prop="operator" label="操作人" align="center"></el-table-column>
          </el-table>
        </div>
      </div>
      <!--操作-->
      <div clasee="exportBtn block" id="btn" style="padding-left: 33%; padding-top: 20px;">
        <el-row>
          <el-button type="primary" @click="onReturn()">返回</el-button>
        </el-row>
      </div>
    </el-form>

  </div>
</template>

<script>
  import ElSpinner from "../../../node_modules/element-ui/packages/spinner/src/spinner.vue";
  import ElRow from "element-ui/packages/row/src/row";
  import ElForm from "../../../node_modules/element-ui/packages/form/src/form.vue";

  export default {
    components: {
      ElForm,
      ElRow,
      ElSpinner
    },
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
        rules: {
          goodsName: [{required: true, message: '请填写货品名称', trigger: 'change'}],
          specificationMode: [{required: true, message: '请填写规格型号', trigger: 'change'}],
          WMSCoding: [{required: true, message: '请填写WMS编码', trigger: 'change'}],
          purchasingGuidancePrice: [{required: true, message: '请填写采购指导价', trigger: 'change'}],
          taxRate: [{required: true, message: '请填写税率', trigger: 'change'}],
          salesGuidancePrice: [{required: true, message: '请填写销售指导价', trigger: 'change'}]
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
