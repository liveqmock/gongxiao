<template>
  <div class="new-purchase-plan" id="new-purchase-plan">
    <div class="new-purchase-plan-form">
      <el-form :inline="true" :model="dataForm" class="" labelWidth="130px">
        <div>
          <el-row>
            <el-form-item label="YH现金账户余额 ">
              <el-input v-model="dataForm.purchaseOrderNumber" disabled clearable></el-input>
            </el-form-item>
            <el-form-item label="上账返利账户余额">
              <el-input v-model="dataForm.supplierOrderNumber" disabled clearable></el-input>
            </el-form-item>
            <el-form-item label="上账代垫账户余额">
              <el-input v-model="dataForm.supplierOrderNumber" disabled clearable></el-input>
            </el-form-item>
          </el-row>
        </div>

        <div label-position="right">
          <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
          <div class="essentialInfo" id="tableDate" >
            <el-row>
              <el-form-item label="供应商" required>
                <el-select v-model="dataForm.test1" placeholder="请选择">
                  <el-option label="飞利浦" value="shanghai"></el-option>
                  <el-option label="京东" value="beijing"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="付款方式">
                <el-select v-model="dataForm.test2" placeholder="请选择">
                  <el-option label="飞利浦" value="shanghai"></el-option>
                  <el-option label="京东" value="beijing"></el-option>
                </el-select>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="采购指导金额">
                <el-input v-model="dataForm.test" placeholder="采购指导金额" disabled clearable></el-input>
              </el-form-item>
              <el-form-item label="采购预留金额">
                <el-input v-model="dataForm.test" placeholder="采购预留金额" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="单价折扣金额">
                <el-input v-model="dataForm.test" placeholder="单价折扣金额" clearable></el-input>
              </el-form-item>
              <el-form-item label="预用现金折扣金额">
                <el-input v-model="dataForm.test" placeholder="预用现金折扣金额" disabled clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="计划应付金额">
                <el-input v-model="dataForm.test" placeholder="计划应付金额" clearable></el-input>
              </el-form-item>
              <el-form-item label="返利预用金额 ">
                <el-input v-model="dataForm.test" placeholder="返利预用金额 " clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="代垫预用金额 ">
                <el-input v-model="dataForm.test" placeholder="代垫预用金额 " clearable></el-input>
              </el-form-item>
              <el-form-item label="使用现金">
                <el-input v-model="dataForm.test" placeholder="使用现金" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="备注" >
                <el-input type="textarea" v-model="dataForm.test" style="width: 588px;"></el-input>
              </el-form-item>
            </el-row>
          </div>
        </div>

        <div>
          <el-row>
            <div style="float: left;">
              <el-popover placement="right" trigger="click">
                <el-transfer filterable :filter-method="filterMethod" filter-placeholder="请输入城市拼音" v-model="value2"
                             :data="productTableData"></el-transfer>
                <el-button slot="reference">添加商品</el-button>
              </el-popover>
            </div>
          </el-row>
        </div>

        <div class="new-purchase-plan-table" id="new-purchase-plan-table">
          <el-table  :data="tableData">
            <el-table-column align="center" header-align="center" prop="productCode" label="型号"></el-table-column>
            <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
            <el-table-column align="center" header-align="center" prop="guidePrice" label="采购指导价"></el-table-column>
            <el-table-column align="center" header-align="center" prop="quantity" label="采购数量/个"></el-table-column>
            <el-table-column align="center" header-align="center" prop="purchasePrice" label="单价折扣"></el-table-column>
            <el-table-column align="center" header-align="center" prop="costPrice" label="采购价"></el-table-column>
            <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
          </el-table>
        </div>

        <div clasee="exportBtn" id="exportBtn">
          <el-button type="primary" @click="preservation()" style=" margin-left: 40%">保存</el-button>
          <el-button type="primary" @click="saveAndSubmit()" style=" margin-left: 3%">保存并提交</el-button>
          <el-button type="button" onclick="cancel()" style=" margin-left: 3%">取消</el-button>
        </div>
      </el-form>
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
          test: ""
        },
        tableInfo:{
          purchaseNumebr:0,
          productKinds:0
        }
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo'
      ])
    },
    methods: {},
    watch: {},
    mounted() {
    },
    created() {
    }
  }
</script>

<style scoped lang="less">

</style>
