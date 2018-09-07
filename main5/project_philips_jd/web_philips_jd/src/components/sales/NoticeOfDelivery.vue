<template>
  <div class="purchase-management" id="purchase-management">
    <el-form :inline="true" :model="salesInfo" class="">
      <el-row>
        <div class="purchase-management-form">
          <el-form-item label="收货城市">
            <el-cascader :options="options" v-model="selectedOptions" @change="handleChange"></el-cascader>
          </el-form-item>
          <el-form-item label="要求发货日期">
            <el-date-picker v-model="salesInfo.createtime" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="送货方式" prop="region">
            <el-select v-model="salesInfo.orderNo1" placeholder="请选择">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
        </div>
      </el-row>

      <el-row>
        <div>
          <el-popover placement="right" trigger="click">
            <el-transfer filterable :filter-method="filterMethod" filter-placeholder="请输入城市拼音" v-model="value2"
                         :data="data2"></el-transfer>
            <el-button slot="reference">添加商品</el-button>
          </el-popover>
        </div>
      </el-row>

      <div class="purchase-management-table">
        <el-table :data="tableData">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column align="center" stripe header-align="center" :row-class-name="tableRowClassName" prop="Customer purchase number" label="货品名称"></el-table-column>
          <el-table-column align="center" header-align="center" prop="oddNumbers" label="货品编码"></el-table-column>
          <el-table-column align="center" header-align="center" prop="orderAttribute" label="仓库"></el-table-column>
          <el-table-column align="center" header-align="center" prop="orderStatus" label="批次号"></el-table-column>
          <el-table-column align="center" header-align="center" prop="amountOfMoney" label="订单出货数量"></el-table-column>
          <el-table-column align="center" header-align="center" prop="customerInformation" label="剩余需发货数量"></el-table-column>
          <el-table-column align="center" header-align="center" prop="orderTime" label="货品库存"></el-table-column>
          <el-table-column align="center" header-align="center" prop="collectionTime" label="批次可出库存"></el-table-column>
          <el-table-column align="center" header-align="center" prop="quantityRemainingShipments" label="本次预约"></el-table-column>
          <el-table-column align="center" header-align="center" prop="settlementMethod" label="备注"></el-table-column>
          <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
        </el-table>
      </div>
        <div clasee="exportBtn block" id="btn" style="padding-left: 26%; padding-top: 20px;">
          <el-row>
            <el-button type="primary" @click="onSubmit()">提交</el-button>
            <el-button type="button" @click="onCancel()">返回</el-button>
          </el-row>
        </div>

    </el-form>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {ElRow},
    data() {
      return {
        salesInfo: {
          orderNo: "",
          createtime: "",
          orderNo1: ""
        },
        tableData: [{}]
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo'
      ])
    },
    methods: {},
    watch: {},
    created() {

    },
    mounted() {

    }
  }
</script>


