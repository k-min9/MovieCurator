<template>
<div id="profile-curator-main">
  <div class="container">
    <h1 class="fw-light text-center text-lg-start mt-4 mb-0">내가 후원한 큐레이터</h1>
    <hr class="mt-3 mb-2">
    <div class="row text-center text-lg-start">
      <div v-if="curators.length > 0">
          <div v-for="(curator, idx) in curators" :key="idx" class="col-lg-3 col-md-4 col-6"> 
              <router-link :to="{ name: 'CuratorDetail', params: { id: curator.to_user.id }}" class="d-block mt-2 mb-1 h-100 text-center">
                  <img v-if="curator.to_user.image === null" src="@/assets/images/profile_basic.jpg" alt="profileImage" class="img-fluid img-thumbnail img80">                   
                  <img v-else :src="SERVER_URL+curator.to_user.image" alt="profileImage" class="img-fluid img-thumbnail img80">
                  <br>{{curator.to_user.nickname}}
              </router-link>
          </div>
      </div>
      
      <div v-else>
        <div class="container">
          <h3 class="fw-light text-center text-lg-start mt-4 mb-0">현재 후원하는 큐레이터가 없습니다.</h3>
        </div>
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
      SERVER_URL: SERVER.ROUTES.image
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
        //url: URL + '/accounts/curators/likes/',
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
  color: white;
  padding: 10px;
  left: 2.5rem;
  top: 12rem;
}

.img80{
  height: 86%;
  width: 80%;
}

</style>