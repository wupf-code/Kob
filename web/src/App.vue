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


    // $.ajax({
    //   url:"http://localhost:3000/user/account/register/",
    //   type:"post",
    //   data:{
    //     username:"1234",
    //     password:"1234",
    //     confirmedPassword:"1234",
    //   },
    //   success(resp){
    //     console.log(resp)
    //   },
    //   error(resp){
    //     console.log(resp);
    //   }
    // });
  }

}
</script>

<style>
body{
  background-image: url("@/assets/image/background.png");
  background-size: cover;
}
</style>