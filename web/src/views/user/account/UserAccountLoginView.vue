<template>
  <ContentFiled>
    <div class="container">
      <div class="row justify-content-md-center">
        <div class="col-3">
          <form @submit.prevent="login">
            <div class="mb-3">
              <label for="username" class="form-label">用户名</label>
              <input type="text" v-model="username" class="form-control" id="username" placeholder="用户名">
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">Password</label>
              <input type="password" v-model="password" class="form-control" id="exampleInputPassword1" placeholder="请输入密码">
            </div>
            <div class="error_message">{{ error_message }}</div>
            <button type="submit" class="btn btn-primary">登录</button>
          </form>
        </div>
      </div>
    </div>
  </ContentFiled>
</template>

<script>
import ContentFiled from "@/components/ContentFiled";
import { useStore } from "vuex";
import {ref} from "vue";
import router from "@/router";
export default {
  name: "UserAccountLoginView",
  components:{
    ContentFiled,
  },
  setup(){
    const store = useStore();
    let username = ref("");
    let password = ref("");
    let error_message = ref("");
    const login = () =>{
      error_message.value="";
        store.dispatch("login",{
          username:username.value,
          password:password.value,
          success(){
            store.dispatch("getinfo",{
              success(){
                router.push({name:"home"});
                console.log(store.state.user);
              }
            })
          },
          error(){
            error_message.value = "用户名或密码错误";
          }
        })
    }
    return {
      store,
      username,
      password,
      error_message,
      login,
    }
  }
}
</script>

<style scoped>
button{
  width: 100%;
}
div.error_message{
  color: red;
}
</style>