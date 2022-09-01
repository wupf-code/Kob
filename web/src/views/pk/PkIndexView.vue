<template>
  <PlayGround v-if="$store.state.pk.status === 'playing'" />
  <MatchGround v-if = "$store.state.pk.status === 'matching'" />
  <ResultBoard v-if="$store.state.pk.loser != 'none'" />
</template>

<script>
import PlayGround from "@/components/PlayGround";
import MatchGround from "@/components/MatchGround";
import ResultBoard from "@/components/ResultBoard";
//加载时卸载时，需要引入
import {onMounted, onUnmounted} from "vue";
import {useStore} from "vuex";

export default {
  name: "PkIndexVue",

  components:{
    PlayGround,
    MatchGround,
    ResultBoard,
  },
  setup(){
    const store = useStore();
    let socket = null;
    const socketUrl = `wss://snake.ymswdfg.top/websocket/${store.state.user.token}/`;
    store.commit("updateLoser", "none");
    store.commit("updateIsRecord", false);
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
          },200);
          store.commit("updateGame",data.game);
        }else if(data.event === "move") {
          const game = store.state.pk.gameObject;
          const [snake0, snake1] = game.snakes;
          snake0.set_direction(data.a_direction);
          snake1.set_direction(data.b_direction);
        }else if(data.event === "result") {
          console.log(data)
          const game = store.state.pk.gameObject;
          const [snake0, snake1] = game.snakes;
          if(data.loser === "all" || data.loser =="A") {
            snake0.status = "die";
          }
          if(data.loser === "all" || data.loser =="B") {
            snake1.status = "die";
          }
          store.commit("updateLoser", data.loser);
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