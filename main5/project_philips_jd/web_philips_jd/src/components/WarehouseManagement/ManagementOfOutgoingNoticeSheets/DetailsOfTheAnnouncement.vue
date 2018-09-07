<template>
  <div class="product-added" id="product-added">
    <el-form :model="dataForm" label-width="100px" class="demo-ruleForm" clearable>
      <!--1.基本信息-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
        <div class="essentialInfo" id="essentialInfo">
          <el-row>
            <el-form-item label="出库单号" prop="gongxiaoOutboundOrderNo">
              <el-input v-model="dataForm.gongxiaoOutboundOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="销售单号" prop="salesOrderNo">
              <el-input v-model="dataForm.salesOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="出库类型" prop="outStorageType" style="width: 220px;">
              <el-input v-model="dataForm.outStorageType" disabled></el-input>
            </el-form-item>
            <el-form-item label="单据状态" prop="orderStatus" style="width: 220px;">
              <el-input v-model="dataForm.orderStatus" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="发货仓库" prop="deliverWarehouse">
              <el-input v-model="dataForm.deliverWarehouse" disabled></el-input>
            </el-form-item>
            <el-form-item label="批次号" prop="bachNo">
              <el-input v-model="dataForm.bachNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="供应商" prop="supplier" style="width: 440px;">
              <el-input v-model="dataForm.supplier" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="发货地址" prop="deliverAddress" style="width: 600px;">
              <el-input v-model="dataForm.deliverAddress" disabled></el-input>
            </el-form-item>
            <el-form-item label="客户" prop="customer" style="width: 600px;">
              <el-input v-model="dataForm.customer" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="更新时间" prop="lastUpdateTime" style="width: 440px;">
              <el-input v-model="dataForm.lastUpdateTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="预计出库日期" prop="estimateOutTime">
              <el-input v-model="dataForm.estimateOutTime" disabled></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">客户收货信息</h4>
        <div class="essentialInfo">
          <el-row>
            <el-form-item label="客户联系人" prop="contectPeople">
              <el-input v-model="dataForm.contectPeople" disabled></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="phoneNum">
              <el-input v-model="dataForm.phoneNum" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="收货地址" prop="receiveAdrress" style="width: 600px;">
              <el-input v-model="dataForm.receiveAdrress" disabled></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">运输信息</h4>
        <div class="essentialInfo">
          <el-row>
            <el-form-item label="运输商名称" prop="transporter">
              <el-input v-model="dataForm.transporter" disabled></el-input>
            </el-form-item>
            <el-form-item label="运单号" prop="tmsOrdNo">
              <el-input v-model="dataForm.tmsOrdNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="运输状态" prop="transportStatus">
              <el-input v-model="dataForm.transportStatus" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="签收人" prop="tmsSignedBy">
              <el-input v-model="dataForm.tmsSignedBy" disabled></el-input>
            </el-form-item>
            <el-form-item label="签收人电话" prop="tmsSignedPhone">
              <el-input v-model="dataForm.tmsSignedPhone" disabled></el-input>
            </el-form-item>
            <el-form-item label="签收时间" prop="tmsSingedTime">
              <el-input v-model="dataForm.tmsSingedTime" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="备注" prop="remark" style="width: 600px;">
              <el-input type="textarea" :rows="2" v-model="dataForm.remark" disabled> </el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">明细信息</h4>
        <el-table :data="tableData1" stripe style="width: 100%">
          <el-table-column prop="outboundOrderItemNo" label="出库明细单号"></el-table-column>
          <el-table-column prop="productName" label="货品名称"></el-table-column>
          <el-table-column prop="batchNo" label="批次" width="150"></el-table-column>
          <el-table-column prop="productCode" label="型号" width="150"></el-table-column>
          <el-table-column prop="inventoryType" label="库存类型" width="150"></el-table-column>
          <el-table-column prop="planOutStockQuantity" label="计划出库数量" width="150"></el-table-column>
          <el-table-column prop="actualOutStockQuantity" label="实际出库数量" width="150"></el-table-column>
          <el-table-column prop="differentOutStockQuantity" label="差异数量" width="150"></el-table-column>
        </el-table>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">操作日志</h4>
        <el-table :data="tableData2" stripe style="width: 100%">
          <el-table-column prop="opName" label="操作人"></el-table-column>
          <el-table-column prop="content" label="操作功能"></el-table-column>
          <el-table-column prop="opTime" label="操作时间"></el-table-column>
          <el-table-column prop="opUid" label="操作备注"></el-table-column>
        </el-table>
      </div>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        dataForm: {
          gongxiaoOutboundOrderNo: '',
          salesOrderNo: '',
          outStorageType: '',
          orderStatus: '',
          deliverWarehouse: '',
          bachNo: '',
          supplier: '',
          deliverAddress: '',
          customer: '',
          lastUpdateTime: '',
          createTime: '',
          estimateOutTime: '',
          contectPeople: '',
          phoneNum: '',
          receiveAdrress: '',
          transporter: '',
          tmsOrdNo: '',
          transportStatus: '',
          tmsSignedBy: '',
          tmsSignedPhone: '',
          tmsSingedTime: '',
          remark: ''
        },
        tableData1 : [{}],
        tableData2 : [{}],
      };
    },
    methods: {
      getTableData(){
        let queryParams = {
          //跳转前的入库单号这个要改成动态的
          gongxiaoOutboundOrderNo: 'XPS_shaver_SOOUT2018082816495400'
        };
        this.$http.get('/storage/outstorenotice/selectOutboundOrderBasicInfo', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.dataForm = data;
            this.tableData2 = data.traceLog;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
        this.$http.get('/storage/outstorenotice/selectOutboundOrderDetailed', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.tableData1 = data;
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


