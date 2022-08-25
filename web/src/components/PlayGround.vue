<template>
  <div class="playground">
    <div class="result-board">
      <div class="result-board-text" v-if="$store.state.pk.a_id === parseInt($store.state.user.id) ">
        蓝蛇
      </div>
      <div class="result-board-text" v-else-if="$store.state.pk.b_id === parseInt($store.state.user.id)">
        红蛇
      </div>
    </div>
    <GameMap />
  </div>
</template>


<script>
import GameMap from "@/components/GameMap";
import { useStore } from 'vuex';
export default {
  name: "PlayGround",
  components:{
    GameMap,
  },
  setup(){
    const store = useStore();

    return {
      store,
    }

  }
}
</script>

<style scoped>
div.playground{
  width:60vw;
  height: 70vh;
  /*background: lightgray;*/
  margin: 40px auto;
}
div.result-board-text {
  text-align: center;
  color: yellow;
  font-size: 50px;
  font-weight: 600;
  font-style: italic;
  padding-top: 5vh;
}
div.result-board {
  /*height: 30vh;*/
  /*width: 30vw;*/
  background-color: rgba(50, 50, 50, 0);
  position: absolute;
  margin-left: 5px;
  margin-top: 10px;
}
</style>