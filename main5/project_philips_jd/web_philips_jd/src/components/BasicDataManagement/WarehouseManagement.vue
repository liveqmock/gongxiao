<template>
  <!--1:查询条件-->
  <div class="project-management" id="project-management">
    <el-row>
      <div class="project-management-form" id="project-management-form">
        <el-form :inline="true" :model="input">
          <el-form-item label="仓库ID">
            <el-input v-model="input.warehouseID" placeholder="仓库ID" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库名称">
            <el-input v-model="input.warehouseName" placeholder="仓库名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-row>


    <!--2：导出-->
    <div clasee="exportBtn block" id="exportBtn">
      <el-button type="button" :to="{path:'/NewWarehouse'}" style=" margin-left: 3%">新增</el-button>
      <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">导出</el-button>
    </div>

    <!--3：仓库列表-->
    <div class="project-management-table" id="project-management-table">
      <el-table height="250" style="width: 100%" :data="tableData">
        <el-table-column align="center" header-align="center" prop="warehouseID" label="EAS仓库编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseName" label="仓库名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="personCharge" label="负责人"></el-table-column>
        <el-table-column align="center" header-align="center" prop="telephone" label="联系电话"></el-table-column>
        <el-table-column align="center" header-align="center" prop="address" label="地址"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operator" label="操作人"></el-table-column>
        <el-table-column align="center" header-align="center" prop="lastModificationTime"
                         label="最后修改时间"></el-table-column>
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
        input: {
          warehouseID: '',
          warehouseName: ''
        },
        tableData: [{
          warehouseID: '1',
          warehouseName: '张三',
          personCharge: '中国银行',
          telephone: '0.1%',
          address: '广东省 / 深圳市 / 龙岗区 / 平湖街道 富安西路1008号',
          operator: '王五',
          lastModificationTime: '2018-7-17'
        }, {
          warehouseID: '1',
          warehouseName: '张三',
          personCharge: '中国银行',
          telephone: '0.1%',
          address: '中国深圳大大大大撒打算发的发的几句创新创造唧唧复唧唧十大大家点击',
          operator: '王五',
          lastModificationTime: '2018-7-17'
        }],
        currentPage: 1
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      exportWarehouseFile() {
        console.log('exportFile!test');
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      }
    }
  }
</script>
<style>


</style>
