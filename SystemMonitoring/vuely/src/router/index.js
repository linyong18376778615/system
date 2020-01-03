import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import Register from '@/components/Register'
import ChangePassword from '@/components/ChangePassword'
import SystemInfo from '@/components/systemInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: '主页',
      component: Home
    },
    {
      path: '/register',
      name: '注册',
      component: Register
    },
    {
      path: '/changePassword',
      name: '修改密码',
      component: ChangePassword
    },
    {
      path: '/systemInfo',
      name: '系统情况',
      component: SystemInfo
    }
  ]
})
