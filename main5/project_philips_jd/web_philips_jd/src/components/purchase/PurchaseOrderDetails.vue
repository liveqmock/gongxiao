<template>
  <div class="purchase-order-details" id="purchase-order-details">
    <div class="purchase-order-details-form">
      <el-form :inline="true" :model="dataForm" class="" labelWidth="130px">
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
              <el-form-item label="仓库" required>
                <el-select v-model="dataForm.test2" placeholder="请选择">
                  <el-option label="飞利浦" value="shanghai"></el-option>
                  <el-option label="京东" value="beijing"></el-option>
                </el-select>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="物流方式" required>
                <el-select v-model="dataForm.test2" placeholder="请选择">
                  <el-option label="飞利浦" value="shanghai"></el-option>
                  <el-option label="京东" value="beijing"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="收货地址" required>
                <el-input v-model="dataForm.test" placeholder="收货地址" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="采购方式" required>
                <el-select v-model="dataForm.test2" placeholder="请选择">
                  <el-option label="飞利浦" value="shanghai"></el-option>
                  <el-option label="京东" value="beijing"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="业务日期" required>
                <el-input v-model="dataForm.test" placeholder="业务日期" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="供应商是否开票" required>
                <el-select v-model="dataForm.test2" placeholder="请选择">
                  <el-option label="飞利浦" value="shanghai"></el-option>
                  <el-option label="京东" value="beijing"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="供应商商订单号" required>
                <el-input v-model="dataForm.test" placeholder="供应商商订单号" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="合同参考号" required>
                <el-input v-model="dataForm.test" placeholder="合同参考号" clearable></el-input>
              </el-form-item>
              <el-form-item label="要求到货日期" required>
                <el-input v-model="dataForm.test" placeholder="要求到货日期" clearable></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="到货截止日期">
                <el-input v-model="dataForm.test" placeholder="到货截止日期" clearable></el-input>
              </el-form-item>
              <el-form-item label="备注" >
                <el-input type="textarea" v-model="dataForm.test" style="width: 215px;"></el-input>
              </el-form-item>
            </el-row>
          </div>
        </div>

        <div label-position="right">
          <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">货品信息</h4>
          <div class="purchase-order-details-table" id="purchase-order-details-table">
            <el-table  :data="tableData">
              <el-table-column align="center" header-align="center" prop="productCode" label="型号"></el-table-column>
              <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
              <el-table-column align="center" header-align="center" prop="guidePrice" label="采购指导价"></el-table-column>
              <el-table-column align="center" header-align="center" prop="quantity" label="采购数量/个"></el-table-column>
              <el-table-column align="center" header-align="center" prop="purchasePrice" label="单价折扣"></el-table-column>
              <el-table-column align="center" header-align="center" prop="costPrice" label="使用返利"></el-table-column>
              <el-table-column align="center" header-align="center" prop="operation" label="使用代垫"></el-table-column>
              <el-table-column align="center" header-align="center" prop="purchasePrice" label="现金折扣"></el-table-column>
              <el-table-column align="center" header-align="center" prop="costPrice" label="采购价"></el-table-column>
              <el-table-column align="center" header-align="center" prop="operation" label="成本价"></el-table-column>
              <el-table-column align="center" header-align="center" prop="costPrice" label="进货价"></el-table-column>
            </el-table>
          </div>
        </div>

        <div label-position="right">
          <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">操作日志</h4>
          <div class="purchase-order-details-table" id="purchase-order-details-table2">
            <el-table  :data="tableData">
              <el-table-column align="center" header-align="center" prop="productCode" label="描述"></el-table-column>
              <el-table-column align="center" header-align="center" prop="productName" label="操作时间"></el-table-column>
              <el-table-column align="center" header-align="center" prop="guidePrice" label="操作人"></el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
    </div>

    <div>
      <el-button type="button" onclick="cancel()" style=" margin-left: 50%">返回</el-button>
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
