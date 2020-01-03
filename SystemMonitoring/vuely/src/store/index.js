import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
 
const state={//要设置的全局访问的state对象
  currentUser: '',
    isLogin: false,
    token: ''
  //要设置的初始属性值
};

const getters = {   //实时监听state值的变化(最新状态)
  getUser(state) {  //方法名随意,主要是来承载变化的showFooter的值
     return state.currentUser
  },
  getLoginStatus(){  //方法名随意,主要是用来承载变化的changableNum的值
     return state.isLogin
  }
};

const mutations = {
  userStatus(state,user) {   //自定义改变state初始值的方法，这里面的参数除了state之外还可以再传额外的参数(变量或对象);
    if(user != '') {
      state.currentUser = user;
      state.isLogin = true;
      sessionStorage.setItem('userName',user);
      sessionStorage.setItem('isLogin',true);
    } else if (user == null) {
      sessionStorage.setItem("userName",null);
      sessionStorage.setItem("userToken","");
      sessionStorage.setItem("isLogin",false);
      state.currentUser = null;
      state.isLogin = false;
      state.token = "";
    }
  }
};

const actions = {
  setUser(context,user) {  //自定义触发mutations里函数的方法，context与store 实例具有相同方法和属性
    context.commit('userStatus',user);
  }
};

const store = new Vuex.Store({
  state,
  getters,
  mutations,
  actions
});

export default store;
