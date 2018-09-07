<template>
  <div class="new-announce" id="new-announce">
    <el-form :inline="true" :model="dataForm" label-width="100px">
      <div class="new-announce-form">
        <el-row>
          <el-form-item label="供应商" required>
            <el-select v-model="dataForm.supplierId" placeholder="供应商">
              <el-option :label="item.supplierName" :value="item.supplierId" v-for="(item,index) in supplierList"
                         :key="index">{{item.supplierName}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="仓库" required>
            <el-select v-model="dataForm.warehouseId" placeholder="仓库">
              <el-option :label="item.warehouseName" :value="item.warehouseId" v-for="(item,index) in warehouseList"
                         :key="index">{{item.warehouseName}}
              </el-option>
            </el-select>
          </el-form-item>
        </el-row>

        <el-row>
          <el-form-item label="出库类型 " prop="reservoirType" required>
            <el-select v-model="dataForm.reservoirType" placeholder="请选择">
              <el-option label="请选择" value="ownStorehouse"></el-option>
              <el-option label="报废出库" value="scrapStorehouse"></el-option>
              <el-option label="其他出库" value="customerStorehouse"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发货地址" prop="receivingAddress" required>
            <el-input v-model="dataForm.receivingAddress"></el-input>
          </el-form-item>
        </el-row>

        <el-row>
          <div class="block">
            <el-form-item label="业务日期" prop="businessDate" required>
              <el-date-picker v-model="dataForm.businessDate" type="date" placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </div>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remarks">
            <el-input type="textarea" :rows="2" v-model="dataForm.remarks" > </el-input>
          </el-form-item>
        </el-row>
      </div>
      <!--操作-->
      <div class="fx-button">
        <el-button type="primary">添加商品</el-button>
        <el-button>导入商品</el-button>
      </div>
      <!--表格-->
      <div class="new-announce-table">
        <el-table  :data="tableData">
          <el-table-column align="center" header-align="center" prop="productCode" label="货品编码"></el-table-column>
          <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
          <el-table-column align="center" header-align="center" prop="guidePrice" label="指导价"></el-table-column>
          <el-table-column align="center" header-align="center" prop="quantity" label="入库数量/个"></el-table-column>
          <el-table-column align="center" header-align="center" prop="purchasePrice" label="采购单价/元"></el-table-column>
          <el-table-column align="center" header-align="center" prop="costPrice" label="成本价/元"></el-table-column>
          <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
        </el-table>
      </div>
      <div class="fx-button" align="center">
        <el-button type="success" @click="saveAndSubmit()">保存并提交</el-button>
        <el-button type="button" @click="cancel()">取消</el-button>
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
          warehouseId: '',
          warehouseName: '',
          recieveAddress: '',
          supplierName: '',
          businessDate: '',
          remark: '',
          inboundType: ''
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
          warehouseId: this.dataForm.warehouseId,
          warehouseName: this.dataForm.warehouseName,
          recieveAddress: this.dataForm.recieveAddress,
          supplierName: this.dataForm.supplierName,
          businessDate: this.dataForm.businessDate,
          remark: this.dataForm.remark,
          inboundType: this.dataForm.inboundType,
          purchaseItemInfoJson: {
            productCode: this.tableData.productCode,
            productName: this.tableData.productName,
            guidePrice: this.tableData.guidePrice,
            quantity: this.tableData.quantity,
            purchasePrice: this.tableData.purchasePrice,
            costPrice: this.tableData.costPrice
          }
        };
        this.$http.get('/storage/outstorenotice/insertOutboundOrder', {params:queryParams}).then((res) => {
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
