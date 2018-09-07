<template>
  <div class="product-added" id="product-added">
    <el-form :model="dataForm" label-width="150px" class="demo-ruleForm" clearable>
      <!--1.基本信息-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
        <div class="essentialInfo" id="essentialInfo">
          <el-row>
            <el-form-item label="出库单号">
              <el-input v-model="dataForm.gongxiaoOutboundOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="出库通知单号">
              <el-input v-model="dataForm.salesOrderNo" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="单据类型">
              <el-input v-model="dataForm.outboundType" disabled></el-input>
            </el-form-item>
            <el-form-item label="WMS出库单号">
              <el-input v-model="dataForm.wmsOutboundOrderNo" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采购订单号">
              <el-input v-model="dataForm.purchaseOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="供应商号">
              <el-input v-model="dataForm.customer" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="TMS单号">
              <el-input v-model="dataForm.tmsOutboundOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="EAS出库单号">
              <el-input v-model="dataForm.easOutboundOrderNo" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="更新时间">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="发货仓库">
              <el-input v-model="dataForm.warehouseName" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="批次号">
              <el-input v-model="dataForm.batchNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="出库日期">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="发货地址">
              <el-input v-model="dataForm.warehouseName" disabled></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">明细信息</h4>
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column prop="productName" label="货品名称"></el-table-column>
          <el-table-column prop="productCode" label="型号"></el-table-column>
          <el-table-column prop="batchNo" label="批次"></el-table-column>
          <el-table-column prop="inventoryType" label="品质" width="150"></el-table-column>
          <el-table-column prop="outStockQuantity" label="入库数量" width="150"></el-table-column>
        </el-table>
      </div>

    </el-form>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import eventBus from '../../../assets/js/eventBus.js';

  export default {
    data() {
      return {
        dataForm: {
          gongxiaoOutboundOrderNo: '',
          salesOrderNo: '',
          outboundType: '',
          wmsOutboundOrderNo: '',
          purchaseOrderNo: '',
          customer: '',
          tmsOutboundOrderNo: '',
          easOutboundOrderNo: '',
          createTime: '',
          batchNo: '',
          warehouseName: ''
        },
        tableData : [{}]
      };
    },
    methods: {
      getTableData(){
        let queryParams = {
          gongxiaoOutboundOrderNo: 'XPS_shaver_SOOUT2018090418034700',
          wmsOutboundOrderNo  : 'TO180904003'
        };
        this.$http.get('/storage/outboundorder/selectByOutboundNum', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.dataForm = data;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
        this.$http.get('/storage/outboundorder/selectOutboundOrderItem', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.tableData = data;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
      }
    },
    mounted() {
      eventBus.$on('loadingDefaultProjectId', () => {
        this.getTableData();
      });
    }
  }
</script>


