<template>
  <div class="purchase-management-form" id="purchase-management-form">
    <el-form :model="warehouse" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-row>
        <el-form-item label="EAS仓库编码" prop="id" >
          <el-input v-model="warehouse.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="仓库名称" prop="name">
          <el-input v-model="warehouse.name" disabled></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="仓库类型" prop="type">
          <el-select v-model="warehouse.type" placeholder="请选择">
            <el-option label="自有仓" value="ownStorehouse"></el-option>
            <el-option label="工厂仓" value="factoryStorehouse"></el-option>
            <el-option label="客户仓" value="customerStorehouse "></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="" prop="yn">
          <el-checkbox v-model="warehouse.yn">是否向仓库发送通知单</el-checkbox>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="负责人" prop="personInCharge">
          <el-input v-model="warehouse.personInCharge"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="contactInformation">
          <el-input v-model="warehouse.contactInformation"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="库位" prop="libraryPosition" style="width: 600px">
          <el-input type="textarea" v-model="warehouse.libraryPosition"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="所在地区" prop="whichLocated">
          <el-select v-model="warehouse.country" prop="whichLocated1">
            <el-option label="中国大陆" value="shanghai"></el-option>
            <el-option label="中国香港" value="beijing"></el-option>
          </el-select>
          <el-select v-model="warehouse.region" prop="whichLocated2">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="详细地址" prop="detailedAddress" style="width: 600px">
          <el-input type="textarea" v-model="warehouse.detailedAddress"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="EAS系统" prop="region">
          <el-select v-model="warehouse.EAS_System" placeholder="请选择">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="WMS系统" prop="region">
          <el-select v-model="warehouse.WMS_System" placeholder="请选择">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-optiosn label="区域二" value="beijing"></el-optiosn>
          </el-select>
        </el-form-item>
      </el-row>

      <el-row>
        <div clasee="exportBtn block" id="exportBtn">
          <el-button type="primary" @click="onSubmit()">保存</el-button>
          <el-button type="button" @click="onCancel()" style=" margin-left: 3%">取消</el-button>
        </div>
      </el-row>

    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        warehouse: {
          id: '',
          name: '',
          type: [],
          yn: '',
          personInCharge: '',
          contactInformation: '',
          libraryPosition: '',
          country: [],
          region: [],
          detailedAddress: '',
          EAS_System: [],
          WMS_System: []
        },
        rules: {
          libraryPosition: [
            {required: true, message: '请填写库位', trigger: 'change'}
          ],
//          whichLocated: [
//            {required: true, message: '请选择所在地区', trigger: 'change'}
//          ],
          whichLocated1: [
            {required: true, message: '请选择所在地区', trigger: 'change'}
          ],
          whichLocated2: [
            {required: true, message: '请选择所在地区', trigger: 'change'}
          ],
          detailedAddress: [
            {required: true, message: '请填写详细地址', trigger: 'change'}
          ]
        }
      };
    },
    methods: {
      onSubmit(){},
      onCancel(){}
    }
  }
</script>

