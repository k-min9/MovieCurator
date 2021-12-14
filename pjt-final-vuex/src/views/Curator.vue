<template>
<div id="curatorMain">
  <div v-if="isLogin || isSocialLogin" id="curator-search" @keyup.enter="searchCurators">
      <input id="curator-search-bar" class="form-control" type="text" v-model="searchKeyword" placeholder="유저 ID 또는 닉네임으로 검색하세요" aria-label="Username" aria-describedby="basic-addon1" autocomplete="off">
    <div class="curator-info">  
      <br><h3>Movie Curator란?</h3><br>
      <h5>
        Movie Curator은 영화를 추천해 다른 사용자들이 흥미로운 영화를 찾을 수 있도록 돕는 개인 또는 단체입니다.<br>
        유저들은 Curator의 평가에 [<i class="fas fa-thumbs-up mx-1"></i> 좋아요]나 [<i class="fas fa-bullhorn mx-1"></i> 후원하기]로 Curator들의 평가를 지원할 수 있고, 누구나 Curator가 될 수 있습니다.
      </h5>
      <br>
    </div>  
  </div>
  <div v-else>
    <div class="curator-info">  
      <br><h3>Movie Curator이란?</h3><br>
      <h5>
        Movie Curator은 영화를 추천해 다른 사용자들이 흥미로운 영화를 찾을 수 있도록 돕는 개인 또는 단체입니다.<br>
        유저들은 Curator의 평가에 [<i class="fas fa-thumbs-up mx-1"></i> 좋아요]나 [<i class="fas fa-bullhorn mx-1"></i> 후원하기]로 Curator들의 평가를 지원할 수 있고, 누구나 Curator가 될 수 있습니다.
      </h5>
      <br>
    </div>  
    <!-- 구글 로그인 -->
    <div class="divider d-flex align-items-center my-4">
      <p class="text-center fw-bold mx-3 mb-0 text-muted">간편 로그인으로 검색기능이 제공됩니다</p>
    </div>
    <div class="social-login" style="text-align: center;">
      <img class="social-login-google" @click="google_login" src="@/assets/images/google.jpg" alt="google">
    </div>
    <!-- 회원 가입 -->
    <div class="divider d-flex align-items-center my-4">
      <p class="text-center fw-bold mx-3 mb-0 text-muted">회원가입하여 [Movie Curators]의 일원이 되시면, 평가/댓글 쓰기, 좋아요, 후원하기, 프로필 등 다양한 기능이 제공됩니다!</p>
    </div>
    <router-link :to="{ name: 'SignUp'}">
      <button class="search-button">회원가입으로 이동<i class="fas fa-arrow-circle-right mx-1"></i></button>
    </router-link> 
  </div> 

    <!-- 검색시 보여줄 큐레이터 리스트 -->
    <div v-if="searchedCurators.length > 0" :key="componentKey">
      <CuratorSearchDetail 
        v-for="(curator, idx) in searchedCurators"
        :key="idx"
        :curator="curator"/>
    </div>
    <!-- 일반 추천 큐레이터 리스트 -->
    <div v-else>
    </div>


</div>
</template>

<script>
import axios from 'axios'
import CuratorSearchDetail from '@/components/curator/CuratorSearchDetail'
import {mapState} from 'vuex'
import SERVER from '@/api/server'

export default {
  name:'Curator',
  components: {
    CuratorSearchDetail,
  },
  data: function() {
    return {
      // 검색 큐레이터
      searchedCurators: [],
      searchKeyword: '',

      // 기본 추천 큐레이터
      curators: [],    

      // 컴포넌트 키(리로딩용)
      componentKey: 0,
    }
  },
  methods: {
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    searchCurators: function ($event) {
      axios({
        method: 'get',
        //url: URL + '/accounts/search/',
        url: SERVER.URL + SERVER.ROUTES.accounts.curatorSearch,
        params: {
          searchKeyword: $event.target.value,
        },
        headers: this.setToken()
      })
      .then((res)=>{
        this.searchedCurators = res.data
        //this.$forceUpdate()
        this.componentKey += 1
      })
    },
    google_login: function() {
      this.$store.dispatch('googleLogin')
    }
  }, 
  computed: {
    ...mapState([
      'isLogin', 'isSocialLogin',
    ]),   
  },
  
}

</script>

<style>
#curatorMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );    
  background-size: cover;
  min-height: 100vh;
  
  padding-bottom: 60px;

  color: white;
}

/* 검색창 전체 */
#curator-search-bar {
  border-radius: 0px;
  background-color: rgb(75, 78, 90);
  color: rgb(187, 190, 201);
  line-height: 45px;
  border: none;
}

#curator-search-bar::placeholder {
  color: rgb(187, 190, 201);
  font-size: 20px;
  font-weight: bold;
}

#curator-search-bar *:focus {
    outline: none;
}

.curator-info {
  color: white;
  margin-left: 1%;
}


.separator-line {
  color: white;
}

/* 디바이더 */
.divider:after,
.divider:before {
  content: "";
  flex: 1;
  height: 1px;
  background: #eee;
}

/* 구글 소셜 로그인 관련 */

.social-login img {
  object-fit: cover;
  width: 200px;
  border-radius: 5px;
}

.social-login-google:hover {
  cursor: pointer;
}


/* 회원가입 이동 버튼*/
.search-button {
    border: 0;
    background: none;
    display: block;
    margin: 20px auto;
    text-align: center;
    border: 2px solid #2ecc71;
    padding: 14px 40px;
    outline: none;
    color: white;
    border-radius: 24px;
    transition: 0.25s;
    cursor: pointer
}

.search-button:hover {
    background: #2ecc71
}

</style>