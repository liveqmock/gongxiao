`
<template>
  <div class="notice-receipt-goods" id="notice-receipt-goods">
    <div class="notice-receipt-goods-form">
      <el-form :inline="true" :model="purchaseInfo" class="" labelWidth="100px">
        <el-row>
          <el-form-item label="货品编码">
            <el-input v-model="purchaseInfo.purchaseOrderNumber" placeholder="货品编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库" required>
            <el-select v-model="purchaseInfo.supplierId" placeholder="仓库"></el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="value1"
              type="date"
              placeholder="选择日期"  style="width: 93%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>

    <!--表格-->
    <div class="notice-receipt-goods-table">
      <el-table :data="tableData">
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="货品名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="contractReferenceOrderNo" label="型号"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="品牌"></el-table-column>
        <el-table-column align="center" header-align="center" prop="contractReferenceOrderNo" label="指导价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="成本价"></el-table-column>
        <el-table-column align="center" header-align="center" prop="contractReferenceOrderNo" label="采购数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="剩余需入库数量"></el-table-column>
        <el-table-column align="center" header-align="center" prop="contractReferenceOrderNo" label="本次预约"></el-table-column>
        <el-table-column align="center" header-align="center" prop="supplierOrderNo" label="备注"></el-table-column>
      </el-table>
    </div>

    <div clasee="exportBtn" id="exportBtn">
      <el-button type="primary" @click="preservation()" style=" margin-left: 40%">提交</el-button>
      <el-button type="button" onclick="cancel()" style=" margin-left: 3%">返回</el-button>
    </div>

  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import eventBus from '../../assets/js/eventBus.js';
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {ElRow},
    data() {
      return {
        purchaseInfo: {
          purchaseOrderNumber: "",
          supplierOrderNumber: "",
          supplierId: "",
          warehouseId: "",
          createTime: ""
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
      }
    },
    computed: {
      ...mapGetters([
        'getProjectInfo'
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
      checkClick(scope) {
        let {purchaseOrderNumber} = scope;
        console.log(purchaseOrderNumber);
      },
      deleteRow(index, rows) {
        rows.splice(index, 1);
      },
    },
    watch: {
      getProjectInfo(newProjectId) {
        this.changedProjectId = newProjectId;
      }
    },
    mounted() {
      eventBus.$on('loadingDefaultProjectId', () => {
      });
    },
    created() {
    }
  }
</script>

<style scoped lang="less">

</style>
