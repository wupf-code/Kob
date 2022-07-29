<template>
  <NarBar/>
  <router-view/>
</template>


<script>
import NarBar from "@/components/NarBar";
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
export default {
    components:{
      NarBar,
  },
  setup(){

  }

}
</script>

<style>
body{
  background-image: url("@/assets/image/background.png");
  background-size: cover;
}
</style>