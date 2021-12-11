<template>
<div id="home-reference-curator">
    <header>
      <br>
      <h3> 머신러닝 프로세스가 추천하는 영화 12선 <i class="fas fa-robot"></i></h3>
    </header>
    <!-- MovieCard 반응형 배치(반복문) -->
    <div class="movie-list row row-cols-2 row-cols-md-4 gy-3">
      <MovieCard id="poster"
        v-for="(movie, idx) in movies"
        :key="idx"
        :movie="movie"/>
    </div>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import MovieCard from '@/components/movie/MovieCard'

export default {
  name:'HomeReferenceCurator',
  components: {
    MovieCard,
  },
  methods: {
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
  },
  computed: {
    ...mapGetters([
      'movies'
    ])
  },
  // HomeReference Movie에서 한 번 나가서 두번 요청 감
  // created() {
  //   this.$store.dispatch('getMovies', this.setToken())
  // }

}
</script>

<style>
#home-reference-curator {
  color: white;
  left: 2.5rem;
  top: 12rem;
}

.movie-list {
  box-sizing: border-box;
  height: 100%;
}

.movie-item {
  position: relative;
  display: block;
  transition: transform 700ms;
  margin: 5%;
  box-shadow: 7px 7px 3px rgb(7, 7, 7);
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
