<template>
  <div id="home-footer" class="border-top">
    <div class="footer-main">
      <div class="container">
        <div class="row justify-content-between">
          <div class="col-lg-3 col-md-6 footer-widget footer-about">
            <h4 class="widget-title mb-3"><span class="bar">|</span> About Our Project</h4>
            <v-img class="footer-logo" contain=true :src="require('@/assets/images/MOVIECURATORS_LOGO_Trans.png')" alt="Logo"/>
            
            <p>영화 평가 후원 커뮤니티</p>
            <p>프로젝트 기술 스택 및 자세한 내용</p>
            <div>
              click to 
              <a class="readme" href="https://github.com/limgeonho/MovieCurators/blob/master/README.md">README.md</a> 
            </div>
          </div><!-- Col end -->

          <div class="col-lg-6 col-md-6 footer-widget mt-5 mt-md-0">
            <h4 class="widget-title mb-4"><span class="bar">|</span> Developers / Contact Us</h4>
            <div class="working-hours">
              <div class="row">
                  <div class="team-member col-6">
                    <a href="https://github.com/k-min9">
                      <!-- <img id="developer-img" class="mx-auto rounded-circle mb-3" src="@/assets/images/userB.jpg" alt="kangmingu"/> -->
                      <v-img id="developer-img" class="mx-auto rounded-circle mb-3" contain=true :src="require('@/assets/images/userB.jpg')" max-width="10rem" max-height="10rem" alt="Logo"/>
                      <!-- <v-img class="mx-auto rounded-circle" :src="require('@/assets/images/userB.jpg')"></v-img> -->
                      <h5 id="developer-name">KANG MIN GU</h5>
                    </a>
                  </div>

                  <div class="team-member col-6">
                    <a href="https://github.com/limgeonho">
                      <!-- <img id="developer-img" class="mx-auto rounded-circle mb-3" src="@/assets/images/userA.jpg" alt="limgeonho"/> -->
                      <v-img id="developer-img" class="mx-auto rounded-circle mb-3" contain=true :src="require('@/assets/images/userA.jpg')" max-width="10rem" max-height="10rem" alt="Logo"/>
                      <h5 id="developer-name">LIM GEON HO</h5>
                    </a>
                  </div> 
              </div>
            </div>
          </div><!-- Col end -->

          <div class="col-lg-3 col-md-6 mt-5 mt-lg-0 footer-widget">
            <h4 class="widget-title mb-4"><span class="bar">|</span> Services</h4>
            <ul class="list-arrow">
              <li><a class="list-item" href="#">Home</a></li>
              <li><router-link :to="{ name: 'MovieList' }" class="list-item">Category</router-link></li>
              <li><router-link :to="{ name: 'Curator' }" class="list-item">Curators</router-link></li>
              <li><router-link :to="{ name: 'Search' }" class="list-item">Search Movie</router-link></li>
            </ul>
          
              <div>
                <v-img id="coffee" class="mx-auto mt-3" aspect-ratio="1" contain=true max-height="100px" @click="kakaoBuyPremium" :src="require('@/assets/images/buyMeCoffee.png')" alt="getPremium"/>
              </div>

          </div><!-- Col end -->
          
        </div><!-- Row end -->
      </div><!-- Container end -->
    </div><!-- Footer main end -->
    <div id="copyright" class="small text-center text-muted fst-italic">
      Copyright &copy; Movie Curators 2021
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import swal from 'sweetalert2'
import SERVER from '@/api/server'

export default {
  name: 'HomeFooter',
  methods: {
    kakaoBuyPremium: function (){
      // 이미 프리미엄 유저인지 확인하는 것 나중에 필요함
    axios({
        method: 'GET',
        // url: `http://127.0.0.1:8080/kakaoPay/`,
        url: SERVER.URL + '/kakaoPay/'
        //url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.movie.id) + SERVER.ROUTES.movies.articleDetail,
        //headers: this.setToken(),
      })
      .then((res) => {
        //window.open(res.data)
        window.location.href = res.data  // 결제 화면 전환        
      })
      .catch (() => {
        swal.fire ({
          icon: 'error',
          title: '결제 실패',
          text: '서버가 혼잡합니다. 다시 시도해 주세요.'
        })
      })
    },
  }
}
</script>

<style>
#home-footer {
  color: white;
}

#developer-name {
  color: white; 
  text-decoration: none;
  text-align: middle;
}

#developer-name:hover { 
  color: grey;
}

#developer-img {

  /*height: 10rem;*/
  /*width: 10rem;*/
  border: solid 2px white;
}

.team-member {
  padding-top: 1.5rem;
}

.list-item {
  color: white; 
  text-decoration: none;
}

.list-item:hover { 
  color: grey;
}

.readme {
  color: white;
}

.bar {
  color: yellow;
}

/* #coffee {
  width: 8rem;
  height: 2.5rem;
} */

#coffee:hover {
  cursor: pointer;
}

.footer-logo {
  margin-bottom: 1rem;
}

.team-member {
  text-align: center;
}

#copyright{
  background-color: black;
  width: 100%;
}

</style>