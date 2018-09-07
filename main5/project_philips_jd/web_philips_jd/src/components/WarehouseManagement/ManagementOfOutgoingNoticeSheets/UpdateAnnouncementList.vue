<template>
  <div class="purchase-management-form" id="purchase-management-form">
    <div>
      <el-form :model="updateAnnouncementList" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-row>
          <el-form-item label="品牌商" prop="supplier" required>
            <el-select v-model="updateAnnouncementList.supplier" placeholder="请选择">
              <el-option label="自有仓" value="ownStorehouse"></el-option>
              <el-option label="客户仓" value="customerStorehouse "></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="仓库" prop="warehouse" required>
            <el-select v-model="updateAnnouncementList.warehouse" placeholder="请选择">
              <el-option label="自有仓" value="ownStorehouse"></el-option>
              <el-option label="客户仓" value="customerStorehouse "></el-option>
            </el-select>
          </el-form-item>
        </el-row>

        <el-row>
          <el-form-item label="入库类型" prop="reservoirType" required>
            <el-select v-model="updateAnnouncementList.reservoirType" placeholder="请选择">
              <el-option label="自有仓" value="ownStorehouse"></el-option>
              <el-option label="客户仓" value="customerStorehouse "></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发货地址" prop="receivingAddress" required>
            <el-input v-model="updateAnnouncementList.shippingAddress"></el-input>
          </el-form-item>
        </el-row>

        <el-row>
          <div class="block">
            <el-form-item label="业务日期" prop="businessDate" required>
              <el-date-picker v-model="updateAnnouncementList.businessDate" type="date" placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </div>
        </el-row>

        <el-row>
          <div style="float: left;">
            <el-popover placement="right" trigger="click">
              <el-transfer filterable :filter-method="filterMethod" filter-placeholder="请输入城市拼音" v-model="value2"
                           :data="data2"></el-transfer>
              <el-button slot="reference">添加商品</el-button>
            </el-popover>
          </div>
          <div clasee="exportBtn block" id="addBtn" style="margin-left: 100px">
            <el-button type="primary" @click="addCommodity()">导入商品</el-button>
          </div>
        </el-row>
      </el-form>
    </div>

    <!--3：项目列表-->
    <div class="project-management-table" id="project-management-table">
      <el-table height="250" style="width: 100%" :data="tableData">
        <el-table-column align="center" header-align="center" prop="model" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="goodsName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="guidancePrice" label="指导价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="number" label="数量/个"></el-table-column>
        <el-table-column align="center" header-align="center" prop="purchaseUnitPrice" label="采购单价/元"></el-table-column>
        <el-table-column align="center" header-align="center" prop="costPrice" label="成本价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
      </el-table>
      <!--分页-->
      <div class="block" style="float: right">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="100"
          layout="total, sizes, prev, pager, next, jumper"
          :total="100">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        updateAnnouncementList: {
          supplier: '',
          warehouse:'',
          reservoirType: '',
          shippingAddress: '',
          businessDate: ''/*,
          model: '',
          goodsName: '',
          guidancePrice: '',
          batchNumber: '',
          number: '',
          purchaseUnitPrice: '',
          costPrice: '',
          operation: ''*/
        },
        rules: {
//          libraryPosition: [
//            {required: true, message: '请填写库位', trigger: 'change'}
//          ],
//          whichLocated1: [
//            {required: true, message: '请选择所在地区', trigger: 'change'}
//          ],
//          whichLocated2: [
//            {required: true, message: '请选择所在地区', trigger: 'change'}
//          ],
//          detailedAddress: [
//            {required: true, message: '请填写详细地址', trigger: 'change'}
//          ]
        }
      };
    },
    methods: {
      addCommodity() {
      }
    }
  }
</script>

