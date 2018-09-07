<template>
  <div class="product-added" id="product-added">
    <el-form :model="dataForm" label-width="150px" class="demo-ruleForm" clearable>
      <!--1.基本信息-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
        <div class="essentialInfo" id="essentialInfo">
          <el-row>
            <el-form-item label="入库单号" prop="gongxiaoInboundOrderNo">
              <el-input v-model="dataForm.gongxiaoInboundOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="入库通知单号" prop="purchaseOrderNo">
              <el-input v-model="dataForm.purchaseOrderNo" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="单据类型" prop="inboundType">
              <el-input v-model="dataForm.inboundType" disabled></el-input>
            </el-form-item>
            <el-form-item label="WMS入库单号" prop="wmsInboundOrderNo">
              <el-input v-model="dataForm.wmsInboundOrderNo" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采购订单号" prop="purchaseOrderNo">
              <el-input v-model="dataForm.purchaseOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="供应商号" prop="supplier">
              <el-input v-model="dataForm.supplier" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="TMS单号" prop="brandName">
              <el-input v-model="dataForm.brandName" disabled></el-input>
            </el-form-item>
            <el-form-item label="EAS入库单号" prop="easInboundOrderNo">
              <el-input v-model="dataForm.easInboundOrderNo" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="更新时间" prop="createTime">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="收货仓库" prop="warehouseName">
              <el-input v-model="dataForm.warehouseName" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="批次号" prop="batchNo">
              <el-input v-model="dataForm.batchNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="入库日期" prop="createTime">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="收获地址" prop="warehouseName">
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
          <el-table-column prop="inventoryType" label="品质" width="150"></el-table-column>
          <el-table-column prop="inStockQuantity" label="入库数量" width="150"></el-table-column>
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
          gongxiaoInboundOrderNo: '',
          purchaseOrderNo: '',
          inboundType: '',
          wmsInboundOrderNo: '',
          supplier: '',
          brandName: '',
          easInboundOrderNo: '',
          createTime: '',
          warehouseName: '',
          batchNo: ''
        },
        tableData : [{}]
      };
    },
    methods: {
      getTableData(){
        let queryParams = {
          gongxiaoInboundOrderNo: 'POIN2018073018584000',
          wmsInboundOrderNo : 'TI18073001A'
        };
        this.$http.get('/storage/warehousereceipt/selectInboundOrderByOrderNo', {params:queryParams}).then((res) => {
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
        this.$http.get('/storage/warehousereceipt/selectInboundOrderInfoByOrderNo', {params:queryParams}).then((res) => {
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
    },
    activated() {
      if (!this.tableData.length) {
        this.getTableData();
      }
    }
  }
</script>


