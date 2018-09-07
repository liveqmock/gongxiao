<template>
  <div class="storage-notice-entry" id="storage-notice-entry">
    <div class="storage-notice-entry-form">
      <el-form :inline="true" :model="dataForm" label-width="90px">
        <el-form-item label="客户帐号">
          <el-input v-model="dataForm.distributorAccount" placeholder="客户帐号" clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="客户名称">
          <el-input v-model="dataForm.distributorName" placeholder="客户名称" clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="状态审核">
          <el-select v-model="dataForm.auditStatus" placeholder="请选择">
            <el-option label="已提交" value="2"></el-option>
            <el-option label="已审核" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getTableData">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--表格-->
    <div class="storage-notice-entry-table">
      <el-table :data="tableData" v-loading="loading">
        <el-table-column align="center" header-align="center" prop="distributorName" label="客户名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="receiver" label="收货人"></el-table-column>
        <el-table-column align="center" header-align="center" prop="receiver" label="省市区"></el-table-column>
        <el-table-column align="center" header-align="center" prop="streetAddress" label="街道地址"></el-table-column>
        <el-table-column align="center" header-align="center" prop="postCode" label="邮编"></el-table-column>
        <el-table-column align="center" header-align="center" prop="phoneNumber" label="电话"></el-table-column>
        <el-table-column align="center" header-align="center" prop="auditStatus" label="状态"></el-table-column>
        <el-table-column align="center" header-align="center" prop="operation" label="操作"></el-table-column>
        <el-table-column align="center" header-align="center" prop="isDefaultAddress" label="是否默认地址"></el-table-column>
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
  import eventBus from '../../assets/js/eventBus.js';

  export default {
    data() {
      return {
        dataForm: {
          distributorAccount: '',
          distributorName: '',
          auditStatus: ''
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

      //审批驳回(只对审批状态为2 已提交)
      auditAddress() {
        let queryParams = {
          // 经销商用户Id
          auditAddress: 1
        };
        this.$http.get('customer/address/auditAddress', {params: queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            console.log(data);
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
      },

      //审批通过(只对审批状态为1初始化)
      rejectAddress() {
        let queryParams = {
          // 经销商用户Id
          auditAddress: 1
        };
        this.$http.get('customer/address/rejectAddress', {params: queryParams}).then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            console.log(data);
          } else {
            console.log(message);
          }
          console.log(res)
        }).catch((err) => {
          console.log(err);
        });
      },
      getTableData() {
        let queryParams = {
          distributorAccount: this.dataForm.distributorAccount,
          distributorName: this.dataForm.distributorName,
          auditStatus: this.dataForm.auditStatus,
          pageNumber: this.pagination.pageNumber,
          pageSize: this.pagination.pageSize
        };
        this.$http.get('customer/address/selectCustomerAddress', {params: queryParams}).then((res) => {
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
      if (!this.tableData.length) {
        this.getTableData();
      }
    }
  }
</script>
