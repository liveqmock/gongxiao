<template>
  <div class="customer-editor-management" id="customer-editor-management">
    <div class="customer-editor-management-form" id="customer-editor-management-form">
      <el-form :inline="true" :model="dataForm" label-width="100px">
        <el-row>
          <el-form-item label="EAS客户编码">
            <el-input v-model="dataForm.EASCoding" clearable></el-input>
          </el-form-item>
          <el-form-item label="客户名称">
            <el-input v-model="dataForm.customerName" clearable></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="账号">
            <el-input v-model="dataForm.userName" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="dataForm.psd" clearable></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="结算方式">
            <el-select v-model="dataForm.settlementMethod" placeholder="结算方式" clearable>
              <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
              <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="账期" id="customerCode">
            <el-input v-model="dataForm.customerCode" clearable></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="联系方式" prop="accountPeriod">
            <el-input v-model="dataForm.accountPeriod" clearable></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="mailbox">
            <el-input v-model="dataForm.mailbox" clearable></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="营业地址" prop="businessAddress">
            <el-input type="textarea" v-model="dataForm.businessAddress" clearable></el-input>
          </el-form-item>
        </el-row>
      </el-form>
    </div>

    <div style="padding-top: 50px;">
      <el-row>
        <el-button type="button" :to="{path:'/xxx'}" style="color: #FF6600; border: aliceblue;">查看收货地址</el-button>
      </el-row>
      <el-table :data="checkReceivingAddress" style="width: 100%; margin-top: 0px;">
        <el-table-column prop="collectGoodsConsignee" label="收货人" width="180"></el-table-column>
        <el-table-column prop="collectGoodsAddress" label="所在地区" width="180"></el-table-column>
        <el-table-column prop="collectGoodsDetailedAddress" label="详细地址"></el-table-column>
        <el-table-column prop="collectGoodsPhone" label="电话/手机" width="180"></el-table-column>
        <el-table-column prop="collectGoodsZipCode" label="邮编" width="180"></el-table-column>
      </el-table>
    </div>

    <div style="padding-top: 50px;" class="customer-editor-management-table" id="customer-editor-management-table">
      <el-row>
        <el-button type="button" :to="{path:'/xxx'}" style="color: #FF6600; border: aliceblue;">查看收件地址</el-button>
      </el-row>
      <el-table :data="viewReceivingAddress" style="width: 100%; margin-top: 0px;">
        <el-table-column prop="collectionConsignee" label="收货人" width="180"></el-table-column>
        <el-table-column prop="collectionAddress" label="所在地区" width="180"></el-table-column>
        <el-table-column prop="collectionDetailedAddress" label="详细地址"></el-table-column>
        <el-table-column prop="collectionPhone" label="电话/手机" width="180"></el-table-column>
        <el-table-column prop="collectionZipCode" label="邮编" width="180"></el-table-column>
      </el-table>
    </div>


    <div clasee="exportBtn block" id="exportBtn">
      <el-button type="primary" @click="onSubmit()">提交</el-button>
      <el-button type="button" @click="onCancel()">取消</el-button>
      <el-button type="button" @click="onCancel()">作废</el-button>
    </div>


  </div>
</template>
<script>
  import ElRow from "element-ui/packages/row/src/row";

  export default {
    components: {ElRow},
    data() {
      return {
        dataForm: {
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
//        customerInfoAudited: {
//          EASCoding: [
//            {required: true, message: '请填写EAS客户编码', trigger: 'change'}
//          ],
//          customerName: [
//            {required: true, message: '请填写客户名称', trigger: 'change'}
//          ]
//        },
        //查看收货地址
        checkReceivingAddress: [{
          collectGoodsConsignee: '张三',
          collectGoodsAddress: '深圳',
          collectGoodsDetailedAddress: '福田填写客户名称填写客户名称填写客户名称填写客户名称',
          collectGoodsPhone: '12232443432',
          collectGoodsZipCode: '42232'
        }],
        //查看收件地址
        viewReceivingAddress: [{
          collectionConsignee: '张三1',
          collectionAddress: '深圳1',
          collectionDetailedAddress: '福田1',
          collectionPhone: 'xxxxxxxxx',
          collectionZipCode: 'xxxxxxx'
        }]
      };
    },
    methods: {
      onSubmit() {
      },
      onSaveAndSubmit() {
      },
      onCancel() {
      }
    }
  }
</script>
