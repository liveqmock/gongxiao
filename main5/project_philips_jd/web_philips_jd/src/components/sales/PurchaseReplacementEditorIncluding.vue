<template>
  <div class="product-added" id="product-added">
    <el-form :model="dataForm" label-width="100px" class="demo-ruleForm" clearable>
      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">基本信息</h4>
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column prop="productName" label="型号"></el-table-column>
          <el-table-column prop="productCode" label="货品名称"></el-table-column>
          <el-table-column prop="batchNo" label="批次号"></el-table-column>
          <el-table-column prop="inventoryType" label="采购价"></el-table-column>
          <el-table-column prop="inStockQuantity" label="成本价"></el-table-column>
          <el-table-column prop="productName" label="销售指导价"></el-table-column>
          <el-table-column prop="productCode" label="出货价"></el-table-column>
          <el-table-column prop="batchNo" label="退货数量"></el-table-column>
          <el-table-column prop="inventoryType" label="退货价"></el-table-column>
          <el-table-column prop="inStockQuantity" label="换货数量"></el-table-column>
          <el-table-column prop="productName" label="退货金额"></el-table-column>
          <el-table-column prop="productCode" label="退货品质"></el-table-column>
          <el-table-column prop="batchNo" label="退货原因"></el-table-column>
          <el-table-column prop="inventoryType" label="操作"></el-table-column>
        </el-table>
      </div>

      <div clasee="exportBtn" id="exportBtn" style="margin-top: 20px;">
        <el-popover placement="right" trigger="click">
          <el-transfer filterable :filter-method="filterMethod" filter-placeholder="请输入城市拼音" v-model="value2" :data="data2"></el-transfer>
          <el-button slot="reference">添加商品</el-button>
        </el-popover>
        <el-form-item label="换货后项目" style="padding-right: 20px;">
          <el-select v-model="dataForm.test" placeholder="请选择" style="width: 125px;">
            <el-option label="请选择" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="test"></el-option>
            <el-option label="飞利浦厨卫京东2" value="test"></el-option>
          </el-select>
        </el-form-item>
      </div>

      <div class="project-management-table" id="project-management-table">
        <el-table height="250" style="width: 100%" :data="tableData">
          <el-table-column align="center" header-align="center" prop="goodsName" label="型号"></el-table-column>
          <el-table-column align="center" header-align="center" prop="model" label="货品名称"></el-table-column>
          <el-table-column align="center" header-align="center" prop="WMSCoding" label="销售指导价"></el-table-column>
          <el-table-column align="center" header-align="center" prop="project" label="批次号"></el-table-column>
          <el-table-column align="center" header-align="center" prop="state" label="采购价"></el-table-column>
          <el-table-column align="center" header-align="center" prop="goodsName" label="成本价"></el-table-column>
          <el-table-column align="center" header-align="center" prop="model" label="换货数量"></el-table-column>
          <el-table-column align="center" header-align="center" prop="WMSCoding" label="退货金额"></el-table-column>
          <el-table-column align="center" header-align="center" prop="project" label="操作"></el-table-column>
        </el-table>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">退货及物流信息</h4>
        <div class="taxRate" id="taxRate" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="供应商名称" prop="region">
              <el-select v-model="dataForm.test" placeholder="请选择">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="收货人地址" prop="taxRate" required>
              <el-input v-model="dataForm.test" clearable></el-input>
            </el-form-item>
            <el-form-item label="详细地址" prop="taxRate" required>
              <el-input v-model="dataForm.test" clearable></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="收货人" prop="taxRate" required>
              <el-input v-model="dataForm.test" clearable></el-input>
            </el-form-item>
            <el-form-item label="手机" prop="taxRate">
              <el-input v-model="dataForm.test" clearable></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div label-position="right">
        <h4 style="border-left: 2px solid #1a6acb; padding-left: 3px;">退货回收人及物流信息</h4>
        <el-button type="primary" @click="onSubmit()" style="margin-left: 96px; margin-bottom: 15px;">清空收货地址信息</el-button>
        <div class="taxRate" id="taxRate2" style="padding-left: 7%;">
          <el-row>
            <el-form-item label="发货仓库" prop="region" required>
              <el-select v-model="dataForm.test" placeholder="请选择">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="物流方式" prop="region" required>
              <el-select v-model="dataForm.test" placeholder="请选择">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="退回仓库" prop="region" required>
              <el-select v-model="dataForm.test" placeholder="请选择">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="物流运单号" prop="taxRate">
              <el-input v-model="dataForm.test" clearable></el-input>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="运费承担方" prop="region">
              <el-select v-model="dataForm.test" placeholder="请选择">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="元" prop="taxRate">
              <el-input v-model="dataForm.test" clearable></el-input>
            </el-form-item>
          </el-row>
        </div>
      </div>

      <div clasee="exportBtn block" id="btn1" style="padding-left: 26%; padding-top: 20px;">
        <el-row>
          <el-button type="primary" @click="onSubmit()">保存</el-button>
          <el-button type="primary" @click="onSaveAndSubmit()">保存并提交</el-button>
          <el-button type="button" @click="onCancel()">取消</el-button>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        dataForm: {
          test:"",
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
        tableData: [{}]
      };
    },
    methods: {
      getTableData() {
        let queryParams = {
          gongxiaoOutboundOrderNo: 'xxxx',
          wmsOutboundOrderNo: 'xxxx'
        };
        this.$http.get('/storage/outboundorder/selectByOutboundNum', {queryParams}).then((res) => {
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
        this.$http.get('/storage/outboundorder/selectOutboundOrderItem', {queryParams}).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            [this.tableData, this.pagination.total] = [list, total];
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


