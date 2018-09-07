<template>
  <!--1:查询条件-->
  <div class="project-management" id="project-management">
    <el-row>
      <div class="project-management-form" id="project-management-form">
        <el-form :inline="true" :model="input">
          <el-form-item label="EAS客户编码">
            <el-input v-model="input.EASCoding" placeholder="EAS客户编码"></el-input>
          </el-form-item>
          <el-form-item label="客户名称">
            <el-input v-model="input.CustmerName" placeholder="客户名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-row>

    <!--2：导出-->
    <div clasee="exportBtn block" id="exportBtn">
      <!--<div>{{全部 (57)}}</div>-->
      <div style="font-size: 14px; color: #909399; padding-left: 40px; background-color: whitesmoke">
        <el-row>
          <el-button type="text" plain @click="onAudited">已审核</el-button><span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onSubmission">已提交</el-button><span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onUnsubmitted">未提交</el-button><span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onReject">驳回</el-button><span style="color: coral;">(100)</span>
        </el-row>
      </div>
      <hr style="margin-top: 0px; margin-bottom: 0px; color: #E4E4E4;">
      <div clasee="exportBtn block" id="exportBtns" style="background-color: #F5F5F5;">
        <el-button type="button" :to="{path:'/NewWarehouse'}" style=" margin-left: 3%">新增客户</el-button>
        <el-button type="button" onclick="exportWarehouseFile()" style=" margin-left: 3%">导出</el-button>
      </div>
    </div>

    <!--3：客户列表-->
    <div class="custmer-table" id="custmer-table">
      <el-table height="250" style="width: 100%" :data="tableData">
        <el-table-column align="center" header-align="center" prop="EASCode" label="EAS客户编码" width="120"></el-table-column>
        <el-table-column align="center" header-align="center" prop="userName" label="客户名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="userCode" label="客户账号" width="120"></el-table-column>
        <el-table-column align="center" header-align="center" prop="psd" label="密码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operator" label="操作人" width="120"></el-table-column>
        <el-table-column align="center" header-align="center" prop="lasTtime" label="最后修改时间" width="100"></el-table-column>
        <el-table-column align="center" header-align="center" prop="dataState" label="数据状态" width="100"></el-table-column>
        <!--<el-table-column align="center" header-align="center" prop="DistributionCenter" label="操作"></el-table-column>-->
        <el-table-column align="center"  fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button @click="resetPsd(scope.row)" type="text" size="small">重置密码</el-button>
            <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
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
  import ElSpinner from "../../../node_modules/element-ui/packages/spinner/src/spinner.vue";
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {
      ElRow,
      ElSpinner
    },
    data() {
      return {
        input: {
          warehouseCoding: '',
          warehouseName: ''
        },
        tableData: [{
          EASCode: '001.022.33.001',
          userName: '常熟市颜港家电器材有限公司',
          userCode: '颜港家电',
          psd: '****zxz****',
          operator: '张三',
          lasTtime: '2018-7-19',
          dataState: '已提交'
        }, {
          EASCode: '001.022.33.001',
          userName: '常熟市颜港家电器材有限公司',
          userCode: '颜港家电',
          psd: '****zxz****',
          operator: '张三',
          lasTtime: '2018-7-19',
          dataState: '已提交'
        }],
        currentPage: 4
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      exportWarehouseFile() {
        console.log('exportFile!test');
      },
      resetPsd(row){},
      edit(row){},
      onAudited(){},
      onSubmission(){},
      onUnsubmitted(){},
      onReject(){},
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
