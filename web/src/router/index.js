import { createRouter, createWebHistory } from 'vue-router'
import PkIndexVue from "@/views/pk/PkIndexView";
import RanklistIndexVue from "@/views/ranklist/RanklistIndexView";
import RecordIndexVue  from "@/views/record/RecordIndexView";
import RecordContentView from "@/views/record/RecordContentView";
import UserBotIndexVue from "@/views/user/bots/UserBotIndexView";
import NotFound from "@/views/error/NotFound";
import UserAccountLoginView from "@/views/user/account/UserAccountLoginView";
import UserAccountRegisterView from "@/views/user/account/UserAccountRegisterView";
import store from "@/store";
const routes = [
  {
    path:"/",
    name:"home",
    redirect:"/pk/",
    meta:{
      requestAuth:true
    }
  },
  {
    path:"/pk/",
    name:"pk_index",
    component:PkIndexVue,
    meta:{
      requestAuth:true
    }
  },
  {
    path:"/record/",
    name:"record_index",
    component: RecordIndexVue,
    meta:{
      requestAuth:true
    }
  },
  {
    path:"/record/:recordId/",
    name:"record_content",
    component: RecordContentView,
    meta:{
      requestAuth: true
    }
  },

  {
    path:"/ranklist/",
    name:"ranklist_index",
    component:RanklistIndexVue ,
    meta:{
      requestAuth:true
    }
  },
  {
    path:"/user/account/login/",
    name:"user_account_login",
    component:UserAccountLoginView,
    meta:{
      requestAuth:false
    }
  },
  {
    path: "/user/account/qq/receive_code",
    name: "user_account_qq_receive_code",
    component: ()=>import('@/views/user/account/UserAccountQQReceiveCodeView'),
    meta:{
      requestAuth : false
    }
  },
  {
    path:"/user/account/register/",
    name:"user_account_register",
    component:UserAccountRegisterView,
    meta:{
      requestAuth:false
    }
  },
  {
    path:"/user/bot/",
    name:"user_bot_index",
    component: UserBotIndexVue,
    meta:{
      requestAuth:true
    }
  },
  {
    path:"/404/",
    name:"404",
    component: NotFound,
    meta:{
      requestAuth:false
    }
  },
  {
    path:"/:catchAll(.*)",
    redirect: "/404/",
    meta:{
      requestAuth:false
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next)=>{
  if(to.meta.requestAuth && !store.state.user.is_login){
    next({name : "user_account_login"});
  }else{
    next();
  }

})


export default router
