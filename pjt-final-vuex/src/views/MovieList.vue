<template>
  <div id="movieListMain">
    <div id="movieListSelectBar">
      <span class="select-bar-title">Movies</span>
    

      <div class="select-bar">
        <select v-model="filter" @change="selectFilter" class="form-select rounded-pill" aria-label="Default select example" style="40px">
          <option class="select-item" selected>장르</option>
          <option class="select-item" value="popularity">인기순</option>
          <option class="select-item" value="release_date">최신순</option>
          <option class="select-item" value="vote_average">평점순</option>
          <option class="select-item" value="Adventure">어드벤처</option>
          <option class="select-item" value="Fantasy">판타지</option>
          <option class="select-item" value="Animation">애니메이션</option>
          <option class="select-item" value="Drama">드라마</option>
          <option class="select-item" value="Horror">공포</option>
          <option class="select-item" value="Action">액션</option>
          <option class="select-item" value="Comedy">코미디</option>
          <option class="select-item" value="History">역사</option>
          <option class="select-item" value="Western">서부</option>
          <option class="select-item" value="Thriller">스릴러</option>
          <option class="select-item" value="Crime">범죄</option>
          <option class="select-item" value="Documentary">다큐멘터리</option>
          <option class="select-item" value="Science Fiction">공상과학[SF]</option>
          <option class="select-item" value="Mystery">미스테리</option>
          <option class="select-item" value="Music">음악</option>
          <option class="select-item" value="Romance">로맨스</option>
          <option class="select-item" value="Family">가족</option>
          <option class="select-item" value="War">전쟁</option>
          <option class="select-item" value="TV Movie">텔레비전 영화</option>
        </select>
      </div>
  </div>

  <!-- MovieCard2 반응형 배치(반복문) / row-cols-md-5 갯수-->
  <div class="movie-list row row-cols-1 row-cols-md-5 gy-3">
    <MovieCard2 id="poster"
      v-for="(movie, idx) in filteredMovies"
      :key="idx"
      :movie="movie"/>
  </div>  

  </div>
</template>

<script>
import axios from 'axios'
import MovieCard2 from '@/components/movie/MovieCard2'
import SERVER from '@/api/server'

export default {
  name: 'MovieList',  
  components: {
    //MovieListSelected,
    MovieCard2,
  },
  data: function() {
    return {
      filteredMovies: [],
      filter: '장르',
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
    selectFilter: function ($event) {
        axios({
        method: 'get',
        //url: 'http://127.0.0.1:8000/movies/list/',
        url: SERVER.URL + SERVER.ROUTES.movies.movieList,
        params: {
          filter: $event.target.value
        },
        headers: this.setToken()
      })
      .then((res)=>{
        this.filteredMovies = res.data
      })
    }, 
  }
}
</script>

<style scoped>
#movieListMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );
  background-size: cover;
  color:white;
  min-height: 100vh;
}

#movieListSelectBar {
  padding-top: 50px;
  background-size: cover;
  color:white;
  margin-bottom: 25px;
}

.select-bar-title {
  margin-right: 30px;
  margin-left: 30px;
  font-size: 2rem;
  vertical-align: middle;
}

.select-bar {
  display: inline-block;
  border-radius: 2px;  
}

.select-item {
  font-size: 14px;
}

#poster {
  border-radius: 4px;
}


</style>