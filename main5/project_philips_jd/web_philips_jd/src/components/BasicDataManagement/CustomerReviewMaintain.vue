<template>
  <!--1:查询条件-->
  <div class="project-management" id="project-management">
    <el-row>
      <div class="project-management-form" id="project-management-form">
        <el-form :inline="true" :model="input">
          <el-form-item label="EAS客户编码">
            <el-input v-model="input.EASCode" placeholder="EAS客户编码" clearable style="width: 150px;"></el-input>
          </el-form-item>
          <el-form-item label="客户名称">
            <el-input v-model="input.customerName" placeholder="客户名称" clearable style="width: 150px;"></el-input>
          </el-form-item>
          <el-form-item label="项目">
            <el-select v-model="input.project" placeholder="请选择" style="width: 150px;">
              <el-option label="飞利浦" value="shanghai"></el-option>
              <el-option label="京东" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="input.state" placeholder="请选择" style="width: 150px;">
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
        <el-table-column align="center" header-align="center" prop="customerName" label="客户名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="EASCode" label="EAS客户编码"></el-table-column>
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
        input: {
          EASCode: '',
          customerName: '',
          project: '',
          state: ''
        },
        tableData: [{
          EASCode: '常熟市颜港家电器材有限公司',
          customerName: '10.00.2.183',
          project: '飞利浦线下须刀',
          state: '已提交'
        }, {
          EASCode: '常熟市颜港家电器材有限公司',
          customerName: '10.00.2.183',
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
