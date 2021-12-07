<template>
  <div>
    <div class="col-mb-6 movie-item">
      <router-link :to="{ name: 'MovieDetail', params: { id: this.movie.id }}" >
        <v-img id="poster" :src="imgSrc" alt="영화 포스터"></v-img>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MovieCard',
  props: {
    movie: {
      type: Object,
      required: true
    },
  },
  computed: {
    imgSrc: function () {
      return "https://image.tmdb.org/t/p/original" + this.movie.poster_path
    },
  },
}
</script>

<style>

</style>