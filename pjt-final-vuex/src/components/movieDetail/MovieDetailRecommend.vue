<template>
  <div class="container">
    <div class="movie-detail-recommend">
      <div class="movie-detail-recommend-title">
        <h2>이 영화를 좋아하신다면... (빅데이터 기반 영화 추천)</h2>
      </div>
        
      <div class="movie-detail-recommend-list row-cols-1 row-cols-md-4 gy-3">
        <MovieDetailRecommendCard 
          id="poster"
          v-for="(movie, idx) in moviesRecommend"
          :key="idx"
          :movie="movie"/>
      </div>
      
      <div>
        <hr class="separator-line">
      </div>
    </div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import MovieDetailRecommendCard from '@/components/movieDetail/MovieDetailRecommendCard'

export default {
  name:'MovieDetailReference',
  components:{
    MovieDetailRecommendCard
  },
  props: {
    movie: {
      type: Object,
      required: true
    },
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
      'moviesRecommend'
    ])
  },
  created() {
    const tokenId = {
      token: this.setToken(),
      id : this.$route.params.id
    }
    this.$store.dispatch('getMoviesRecommend', tokenId)
  }
}
</script>

<style>

.movie-detail-recommend {
  position: relative;
  color: white;
  clear: both;
  z-index: 1;
}

.movie-detail-recommend-title {
  padding-bottom: 2rem;
}

.movie-detail-recommend-list {
  display: flex;
  justify-content: center;
  padding-bottom: 2rem;
  box-sizing: border-box;
  height: 100%;
  padding: 0% 7%;
}


/* hover이나 focus되었을 때 움직이는 기능 */
.movie-detail-recommend-list.movie-item:focus,
.movie-detail-recommend-list .movie-item:hover {
  border: white solid 2px;
  transform: scale(1.15);
  z-index: 1;
}


.movie-detail-recommend-item {
  position: relative;
  display: block;
  /* flex: 1 1 0px; */
  transition: transform 700ms;
  border-radius: 4px;
  box-shadow: 3px 3px 3px rgb(7, 7, 7);
}

.movie-detail-recommend-item {
  cursor: pointer;
  border-radius: 4px;
}

.movie-detail-recommend-list .movie-detail-recommend-item:focus,
.movie-detail-recommend-list .movie-detail-recommend-item:hover {
  border: white solid 2px;
  transform: scale(1.15);
  z-index: 1;
}

</style>