<template>
  <div class="product-audit-management-form" id="product-audit-management-form">
    <el-form :inline="true" :model="orderJumpOutOfTheWarehouseNotice">
      <el-row>
        <el-form-item label="出库通知单号">
          <el-input v-model="orderJumpOutOfTheWarehouseNotice.outOfWarehouseNotificationNumber" placeholder="入库单号" clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="单据类型">
          <el-select v-model="orderJumpOutOfTheWarehouseNotice.documentType" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单号">
          <el-input v-model="orderJumpOutOfTheWarehouseNotice.oddNumbers" placeholder="单号" clearable
          ></el-input>
        </el-form-item>
      </el-row>

      <el-row style="margin-left: 2.5%;">
        <el-form-item label="货品编码">
          <el-input v-model="orderJumpOutOfTheWarehouseNotice.productCodes" placeholder="入库单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="单据状态">
          <el-select v-model="orderJumpOutOfTheWarehouseNotice.documentStatus" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户">
          <el-input v-model="orderJumpOutOfTheWarehouseNotice.customer" placeholder="入库单号" clearable></el-input>
        </el-form-item>
      </el-row>

      <el-row style="margin-left: 2.5%;">
        <el-form-item label="仓库名称">
          <el-select v-model="orderJumpOutOfTheWarehouseNotice.warehouseName" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" v-model="orderJumpOutOfTheWarehouseNotice.creationTime">
          <div class="block">
            <el-date-picker
              v-model="value4"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onQuery()">查询</el-button>
        </el-form-item>
      </el-row>

    </el-form>

    <!--2：导出-->
    <div clasee="exportBtn block" id="exportBtn">
      <el-button type="button" onclick="exportFile()">导出</el-button>
    </div>

    <!--3：列表-->
    <div class="project-management-table" id="project-management-table">
      <el-table height="250" style="width: 100%" :data="tableData">
        <el-table-column align="center" header-align="center" prop="outOfWarehouseSingleNumber" label="出库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="salesOrderNumber" label="销售订单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="documentType" label="单据类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="documentStatus" label="单据状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="deliveryWarehouse" label="发货仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="plannedOutgoingQuantity" label="计划出库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="numberOfActualOutages" label="实际出库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="differenceQuantity" label="差异数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplier" label="供应商"></el-table-column>
        <el-table-column align="center" header-align="center" prop="customer" label="客户"></el-table-column>
        <el-table-column align="center" header-align="center" prop="creationTime" label="创建时间"></el-table-column>
      </el-table>
      <!--分页-->
      <div class="block" style="float: right">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
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
        orderJumpOutOfTheWarehouseNotice: {
          outOfWarehouseNotificationNumber: '',
          documentType: '',
          oddNumbers: '',
          productCodes: '',
          documentStatus: '',
          customer: '',
          warehouseName: '',
          creationTime: ''
        },
        pickerOptions2: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        value4: ''
      };
    },
    methods: {
      onQuery() {
      },
      exportFile() {
      }
    },

  }
</script>


