<template>
  <div class="product-audit-management-form" id="product-audit-management-form">
    <el-form :inline="true" :model="managementInforming">
      <el-row>
        <el-form-item label="入库单号">
          <el-input v-model="managementInforming.warehouseNumber" placeholder="入库单号" clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="仓库名称">
          <el-select v-model="managementInforming.warehouseName" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="货品编码">
          <el-input v-model="managementInforming.productCodes" placeholder="货品编码" clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="单据类型">
          <el-select v-model="managementInforming.documentType" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="采购单号">
          <el-input v-model="managementInforming.purchaseSingleNumber" placeholder="采购单号" clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="单据状态">
          <el-select v-model="managementInforming.documentStatus" placeholder="请选择">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="创建时间">
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
          <el-button type="primary" @click="onSubmit">查询</el-button>
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
        <el-table-column align="center" header-align="center" prop="warehouseNotificationSingleNumber"
                         label="入库通知单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="PurchaseSingleNumber"
                         label="采购单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplier" label="供应商"></el-table-column>
        <el-table-column align="center" header-align="center" prop="documentType" label="单据类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="documentStatus" label="单据状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehousingWarehouse"
                         label="收货仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="numberOfPlannedWarehouses"
                         label="计划入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="actualNumberOfWarehouses"
                         label="实际入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="differenceQuantity" label="差异数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="expectedToDate" label="预计到仓日期"></el-table-column>
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
        managementInforming: {
          warehouseNumber: '',
          warehouseName: '',
          productCodes: '',
          documentType: '',
          purchaseSingleNumber: '',
          documentStatus: '',
          warehouseNotificationSingleNumber: '',
          PurchaseSingleNumber: '',
          supplier: '',
          documentType: '',
          documentStatus: '',
          warehousingWarehouse: '',
          numberOfPlannedWarehouses: '',
          actualNumberOfWarehouses: '',
          differenceQuantity: '',
          expectedToDate: '',
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
      onSubmit(){},
      exportFile(){},
      edit(row){}
    },

  }
</script>


