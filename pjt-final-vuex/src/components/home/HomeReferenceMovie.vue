<template>
  <div class="home-reference-movie">
      <v-app id="inspire">
       
        <v-carousel 
          class="home-reference-movie-img"
          cycle
          height="430"
          hide-delimiter-background
          show-arrows-on-hover
          >
          <template v-slot:prev="{ on, attrs }">
            <v-btn
              id="carousel-btn"
              v-bind="attrs"
              v-on="on"
            >&lt;</v-btn>
          </template>
          <template v-slot:next="{ on, attrs }">
            <v-btn
              id="carousel-btn"
              v-bind="attrs"
              v-on="on"
            >&gt;</v-btn>
          </template>

          <v-carousel-item v-for="(movie,i) in movies" :key="i" >
            <div>
              <img class="bg-img" :src="`https://image.tmdb.org/t/p/original${movie.backdrop_path}`" alt="..." @click="goToDetail(movie.id)">
            </div>
            <div id="movie-title">
              {{ movie.title }}
            </div>
            
        </v-carousel-item>
      </v-carousel>
      
    </v-app>
      
  </div>
</template>
  

<script>
import { mapGetters } from 'vuex'

export default {
  name:'HomeReferenceMovie',
  data () {
    return {
    }    
  },
  methods:  {
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    goToDetail(id) {
      this.$router.push({ name: 'MovieDetail', params: { id: id }})
    }
  },
  computed: {
    ...mapGetters([
      'movies'
    ]),
    imgSrc: function () {
      return "https://image.tmdb.org/t/p/original" + this.movie.backdrop_path
    },
  },
  created() {
    this.$store.dispatch('getMovies', this.setToken())
  }

}
</script>

<style scoped lang="scss">

::v-deep .v-application--wrap {
    min-height: fit-content;
  }

.home-reference-movie {
  margin-bottom: 20px;
}

h2 {
  color: white;
}

.bg-img{
  position: relative;
  height: 450px;
  width: 100%;
}

v-carousel > button {
  color: blue;
}


.home-reference-movie-img {
  transition: transform 700ms;
  box-shadow: 0px 7px 5px rgb(12, 12, 12);
  max-width: 100%;
  height: auto;
}

.home-reference-movie-img:hover {
  cursor: pointer;
  border-radius: 4px;
  border: 2px solid white;
}

#movie-title {
  position: absolute;
  color: white;
  top:40px;
  left:40px;
  font-size: 1.7rem;
  font-family: 'Black Han Sans', sans-serif;
  background-color: rgb(187, 176, 176);
  border-radius: 4px;
  padding: 3px;
  line-height: 110%;
  padding-bottom: 0px;
  text-align: center;
  vertical-align: middle;
}

#carousel-btn{
  color: white;
  background-color: transparent;
  border-radius: 100%;
  
}

</style>