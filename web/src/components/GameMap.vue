<template>
  <div ref="parent" class="game-map">
    <canvas ref="canvas" tabindex="0"></canvas>
  </div>

</template>

<script>
import {GameMap} from "@/assets/scripts/GameMap";
import {ref, onMounted} from "vue";

export default {
  name: "GameMap",
  setup(){
      let parent=ref(null);
      let canvas=ref(null);
      onMounted(()=>{
        new GameMap(canvas.value.getContext('2d'),parent.value)
      });
      console.log(parent.value);
      console.log(canvas.value);
      return {
        parent,
        canvas
      }
  }
}
</script>

<style scoped>
div.game-map{
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>