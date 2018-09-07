<template>
  <div class="project-info" id="project-info">
    <el-form :label-position="labelPosition" label-width="180px" :model="dataForm" style="float: right">
      <el-form-item label="EAS项目编码">
        <el-input v-model="dataForm.projectID" placeholder="EAS项目编码" clearable :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="项目名称">
        <el-input v-model="dataForm.projectName" placeholder="项目名称" clearable :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="公司主体">
        <el-input v-model="dataForm.companyBody" placeholder="公司主体" clearable :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="月度返利应收比例">
        <el-input v-model="dataForm.monthlyRebateRatio" placeholder="月度返利应收比例" clearable></el-input>
      </el-form-item>
      <el-form-item label="项目编码">
        <el-input v-model="dataForm.projectCoding" placeholder="项目编码" clearable></el-input>
      </el-form-item>
      <el-form-item label="季度返利应收比例">
        <el-input v-model="dataForm.quarterlyRebateRatio" placeholder="季度返利应收比例" clearable></el-input>
      </el-form-item>
      <el-form-item label="供应商">
        <el-select v-model="dataForm.supplierId" placeholder="供应商">
          <el-option :label="item.supplierName" :value="item.supplierId" v-for="(item,index) in supplierList"
                     :key="index">{{item.supplierName}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年度返利应收比例">
        <el-input v-model="dataForm.yearRebateRatio" placeholder="年度返利应收比例" clearable></el-input>
      </el-form-item>
      <el-form-item label="前返现金返利">
        <el-input v-model="dataForm.frontCashRebate" placeholder="前返现金返利" clearable></el-input>
      </el-form-item>
      <el-form-item label="后返现金返利">
        <el-input v-model="dataForm.backCashRebate" placeholder="后返现金返利" clearable></el-input>
      </el-form-item>
    </el-form>
    <div style=""></div>

    <div class="project-info-data" id="project-info-data" style="margin-left: 80px">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="onSave()">保存</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onCancels()">取消</el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        labelPosition: 'right',
        dataForm: {
          projectID: '',
          projectName: '',
          companyBody: '',
          monthlyRebateRatio: '',
          projectCoding: '',
          quarterlyRebateRatio: '',
          supplierId: '',
          yearRebateRatio: '',
          frontCashRebate: '',
          backCashRebate: ''
        },
        supplierList:[{}]
      }
    },

    methods: {
      //保存(接口有问题参数不一致)
      onSave() {
        let queryParams = {
//          easProjectCode: this.dataForm.batchNumber,
//          supplierId: this.dataForm.warehouseNumber,
//          supplierName: this.dataForm.outWarehouseSingleNumber,
//          beforeCouponAmount: this.dataForm.startDate,
//          afterCouponAmount: this.dataForm.endDate,
//          monthCouponRate: this.dataForm.batchNumber,
//          quarterCouponRate: this.dataForm.warehouseNumber,
//          annualCouponRate: this.dataForm.outWarehouseSingleNumber,
//          operater: this.dataForm.startDate,
//          lastUpdateTime: this.dataForm.endDate
        };
        this.$http.get('/inventory/center/selectDailyInventoryReport',{params:queryParams}).then((res) => {
          let {data: {data: {list, total}, message, returnCode}, status} = res;
          if (status === 200 && returnCode === 0) {
            this.tableData = data;
          } else {
            console.log(message);
          }
        }).catch((err) => {
          console.log(err);
        });
      },

      //取消
      onCancels() {
      },

      //获取供应商
      getSupplierList(){
        this.$http.get('/supplier/selectAllSupplier').then((res) => {
          let {data: {data, message, returnCode}, status} = res;
          if(status === 200 && returnCode === 0){
            this.supplierList = data;
          }else{
            console.log(message);
          }
        }).catch((err) => {
          console.log(err)
        })
      },

    //加载项目列表数据到到列表
      getTableData() {
        var _this = this;
        this.$http.get('/base/project/getProjectById', {
          params: {
            //跳转页面传过来的EASCode
//            EASCode:this.projectID
            EASCode: "146798161"
          }
        }).then(function (res) {
          if (res.data.returnCode == 0) {
//            // 把数据加载到字段中
            _this.dataForm.projectID = res.data.data.easProjectCode;
            _this.dataForm.projectName = res.data.data.projectName;
            _this.dataForm.companyBody = res.data.data.company;
            _this.dataForm.monthlyRebateRatio = res.data.data.monthCouponRate;
            _this.dataForm.projectCoding = res.data.data.projectCode;
            _this.dataForm.quarterlyRebateRatio = res.data.data.quarterCouponRate;
            _this.dataForm.Supplier = res.data.data.supplierName;
            _this.dataForm.yearRebateRatio = res.data.data.annualCouponRate;
            _this.dataForm.frontCashRebate = res.data.data.beforeCouponAmount;
            _this.dataForm.backCashRebate = res.data.data.afterCouponAmount;
          }
        }).catch(function (err) {
          console.log(err);
        })
      }
    },
    mounted: function () {
      this.getTableData();
    },
    created() {
      this.getSupplierList();
    }
  }
</script>
<style>


</style>
