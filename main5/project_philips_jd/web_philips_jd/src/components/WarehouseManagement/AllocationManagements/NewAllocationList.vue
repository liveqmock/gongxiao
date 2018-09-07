<template>
  <div class="new-entry-notice" id="new-entry-notice">
    <div class="new-entry-notice-form" id="new-entry-notice-form">
      <el-form :inline="true" :model="dataForm" label-width="130px">
        <el-form-item label="调出公司主体" required>
          <el-input v-model="dataForm.companyout" placeholder="调出公司主体" clearable></el-input>
        </el-form-item>
        <el-form-item label="调入公司主体" required>
          <el-input v-model="dataForm.companyentry" placeholder="调入公司主体" clearable></el-input>
        </el-form-item>
        <!--调出项目,调入项目 该接口和getProjectList一致-->
        <el-form-item label="调出项目" required>
          <el-select v-model="dataForm.outProject" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调入项目" required>
          <el-select v-model="dataForm.intProject" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调出仓库" required>
          <el-select v-model="dataForm.warehouseOutId" placeholder="调出仓库">
            <el-option :label="item.warehouseName" v-for="(item,index) in warehouseList" :value="item.warehouseId" :key="index">{{item.warehouseName}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调入仓库" required>
          <el-select v-model="dataForm.warehouseEntryId" placeholder="调入仓库">
            <el-option :label="item.warehouseName" v-for="(item,index) in warehouseList" :value="item.warehouseId" :key="index">{{item.warehouseName}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发货地址" required>
          <el-input v-model="dataForm.deliveraddress" placeholder="调拨单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="收货地址" required>
          <el-input v-model="dataForm.receiveaddress" placeholder="出库通知单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="调拨方式" required>
          <el-select v-model="dataForm.allocateway" placeholder="请选择">
            <el-option label="请选择" value="1"></el-option>
            <el-option label="同仓调拨" value="2"></el-option>
            <el-option label="跨仓调拨" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务日期" required>
          <el-date-picker v-model="dataForm.yhbusinessDate" type="date" placeholder="选择日期"
                          style="width: 94%"></el-date-picker>
        </el-form-item>
        <el-form-item label="要求发货日期" required>
          <el-date-picker style="width: 94%" v-model="dataForm.askDate" type="date"
                          placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="到货截止日期" style="margin-left: -1.5%;" required>
          <el-date-picker v-model="dataForm.deadDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" :rows="2" v-model="dataForm.remarks" resize="both"></el-input>
        </el-form-item>

      </el-form>
    </div>
    <!--操作-->
    <div class="fx-button">
      <el-button type="primary">添加商品</el-button>
      <el-button>导入商品</el-button>
    </div>
    <!--表格-->
    <div class="new-entry-notice-table">
      <el-table :data="tableData">
        <el-table-column align="center" header-align="center" prop="productCode" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="batchNo" label="批次号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="guidePrice" label="指导价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchasePrice" label="采购价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="inventoryStatus" label="库存品质"></el-table-column>
        <el-table-column align="center" header-align="center" prop="costPrice" label="成本价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="quantity" label="调拨数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
      </el-table>
    </div>
    <!--保存提交-->
    <div class="fx-button" align="center">
      <el-button type="success" @click="saveAndSubmit()">保存并提交</el-button>
      <el-button type="button" @click="cancel()">取消</el-button>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import eventBus from '../../../assets/js/eventBus';

  export default {
    data() {
      return {
        dataForm: {
          companyout: '',
          companyentry: '',
          outProject: '',
          intProject: '',
          warehouseOutId: '',
          warehouseEntryId: '',
          deliveraddress: '',
          receiveaddress: '',
          allocateway: '',
          yhbusinessDate: '',
          askDate: '',
          deadDate: '',
          remarks: ''
        },
        tableData: [],
        changedProjectId: 222,
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo',
        'warehouseList',
        'supplierList'
      ])
    },
    methods: {
      saveAndSubmit() {
        let queryParams = {
          companyout: this.dataForm.companyout,
          companyentry: this.dataForm.companyentry,
          warehouseOutId: this.dataForm.warehouseOutId,
          warehouseEntryId: this.dataForm.warehouseEntryId,
          deliveraddress: this.dataForm.deliveraddress,
          receiveaddress: this.dataForm.receiveaddress,
          allocateway: this.dataForm.allocateway,
          yhbusinessDate: this.dataForm.yhbusinessDate,
          askDate: this.dataForm.askDate,
          deadDate: this.dataForm.deadDate,
          remarks: this.dataForm.remarks,
          itemsInfo: {
            productCode: this.tableData.productCode,
            productName: this.tableData.productName,
            guidePrice: this.tableData.guidePrice,
            quantity: this.tableData.quantity,
            purchasePrice: this.tableData.purchasePrice,
            costPrice: this.tableData.costPrice
          }
        };
        this.$http.get('/storage/allocation/insertAllocateOrder', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.data = data;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
      },
      //获取批次号
      getBatchNumber(){
        this.$http.get('/storage/allocation/getBatchNumber').then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.data = data;
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
      }
    },

    watch: {
      getProjectInfo(newProjectId) {
        this.changedProjectId = newProjectId;
      }
    },
    mounted() {
    }
  }
</script>
