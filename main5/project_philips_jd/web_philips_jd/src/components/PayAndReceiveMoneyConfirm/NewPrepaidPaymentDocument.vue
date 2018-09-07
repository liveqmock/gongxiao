<template>
  <div class="new-prepaid-payment-document" id="new-prepaid-payment-document">
<h3>基本信息</h3>
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="供应商"  prop="supplier">
    <el-input v-model="ruleForm.supplier"></el-input>
  </el-form-item>
  <el-form-item label="结算方式" prop="settlementStyle">
    <el-select v-model="ruleForm.settlementStyle" placeholder="请选择">
      <el-option label="结算单" value="shanghai"></el-option>
      <el-option label="现金付款" value="beijing"></el-option>
      <el-option label="其他代垫" value="beijing"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="付款方"  prop="payer">
    <el-input v-model="ruleForm.payer"></el-input>
  </el-form-item>
  <el-form-item label="结算单号"  prop="settlementNo">
    <el-input v-model="ruleForm.settlementNo"></el-input>
  </el-form-item>
  <el-form-item label="收款方"  prop="receiver">
    <el-input v-model="ruleForm.receiver"></el-input>
  </el-form-item>
  <el-form-item label="业务日期" required>
    <el-col :span="11">
      <el-form-item prop="date1">
        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>
      </el-form-item>
    </el-col>
  </el-form-item>
  <el-form-item label="税号" prop="taxNo">
    <el-switch v-model="ruleForm.taxNo"></el-switch>
  </el-form-item>
  <el-form-item label="联系方式"  prop="contactStyle">
    <el-input v-model="ruleForm.contactStyle"></el-input>
  </el-form-item>
  <el-form-item label="地址" prop="address">
    <el-input type="textarea" v-model="ruleForm.address"></el-input>
  </el-form-item>
  <el-form-item label="CNY开户行" prop="CNYBank">
    <el-input v-model="ruleForm.CNYBank"></el-input>
  </el-form-item>
  <el-form-item label="CNY账号" prop="CNYAccount">
    <el-input v-model="ruleForm.CNYAccount"></el-input>
  </el-form-item>
  <el-form-item label="USD开户行" prop="USDBank">
    <el-input v-model="ruleForm.USDBank"></el-input>
  </el-form-item>
  <el-form-item label="USD账号" prop="USDAccount">
    <el-input v-model="ruleForm.USDAccount"></el-input>
  </el-form-item>
  <el-form-item label="HKD开户行" prop="HKDBank">
    <el-input v-model="ruleForm.HKDBank"></el-input>
  </el-form-item>
  <el-form-item label="HKD账号" prop="HKDAccount">
    <el-input v-model="ruleForm.HKDAccount"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" >保存</el-button>
    <el-button type="primary" @click="submitForm('ruleForm')">保存并提交</el-button>
    <el-button @click="resetForm('ruleForm')">取消</el-button>
  </el-form-item>
</el-form>

<h3>费用明细</h3>
// 在form中嵌套还是在table中 费用明细里点击添加 则增加一行?
</div></template>
<script>
  export default {
    data() {
      return {
        ruleForm: {
          supplier: '',
          settlementStyle: '',
          payer:'',
          settlementNo:'',
          receiver:'',
          date1: '',
          taxNo:'',
          contactStyle:'',
          address: '',
          CNYBank:'',
          CNYAccount:'',
          USDBank:'',
          USDAccount:'',
          HKDBank:'',
          HKDAccount:''
        },
        rules: {
          supplier: [
            { required: true, message: '请输入供应商名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          settlementStyle: [
            { required: true, message: '请选择结算方式', trigger: 'change' }
          ],
          date1: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
          ],
          address: [
            { required: true, message: '请填写地址', trigger: 'blur' }
          ],
          payer: [
            { required: true, message: '请填写付款方', trigger: 'blur' }
          ],
          receiver: [
            { required: true, message: '请填写收款方', trigger: 'blur' }
          ],
          taxNo: [
            { required: true, message: '请填写税号', trigger: 'blur' }
          ],
          contactStyle: [
            { required: true, message: '请填写联系方式', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>
