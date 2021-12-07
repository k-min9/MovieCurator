<template>
  <div id="searchedMovies">
    <div class="col-mb-6 movie-item">
      <router-link :to="{ name: 'MovieDetail', params: { id: this.movie.id }}" >
        <v-img id="poster" :src="imgSrc" alt="영화 포스터"></v-img>
        <figcaption>
          <span>{{this.movie.title}}</span>
        </figcaption>

      </router-link>
    </div>

  
  </div>
</template>

<script>
export default {
  name: 'MovieCard2',
  props: {
    movie: {
      type: Object,
      required: true
    },
  },
  computed: {
    imgSrc: function () {
      return "https://image.tmdb.org/t/p/original" + this.movie.backdrop_path
    },
  },
}
</script>

<style scoped>

.movie-item:after,
.movie-item figcaption {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  color: white;
}

.movie-item:after {
  content: '';
  background-color: rgba(0, 0, 0, 0.65);
  border-radius: 4px;
  -webkit-transition: all 0.35s ease;
  transition: all 0.35s ease;
  opacity: 0;
}

.movie-item figcaption {
  z-index: 1;
  padding: 7px;
}

.movie-item span {
  opacity: 0;
}

.movie-item:hover:after,
.movie-item.hover:after {
  opacity: 1;
  position: absolute;
  top: 0px;
  bottom: 0px;
  left: 0px;
  right: 0px;
}

.movie-item:hover span,
.movie-item.hover span,
.movie-item:hover p,
.movie-item.hover p,
.movie-item:hover i,
.movie-item.hover i {
  -webkit-transform: translate(0px, 0px);
  transform: translate(0px, 0px);
  opacity: 1;
}


</style>