<template>
  <div class="match-ground">
    <div class="row">
      <div class="col-4">
        <div class="user-photo">
          <img :src="$store.state.user.photo">
        </div>
        <div class="user-username">
          {{ $store.state.user.username }}
        </div>
      </div>
      <div class="col-4">
        <div class="user-select-bot">
          <select v-model="select_bot" class="form-select" aria-label="Default select example">
            <option value="-1" selected>亲自上阵</option>
            <option v-for="bot in bots" :key="bot.id" :value="bot.id">{{bot.title}}</option>
          </select>
        </div>
      </div>
      <div class="col-4">
        <div class="user-photo">
          <img :src="$store.state.pk.opponent_photo">
        </div>
        <div class="user-username">
          {{ $store.state.pk.opponent_username }}
        </div>
      </div>
      <div class="col-12" style="text-align: center;padding-top: 15vh">
        <button type = "button" @click="click_match_btn" class="btn btn-warning">{{ match_btn_info }}</button>
      </div>
    </div>
  </div>
</template>

<script>
import {ref} from "vue";
import {useStore} from "vuex";
import $ from 'jquery';
export default {
  name: "MatchGround",
  components:{
  },
  setup(){
    let store = useStore();
    let bots = ref([]);
    let select_bot = ref("-1");
    let match_btn_info = ref("开始匹配");
    const click_match_btn = () => {
      if(match_btn_info.value === "开始匹配"){
        match_btn_info.value = "取消匹配";
        store.state.pk.socket.send(JSON.stringify({
          event : "start-matching",
          bot_id: select_bot.value,
        }))
      }else{
        match_btn_info.value = "开始匹配";
        store.state.pk.socket.send(JSON.stringify({
          event : "stop-matching",
        }))
      }
    }
    const refresh_bots = () => {
      $.ajax({
        url: "https://snake.ymswdfg.top/api/user/bot/getlist/",
        type: 'get',
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp) {
          bots.value = resp;
        }
      })
    }
    refresh_bots();
    return {
      match_btn_info,
      click_match_btn,
      bots,
      select_bot,
    }
  }
}
</script>

<style scoped>
div.match-ground{
  width:60vw;
  height: 70vh;
  background-color: rgba(50, 50, 50, 0.1);
  margin: 40px auto;
}
div.user-photo{
  text-align: center;
  padding-top: 10vh;

}
div.user-photo > img{
  border-radius: 50%;
  width: 20vh;
}
div.user-username{
  text-align: center;
  font-size: 24px;
  font-weight: 600;
  color: white;
  padding-top: 2px;
}
div.user-select-bot {
  padding-top: 20vh;
}
div.user-select-bot > select {
  width: 60%;
  margin: 0 auto;
}
</style>