<template>
  <div class="MovieDetailInfo">
    <div class="container">
      <div class="row wow fadeIn"> 
        
        <div class="info">
          
          <div class="info-poster col-md-4">
            <img id="info-poster" :src="imgSrc" class="img-fluid rounded-start" alt="...">
          </div>


          <div class="info-content">

              <h5 class="card-title font-weight-bold">{{ movie.title }}</h5>
              <br>
              <p class="overview">{{movie.overview}}</p>
              
              <div class="mb-3">
                <a href="">
                  <span class="badge purple mx-1">Popular</span>
                </a>
                <a href="">
                  <span class="badge blue mx-1">New</span>
                </a>
                <a href="">
                  <span class="badge red mx-1">Bestseller</span>
                </a>
              </div>

              <p>
                <span class="lead font-weight-bold mx-1">
                  평점 : 
                </span>
                <span>{{movie.vote_average}}</span>
              </p>

              <p>
                <span class="lead font-weight-bold mx-1">
                  개봉일 : 
                </span>
                <span>{{movie.release_date}}</span>
              </p>
          </div>
        </div>

      <hr class="separator-line">
    </div>
  </div>
</div>
</template>

<script>
export default {
  name:'MovieDetailInfo',
  props: {
    movie: {
      type: Object,
      required: true
    },
  },
  data: function () {
    return {
      windowWidth: parseInt(screen.availWidth)+"px",
    }
  },
  computed: {
    imgSrc: function () {
      return "https://image.tmdb.org/t/p/original" + this.movie.poster_path
    },
    imgSrc2: function () {
      return "https://image.tmdb.org/t/p/original" + this.movie.backdrop_path
    },
  },
}
</script>

<style scoped>

.MovieDetailInfo {
  padding-top: 75px;
  position: relative;
}

.overview {
  font-size: 12px;
}

.lead {
  font-size: 1rem;
  font-weight: 300;
  color: white;
}

.separator-line {
  color: white;
  clear: both;
}

.info {
  z-index: 1;
  opacity: 1;
}

.info-poster{
  float: left;
  width: 30%;
}

.info-content{
  float: right;
  width: 70%;
  padding: 20px;
  color: white;
}


/* ================================================================================= */

@-webkit-keyframes fadeIn {
  from {
    opacity: 0; }
  to {
    opacity: 1; } }

@keyframes fadeIn {
  from {
    opacity: 0; }
  to {
    opacity: 1; } }

.fadeIn {
  -webkit-animation-name: fadeIn;
          animation-name: fadeIn; }

.img-fluid {
  max-width: 100%;
  height: auto; }

.badge {
  color: #fff !important;
  border-radius: 0.125rem;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12); }

.badge-pill {
  padding-right: 0.6em;
  padding-left: 0.6em;
  border-radius: 10rem; }

.badge-primary {
  color: #fff !important;
  background-color: #4285f4 !important; }

.badge-danger {
  color: #fff !important;
  background-color: #ff3547 !important; }

.badge-warning {
  color: #fff !important;
  background-color: #fb3 !important; }

.badge-success {
  color: #fff !important;
  background-color: #00c851 !important; }

.badge-info {
  color: #fff !important;
  background-color: #33b5e5 !important; }

.badge-default {
  color: #fff !important;
  background-color: #2bbbad !important; }

.badge-secondary {
  color: #fff !important;
  background-color: #a6c !important; }

.badge-dark {
  color: #fff !important;
  background-color: #212121 !important; }

.badge-light {
  color: #000 !important;
  background-color: #e0e0e0 !important; }

.purple {
  background-color: #9c27b0 !important; }

.blue {
  background-color: #2196f3 !important; }

.red {
  background-color: #f44336 !important; }

.font-weight-bold {
  font-weight: 700 !important;
}

</style>