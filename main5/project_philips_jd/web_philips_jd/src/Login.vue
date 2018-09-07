<template>
  <div id="login" class="login">
    <div class="login-content">
      <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px"
               class="demo-ruleForm login-container">
        <h2><img src="./assets/login/yhlogo.png" alt="logo" width="57" height="60">供应链协同平台</h2>
        <el-form-item prop="account">
          <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>
        <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
        <el-form-item style="width:100%;">
          <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录
          </el-button>
          <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        logining: false,
        ruleForm2: {
          account: 'admin',
          checkPass: '123456'
        },
        rules2: {
          account: [
            {required: true, message: '请输入账号', trigger: 'blur'},
            //{ validator: validaePass }
          ],
          checkPass: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            //{ validator: validaePass2 }
          ]
        },
        checked: true
      };
    },
    methods: {
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            //NProgress.start();
            var loginParams = {username: this.ruleForm2.account, password: this.ruleForm2.checkPass};
            requestLogin(loginParams).then(data => {
              this.logining = false;
              //NProgress.done();
              let {msg, code, user} = data;
              if (code !== 200) {
                this.$message({
                  message: msg,
                  type: 'error'
                });
              } else {
                sessionStorage.setItem('user', JSON.stringify(user));
                this.$router.push({path: '/table'});
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>
  .login {
    position: fixed;
    right: 0;
    left: 0;
    top: 0;
    bottom: 0;
    overflow: hidden;
    background: url(./assets/login/login-bg.jpg) no-repeat;
    background-size: 100% 100%;
  }

  .login-content {
    width: 354px;
    height: 354px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
  el-form{
    img{
      display: inline-block;
      margin-right: 12px;
    }
    h2 {
      padding-left: 38px;
      font-size: 30px;
      color: #fff;
    }
    input {
      width: 348px;
      height: 50px;
      display: block;
      margin-top: 20px;
      border: 1px solid #7d98bf;
      color: #7d98bf;
      font-size: 18px;
      text-indent: 40px;
      background-color: #344663;
      outline: none;
    }
    input.inputfocus {
      color: #fff;
      border: 1px solid #fff;
    }
    input:-webkit-autofill {
      -webkit-box-shadow: 0 0 0 400px #344663 inset;
    }
  }
</style>
