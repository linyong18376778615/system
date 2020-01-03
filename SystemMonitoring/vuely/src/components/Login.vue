<template>
  <el-form :rules="rules" class="login-container" :model="loginForm"
            v-loading="loading">
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="username" label="账号:" label-width="85px">
      <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号" maxlength="20"></el-input>
    </el-form-item>
    <el-form-item prop="password" label="密码:" label-width="85px">
      <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码" maxlength="20"></el-input>
    </el-form-item>
    <el-form-item prop="vaidCode" label="验证码:" label-width="85px">
          <el-input type="text" class="login-input" v-model="loginForm.vaidCode" placeholder="请输入验证码" @blur="checkNum" maxlength="4"></el-input>
          <input type="button" class="login_vaid" v-model="loginForm.randomCode" @click="createCode"/>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="checked" label-position="left">7天内自动登录</el-checkbox>
      <el-link class="el-link.el-link--default" href="http://localhost:8080/?#/changePassword" target="_blank">修改密码</el-link>
      <el-link class=".el-link.el-link--primary" href="http://localhost:8080/?#/register" target="_blank" type="primary">注册</el-link>
    <el-button type="primary" style="width: 100%" @click="submitClick">登录</el-button>
  </el-form>
  
</template>
<script>
  export default{
    data(){
      return {
        rules: {
          username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'}],
          vaidCode: [{required: true, message: '请输入验证码', trigger: 'blur'}]
        },
        checked: true,
        vaidcheck: false,
        loginForm: {
          username: '',
          password: '',
          vaidCode: '',
          randomCode: '',
          vaidTrue: ''
        },
        loading: false
      }
    },
    created() {
      this.createCode();//初始化生成一个4位数的验证码
    },
    methods: {
      setCookie(c_name, c_pwd, exdays) {
        var exdate = new Date(); //获取时间
        exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
        //字符串拼接cookie
        window.document.cookie = "userName" + "=" + c_name + ";path=/;expires=" + exdate.toGMTString();
        window.document.cookie = "userPwd" + "=" + c_pwd + ";path=/;expires=" + exdate.toGMTString();
      },
      getCookie: function() {
        if (document.cookie.length > 0) {
            var arr = document.cookie.split('; '); //这里显示的格式需要切割一下自己可输出看下
            for (var i = 0; i < arr.length; i++) {
                var arr2 = arr[i].split('='); //再次切割
                //判断查找相对应的值
                if (arr2[0] == 'userName') {
                    this.loginForm.username = arr2[1]; //保存到保存数据的地方
                } else if (arr2[0] == 'userPwd') {
                    this.loginForm.password = arr2[1];
                }
            }
        }
      },
      clearCookie: function() {
        this.setCookie("", "", -1); //修改2值都为空，天数为负1天就好了
      },
      checkNum() {
        var num = this.loginForm.vaidCode.toUpperCase();
        if(this.loginForm.vaidCode != '') {
          if(num == this.loginForm.randomCode) {
          this.loginForm.vaidTrue = "验证码正确";
          this.vaidcheck = true
          }else{
            this.loginForm.vaidTrue = "验证码错误!!!";
            this.loginForm.vaidCode = ''
            this.vaidcheck = false
            this.createCode();
          }
        }
      },
      createCode() {
        var code = "";
        var codeLength = 4;//验证码的长度
        var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R',
        'S','T','U','V','W','X','Y','Z');//随机数
        for(var i = 0; i < codeLength; i++) {
          var index = Math.floor(Math.random()*36);//取得随机数的索引（0~35）
          code += random[index];//根据索引取得随机数加到code上
        }
        this.loginForm.randomCode = code
      },
      changePassword() {
        this.$router.push("/changePassword")
      },
      register() {
        this.$router.push("/register")
      },
      submit() {
        var _this = this;
        if (_this.checked == true) {
          //传入账号名，密码，和保存天数3个参数
          _this.setCookie(_this.loginForm.username, _this.loginForm.password, 7);
        }else {
          // _this.setCookie(_this.loginForm.username, _this.loginForm.password, 0.00005);
          //清空Cookie
          _this.clearCookie();
        }
        this.postRequest('http://localhost:8081/login/user', {
          name: this.loginForm.username,
          password: this.loginForm.password
          }).then(resp=> {
            _this.loading = false;
            if (resp && resp.status == 200) {
              if(resp.data == "SUCCESS"){
                sessionStorage.setItem('userName',this.loginForm.username);
                sessionStorage.setItem('isLogin',true);
                this.$store.dispatch('setUser',this.loginForm.username);
                this.$message({
                  showClose: true,
                  message: '登陆成功！',
                  type: 'success',
                  center: true
                });
                this.$router.push("/home")
              } else if(resp.data == "1") {
                this.$message({
                  showClose: true,
                  message: '账号或密码错误！',
                  type: 'error',
                  center: true
                });
              } else {
              this.$message({
                  showClose: true,
                  message: '登录失败！',
                  type: 'error',
                  center: true
                });
              }
            }else {
              this.$message({
                  showClose: true,
                  message: '登录失败！',
                  type: 'error',
                  center: true
                });
            }
          });
      },
      submitClick: function () {
        this.loading = true;
        if(this.loginForm.username=='' || this.loginForm.password=='') {
          this.$message({
            message: '账号或密码不能为空',
            type: 'warning',
            center: true
          });
          this.loading = false;
        } else {
          if(this.vaidcheck == false) {
            this.$message({
            message: '请输入正确的验证码',
            type: 'warning',
            center: true
            });
            this.loading = false;
          } else {
            this.submit();
          }
        }
        
      }
    },
    mounted() {
        this.getCookie();
    },
  }
</script>
<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
  .el-link.el-link--primary {
    color: #409EFF;
    left: 30px;
  }
  .el-link.el-link--default {
    color: #606266;
    left: 18px;
  }
  .login_vaid {
    color: rgb(248, 244, 244);
    background-color: #3b444d69;
    height: 36px;
    width: 78px;
    font-size: 22px;
    font-family: '宋体';
    font-style: oblique;
    font-weight: bolder;
  }
  .login-input {
    width: 140px;
    right: 20px;
  }
</style>
