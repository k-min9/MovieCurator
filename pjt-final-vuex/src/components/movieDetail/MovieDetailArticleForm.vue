<template>
<div class="container">
  <div class="article-form">
    <div class="article-form-info">
      <span class="article-form-info-title">[ {{movie.title}} ]</span> 에 대한 평가 작성
      <br><br/>
      <p>이 영화의 좋았던 점 또는 싫었던 점, 그리고 이를 다른 사람에게 추천하는지 여부에 대해 써주세요.<br>
      정중하게 써주셔야 하며 <span class="article-form-info-title-rule">규칙 및 기준</span>을 지키셔야합니다.</p>
    </div>

    <div class="article-form-input-main">
      <div class="row wow fadeIn">       
      
        <!-- input 상자들...(프로필, 입력폼, 별점) -->
        <div class="article-form-input-detail">
          <div v-if="isLogin">
            <!-- 프로필 -->
            <div class="article-form-profile col-md-4">
              <div v-if="image === null">
                <img src="@/assets/images/profile_basic.jpg" class="img-fluid rounded" alt="profileImage">
              </div>
              <div v-else>
                <img :src="image" class="img-fluid rounded" alt="profileImage">
              </div>
            </div>

            <!-- 글 입력 폼 -->
            <div v-if="!isArticleWriten" class="article-form-input-box">
              <v-row @keyup.enter="createArticle">
                <v-col offset="1" class="col-10">

                  <div class="article-form-star-box">
                    <span class="article-form-star-title">별점</span>
                    <div class="article-form-star stars">
                      <form action="" @change="rateStar">
                        <input class="star star-5" id="star-5" type="radio" name="star" value="5"/>
                        <label class="star star-5" for="star-5"></label>
                        <input class="star star-4" id="star-4" type="radio" name="star" value="4"/>
                        <label class="star star-4" for="star-4"></label>
                        <input class="star star-3" id="star-3" type="radio" name="star" value="3"/>
                        <label class="star star-3" for="star-3"></label>
                        <input class="star star-2" id="star-2" type="radio" name="star" value="2"/>
                        <label class="star star-2" for="star-2"></label>
                        <input class="star star-1" id="star-1" type="radio" name="star" value="1"/>
                        <label class="star star-1" for="star-1"></label>
                      </form>
                    </div>
                  </div>
                

                  <!-- 제목, 내용 -->              
                  <v-text-field class="article-form-input-box-title" label="글 제목을 입력하세요" color="white" v-model="articleTitle"></v-text-field>
                  <v-textarea
                    class="article-form-input-box-content" 
                    clearable
                    clear-icon="mdi-cached"
                    label="글 내용을 입력하세요"
                    v-model="articleContent"
                    color="white"
                  ></v-textarea>

                  <!-- 버튼 -->
                  <div class="article-form-input-box-btn d-flex flex-row-reverse justify-space-between">
                    <v-btn class="font-weight-bold" depressed @click="createArticle" large light>평가 작성</v-btn>
                  </div>
                
                </v-col>
              </v-row>
            </div>

            <div v-else>
              <v-col offset="2" class="col-10">
                이미 평가를 적었습니다.<br><br>
                <p class="article-form-p">별점 : {{this.articleRate}} 점</p>
                <p class="article-form-p">제목 : {{this.articleTitle}}</p>
                    
                <textarea class="article-form-textarea" v-model="this.articleContent" cols="60" rows="7" disabled></textarea>
                <br>
              <div class="article-form-input-box-btn d-flex flex-row-reverse justify-space-between">
                <router-link :to="{ name: 'ArticleDetail', params: { id: this.articleId }}" >
                  <v-btn class="font-weight-bold" depressed small light>평가 상세</v-btn>
                </router-link>         
              </div>
              </v-col>
              <hr class="separator-line">
            </div>
          </div>
          <div v-else>
            <!-- 회원 가입 -->
            <div class="divider d-flex align-items-center my-4">
              <p class="text-center fw-bold mx-3 mb-0 text-muted">회원가입하여 [Movie Curators]의 일원이 되시면, 좋아요, 후원하기, 프로필 등 다양한 기능이 제공됩니다!</p>
            </div>
            <router-link :to="{ name: 'SignUp'}">
              <button class="search-button">회원가입으로 이동<i class="fas fa-arrow-circle-right mx-1"></i></button>
            </router-link>                
            <hr class="separator-line"><br>
          </div>

          <!-- 평가 리스트들 -->
          
          <h2 class="text-center">다른 유저들의 평가</h2>
          <div v-if="articles.length > 0">
            <div class="">
              <div v-for="(article, idx) in articles"
                :key="idx"
                :movie="movie">

                <div class="container">
                  <div class="card">
                    <div class="card-body">
                      <div class="row">
                        
                        <!-- 사진과 작성 시간-->
                        <div class="col-md-2">
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
                        
                        <div class="col-md-10">
                          <p class="text-primary"></p>
                            <!-- 작성자 이름 -->
                            <router-link :to="{ name: 'CuratorDetail', params: { id: article.user.id }}">
                              <strong>{{article.user.nickname}}</strong>
                            </router-link>

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
                          <p class="text-secondary">{{article.content}}</p>
                          
                          <!-- 평가 상세 버튼 -->
                          <router-link :to="{ name: 'ArticleDetail', params: { id: article.id }}">
                            <v-btn class="review-form-btn font-weight-bold aaa" depressed small light><i class="fa fa-reply"></i>평가 상세</v-btn>
                          </router-link>   

                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                    
                <hr class="separator-line">
              </div>
            </div>
          </div>
          <div v-else>
            <hr class="separator-line">
            <h3>현재 평가가 없습니다.</h3>
            <h4>이 영화의 첫 평가를 달아주세요!</h4>
          </div>
        </div>     
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import swal from 'sweetalert2'
import { mapState } from 'vuex'
import SERVER from '@/api/server'

export default {
  name:'MovieDetailArticleForm',
  props: {
    movie: {
      type: Object,
      required: true
    },
  },
  data: function() {
    return {
      // 전체 평가
      articles: [],
      // 사용자 단일 평가
      articleId: null,
      articleTitle: '',
      articleContent: '',
      articleRate: 0, // 평가 평점
      isArticleWriten: false, // 영화 하나당 평가 하나
      // 이미지 주소 조합용
      //SERVER_URL: 'http://127.0.0.1:8000',
      SERVER_URL : SERVER.URL
    }
  },
  methods: {
    // 별점 클릭
    rateStar($event) {
      this.articleRate = $event.target.value
    },
    //SetToken
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    // 이 영화에 적힌 평가 다 가져오기
    getArticles: function () {
      axios({
        method: 'get',
        //url: URL + `/movies/${this.$route.params.id}/articles/list/`,
        url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.$route.params.id) + SERVER.ROUTES.movies.articleList,
        headers: this.setToken(),
      })
      .then((res)=>{
        console.log('articleList')
        console.log(res.data)
        this.articles = res.data
      })
    },
    // 평가 작성, 평가 작성 여부 갱신
    createArticle: function () {
        if (this.articleRate === 0) {
          swal.fire ({
            icon: 'error',
            title: '평가 작성 실패',
            text: '별점을 입력해주세요.'
        })
        } else if (this.articleTitle === '') {
          swal.fire ({
            icon: 'error',
            title: '평가 작성 실패',
            text: '제목을 입력해주세요.'
        })
        } else if (this.articleContent === '') {
          swal.fire ({
            icon: 'error',
            title: '평가 작성 실패',
            text: '내용을 입력해주세요.'
        })
        } else {
        const contents = {
        title: this.articleTitle,
        content: this.articleContent,
        rate: this.articleRate,
        }
        axios({
          method: 'post',
          //url: `http://127.0.0.1:8000/movies/${this.$route.params.id}/articles/`,
          url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.$route.params.id) + SERVER.ROUTES.movies.articleDetail,
          headers: this.setToken(),
          data: contents,
        })
        .then(res => {
          this.isArticleWriten = true
          this.articleId = res.data.id
          this.articles.unshift(res.data)
          //this.reviewText = ''
        })      
      }
    }
  },
  computed: {
    ...mapState(['image', 'isLogin'])
  },
  created() {
    // 시작 시점에 유저와 영화 정보 가져오기

    // 사용자 평가 정보 가져오기
    axios ({
      method: 'get',
      //url: `http://127.0.0.1:8000/movies/${this.$route.params.id}/articles/`,
      url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.$route.params.id) + SERVER.ROUTES.movies.articleDetail,
      headers: this.setToken(),
    })
    .then(res => {
      console.log('articleDetail')
      console.log(res.data)
      // 내용이 비어있음 = 평가 적은 적이 없음
      if (Object.keys(res.data.content).length) {
        this.isArticleWriten = true
        this.articleId = res.data.id
        this.articleRate = res.data.rate
        this.articleTitle = res.data.title
        this.articleContent = res.data.content
      } else {
        this.isArticleWriten = false
      }
    })

    // 기존에 적힌 평가 정보 가져오기
    this.getArticles()

  }
}
</script>

<style scoped>

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

/* .review-writer {
  display: inline-block;
} */

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