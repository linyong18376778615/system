<template>
    <el-form :rules="rules" class="changer-password" :model="userInfo"
           label-width="85px" v-loading="loading">
            <h3 class="login_title">修改密码</h3>
            <el-form-item prop="userName" label="账号:">
            <el-input v-model="userInfo.userName" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item prop="oldPassWord" label="原始密码:">
            <el-input type="password" v-model="userInfo.oldPassWord" placeholder="请输入原始密码"></el-input>
            </el-form-item>
            <el-form-item prop="newPassWord1" label="新密码:">
            <el-input type="password" v-model="userInfo.newPassWord1" placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item prop="newPassWord2" label="新密码:">
            <el-input type="password" v-model="userInfo.newPassWord2" placeholder="请再次输入密码"></el-input>
            </el-form-item>
            <el-button type="primary" class="button-sumit" @click="sumit(userInfo)">提交</el-button>
            <el-button class="button-cancel" @click="cancel">取消</el-button>
    </el-form>
</template>

<script>
export default {
    data(){
        return {
            rules: {
                userName: [{required: true, message: '请输入账号', trigger: 'blur'}],
                oldPassWord: [{required: true, message: '请输入密码', trigger: 'blur'}],
                newPassWord1: [{required: true, message: '请输入密码', trigger: 'blur'}],
                newPassWord2: [{required: true, message: '请输入密码', trigger: 'blur'}]
            },
            userInfo: {
                userName: '',
                oldPassWord: '',
                newPassWord1: '',
                newPassWord2: ''
            },
            loading: false
        }
    },
    methods: {
        cancel() {
            this.$router.push("/login")
        },
        sumit(row) {
            this.loading = true;
            if(row.newPassWord1 !== row.newPassWord2) {
                alert("两次密码不一致")
                row.newPassWord1 = '';
                row.newPassWord2 = '';
                this.loading = false;
                return
            } else {
                this.postRequest1("http://localhost:8081/user/updateUserInfo",this.userInfo).then(Response=>{
                    if(Response && Response.status ==200 && Response.data=="SUCCESS") {
                        alert("修改成功")
                    } else if(Response.data=="1"){
                        alert("账号或密码错误")
                    } else {
                        alert("修改失败")
                    }
                });
                this.loading = false;
            }
        }
    }
}
</script>

<style>
    .changer-password {
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