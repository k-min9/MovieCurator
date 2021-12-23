<template>
  <div class="curatorDetailMain">
    <div class="curator-detail-main row">
      <h2 class="curator-title">About <strong>[{{curator.nickname}}]</strong></h2>
      <div class="curator-profile col-md-2 col-sm-2">
        <div v-if="curator.image === null">
          <img class="img-fluid rounded" src="@/assets/images/profile_basic.jpg" alt="profileImage">                   
        </div>
        <div v-else>
          <img class="img-fluid rounded" :src="SERVER_URL+curator.image" alt="profileImage">
        </div>
      </div> 

      <div class="curator-about col-md-10 col-sm-10">
        <span class="curator-about-span">닉네임 : {{curator.nickname}}</span><br>
        <span class="curator-about-span">등급 : </span>
        <span v-if="curator.exp === 0" >
          <span class="badge silver mx-1">basic</span><br>
        </span>
        <span v-else> 
          <span class="badge gold mx-1">premium</span><br>
        </span>
        <span class="curator-about-span">자기소개 : {{curator.introduction}}</span>
      </div>
      

    </div>
    <br>
  <hr class="separator-line">

  <!-- 해당 큐레이터가 적은 모든 평가 -->
  <h3 class="curator-assessmemt">{{curator.nickname}} 님의 모든 평가들</h3>

  <hr class="separator-line">
  <br>

  <!-- MovieDetailArticleForm 참조 -->
  <div v-for="(article, idx) in articlesCurator" :key="idx">
    <v-main
    :style="`background-image: url(https://image.tmdb.org/t/p/original${article.movie.backdrop_path})`"
    class="fill-height bg-image">

    <div class="container">
      <h4>[{{article.movie.title}}]에 작성하신 평가</h4>
      <div class="card">
        <div class="card-body">
          <div class="row">
            
            <!-- 사진과 작성 시간-->
            <div class="d-none col-sm-2 d-md-block col-md-2">
              <div v-if="article.user.image === null">
                <img src="@/assets/images/profile_basic.jpg" class="review-form-profile img-fluid rounded" alt="profileImage">
                <div class="review-form-time">
                  <p class="text-secondary text-center">{{article.updated_at.split('T')[0]}}</p>
                </div>                        
              </div>
              <div v-else>
                <img :src="SERVER_URL+article.user.image" class="review-form-profile img-fluid rounded" alt="profileImage">
                <div class="review-form-time">
                  <p class="text-secondary text-center">{{article.updated_at.split('T')[0]}}</p>
                </div>
              </div>
            </div>
            
            <div class="col-md-10 col-sm-10 curator-assessment-details">
              <p class="text-primary"></p>
                <!-- 작성자 이름 -->
                <a class="review-writer float-left" href="#"><strong>{{article.user.username}}</strong></a>
                <div class="review-form-star-box">

                  <!-- 별 갯수-->
                  <div v-if="article.rate === 1">
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  </div>
                  <div v-else-if="article.rate === 2">
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  </div>
                  <div v-else-if="article.rate === 3">
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  </div>
                  <div v-else-if="article.rate === 4">
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  </div>
                  <div v-else>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                    <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  </div>
              
                </div>
              <!-- 제목 -->
              <p class="text-secondary">제목 : {{article.title}}</p>

              <div class="clearfix"></div>
              
              <!-- 내용 -->
              <pre class="text-secondary">{{article.content}}</pre>
              
              <!-- 평가 상세 버튼 -->
              <router-link :to="{ name: 'ArticleDetail', params: { id: article.id }}">
                <v-btn class="curator-form-btn font-weight-bold aaa" depressed small light><i class="fa fa-reply"></i>평가 상세</v-btn>
              </router-link>   

            </div>
          </div>
        </div>
      </div>
    </div>
    </v-main>
        
    <hr class="separator-line">
  </div>



  </div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/server'

export default {
  name:'CuratorDetail',
  data: function () {
    return {
      curator: null,
      articlesCurator: [],
      // 이미지 주소 조합용
      SERVER_URL: SERVER.ROUTES.image
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
    // 대상 큐레이터 정보 가져오기
    getCurator: function () {
      axios({
        method: 'get',
        //url: URL + `/accounts/curators/${this.$route.params.id}`,
        url: SERVER.URL + SERVER.ROUTES.accounts.curatorDetail + String(this.$route.params.id)+ '/',
        headers: this.setToken()
      })
      .then((res)=>{
        this.curator = res.data
        this.getArticleCurator()
      })
    },
    getArticleCurator: function () {
      axios({
        method: 'get',
        //url: URL + `/movies/${this.curator.id}/articles/curators/all/`,
        url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.curator.id) + SERVER.ROUTES.movies.articleCuratorAll,
        headers: this.setToken(),
      })
      .then((res)=>{
        this.articlesCurator = res.data
      })
    },
    
  }, 
  created() {
    //초기화 (큐레이터 -> 평가)
    this.getCurator()
  }
}
</script>

<style>
.curatorDetailMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );
  background-size: cover;
  color:white;
  min-height: 100vh;
  padding: 0% 12%;
  padding-top: 75px;
  z-index: -99;
}

.curator-title {
  margin-top: 20px;
}

.curator-detail-main {
  display: block;
}

.curator-profile {
  display: inline;
}

.curator-about {
  display: inline;
}

.curator-assessmemt {
  text-align: center;
}


/* -- 배경 -- */
 .bg-image {
   background-repeat: no-repeat;
   background-position: top;
   background-size: 100% 100%;
 }

/*-- MovieDetailArticleForm 참조 */

a:link {
  text-decoration: none!important;
}

.article-form-input-box-btn >v-btn {
  text-decoration: none!important;
}

/*=========================================== 메인form ===========================================*/
.article-form {
  position: relative;
  color: white;
  z-index: 1;
}

/*=========================================== form-info ===========================================*/
.article-form-info {
  margin-bottom: 2rem;
}

.article-form-info-title {
  font-weight: bold;
  font-size: 1.5rem;
}

.article-form-info-title-rule{
  font-weight: bold;
  font-size: 1.1rem;
  clear: left;
}

/*=========================================== input-form ===========================================*/

/* 별점 */
.article-form-star-box {
  display: inline-block;
  line-height: 35px;
}

/* 프로필이미지 관련 */
.article-form-profile{
  float: left;
  margin-top: 12px;
  width: 12%;
}

/* article title */
.article-form-input-box-title {
  background-color: white;
  border-radius: 4px;
  opacity: 0.5;
  margin-bottom: 7px;
  padding: 5px;
} 

/* article content */
.article-form-input-box-content {
  background-color: white;
  border-radius: 4px;
  opacity: 0.5;
  margin-bottom: 7px;
  padding: 5px;
} 

/* 버튼 */
.article-form-input-box-btn {
  margin-bottom: 20px;
}

/* 가로줄 */
.separator-line {
  color: white;
}

/*=========================================== v-else ===========================================*/

.article-form-textarea {
  /* color: white; */
  color: white;
  border: white solid 1px;
  border-radius: 4px;
  margin-top: 2px;
  padding: 3px;
  margin-bottom: 4px;
  width: 100%;
}

.article-form-p {
  margin-bottom: 4px;
}

/*=========================================== review-form ===========================================*/


.review-form-star-box {
  display: inline-block;
  position: absolute;
  right: 16px;
}

.review-form-star {
  position: relative;
  float: right;
}

.curator-assessment-details {
  position: relative;
}

.curator-form-btn {
  position: absolute;
  float: right;
}

.review-form-time {
  margin-top: 20px;
}

.review-form-profile {
  border: 0.5px solid black;
}


/*=========================================== 동적요소 ===========================================*/
.theme--light.v-input, .theme--light.v-input input, .theme--light.v-input textarea {
  color: black;
}

input.star{
  display: none;
}

label.star {
  float: right;
  padding-right: 15px;
  font-size: 20px;
  color: rgb(96, 95, 95);
  transition: all .2s;
}

input.star:checked ~ label.star:before {
  content:'\f005';
  color: #FD4;
  transition: all .25s;
}

input.star-5:checked ~ label.star:before {
  color:#FE7;
  text-shadow: 0 0 20px #952;
}

input.star-1:checked ~ label.star:before {
  color: #F62;
}

label.star:hover{
  transform: rotate(-15deg) scale(1.3);
}

label.star:before{
  content:'\f005';
  font-family: FontAwesome;
}

/*================================ badge ======================================*/
.badge {
  color: #fff !important;
  border-radius: 0.125rem;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12); }

.badge-pill {
  padding-right: 0.6em;
  padding-left: 0.6em;
  border-radius: 10rem; }

.gold {
  background-color: #FFD700 !important; }


.silver {
  background-color: #C0C0C0 !important; }


</style>