<template>
    <el-form :model="loginInfo" v-loading="loading">
    <div>欢迎您登录：{{loginInfo.userName}}</div><br>
    <el-button class="my-info" @click="getMyInfo">个人信息</el-button>
    <el-dialog title="个人信息" :visible.sync="dialogFormVisible" class="el-dialog__wrapper">
        <el-form :model="loginInfo">
            <el-form-item label="账号" :label-width="formLabelWidth">
            <el-input v-model="loginInfo.userName" disabled></el-input>
            </el-form-item>
            <!-- <el-form-item label="密码" :label-width="formLabelWidth">
            <el-input v-model="loginInfo.userPassword" disabled></el-input>
            </el-form-item> -->
            <el-form-item label="电话号码" :label-width="formLabelWidth">
            <el-input v-model="loginInfo.userIphone" disabled></el-input>
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth">
            <el-input v-model="loginInfo.sex" disabled></el-input>
            </el-form-item>
            <el-form-item label="年龄" :label-width="formLabelWidth">
            <el-input v-model="loginInfo.age" disabled></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <!-- <el-button @click="dialogFormVisible = false">取 消</el-button> -->
            <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
        </el-dialog>

    </el-form>
</template>

<script>
export default {
    data() {
        return {
            loginInfo: {
                userName: '',
                userPassword: '',
                userIphone: '',
                sex: '',
                age: ''
            },
            dialogTableVisible: false,
            dialogFormVisible: false,
            loading: false,
            formLabelWidth: '70px',
            isLogin: true
        }
    },
    created() {
        this.getCookie();
    },
    methods: {
        getCookie: function() {
            console.log(this.$store.getters.getLoginStatus)
            if (document.cookie.length > 0) {
                var arr = document.cookie.split('; '); //这里显示的格式需要切割一下自己可输出看下
                for (var i = 0; i < arr.length; i++) {
                    var arr2 = arr[i].split('='); //再次切割
                    //判断查找相对应的值
                    if (arr2[0] == 'userName') {
                        this.loginInfo.userName = arr2[1]; //保存到保存数据的地方
                    } else if (arr2[0] == 'userPwd') {
                        this.loginInfo.userPassword = arr2[1];
                    }
                }
            } else {
                this.isLogin = false;
            }
            if(this.loginInfo.userName == null || this.loginInfo.userName ==''){
                if(sessionStorage.getItem("userName")){
                this.loginInfo.userName = sessionStorage.getItem("userName");
                this.isLogin = true;
                } 
            }
            if(this.loginInfo.userName == null || this.loginInfo.userName ==''){
                if(this.$store.getters.getUser){
                    this.loginInfo.userName = this.$store.getters.getUser;
                    this.isLogin = true;
                }
            }
            if(!this.isLogin) {
                this.$router.push("/login")
            }
        },
        getMyInfo() {
            this.dialogFormVisible = true;
            var url = 'http://localhost:8081/login/userInfo/';
            if(this.loginInfo.username!='') {
                url += this.loginInfo.userName
            }
            this.getRequest(url).then(res=>{
                if(res && res.status ==200) {
                   this.loginInfo = res.data 
                }
            });
        }
    }
}
</script>


<style >
    .my-info {
        font-size: 16px;
    }
    .el-dialog__wrapper {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 22%;
    overflow: auto;
    margin: 0;
    width: 50%;
}
</style>