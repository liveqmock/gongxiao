<template>
  <!--1:查询条件-->
  <div class="product-management" id="product-management">
    <el-row>
      <div class="productManagement-form" id="productManagement-form">
        <el-form :inline="true" :model="productManagement">
          <el-form-item label="型号">
            <el-input v-model="productManagement.model" placeholder="型号"></el-input>
          </el-form-item>
          <el-form-item label="货品名称">
            <el-input v-model="productManagement.goodsName" placeholder="货品名称"></el-input>
          </el-form-item>
          <el-form-item label="物料编码">
            <el-input v-model="productManagement.materialCoding " placeholder="物料编码"></el-input>
          </el-form-item>
          <el-form-item style="padding-left: 20px">
            <el-button type="primary" @click="onQuery()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-row>

    <!--2：导出-->
    <div clasee="exportBtn block" id="exportBtn">
      <div style="font-size: 14px; color: #909399; padding-left: 40px; background-color: whitesmoke">
        <el-row>
          <el-button type="text" plain @click="onAudited">已审核</el-button>
          <span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onSubmission">已提交</el-button>
          <span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onUnsubmitted">未提交</el-button>
          <span style="color: coral;">(100)</span>
          <el-button type="text" plain @click="onReject">驳回</el-button>
          <span style="color: coral;">(100)</span>
        </el-row>
      </div>
      <hr style="margin-top: 0px; margin-bottom: 0px; color: #E4E4E4;">
      <div clasee="exportBtn block" id="exportBtns" style="background-color: #F5F5F5;">
        <el-button type="button" :to="{path:'/xxxx'}" style=" margin-left: 3%">创建货品</el-button>
        <el-button type="button" onclick="onSubmit()" style=" margin-left: 3%">提交</el-button>
        <el-button type="button" onclick="exportFile()" style=" margin-left: 3%">导出</el-button>
      </div>
    </div>

    <!--3：货品列表-->
    <div class="custmer-table" id="custmer-table">
      <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" header-align="center" prop="descriptionGoods" label="货品名称"
                         width="120"></el-table-column>
        <el-table-column align="center" header-align="center" prop="model" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="EASCoding" label="EAS物料编码"
                         width="120"></el-table-column>
        <el-table-column align="center" header-align="center" prop="WMSCoding" label="WMS物料编码"></el-table-column>
        <el-table-column align="center" header-align="center" prop="dataState" label="数据状态"
                         width="120"></el-table-column>
        <el-table-column align="center" header-align="center" prop="boxNumber" label="箱装数"
                         width="100"></el-table-column>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
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
        productManagement: {
          model: '',
          goodsName: '',
          materialCoding: ''
        },
        tableData: [{
          descriptionGoods: '飞利浦电动剃须刀S360/02',
          model: '880236002710',
          EASCoding: '10.00.2.183',
          WMSCoding: '880236002710',
          dataState: '已生效',
          boxNumber: '1000'
        }, {
          descriptionGoods: '飞利浦电动剃须刀S360/02',
          model: '880236002710',
          EASCoding: '10.00.2.183',
          WMSCoding: '880236002710',
          dataState: '已生效',
          boxNumber: '1000'
        }],
        currentPage: 4
      }
    },
    methods: {
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      onQuery(){
      },
      onSubmit() {
      },
      onAudited() {
      },
      onSubmission() {
      },
      onUnsubmitted() {
      },
      onReject() {
      },
      exportFile() {
      },
      edit(){
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
