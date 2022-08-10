<template>
  <PlayGround v-if="$store.state.pk.status === 'playing'" />
  <MatchGround v-if = "$store.state.pk.status === 'matching'" />
</template>

<script>
import PlayGround from "@/components/PlayGround";
import MatchGround from "@/components/MatchGround";
//加载时卸载时，需要引入
import {onMounted, onUnmounted} from "vue";
import {useStore} from "vuex";

export default {
  name: "PkIndexVue",

  components:{
    PlayGround,
    MatchGround,
  },
  setup(){
    const store = useStore();
    let socket = null;
    const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
    onMounted(() =>{
      store.commit("updateOpponent",{
        username:"sm",
        photo:"https://s1.ax1x.com/2022/08/10/v39Hbt.png",
      })
      socket = new WebSocket(socketUrl);
      socket.onopen = () =>{
        console.log("connected");
        store.commit("updateSocket", socket);
      };
      socket.onclose = () =>{
        console.log("disconnected");
      };
      socket.onmessage = (message) => {
        const data = JSON.parse(message.data);
        if(data.event === "start-matching"){
          store.commit("updateOpponent",{
            username:data.opponent_username,
            photo:data.opponent_photo,
          });
          setTimeout(()=>{
            store.commit("updateStatus","playing");
          },2000);
          store.commit("updateGamemap",data.gamemap);
        }
      };

    });
    onUnmounted(() => {
      socket.close();
      store.commit("updateStatus","matching");
    })
  }

}
</script>

<style scoped>

</style>