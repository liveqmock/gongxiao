<template>
  <!--1:查询条件-->
  <div class="project-management" id="project-management">

    <!--3：收件地址列表-->
    <div class="custmer-table" id="custmer-table">
      <el-table height="250" style="width: 100%" :data="tableAddress">
        <el-table-column align="center" header-align="center" prop="customerNumber" label="客户账号"
                         width="90"></el-table-column>
        <el-table-column align="center" header-align="center" prop="customerName" label="客户名称"
                         width="180"></el-table-column>
        <el-table-column align="center" header-align="center" prop="consignee" label="收货人"
                         width="90"></el-table-column>
        <el-table-column align="center" header-align="center" prop="region" label="所在地区" width="200"></el-table-column>
        <el-table-column align="center" header-align="center" prop="address" label="详细地址"></el-table-column>
        <el-table-column align="center" header-align="center" prop="phone" label="电话/手机" width="120"></el-table-column>
        <el-table-column align="center" header-align="center" prop="zipCode" label="邮编" width="90"></el-table-column>
        <el-table-column align="center" header-align="center" prop="state" label="状态" width="80"></el-table-column>
        <el-table-column fixed="right" width="100">
          <template slot-scope="scope">
            <el-button @click="onUpdateAddress(scope.row)" type="text" size="small">默认地址</el-button>
          </template>
        </el-table-column>
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
        tableAddress: [{
          customerNumber: '352061435',
          customerName: '北京飞利浦商贸有限责任公司',
          consignee: '黄佳林',
          region: '广东省 深圳市 龙岗区 平湖街道',
          address: '富安西路1008号越海华南物流中心',
          phone: '86-13423755806',
          zipCode: '000000',
          state: '已审核'
        }, {
          customerNumber: '352061435',
          customerName: '北京飞利浦商贸有限责任公司',
          consignee: '黄佳林',
          region: '广东省 深圳市 龙岗区 平湖街道',
          address: '富安西路1008号越海华南物流中心',
          phone: '86-13423755806',
          zipCode: '000000',
          state: '已审核'
        }],
        currentPage: 4
      }
    },
    methods: {
      onUpdateAddress(row) {
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
