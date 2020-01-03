<template>
    <el-form :rules="rules" class="user-register" :model="userInfo"
           label-width="85px" v-loading="loading">
            <h3 class="login_title">注册账号</h3>
            <el-form-item prop="userName" label="账号:">
            <el-input type="text" v-model="userInfo.userName" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item prop="userPassWord" label="密码:">
            <el-input type="password" v-model="userInfo.userPassWord" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="userPassWord1" label="确认密码:">
            <el-input type="password" v-model="userInfo.userPassWord1" placeholder="请再次输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="userIphone" label="手机号码:">
            <el-input type="text" v-model="userInfo.userIphone" placeholder="请输入手机号码"></el-input>
            </el-form-item>
            <el-form-item prop="age" label="年龄:">
            <el-input type="Number" v-model="userInfo.age" placeholder="请输入年龄"></el-input>
            </el-form-item>
            <el-form-item prop="sex">
                <el-radio v-model="userInfo.sex" label="1">男</el-radio>
                <el-radio v-model="userInfo.sex" label="2">女</el-radio>
            </el-form-item>
            <el-button type="primary" class="button-sumit" @click="vaid(userInfo)">提交</el-button>
            <el-button class="button-cancel" @click="cancel">取消</el-button>
    </el-form>
</template>

<script>
export default {
    data(){
        return {
            loading: false,
            userInfo: {
                userName: '',
                userPassWord: '',
                userPassWord1: '',
                userIphone: '',
                sex: '1',
                age: '0'
            },
            rules: {
                userName: [{required: true, message: '请输入账号', trigger: 'blur'}],
                userPassWord: [{required: true, message: '请输入密码', trigger: 'blur'}],
                userPassWord1: [{required: true, message: '请输入密码', trigger: 'blur'}]
            }
        }
    },
    methods: {
        vaid(row) {
            if(row.userName =='' || row.userPassWord=='' || row.userPassWord1==''){
                alert("请填写账号或密码")
                return
            }else {
                this.sumit(row);
            }
        },
        sumit(row) {
            this.loading = true;
            if(row.userPassWord !== row.userPassWord1) {
                alert("两次密码不一致")
                row.userPassWord = '';
                row.userPassWord1 = '';
                this.loading = false;
                return
            } else {
                this.postRequest1("http://localhost:8081/user/insertUserInfo",this.userInfo).then(Response=>{
                    if(Response && Response.status ==200 && Response.data=="SUCCESS") {
                        alert("注册成功")
                        this.$router.push("/login")
                    } else if(Response.data=="1"){
                        alert("手机号码不正确！")
                    } else if(Response.data=="2") {
                        alert("年龄对不上啊！！")
                    }else if(Response.data=="3") {
                        alert("账号名已存在！")
                    } else {
                        alert("注册失败")
                    }
                });
                this.loading = false;
            }
        },
        cancel() {
            this.$router.push("/login")
        }
    }
}
</script>

<style>
    .user-register {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    }
    .button-sumit {
        line-height: 0.5;
        width: 70px;
        border-radius: 15px;
        background-clip: padding-box;
    }
    .button-cancel {
        line-height: 0.5;
        width: 70px;
        border-radius: 15px;
        background-clip: padding-box;
    }
</style>