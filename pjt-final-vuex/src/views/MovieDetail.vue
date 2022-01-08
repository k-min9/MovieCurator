<template>
<div class="movieDetailMain">
  <span class="movieDetailMainTitle">
  </span>
  <movie-detail-info :movie="movie"></movie-detail-info>
  <movie-detail-recommend :movie="movie"></movie-detail-recommend>
  <movie-detail-article-form :movie="movie"></movie-detail-article-form>
  <div>
    <img :src="imgSrc2" class="backdrop-bg img-fluid d-none d-md-block" alt="메인 포스터">
  </div>

</div>
</template>

<script>
import { mapGetters } from 'vuex'
import MovieDetailInfo from '@/components/movieDetail/MovieDetailInfo'
import MovieDetailRecommend from '@/components/movieDetail/MovieDetailRecommend'
import MovieDetailArticleForm from '@/components/movieDetail/MovieDetailArticleForm'

export default {
  name:'MovieDetail',
  components:{
    MovieDetailInfo,
    MovieDetailRecommend,
    MovieDetailArticleForm,
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
      'movie'
    ]),
    imgSrc2: function () {
      return "https://image.tmdb.org/t/p/original" + this.movie.backdrop_path
    },
  },
  created() {
    const tokenId = {
      token: this.setToken(),
      id : this.$route.params.id
    }
    this.$store.dispatch('getMovie', tokenId)
  }

}
</script>

<style>
.movieDetailMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );
  background-size: cover;
  color:white;
  min-height: 100vh;
  padding: 0% 12%;
  z-index: -99;
}

.backdrop-bg {
  padding-top: 60px;
  position: fixed; 
  top: 0; 
  left: 0; 
  margin: auto;
  opacity: 0.2;
  z-index: 0;
}


.movieDetailMainTitle {
  color:white;
}

</style>