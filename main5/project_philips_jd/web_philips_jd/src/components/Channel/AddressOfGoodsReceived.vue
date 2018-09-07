<template>
  <!--1:查询条件-->
  <div class="project-management" id="project-management">
<<<<<<< .mine
    <div class="project-management-form" id="project-management-form">
      <el-row>
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="EAS项目编码">
            <el-input v-model="dataForm.EASCode" placeholder="EAS项目编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="dataForm.projectName" placeholder="项目名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="公司主体">
            <el-input v-model="dataForm.companyBody" placeholder="公司主体" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <!--导出-->
    <div clasee="exportBtn" id="exportBtn">
      <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">导出</el-button>
    </div>
    <!--表格-->
    <div class="project-management-table" id="project-management-table">
      <el-table  :data="tableData">
        <el-table-column align="center" header-align="center" prop="projectCode" label="项目ID"></el-table-column>
        <el-table-column align="center" header-align="center" prop="projectName" label="项目名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="company" label="公司主体"></el-table-column>
        <el-table-column align="center" header-align="center" prop="monthCouponRate"
                         label="月度应收返利比例"></el-table-column>
        <el-table-column align="center" header-align="center" prop="quarterCouponRate"
                         label="季度应收返利比例"></el-table-column>
        <el-table-column align="center" header-align="center" prop="annualCouponRate" label="年度应收返利比例"></el-table-column>
        <!--<el-table-column align="center" header-align="center" prop="proportionOfRebateUse" label="返利使用比例"></el-table-column>-->
        <!--<el-table-column align="center" header-align="center" prop="proportionOfSubstitute" label="代垫使用比例"></el-table-column>-->
        <!--<el-table-column align="center" header-align="center" prop="operator" label="操作人"></el-table-column>-->
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
//        tableAddress: {
//          customerNumber: '',
//          customerName: '',
//          consignee: '',
//          region: '',
//          address: '',
//          phone: '',
//          zipCode: '',
//          state: ''
//        },
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
