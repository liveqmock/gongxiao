<template>
  <!--1:查询条件-->
  <div class="product-added" id="product-added">
    <el-form :model="productManagement" :rules="rules" label-width="100px" class="demo-ruleForm" clearable>
      <!--1.基本信息-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">退货单详情</h4>
        <div clasee="exportBtn block" id="exportBtns" style="background-color: #F5F5F5;">
          <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">生成代垫</el-button>
          <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">报废</el-button>
          <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">采购换货</el-button>
        </div>

        <div class="project-management-table" id="project-management-table">
          <el-table height="250" style="width: 100%" :data="tableData">
            <el-table-column align="center" header-align="center" prop="goodsName" label="客户SKU"></el-table-column>
            <el-table-column align="center" header-align="center" prop="model" label="型号"></el-table-column>
            <el-table-column align="center" header-align="center" prop="WMSCoding" label="货品名称"></el-table-column>
            <el-table-column align="center" header-align="center" prop="project" label="批次号"></el-table-column>
            <el-table-column align="center" header-align="center" prop="state" label="出库单号"></el-table-column>
            <el-table-column align="center" header-align="center" prop="goodsName" label="采购价"></el-table-column>
            <el-table-column align="center" header-align="center" prop="model" label="成本价"></el-table-column>
            <el-table-column align="center" header-align="center" prop="WMSCoding" label="销售指导价"></el-table-column>
            <el-table-column align="center" header-align="center" prop="project" label="出货价"></el-table-column>
            <el-table-column align="center" header-align="center" prop="state" label="退货数量"></el-table-column>
            <el-table-column align="center" header-align="center" prop="goodsName" label="退货价"></el-table-column>
            <el-table-column align="center" header-align="center" prop="model" label="退货总金额"></el-table-column>
            <el-table-column align="center" header-align="center" prop="goodsName" label="实收数量"></el-table-column>
            <el-table-column align="center" header-align="center" prop="model" label="库存品质"></el-table-column>
            <el-table-column align="center" header-align="center" prop="WMSCoding" label="描述"></el-table-column>
          </el-table>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">客户退货及物流信息</h4>
        <div class="essentialInfo" id="essentialInfo3" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="退货人">
              <el-input v-model="productManagement.salelong" placeholder="退货人" clearable></el-input>
            </el-form-item>
            <el-form-item label="退货发出仓库">
              <el-select v-model="productManagement.salelong" placeholder="请选择" >
                <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
                <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="运输方式">
              <el-select v-model="productManagement.salelong" placeholder="请选择" >
                <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
                <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="退回仓库">
              <el-select v-model="productManagement.salelong" placeholder="请选择" >
                <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
                <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="详细地址">
              <el-input v-model="productManagement.salelong" placeholder="详细地址" clearable></el-input>
            </el-form-item>
            <el-form-item label="仓库地址">
              <el-input v-model="productManagement.salelong" placeholder="仓库地址" clearable></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="手机">
              <el-input v-model="productManagement.salelong" placeholder="手机" clearable></el-input>
            </el-form-item>
            <el-form-item label="物流运单号">
              <el-input v-model="productManagement.salelong" placeholder="物流运单号" clearable></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div clasee="exportBtn block" id="btn" style="padding-left: 26%; padding-top: 20px;">
        <el-row>
          <el-button type="primary" @click="onSubmit()">保存</el-button>
          <el-button type="primary" @click="onSaveAndSubmit()">保存并提交</el-button>
          <el-button type="button" @click="onCancel()">取消</el-button>
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
        }
      }
    },
    methods: {
      onSubmit() {
      },
      onSaveAndSubmit() {
      },
      onCancel() {
      }
    }
  }
</script>
