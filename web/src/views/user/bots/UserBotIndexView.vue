<template>
<ContentFiled>我的bot</ContentFiled>
</template>

<script>
import ContentFiled from "@/components/ContentFiled";
import $ from 'jquery';
import {useStore} from "vuex";

export default {
  name: "UserBotIndexVue",
  components:{
    ContentFiled,
  },
  setup(){
    const store = useStore();
    $.ajax({
      url: "http://localhost:3000/user/bot/add/",
      type:"post",
      data:{
        title:"mybot",
        description:"描述",
        content:"代码",
      },
      headers:{
        Authorization:"Bearer "+store.state.user.token,
      },
      success(resp){
        console.log(resp)
      },
      error(resp){
        console.log(resp);
      }
    })
    // $.ajax({
    //   url: "http://localhost:3000/user/bot/remove/",
    //   type:"post",
    //   data:{
    //    bot_id:'3',
    //   },
    //   headers:{
    //     Authorization:"Bearer "+store.state.user.token,
    //   },
    //   success(resp){
    //     console.log(resp)
    //   },
    //   error(resp){
    //     console.log(resp);
    //   }
    // })
    // $.ajax({
    //       url: "http://127.0.0.1:3000/user/bot/update/",
    //       type: "POST",
    //       data: {
    //           bot_id: 4,
    //           title: "Bot的标题7",
    //           description : "Bot的描述7",
    //           content: "Bot的代码7",
    //       },
    //       headers: {
    //           Authorization: "Bearer " + store.state.user.token,
    //       },
    //       success(resp) {
    //           console.log(resp);
    //       },
    //       error(resp) {
    //           console.log(resp);
    //       }
    //   })
    $.ajax({
      url: "http://127.0.0.1:3000/user/bot/getlist/",
      type: "get",

      headers: {
        Authorization: "Bearer " + store.state.user.token,
      },
      success(resp) {
        console.log(resp);
      },
      error(resp) {
        console.log(resp);
      }
    })

    }
}
</script>

<style scoped>

</style>