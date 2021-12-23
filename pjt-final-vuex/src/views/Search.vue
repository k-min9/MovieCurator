<template>
  <div id="searchMain">
    <div v-if="isLogin || isSocialLogin">
      <div class="search" @keyup.enter="searchMovies">
      <input id="search-bar" type="text" v-model="searchKeyword" class="form-control" placeholder="영어 또는 한글로 제목을 검색하세요" aria-label="Username" aria-describedby="basic-addon1" autocomplete="off">
      </div>

      <!-- MovieCard2 반응형 배치(반복문) / row-cols-md-5 갯수-->
      <div class="movie-list row row-cols-2 row-cols-md-5 gy-3">
        <MovieCard2 id="poster"
          v-for="(movie, idx) in searchedMovies"
          :key="idx"
          :movie="movie"/>
      </div>  
    </div>
    <div v-else>
      <div class="curator-info">
        <br>
        <h3>영화 검색 기능을 이용하시기 위해서는 인증이나 로그인이 필요합니다.</h3>
        <h4>[Movie Curators]의 일원이 되셔서 자유로운 서비스를 누려주세요!</h4>
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
    </div>
  </div>

</template>

<script>
import axios from 'axios'
import {mapState} from 'vuex'
import MovieCard2 from '@/components/movie/MovieCard2'
import SERVER from '@/api/server'

export default {
  name: 'Search',
  components: {
    MovieCard2,
  },
  data: function() {
    return {
      searchedMovies: [],
      searchKeyword: '',
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
    searchMovies: function ($event) {
      axios({
        method: 'get',
        //url: 'http://127.0.0.1:8000/movies/search/',
        url: SERVER.URL + SERVER.ROUTES.movies.movieSearch,
        params: {
          searchKeyword: $event.target.value,
        },
        headers: this.setToken()
      })
      .then((res)=>{
        this.searchedMovies = res.data
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

<style scoped>
#searchMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );    
  background-size: cover;
  min-height: 100vh;
  padding-bottom: 60px;
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

/* 검색창 전체 */
#search-bar {
  border-radius: 0px;
  background-color: rgb(75, 78, 90);
  color: rgb(187, 190, 201);
  line-height: 45px;
  border: none;
}

#search-bar::placeholder {
  color: rgb(187, 190, 201);
  font-size: 20px;
  font-weight: bold;
}

#search-bar *:focus {
    outline: none;
}


/* 아래부터는 movie list */

.movie-list {
  box-sizing: border-box;
  height: 100%;
  padding: 0% 7%;
}

.movie-item {
  position: relative;
  display: block;
  transition: transform 700ms;
  box-shadow: 0px 7px 5px rgb(7, 7, 7);
}

/* 포스터에 hover되었을 때 커서바뀜 + 테두리 둥글게 */
.movie-item:hover {
  cursor: pointer;
  border-radius: 4px;
}

  /* 포스터 테두리 둥글게 */
#poster {
  border-radius: 4px;
} 

/* hover이나 focus되었을 때 움직이는 기능 */
.movie-list .movie-item:focus,
.movie-list .movie-item:hover {
  border: white solid 2px;
  transform: scale(1.15);
  z-index: 1;
}

</style>