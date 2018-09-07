<template>
  <div class="product-audit-management-form" id="product-audit-management-form">
    <el-form :inline="true" :model="dataForm" label-width="100px">
      <el-row>
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="EAS项目编码">
            <el-input v-model="dataForm.EASCode" placeholder="EAS项目编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="dataForm.projectName" placeholder="项目名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="公司主体">
            <el-input v-model="dataForm.companyBody" placeholder="公司主体" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-form-item label="账号" prop="userName">
          <el-input v-model="dataForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="psd">
          <el-input v-model="dataForm.psd"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="结算方式">
          <el-select v-model="dataForm.settlementMethod" placeholder="结算方式">
            <el-option label="飞利浦厨卫京东1" value="shanghai"></el-option>
            <el-option label="飞利浦厨卫京东1" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账期" prop="customerCode" id="customerCode">
          <el-input v-model="dataForm.customerCode"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="联系方式" prop="accountPeriod">
          <el-input v-model="dataForm.accountPeriod"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="mailbox">
          <el-input v-model="dataForm.mailbox"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="营业地址" prop="businessAddress">
          <el-input type="textarea" v-model="dataForm.businessAddress"></el-input>
        </el-form-item>
      </el-row>
    </el-form>
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
    <hr>
    <div style="padding-top: 50px;">
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
    <hr>

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
