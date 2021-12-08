<template>
<div id="profile-article-main">
  <div class="container">
    <h1 class="fw-light text-center text-lg-start mt-4 mb-0">내가 좋아요 한 평가</h1>
    <hr class="mt-3 mb-2">
  </div>

  <div v-if="Likes.length > 0">
  <div v-for="(like, idx) in Likes" :key="idx"> 
  <v-main
    :style="`background-image: url(https://image.tmdb.org/t/p/original${like.backdrop_path})`"
    class="fill-height bg-image">
  <!-- MovieDetailArticleForm 참조 -->
  <div class="container">
    <h4>[{{like.article.user.username}}]님이 [{{like.title}}]에 작성하신 평가</h4>
    <div class="card">
      <div class="card-body">
        <div class="row">
          <!-- 사진과 작성 시간 -->
          <div class="col-md-2">
            <div v-if="like.article.user.image === null">
              <img src="@/assets/images/profile_basic.jpg" class="review-form-profile img-fluid rounded" alt="profileImage">
              <div class="review-form-time">
                <p class="text-secondary text-center">{{like.article.updated_at.split('T')[0]}}</p>
              </div>                        
            </div>
            <div v-else>
              <img :src="SERVER_URL+like.article.user.image" class="review-form-profile img-fluid rounded" alt="profileImage">
              <div class="review-form-time">
                <p class="text-secondary text-center">{{like.article.updated_at.split('T')[0]}}</p>
              </div>
            </div>
          </div>
          
          <div class="col-md-10">
            <p class="text-primary"></p>
              <!-- 작성자 이름 -->
              <a class="review-writer float-left" href="#"><strong>{{like.article.user.username}}</strong></a>
              <div class="review-form-star-box">

                <!-- 별 갯수-->
                <div v-if="like.article.rate === 1">
                  <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>
                <div v-else-if="like.article.rate === 2">
                  <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>
                <div v-else-if="like.article.rate === 3">
                  <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="review-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>
                <div v-else-if="like.article.rate === 4">
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
            <p class="text-secondary">제목 : {{like.article.title}}</p>

            <div class="clearfix"></div>
            
            <!-- 내용 -->
            <p class="text-secondary">{{like.article.content}}</p>
            
            <!-- 평가 상세 버튼 -->
            <router-link :to="{ name: 'ArticleDetail', params: { id: like.article.id }}">
              <v-btn class="review-form-btn font-weight-bold aaa" depressed small light><i class="fa fa-reply"></i>평가 상세</v-btn>
            </router-link>   

          </div>
        </div>
      </div>
    </div>
  </div>
  </v-main>
  <hr class="separator-line">
  <!-- MovieDetailArticleForm 여기까지 -->
  </div>
  </div>
  <div v-else>
    <div class="container">
      <h3 class="fw-light text-center text-lg-start mt-4 mb-0">현재 [<i class="fas fa-thumbs-up"></i> 좋아요]를 한 평가가 없습니다.</h3>
      <h4 class="fw-light text-center text-lg-start mt-4 mb-0">평가에 [<i class="fas fa-thumbs-up"></i> 좋아요]를 달아 큐레이터를 응원해주세요!</h4>
      <hr class="mt-3 mb-2">
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/server'

export default {
  name:'ProfileArticleList',
  data: function() {
    return {
      Likes: [],
      // 이미지 주소 조합용
      //SERVER_URL: 'http://127.0.0.1:8000',
      SERVER_URL : SERVER.URL
    }
  },
  methods: {
    // Set Token
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    // 좋아요 한 평가들 가져오기
    getLikes: function () {
      axios({
        method: 'GET',
        //url: 'http://127.0.0.1:8000/movies/likes/',
        url: SERVER.URL + SERVER.ROUTES.movies.likesList,
        headers: this.setToken()
      })
      .then((res)=>{
        this.Likes = res.data 
      }) 
    },
  },
  created: function () {
    this.getLikes()
  },
}
</script>

<style scoped>
#profile-article-main {
  /*position: absolute;*/
  color: white;
  /*background-color: powderblue;*/
  padding: 10px;
  left: 2.5rem;
  top: 12rem;
}

/* -- 배경 -- */
 .bg-image {
   background-repeat: no-repeat;
   background-position: top;
   /* background-attachment: fixed; */
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
    /* padding-top: 9rem; */
    /* clear: both; */
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

.review-form-btn {
  position: absolute; 
  right: 16px; 
  bottom: 16px;
}

.review-form-time {
  margin-top: 20px;
}

.review-form-profile {
  border: 0.5px solid black;
}


/*=========================================== 동적요소 ===========================================*/
.theme--light.v-input, .theme--light.v-input input, .theme--light.v-input textarea {
  color: white;
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

</style>