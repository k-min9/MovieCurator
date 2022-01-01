<template>
  <nav class = "navbar navbar-expand-lg">
    <div class = "container-fluid">
      <router-link class="navbar-brand" :to="{ name: 'Home'}">
        <img id="navbar-logo" src="@/assets/images/MOVIECURATORS_LOGO_White.png" alt="movie_curators_logo">
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <button type="button" class="toggler-btn btn btn-outline-light">MENU</button>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'Home' }"><i class="fas fa-home mx-1"></i> 홈</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'MovieList' }"><i class="fas fa-film mx-1"></i> 영화</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'Curator' }"><i class="fas fa-id-badge mx-1"></i> 큐레이터</router-link>
          </li>
 
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'Search' }"><i class="fas fa-search mx-1"></i> 검색</router-link>
          </li>
        </ul>
        <!-- 로그인 / 소셜로그인/ 로그아웃 상태 -->
        <ul class="navbar-nav">
          <li v-if="isLogin" class="nav-item-right"> 
          <span v-if="this.$store.state.userExp === 0" >
            <router-link class="nav-link" :to="{ name: 'Profile', params: {id:`${this.$store.state.userId}`}}">{{this.$store.state.nickname}}'s 프로필</router-link>
          </span>
          <span v-else> 
            <router-link class="nav-link badge-premium" :to="{ name: 'Profile', params: {id:`${this.$store.state.userId}`}}"><i class="fas fa-crown mx-2 yellow"></i>{{this.$store.state.nickname}}'s 프로필&nbsp;&nbsp;</router-link>
          </span>
          </li>
          <li v-if="!isLogin && !isSocialLogin" class="nav-item-right">
            <router-link class="nav-link" :to="{ name: 'Login' }">로그인</router-link>
          </li>
          <li v-if="!isLogin" class="nav-item-right">
            <router-link class="nav-link" :to="{ name: 'SignUp' }">회원가입</router-link>
          </li>
          <li v-if="isLogin || isSocialLogin" class="nav-item-right">
            <router-link class="nav-link" @click.native="logout" to="#">로그아웃</router-link>
          </li>
        </ul>
        
      </div>
    </div>
  </nav>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'NavBar',
  methods: {
    logout() {
      this.$store.dispatch('logout')
      this.$router.push({ name: 'Home'})    
    },
    getToken() {
      const token = localStorage.getItem('jwt')
      return token
    },
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
  },
  computed: {
    ...mapActions([
      'checkLogin'
    ]),
    ...mapState([
      'isLogin', 'isSocialLogin'
    ]),   
  },
  created() {
    this.$store.dispatch('checkLogin', this.getToken())
  }
}
</script>


<style scoped>

.navbar {
  position: sticky;
  background: linear-gradient(to bottom, black, rgb(27, 26, 26));  
  padding: 0px;
  top: 0;
  width: 100%;
  z-index: 99;
}

/* container 위치 */
.container-fluid {
    margin-left: 20px;
    margin-right: 20px;
  }

#navbar-logo {
  width: 80px;
  height: 60px;
}

#navbarNav {
  justify-content: space-between;
  margin: 5px;
}

.nav-link {
  text-decoration: none;
  color: white;
}

.nav-link:visited { 
  text-decoration:none;
  color:white;
  }

.nav-link:active { 
  text-decoration:none;
  color:blue;
  }


/* 링크 눌렀을때 효과 */
.nav-link:after {
  display:block;
  content: '';
  border-bottom: solid 3px white;  
  transform: scaleX(0);  
  transition: transform 300ms ease-in-out;
}
.nav-link:hover:after {
  transform: scaleX(1); 
  }
.nav-link:after{  
  transform-origin:  0% 50%; 
  }

/* 화면이 줄어들었을 경우 toggler버튼 크기 */
.toggler-btn {
  color: white;
  font-size: 7px;
}


/* 프리미엄 유저 */
.badge-premium {
  color: #fff !important;
  background-color: #980000 !important; 
  /* background-color: red; */

  padding-right: 0.6em;
  padding-left: 0.6em;
  border-radius: 10rem;

  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12); }

.yellow {
  color: yellow !important;
}

</style>