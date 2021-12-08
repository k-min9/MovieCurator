<template>
<div id="profile-curator-main">
  <div class="container">
    <h1 class="fw-light text-center text-lg-start mt-4 mb-0">내가 후원한 큐레이터</h1>
    <hr class="mt-3 mb-2">
    <div class="row text-center text-lg-start">
      <div v-for="(curator, idx) in curators" :key="idx" class="col-lg-3 col-md-4 col-6"> 
          <router-link :to="{ name: 'CuratorDetail', params: { id: curator.to_user.id }}" class="d-block mt-2 mb-1 h-100 text-center">
              <img v-if="curator.to_user.image === null" src="@/assets/images/profile_basic.jpg" alt="profileImage" class="img-fluid img-thumbnail img80">                   
              <img v-else :src="SERVER_URL+curator.to_user.image" alt="profileImage" class="img-fluid img-thumbnail img80">
              <br>{{curator.to_user.nickname}}
          </router-link>
      </div>
    </div>
  </div>

</div>

</template>

<script>
import axios from 'axios'
import SERVER from '@/api/server'

export default {
  name:'ProfileCuratorList',
  data: function() {
    return {
      curators: [],
      // 이미지 주소 조합용
      //SERVER_URL: 'http://127.0.0.1:8000',
      SERVER_URL : SERVER.URL
    }
  },
  methods: {
    // Set Token
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    // 후원한 큐레이터들 가져오기
    getCurators: function () {
      axios({
        method: 'GET',
        //url: 'http://127.0.0.1:8000/accounts/curators/likes/',
        url: SERVER.URL + SERVER.ROUTES.accounts.likesListCurator,
        headers: this.setToken()
      })
      .then((res)=>{
        this.curators = res.data 
      }) 
    },
  },
  created: function () {
    this.getCurators()
  },
}
</script>

<style scoped>
#profile-curator-main {
  /*position: absolute;*/
  color: white;
  /*background-color: powderblue;*/
  padding: 10px;
  left: 2.5rem;
  top: 12rem;
}

.img80{
  height: 86%;
  width: 80%;
}

</style>