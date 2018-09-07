<template>
  <div class="product-added" id="product-added">
    <el-form :model="dataForm" label-width="100px" class="demo-ruleForm" clearable>
      <!--1.基本信息-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
        <div class="essentialInfo" id="essentialInfo">
          <el-row>
            <el-form-item label="入库通知单号" prop="gongxiaoInboundOrderNo">
              <el-input v-model="dataForm.gongxiaoInboundOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="采购单号" prop="purchaseOrderNo">
              <el-input v-model="dataForm.purchaseOrderNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="入库类型" prop="inboundType" style="width: 220px;">
              <el-input v-model="dataForm.inboundType" disabled></el-input>
            </el-form-item>
            <el-form-item label="单据状态" prop="orderStatus" style="width: 220px;">
              <el-input v-model="dataForm.orderStatus" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="收货仓库" prop="warehouseName">
              <el-input v-model="dataForm.warehouseName" disabled></el-input>
            </el-form-item>
            <el-form-item label="批次号" prop="batchNo">
              <el-input v-model="dataForm.batchNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="供应商" prop="brandName" style="width: 440px;">
              <el-input v-model="dataForm.brandName" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="收货地址" prop="warehouseDetailAddress" style="width: 600px;">
              <el-input v-model="dataForm.warehouseDetailAddress" disabled></el-input>
            </el-form-item>
            <el-form-item label="更新时间" prop="lastUpdateTime" style="width: 440px;">
              <el-input v-model="dataForm.lastUpdateTime" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="创建时间" prop="createTime">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="预计出库日期" prop="createTime">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">供应商发货信息</h4>
        <div class="essentialInfo">
          <el-row>
            <el-form-item label="发货联系人" prop="contactsPeople">
              <el-input v-model="dataForm.contactsPeople" disabled></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="phoneNum">
              <el-input v-model="dataForm.phoneNum" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="发货地址" prop="deliverAddress" style="width: 600px;">
              <el-input v-model="dataForm.deliverAddress" disabled></el-input>
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
            <el-form-item label="运单号" prop="transportNum">
              <el-input v-model="dataForm.transportNum" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="备注" prop="transportStatus" style="width: 600px;">
              <el-input type="textarea" :rows="2" v-model="dataForm.transportStatus" disabled> </el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">明细信息</h4>
        <el-table :data="tableData1" stripe style="width: 100%">
          <el-table-column prop="productName" label="货品名称"></el-table-column>
          <el-table-column prop="productCode" label="型号"></el-table-column>
          <el-table-column prop="planInStockQuantity" label="计划入库数量" width="150"></el-table-column>
          <el-table-column prop="actualInStockQuantity" label="实际入库数量" width="150"></el-table-column>
          <el-table-column prop="nonImperfectQuantity" label="良品" width="150"></el-table-column>
          <el-table-column prop="imperfectQuantity" label="残次品" width="150"></el-table-column>
          <el-table-column prop="differentQuantity" label="差异数量" width="150"></el-table-column>
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
          gongxiaoInboundOrderNo: '',
          purchaseOrderNo: '',
          inboundType: '',
          orderStatus: '',
          warehouseName: '',
          batchNo: '',
          brandName: '',
          warehouseDetailAddress: '',
          lastUpdateTime: '',
          createTime: '',
          estimateOutTime: '',
          contactsPeople: '',
          phoneNum: '',
          nameOfTransporter: '',
          waybillNumber: '',
          remarks: ''
        },
        tableData1 : [{}],
        tableData2 : [{}],
      };
    },
    methods: {
      getTableData(){
        let queryParams = {
          //跳转前的入库单号这个要改成动态的
          gonxiaoInboundOrderNo: 'XPS_shaver_POIN2018082710183400'
        };
        this.$http.get('/storage/informingNotice/selectInboundByInboundNum', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.dataForm = data;
            this.tableData2 = data.tracelog;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
        this.$http.get('/storage/informingNotice/selectInboundOrderItem', {params:queryParams}).then((res) => {
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


