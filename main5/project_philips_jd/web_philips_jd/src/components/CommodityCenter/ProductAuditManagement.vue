<template>
  <!--1:查询条件-->
  <div class="product-audit-management" id="product-audit-management">
    <el-row>
    <div class="product-audit-management-form" id="product-audit-management-form">
      <el-form :inline="true" :model="productAuditManagement">
        <el-form-item label="型号">
          <el-input v-model="productAuditManagement.Model" placeholder="型号" clearable
                    style="width: 125px;"></el-input>
        </el-form-item>
        <el-form-item label="货品名称">
          <el-input v-model="productAuditManagement.goodsName" placeholder="货品名称" clearable
                    style="width: 125px;"></el-input>
        </el-form-item>
        <el-form-item label="EAS物料编码">
          <el-input v-model="productAuditManagement.EASCoding" placeholder="EAS物料编码" clearable
                    style="width: 125px;"></el-input>
        </el-form-item>
        <el-form-item label="项目">
          <el-select v-model="productAuditManagement.project" placeholder="飞利浦厨卫京东1" style="width: 125px;">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态审核">
          <el-select v-model="productAuditManagement.stateAudit" placeholder="请选择" style="width: 125px;">
            <el-option label="已提交" value="shanghai"></el-option>
            <el-option label="未提交" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-row>

    <!--2：导出-->
    <div clasee="exportBtn" id="exportBtn">
      <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">导出</el-button>
    </div>

    <!--3： 客户审核列表-->
    <div class="project-management-table" id="project-management-table">
      <el-table height="250" style="width: 100%" :data="tableData">
        <el-table-column align="center" header-align="center" prop="goodsName" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="model" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="EASCoding" label="EAS物料编码">
          <!--<template slot-scope="scope">-->
          <!--<el-input v-model="scope.row.order2" placeholder="请输入内容"></el-input>-->
          <!--</template>-->
          <span v-if="this.val ==''">
            <template slot-scope="scope">
              <el-input v-model="scope.row.order2" placeholder="请输入内容"></el-input>
            </template>
          </span>

          <span v-else>{{this.val }}</span>

        </el-table-column>
        <el-table-column align="center" header-align="center" prop="WMSCoding" label="WMS物料编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="project" label="项目"></el-table-column>
        <el-table-column align="center" header-align="center" prop="state" label="状态"></el-table-column>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click="onExamine(scope.row)" type="text" size="small">审核</el-button>
            <el-button @click="onReject(scope.row)" type="text" size="small">驳回</el-button>
          </template>
        </el-table-column>
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
        productAuditManagement: {
          Model: '',
          goodsName: '',
          EASCoding: '',
          project: '',
          stateAudit: ''
        },
        tableData: [{
          goodsName: '飞利浦电动剃须刀S360/02',
          model: '880236002710',
          EASCoding: '',
          WMSCoding: '880236002710',
          project: '飞利浦线下须刀',
          state: '已提交'
        }, {
          goodsName: '飞利浦电动剃须刀S360/02',
          model: '880236002710',
          EASCoding: 'zxzxzdsada',
          WMSCoding: '880236002710',
          project: '飞利浦线下须刀',
          state: '已提交'
        }],
        currentPage4: 4
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      exportFile() {
        console.log('exportFile!test');
      },
      onExamine() {
      },
      onReject() {
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
