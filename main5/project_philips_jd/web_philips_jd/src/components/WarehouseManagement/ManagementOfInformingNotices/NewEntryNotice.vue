<template>
  <div class="new-entry-notice" id="new-entry-notice">
    <div class="new-entry-notice-form" id="new-entry-notice-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
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
          <el-form-item label="入库类型" prop="reservoirType" required>
            <el-select v-model="dataForm.reservoirType" placeholder="请选择">
              <el-option label="请选择" value="ownStorehouse"></el-option>
              <el-option label="其他入库" value="customerStorehouse "></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="收货地址" prop="receivingAddress" required>
            <el-input v-model="dataForm.receivingAddress" clearable></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="业务日期" prop="businessDate" required>
            <el-date-picker v-model="dataForm.businessDate" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remarks">
            <el-input type="textarea" :rows="2" v-model="dataForm.remarks"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
    <!--操作-->
    <div class="fx-button">
      <el-button type="primary" @click="maskTableVisible=true">添加商品</el-button>
      <el-button>导入商品</el-button>
    </div>
    <!--表格-->
    <div class="new-entry-notice-table">
      <el-table :data="tableData">
        <el-table-column align="center" header-align="center" prop="productCode" label="货品编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="productName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="guidePrice" label="指导价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="quantity" label="入库数量/个"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchasePrice" label="采购单价/元"></el-table-column>
        <el-table-column align="center" header-align="center" prop="costPrice" label="成本价/元"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
      </el-table>
    </div>
    <!--添加商品-->
    <div class="fx-mask">
      <el-dialog title="添加商品" :visible.sync="maskTableVisible">
        <!--待添加商品列表-->
        <div class="mask-old-table">
          <el-table :data="oldTableData">
            <el-table-column property="productCode" label="货品" width="150"></el-table-column>
            <el-table-column property="productName" label="货品名称" width="200"></el-table-column>
            <el-table-column property="opera" label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  size="small"
                  @click="addClick(scope)"
                >添加
                </el-button>
                <el-button
                  type="text"
                  size="small"
                  @click.native.prevent="deleteRow(scope.$index, tableData)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--已添加的商品列表-->
        <div class="mask-new-table">
          <el-table :data="newTableData">
            <el-table-column property="productId" label="货品" width="150"></el-table-column>
            <el-table-column property="productName" label="货品名称" width="200"></el-table-column>
            <el-table-column property="opera" label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  size="small"
                  @click.native.prevent="deleteRow(scope.$index, tableData)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-dialog>
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
          warehouseId: '',
          warehouseName: '',
          receiveAddress: '',
          supplierName: '',
          businessDate: '',
          remark: '',
          inboundType: ''
        },
        tableData: [],
        changedProjectId: 222,
        maskTableVisible: true,
        oldTableData:[
          {
            "guidePrice": "203.150000",
            "inStockQuantity": 3504,
            "productCode": "880230002710",
            "productId": "108",
            "productName": "S300/02 飞利浦电动剃须刀",
            "saleGuidePrice": "203.150000"
          },
          {
            "guidePrice": "262.650000",
            "inStockQuantity": 2400,
            "productCode": "880236002710",
            "productId": "109",
            "productName": "S360/02 飞利浦电动剃须刀",
            "saleGuidePrice": "262.650000"
          },
          {
            "guidePrice": "152.150000",
            "inStockQuantity": 0,
            "productCode": "885222618710",
            "productId": "110",
            "productName": "PQ226/18 飞利浦电动剃须刀",
            "saleGuidePrice": "160.710000"
          }
        ],
        newTableData:[]
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
      addClick(scope){
        console.log(scope)
      },
      saveAndSubmit() {
        let queryParams = {
          warehouseId: this.dataForm.warehouseId,
          warehouseName: this.dataForm.warehouseName,
          receiveAddress: this.dataForm.receiveAddress,
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
        this.$http.get('/storage/informingNotice/insertInwardNotice', {queryParams}).then((res) => {
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
