<template>
  <div class="product-added" id="product-added">
    <el-form :model="dataForm" label-width="100px" class="demo-ruleForm" clearable>
      <!--1.基本信息-->
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
        <div class="essentialInfo" id="essentialInfo">
          <el-row>
            <el-form-item label="调出公司主体" prop="allocateNo">
              <el-input v-model="dataForm.allocateNo" disabled></el-input>
            </el-form-item>
            <el-form-item label="调入公司主体" prop="projectIdEnter">
              <el-input v-model="dataForm.projectIdEnter" disabled></el-input>
            </el-form-item>
            <el-form-item label="调出项目" prop="projectIdOut">
              <el-input v-model="dataForm.projectIdOut" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="调入项目" prop="companyNameEnter">
              <el-input v-model="dataForm.companyNameEnter" disabled></el-input>
            </el-form-item>
            <el-form-item label="调出仓库" prop="companyNameOut">
              <el-input v-model="dataForm.companyNameOut" disabled></el-input>
            </el-form-item>
            <el-form-item label="调入仓库" prop="warehouseOut">
              <el-input v-model="dataForm.warehouseOut" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="发货地址" prop="warehouseEnter">
              <el-input v-model="dataForm.warehouseEnter" disabled></el-input>
            </el-form-item>
            <el-form-item label="收货地址" prop="deliverAddress">
              <el-input v-model="dataForm.deliverAddress" disabled></el-input>
            </el-form-item>
            <el-form-item label="调拨方式" prop="receiveAddress">
              <el-input v-model="dataForm.receiveAddress" disabled></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="业务时间" prop="alloteWay">
              <el-input v-model="dataForm.alloteWay" disabled></el-input>
            </el-form-item>
            <el-form-item label="要求到货时间" prop="createTime">
              <el-input v-model="dataForm.createTime" disabled></el-input>
            </el-form-item>
            <el-form-item label="到货截止时间" prop="requireTime">
              <el-input v-model="dataForm.requireTime" disabled></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">明细信息</h4>
        <el-table :data="tableData1" stripe style="width: 100%">
          <el-table-column prop="productCode" label="型号"></el-table-column>
          <el-table-column prop="productName" label="货品名称"></el-table-column>
          <el-table-column prop="inventoryQuantity" label="可发库存数量"></el-table-column>
          <el-table-column prop="alloteQuantity" label="调拨数量"></el-table-column>
          <el-table-column prop="operation" label="操作"></el-table-column>
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
  import eventBus from '../../../assets/js/eventBus';

  export default {
    data() {
      return {
        dataForm: {
          allocateNo: '',
          projectIdOut: '',
          projectIdEnter: '',
          companyNameOut: '',
          companyNameEnter: '',
          warehouseOut: '',
          warehouseEnter: '',
          deliverAddress: '',
          receiveAddress: '',
          alloteWay: '',
          createTime: '',
          requireTime: '',
          deadline: ''
        },
        tableData1: [{}],
        tableData2: [{}],
      };
    },
    methods: {
      getTableData() {
        let queryParams = {
          allocateNo: 'XPS_shaver_TSF2018082816591709'
        };
        this.$http.get('/storage/allocation/selectByAllocateNo', {params: queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            let tracelog = JSON.parse(data.traceLog);
            this.dataForm = data;
            this.tableData2 = tracelog;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
        this.$http.get('/storage/allocation/selectItemByAllocateNo', {params: queryParams}).then((res) => {
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


