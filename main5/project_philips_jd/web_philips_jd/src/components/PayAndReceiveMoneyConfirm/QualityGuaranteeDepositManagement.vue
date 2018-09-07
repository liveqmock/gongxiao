// 质保金管理界面
<template>
<div class="quality-guarantee-deposit-management" id="quality-guarantee-deposit-management">
  <el-form-item label="单号">
    <el-input type="text" id="reduce-amount" v-model="amountValue"></el-input>
  </el-form-item>
  <el-form-item label="账户类型">
    <el-select v-model="value" placeholder="全部">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="资金流向">
    <el-select v-model="value2" placeholder="全部">
      <el-option
        v-for="item in options2"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="未知">
    <el-select v-model="value3" placeholder="全部">
      <el-option
        v-for="item in options3"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
  </el-form-item>
  // 导出 上传收据按钮
  <div>
    <div>
      <button @click="exportFlow()">导出</button>
      <button @click="uploadDocument()">上传收据</button>
    </div>
  </div>

  <el-table
    ref="multipleTable"
    :data="tableData3"
    tooltip-effect="dark"
    style="width: 100%"
    @selection-change="handleSelectionChange">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      label="状态"
      width="120">
      <template slot-scope="scope">{{ scope.row.state }}</template>
    </el-table-column>
    <el-table-column
      prop="name"
      label="应付单据类型"
      width="120">
    </el-table-column>
    <el-table-column
      prop="name"
      label="单据编号"
      width="120">
    </el-table-column>
    <el-table-column
      prop="name"
      label="金额"
      width="120">
    </el-table-column>
    <el-table-column
      prop="name"
      label="单据时间"
      width="120">
    </el-table-column>
    <el-table-column
      prop="name"
      label="采购员"
      width="120">
    </el-table-column>
    <el-table-column
      prop="name"
      label="备注"
      width="120">
    </el-table-column>
    <el-table-column
      prop="name"
      label="供应商主体"
      width="120">
    </el-table-column>
    <el-table-column
      prop="name"
      label="结算单号"
      width="120">
    </el-table-column>
    <el-table-column
      prop="address"
      label="操作"
      show-overflow-tooltip>
    </el-table-column>
  </el-table>
</div>
</template>

<script>
  export default {
    data() {
      return {
        options: [{
          value: '选项1',
          accountCode:0,
          label: '采购入库单'
        }, {
          value: '选项2',
          accountCode:1,
          label: '新退货'
        }, {
          value: '选项3',
          accountCode:1,
          label: '售后退货'
        }, {
          value: '选项4',
          accountCode:1,
          label: '返点单'
        }, {
          value: '选项5',
          accountCode:1,
          label: '批次单'
        }, {
          value: '选项6',
          accountCode:1,
          label: '质保金'
        }],
        value:'',
        options2: [{
          value: '选项1',
          flowNo:0,
          label: '未退回'
        }, {
          value: '选项2',
          flowNo:1,
          label: '已退回'
        }],
        value2: '',
        options3: [{
          value: '选项1',
          flowNo:0,
          label: '飞利浦厨电京东'
        }, {
          value: '选项2',
          flowNo:1,
          label: '飞利浦个护京东'
        }, {
          value: '选项3',
          flowNo:3,
          label: '飞利浦加湿京东'
        }, {
          value: '选项4',
          flowNo:4,
          label: '飞利浦空净京东'
        }],
        value3: '',
        tableData3: [{
          state: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          state: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
        multipleSelection: []
      }
    },

    methods: {
      uploadDocument(){
        // 上传收据
      },
      exportFlow(){
        // 导出查询流水
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      }
    }
  }
</script>


