import { createRouter, createWebHistory } from 'vue-router'
import PkIndexVue from "@/views/pk/PkIndexVue";
import RanklistIndexVue from "@/views/ranklist/RanklistIndexVue";
import RecordIndexVue  from "@/views/record/RecordIndexVue";
import UserBotIndexVue from "@/views/user/bots/UserBotIndexVue";
import NotFound from "@/views/error/NotFound";

const routes = [
  {
    path:"/",
    name:"home",
    redirect:"/pk/"
  },
  {
    path:"/pk/",
    name:"pk_index",
    component:PkIndexVue,
  },
  {
    path:"/record/",
    name:"record_index",
    component: RecordIndexVue,
  },
  {
    path:"/ranklist/",
    name:"ranklist_index",
    component:RanklistIndexVue ,
  },
  {
    path:"/user/bot/",
    name:"user_bot_index",
    component: UserBotIndexVue,
  },
  {
    path:"/404/",
    name:"404",
    component: NotFound,
  },
  {
    path:"/:catchAll(.*)",
    redirect: "/404/",
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
