<template>
  <div class="customerInfoAudited-form" id="customerInfoAudited-form">
    <el-form :model="customerInfoAuditedForm" :rules="customerInfoAudited" ref="customerInfoAuditedForm"
             label-width="100px"
             class="demo-ruleForm">
      <el-row>
        <el-form-item label="EAS客户编码" prop="EASCoding" :disabled="true">
          <el-input v-model="customerInfoAuditedForm.EASCoding"></el-input>
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName" :disabled="true">
          <el-input v-model="customerInfoAuditedForm.customerName"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="税号" prop="userName">
          <el-input v-model="customerInfoAuditedForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="客户编码" prop="psd">
          <el-input v-model="customerInfoAuditedForm.psd"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="渠道" prop="settlementMethod">
          <el-select v-model="customerInfoAuditedForm.settlementMethod" placeholder="请选择" style="width: 93%;">
            <el-option label="单结" value="singleKnot"></el-option>
            <el-option label="账结" value="accountKnot"></el-option>
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <div clasee="exportBtn block" id="exportBtn">
          <el-button type="primary" @click="onSubmit()">保存</el-button>
          <el-button type="primary" @click="onSubmit()">保存并提交</el-button>
          <el-button type="button" @click="onCancel()" style=" margin-left: 3%">取消</el-button>
        </div>
      </el-row>
    </el-form>
  </div>
</template>
<script>
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {ElRow},
    data() {
      return {
        customerInfoAuditedForm: {
          EASCoding: '',
          customerName: '',
          userName: '',
          psd: '',
          settlementMethod: [],
          customerCode: '',
          accountPeriod: '',
          mailbox: '',
          dutyParagraph: '',
          contactInformation: '',
          businessAddress: '',
          collectGoodsConsignee: '',
          collectGoodsAddress: '',
          collectGoodsDetailedAddress: '',
          collectGoodsPhone: '',
          collectGoodsZipCode: '',
          collectionConsignee: '',
          collectionAddress: '',
          collectionDetailedAddress: '',
          collectionPhone: '',
          collectionZipCode: ''
        },
      };
    },
    methods: {
    }
  }
</script>
