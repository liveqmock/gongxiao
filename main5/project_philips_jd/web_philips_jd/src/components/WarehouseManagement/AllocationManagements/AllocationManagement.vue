<template>
  <div class="storage-notice-entry" id="storage-notice-entry">
    <div class="storage-notice-entry-form">
      <el-form :inline="true" :model="dataForm" label-width="120px">
        <el-form-item label="调拨单号">
          <el-input v-model="dataForm.allocateNo" placeholder="调拨单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="调拨入库单号">
          <el-input v-model="dataForm.gongxiaoInboundOrderNo" placeholder="调拨入库单号" clearable></el-input>
        </el-form-item>
        <el-form-item label="调拨出库单号">
          <el-input v-model="dataForm.gongxiaoOutboundOrderNo" placeholder="调拨出库单号" clearable></el-input>
        </el-form-item>

        <el-form-item label="调出仓库">
          <el-select v-model="dataForm.warehouseOut" placeholder="调出仓库">
            <el-option :label="item.warehouseName" v-for="(item,index) in warehouseList" :value="item.warehouseId" :key="index">{{item.warehouseName}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调入仓库">
          <el-select v-model="dataForm.warehouseEnter" placeholder="调入仓库">
            <el-option :label="item.warehouseName" v-for="(item,index) in warehouseList" :value="item.warehouseId" :key="index">{{item.warehouseName}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <div class="block">
            <el-date-picker
              v-model="dataForm.createBeginTime"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getTableData()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--导出-->
    <div class="fx-button">
      <router-link :to="{path:'/NewAllocationList'}" tag="el-button">新增</router-link>
      <el-button>导入</el-button>
    </div>
    <!--表格-->
    <div class="storage-notice-entry-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="allocateNo" label="调拨单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="status" label="状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="gongxiaoOutboundOrderNo" label="出库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="gongxiaoInboundOrderNo" label="入库单号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseOut" label="调出仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="warehouseEnter" label="调入仓库"></el-table-column>
        <el-table-column align="center" header-align="center" prop="alloteWay" label="调拨方式"></el-table-column>
        <el-table-column align="center" header-align="center" prop="createTime" label="创建时间"></el-table-column>
        <el-table-column align="center" fixed="right" label="操作" prop="operation"></el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <div class="fx-pagination">
      <span class="pagination-total">共 {{this.pagination.total}} 条</span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="pagination.pageNumber"
        :page-sizes="pagination.pageList"
        :page-size="pagination.pageSize"
        layout="sizes, prev, pager, next"
        :total="pagination.total">
      </el-pagination>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import eventBus from '../../../assets/js/eventBus.js';

  export default {
    data() {
      return {
        dataForm: {
          allocateNo: '',
          gongxiaoOutboundOrderNo: '',
          gongxiaoInboundOrderNo: '',
          warehouseOut: '',
          warehouseEnter: '',
          createBeginTime: '',
          createEndTime: ''
        },
        pagination: {
          // 每页显示数量
          pageSize: 60,
          // 页码
          pageNumber: 1,
          // 页码list
          pageList: [5, 10, 20],
          // 总条数
          total: 0
        },
        tableData: [],
        changedProjectId: 222,
        loading: true
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo',
        'warehouseList'
      ])
    },
    methods: {
      purchaseOrderNumberClick({row}) {
        let {purchaseOrderNumber} = row;
        console.log(purchaseOrderNumber)
      },
      tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'waring-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return ''
      },
      handleSizeChange(size) {
        this.pagination.pageSize = size;
        this.getTableData();
      },
      handleCurrentChange(currentPage) {
        this.pagination.currentPage = currentPage;
        this.getTableData()
      },
      //确认调拨
      confirmationOfAllocation() {
        let queryParams = {
          gongxiaoOutboundOrderNo: this.dataForm.gongxiaoOutboundOrderNo
        };
        this.$http.get('/storage/allocation/confirmationOfAllocation', {params:queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
//            this.tableData = data;
          } else {
            console.log(message);
          }
        }).catch((err) => {
          console.log(err);
        });
      },
      getTableData() {
        let queryParams = {
          allocateNo: this.dataForm.allocateNo,
          gongxiaoOutboundOrderNo: this.dataForm.gongxiaoOutboundOrderNo,
          gongxiaoInboundOrderNo: this.dataForm.gongxiaoInboundOrderNo,
          warehouseOut: this.dataForm.warehouseOut,
          warehouseEnter: this.dataForm.warehouseEnter,
//          createBeginTime: this.dataForm.createBeginTime,
//          createEndTime: this.dataForm.createEndTime,
          createBeginTime: "",
          createEndTime: "",
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('/storage/allocation/selectAllocateOrder', {params:queryParams}).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            [this.tableData, this.pagination.total] = [list, total];
            this.loading = false;
          } else {
            console.log(message);
          }
        }).catch((err) => {
          console.log(err);
        });
      }
    },
    watch: {
      getProjectInfo(newProjectId) {
        this.changedProjectId = newProjectId;
      }
    },
    mounted() {
      eventBus.$on('loadingDefaultProjectId', () => {
        this.getTableData();
      });
    },
    activated() {
      if(!this.tableData.length){
        this.getTableData();
      }
    }
  }
</script>
