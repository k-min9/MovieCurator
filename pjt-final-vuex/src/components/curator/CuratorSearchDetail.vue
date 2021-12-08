<template>
  <div class="curator-search-detail">
    <div class="row">
      <div class="col-12">
        <div class="post-content">

        <div class="post-container p-2">
          <div class="post-detail">
            <div class="post-comment">
              <div v-if="curator.image === null">
                <img class="profile-photo-md" src="@/assets/images/profile_basic.jpg" alt="profileImage">                   
              </div>
              <div v-else>
                <router-link :to="{ name: 'CuratorDetail', params: { id: curator.id }}">
                  <img class="profile-photo-md" :src="SERVER_URL+curator.image" alt="profileImage">
                </router-link>
              </div>

              <div class="col-11">
              
                <div>
                  닉네임:
                  <router-link :to="{ name: 'CuratorDetail', params: { id: curator.id }}">
                    <span class="curator-detail-info">{{curator.nickname}}</span><br>
                  </router-link>
                    <span class="curator-detail-info">경험치 : {{curator.exp}}</span><br>
                    <span class="curator-detail-info">ID : {{curator.id}}</span><br>
                </div>
              
                <div>
                  <span>자기 소개 : {{curator.introduction}}</span>
                  <hr>
                </div>

                <!-- 검색 결과 -->
                <div>
                  <div v-if="articlesCurator.length > 0" class="curator-search-result row-cols-3 row-cols-sm-3 gy-3">
                    <div class="curator-search-result-item col-mb-6" v-for="(article, idx) in articlesCurator" :key="idx"> 
                      
                      <router-link :to="{ name: 'MovieDetail', params: { id: article.movie.id }}">
                        <v-img :src="`https://image.tmdb.org/t/p/original${article.movie.backdrop_path}`" alt="profileImage"></v-img>
                      </router-link>
                        
                        <span>제목 : {{article.movie.title}}</span><br>
                          
                          <!-- 별 갯수-->
                          <div v-if="article.rate === 1">
                            <span class=""><i class="text-warning fa fa-star"></i></span>
                          </div>
                          <div v-else-if="article.rate === 2">
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                          </div>
                          <div v-else-if="article.rate === 3">
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                          </div>
                          <div v-else-if="article.rate === 4">
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                          </div>
                          <div v-else>
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                            <span><i class="text-warning fa fa-star"></i></span>
                          </div>            
                    </div>
                  </div>

                  <div v-else class="curator-search-result row-cols-3 row-cols-sm-3 gy-3">
                    작성 평가 없음
                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
         
</template>

<script>
import axios from 'axios'
import SERVER from '@/api/server'

export default {
  name: 'CuratorSearchDetail',
  props: {
    curator: {
      type: Object,
      required: true
    },
  },
  data: function() {
    return {
      // 검색 아티클들
      articlesCurator: [],  
      // 이미지 주소 조합용
      //SERVER_URL: 'http://127.0.0.1:8000',  
      SERVER_URL: SERVER.URL
    }
  },
  methods:{
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    // 해당 큐레이터가 쓴 평가 목록 가져오기
    getArticleCurator: function () {
      axios({
        method: 'get',
        //url: `http://127.0.0.1:8000/movies/${this.curator.id}/articles/curators/`,
        url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.curator.id) + SERVER.ROUTES.movies.articleCurator,
        headers: this.setToken(),
      })
      .then((res)=>{
        this.articlesCurator = res.data
      })
    },
  },
  created() {
    this.getArticleCurator()
  },
}

</script>

<style scoped>
.curator-search-detail {
  padding: 0% 9%;
  color: black;
}

.curator-search-detail-info {
  color: black;
  font-weight: 600;
}

.curator-search-result {
  display: flex;
  justify-content: center;
  padding-bottom: 2rem;
  box-sizing: border-box;
  height: 100%;
  padding: 0% 7%;
}

.curator-search-result-item {
  padding-right: 15px;
}

.profile-photo-md {
  margin-top: 12px;
}

.separator-line {
  color: white;
}


/*================================ 코멘트 폼 관련 요소 ===================================*/
.post-content{
  background: #f8f8f8;
  border-radius: 4px;
  width: 100%;
  border: 1px solid #f1f2f2;
  margin-bottom: 20px;
  overflow: hidden;
  position: relative;
}

.post-content img.post-image, video.post-video, .google-maps{
  width: 100%;
  height: auto;
}

.post-content .google-maps .map{
  height: 300px;
}


.post-content .post-container{
  padding: 20px;
}

.post-content .post-container .post-detail{
  /*margin-left: 65px;*/
  position: relative;
}

.post-content .post-container .post-detail .post-text{
  line-height: 24px;
  margin: 0;
}

.post-content .post-container .post-detail .reaction{
  position: absolute;
  right: 0;
  top: 0;
}

.post-content .post-container .post-detail .post-comment{
  display: inline-flex;
  /* margin: 10px auto; */
  width: 100%;
}

.post-content .post-container .post-detail .post-comment img.profile-photo-sm{
  margin-right: 10px;
}

.post-content .post-container .post-detail .post-comment .form-control{
  height: 30px;
  /* border: 1px solid #ccc; */
  box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
  margin: 7px 0;
  min-width: 0;
}

img.profile-photo-md {
    height: 50px;
    width: 50px;
    border-radius: 50%;
}

img.profile-photo-sm {
    height: 40px;
    width: 40px;
    border-radius: 40%;
    border: 0.3px black solid;
}

.text-green {
    color: #8dc63f;
}

.text-red {
    color: #ef4136;
}

.following {
    color: #8dc63f;
    font-size: 12px;
    margin-left: 20px;
}

/*================================코멘트 관련 요소 ======================================*/
.super-comment {
  background-color : #add9e4;
  border: 5px outset;
  color : black;
}

</style>