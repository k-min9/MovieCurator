<template>
<div class="articleDetialMain">

  <!-- 배경사진 -->
  <div class="col-md-6 mb-4">
    <img :src="imgSrc2" class="backdrop-bg-1 img-fluid d-none d-md-block" alt="메인 포스터" >  <!--:style="{ width: windowWidth }"  -->
  </div>
  <div class="article-detail-index">  
  <div class="article-detail-form-info">
    <span class="article-detail-form-info-title">[ {{movie.title}} ]</span>에 대한 [ {{this.articleNickname}} ] 님의 평가
  </div>

  <div v-if="!selectUpdateBtn" class="article-detail-form">    
    <div class="container">
      <div class="card">
        <div class="card-body">
          <div class="row">

            <!-- 프로필 -->
            <div class="col-md-2">
              <div v-if="articleImage === null">
                <router-link :to="{ name: 'CuratorDetail', params: { id: articleUserId }}">
                  <img src="@/assets/images/profile_basic.jpg" class="article-detail-profile img img-rounded img-fluid" alt="profileImage">
                </router-link> 
              </div>
              <div v-else>
                <router-link :to="{ name: 'CuratorDetail', params: { id: articleUserId }}">
                  <img :src="SERVER_URL+articleImage" class="img img-rounded img-fluid" alt="profileImage">
                </router-link> 
              </div>
            </div>

            <!-- 작성자 + 별상자 -->
            <div class="col-md-10">
              <!-- 작성자 -->
                <span>작성자 : </span>
              <router-link :to="{ name: 'CuratorDetail', params: { id: articleUserId }}">
                <span>{{this.articleNickname}}</span>
              </router-link>

              <!-- 별 상자 -->
              <div class="article-detail-form-star-box">
                <div v-if="this.articleRate === 1">
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>
                <div v-else-if="this.articleRate === 2">
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>
                <div v-else-if="this.articleRate === 3">
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>               
                <div v-else-if="this.articleRate === 4">
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>
                <div v-else>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                  <span class="article-detail-form-star"><i class="text-warning fa fa-star"></i></span>
                </div>
                
                
              </div>   
              
              <!-- 제목 -->
              <p class="text-secondary">제목 : {{this.articleTitle}}</p>
      
              <div class="clearfix"></div>

              <!-- 내용 -->
              <p class="text-secondary">{{this.articleContent}}</p><br>

              <!-- 버튼 -->
              <div class="article-detail-form-btns">
                <button v-if="!isLike" type="button" class="article-detail-form-btn btn btn-outline-primary" @click="addLikes"><i class="fas fa-thumbs-up mx-1"></i> 좋아요</button>
                <button v-else type="button" class="article-detail-form-btn btn btn-outline-primary" @click="deleteLikes"><i class="fas fa-thumbs-up mx-1"></i> 좋아요 취소</button>
                <button type="button" class="article-detail-form-btn btn btn-outline-warning" @click="startDonation"><i class="fas fa-bullhorn mx-1"></i> 후원하기</button>
                <button type="button" class="article-detail-form-btn btn btn-outline-success" @click="selectUpdate"><i class="fas fa-pencil-alt mx-1"></i> 수정</button>
                <button type="button" class="article-detail-form-btn btn btn-outline-danger" @click="deleteArticle"><i class="fas fa-trash-alt mx-1"></i> 삭제</button>
              </div>
            </div>
          </div>
        </div>
      </div>       
    </div>
  </div>

<!-- else 부분!! -->
  <div v-else class="article-detail-form-else">
    <div class="container">

      <div class="row wow fadeIn">       
      
        <!-- input 상자들...(프로필, 입력폼, 별점) -->
        <div class="article-form-input-detail">
        
          <!-- 프로필 -->
          <div class="article-detail-form-profile col-md-4">
            <div v-if="image === null">
              <img src="@/assets/images/profile_basic.jpg" class="rounded img-fluid" alt="profileImage">
            </div>
            <div v-else>
              <img :src="image" class="rounded img-fluid" alt="profileImage">
            </div>
          </div>

          <!-- 글 입력 폼 -->
          <div class="article-form-input-box">
            <v-row @keyup.enter="createArticle">
              <v-col offset="1" class="col-10">
                
                <!-- 별 -->
                <div class="article-detail-form-star-box-else">
                  <span class="article-detail-form-star-title">별점</span>
                  <div class="article-form-star stars">
                    <form action="" @change="rateStar">
                      <input v-if="newArticleRate === 5" class="star star-5" id="star-5" type="radio" name="star" value="5" checked/>
                      <input v-else class="star star-5" id="star-5" type="radio" name="star" value="5"/>
                      <label class="star star-5" for="star-5"></label>
                      <input v-if="newArticleRate === 4" class="star star-4" id="star-4" type="radio" name="star" value="4" checked/>
                      <input v-else class="star star-4" id="star-4" type="radio" name="star" value="4"/>
                      <label class="star star-4" for="star-4"></label>
                      <input v-if="newArticleRate === 3" class="star star-3" id="star-3" type="radio" name="star" value="3" checked/>
                      <input v-else class="star star-3" id="star-3" type="radio" name="star" value="3"/>
                      <label class="star star-3" for="star-3"></label>
                      <input v-if="newArticleRate === 2" class="star star-2" id="star-2" type="radio" name="star" value="2" checked/>
                      <input v-else class="star star-2" id="star-2" type="radio" name="star" value="2"/>
                      <label class="star star-2" for="star-2"></label>
                      <input v-if="newArticleRate === 1" class="star star-1" id="star-1" type="radio" name="star" value="1" checked/>
                      <input v-else class="star star-1" id="star-1" type="radio" name="star" value="1"/>
                      <label class="star star-1" for="star-1"></label>
                    </form>
                  </div>
                </div>
              

                <!-- 제목, 내용 -->              
                <v-text-field class="article-detail-form-input-box-title" label="글 제목을 입력하세요" color="white" v-model="newArticleTitle"></v-text-field>
                <v-textarea
                  class="article-detail-form-input-box-content" 
                  clearable
                  clear-icon="mdi-cached"
                  label="글 내용을 입력하세요"
                  auto-grow
                  v-model="newArticleContent"
                  color="white"
                ></v-textarea>

                <!-- 버튼 -->
                <div class="article-detail-form-input-box-btns">
                  <button type="button" class="article-detail-form-btn btn btn-outline-success" @click="updateArticle"><i class="fas fa-pencil-alt mx-1"></i> 수정</button>
                  <button type="button" class="article-detail-form-btn btn btn-outline-danger" @click="cancelUpdate"><i class="fas fa-arrow-left"></i> 취소</button>
                </div>
              
              </v-col>
            </v-row>
          </div>
        </div>     
      </div>
    </div>

  </div>

</div>

  <div>
    <hr class="separator-line">
  </div>

<!-- ------------------------------------------------------------------------------------------------------------------------- -->

  <div class="container">
      <div class="row">
          <div class="col-12">
              <div v-if="isLogin" class="post-content">
                <div class="post-container"> <!-- article-comment-form 넣으려면 여기-->
                  <div class="post-detail">
                    <div class="post-comment">
                      <div v-if="image === null">
                        <img class="profile-photo-sm" src="@/assets/images/profile_basic.jpg" alt="">
                      </div>
                      <div v-else>
                        <img class="profile-photo-sm" :src="image" alt="">
                      </div>
                      <!--<input type="text" class="form-control" placeholder="Post a comment">-->
                      <input 
                        class="form-control" 
                        placeholder="댓글을 작성해 주세요. (50자 이내)"  
                        v-model="commentContent"
                        @keyup.enter="createComment"
                        >
                    </div>
                  </div>
                </div>
              </div>
              <div v-else>
              <!-- 회원 가입 -->
                <div class="divider d-flex align-items-center my-4">
                  <p class="text-center fw-bold mx-3 mb-0" style="color: white;">회원가입하여 [Movie Curators]의 일원이 되시면, 평가/댓글 쓰기, 좋아요, 후원하기, 프로필 등 다양한 기능이 제공됩니다!</p>
                </div>
              </div>
          </div>
      </div>
  </div>
  <div>
    <hr class="separator-line">
  </div>

<!-- ------------------------------------------------------------------------------------------------------------------------- -->

  <div class="container">
    <div class="row">
    <div class="col-12">
    <div class="post-content" v-for="(comment, idx) in comments"
      :key="idx"
      :movie="movie">

      <!-- 후원 댓글 -->
      <div v-if="comment.mileage > 0" class="super-comment post-container">
         <div class="post-detail">
            <div class="post-comment">


              <!-- 프로필 사진 -->
              <div v-if="comment.user.image === null">
                <img class="profile-photo-sm" src="@/assets/images/profile_basic.jpg" alt="프로필 이미지">
              </div>
              <div v-else>
                <img class="profile-photo-sm" :src="SERVER_URL+comment.user.image" alt="프로필 이미지">
              </div>    


              <!-- 댓글내용 -->
              <div class="col-11" v-if="!selectCommentUpdateBtn || comment.id !== selectedCommentId">
                <div class="article-comment-list-item-content">
                  <div>
                    <span class="article-comment-list-item-content-span">{{comment.thanks_content}}</span>  
                    <hr>     
                  </div>
                
                  <div>
                    <span class="article-comment-list-item-content-span">{{comment.user.nickname}}</span> 
                    <span class="article-comment-list-item-content-span">{{comment.content}}</span>  
                    
                    <div id="article-comment-list-item-content-btns">
                      <button type="button" class="article-comment-list-item-content-btn btn btn-outline-success" @click="selectCommentUpdate(comment.id, comment.user.id)"><i class="fas fa-pencil-alt"></i></button>
                      <button type="button" class="article-comment-list-item-content-btn btn btn-outline-danger" @click="deleteComment(comment.id)"><i class="fas fa-trash-alt"></i></button>
                    </div>
                    
                    <span class="article-comment-list-item-content-span-time">{{comment.created_at.split('T')[0]}}</span>
                  </div>
                </div>
              </div>


              <!-- 댓글 수정 페이지 -->
              <div class="col-11" v-else>
                <!-- input -->
                <input 
                    class="form-control" 
                    placeholder="댓글을 수정해 주세요. (50자 이내)"  
                    aria-describedby="basic-addon2"
                    v-model="newCommentContent"
                    @keyup.enter="updateComment(comment.id)"
                    >
                
              </div>      
            </div>
          </div>
        </div>
        
      

<!-- ------------------------------------------------------------------------------------------------------------------------- -->

          <!-- 일반 댓글 -->
          <div v-else class="normal-comment post-container">
            <div class="post-detail">
              <div class="post-comment">


                <!-- 프로필 사진 -->
                <div v-if="comment.user.image === null">
                  <img class="profile-photo-sm" src="@/assets/images/profile_basic.jpg" alt="프로필 이미지">
                </div>
                <div v-else>
                  <img class="profile-photo-sm" :src="SERVER_URL+comment.user.image" alt="프로필 이미지">
                </div>    
                    
                <div class="col-11" v-if="!selectCommentUpdateBtn || comment.id !== selectedCommentId">
                  <div class="article-comment-list-item-content">
                    <span class="article-comment-list-item-content-span">{{comment.user.nickname}}</span> 
                    <span class="article-comment-list-item-content-span">{{comment.content}}</span>            
                    <div id="article-comment-list-item-content-btns">
                      <button type="button" class="article-comment-list-item-content-btn btn btn-outline-success" @click="selectCommentUpdate(comment.id, comment.user.id, comment)"><i class="fas fa-pencil-alt"></i></button>
                      <button type="button" class="article-comment-list-item-content-btn btn btn-outline-danger" @click="deleteComment(comment.id)"><i class="fas fa-trash-alt"></i></button>
                    </div>
                    <span class="article-comment-list-item-content-span-time">{{comment.created_at.split('T')[0]}}</span>
                  </div>
                </div>


                <!-- 댓글 수정 페이지 -->
                <div class="col-11" v-else>
                  <div>
                    <input 
                      class="form-control" 
                      placeholder="댓글을 수정해 주세요. (50자 이내)"  
                      aria-describedby="basic-addon2"
                      v-model="newCommentContent"
                      @keyup.enter="updateComment(comment.id)"
                      >
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
import swal from 'sweetalert2'
import { mapGetters } from 'vuex'
import { mapState } from 'vuex'
import SERVER from '@/api/server'

export default {
  name:'MovieDetail',
  components:{
  },
  data: function () {
    return {
      // 대상 영화 이름
      movieTitle: '',
      // article 아이디 (라우터 버전)
      articleId : this.$route.params.id,
      // 사용자 단일 평가
      articleUserId: '',
      articleUsername: '',
      articleNickname: '',
      articleTitle: '',
      articleContent: '',
      articleRate: 0, // 평가 평점
      articleImage: null,
      isArticleWriten: false, // 영화 하나당 평가 하나
      // 전체 댓글
      comments: [],
      // 도네이션 마일리지
      donationMileage: 1000,
      // 수정버튼 선택여부
      selectUpdateBtn: false, 
      // 수정정보 담기
      newArticleTitle : '',
      newArticleContent : '',
      newArticleRate: 0,
      // 사용자 단일 댓글
      commentContent: '',
      // 댓글 수정 버튼 선택여부
      selectCommentUpdateBtn: false,
      newCommentContent: '',
      selectedCommentId: '',
      // 사용자의 현 평가 좋아요 여부
      isLike: false,
      // 이미지 주소 조합용
      //SERVER_URL: 'http://127.0.0.1:8000'
      SERVER_URL : SERVER.URL
    }
  },
  computed: {
      ...mapGetters([
        'movie'
      ]),
      ...mapState(['mileage', 'userId', 'image', 'isLogin',]),
      imgSrc2: function () {
        return "https://image.tmdb.org/t/p/original" + this.movie.backdrop_path
      },
    },

  methods: {
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    // 별점 클릭
    rateStar($event) {
      this.articleRate = $event.target.value
      this.newArticleRate = $event.target.value
    },
    // article 수정 버튼 누름
    selectUpdate: function() {
      if (this.articleUserId === this.userId) {
      this.selectUpdateBtn = !this.selectUpdateBtn
      this.newArticleTitle = this.articleTitle
      this.newArticleContent = this.articleContent
      this.newArticleRate = this.articleRate
      } else {
        swal.fire ({
          icon: 'error',
          title: '평가 수정 실패',
          text: '작성자가 아닙니다.'
      })
    }
    }, 
    cancelUpdate: function() {
      this.selectUpdateBtn = !this.selectUpdateBtn
    },
    // article 수정 
    updateArticle: function () {
        if (this.newArticleRate === 0) {
          swal.fire ({
            icon: 'error',
            title: '평가 수정 실패',
            text: '별점을 입력해주세요.'
        })
        } else if (this.newArticleTitle === '') {
          swal.fire ({
            icon: 'error',
            title: '평가 수정 실패',
            text: '제목을 입력해주세요.'
        })
        } else if (this.newArticleContent === '') {
          swal.fire ({
            icon: 'error',
            title: '평가 수정 실패',
            text: '내용을 입력해주세요.'
        })
        } else {
        const contents = {
          title: this.newArticleTitle,
          content: this.newArticleContent,
          rate: this.newArticleRate,
          id: this.articleId
          }
        axios({
          method: 'PUT',
          //url: URL + `/movies/${this.movie.id}/articles/`,
          url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.movie.id) + SERVER.ROUTES.movies.articleDetail,
          headers: this.setToken(),
          data: contents,
        })
        .then(res => {
          console.log("articledetailput")
          console.log(res.data)
          this.selectUpdateBtn = !this.selectUpdateBtn
          this.articleTitle = res.data.title
          this.articleContent = res.data.content
          this.articleRate = res.data.rate
        })
        .catch (() => {
          swal.fire ({
            icon: 'error',
            title: '평가 수정 실패',
            text: '수정 권한이 없습니다.'
          })
        })
      }
    },
    // article 삭제
    deleteArticle: function (){
      const contents = {
        id: this.articleId,
      }
      axios({
          method: 'DELETE',
          //url: `http://127.0.0.1:8000/movies/${this.movie.id}/articles/`,
          url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.movie.id) + SERVER.ROUTES.movies.articleDetail,
          headers: this.setToken(),
          data: contents,
        })
        .then(() => {
          this.$router.push({ name: 'MovieDetail', params: { id: this.movie.id }})
        })
        .catch (() => {
          swal.fire ({
            icon: 'error',
            title: '평가 삭제 실패',
            text: '삭제 권한이 없습니다.'
          })
        })
    },
    // 도네이션 시작
    startDonation : function() {
      if (this.articleUserId === this.userId) {
        swal.fire ({
          icon: 'error',
          title: '후원하기 실패',
          text: '자신의 평가에는 후원할 수 없습니다.'
      }) 
      } else {
      const Buttons = swal.mixin({
        customClass: {
          confirmButton: 'btn btn-success mx-2',
          denyButton: 'btn btn-warning mx-1',
          cancelButton: 'btn btn-warning mx-1',
        },
        buttonsStyling: false
      })
      Buttons.fire ({
        title: '마일리지로 평가를 후원해 주세요.',
        showCloseButton: true,
        showDenyButton: true,
        showCancelButton: true,      
        html: '후원 마일리지를 정해주세요 : ' + this.donationMileage + '<br>보유 마일리지 : ' + this.mileage,       
        confirmButtonText: '후원하기',
        denyButtonText:'+500',
        cancelButtonText: '-500',
      }). then ( (result) => {
        if (result.isConfirmed){
          this.donate(this.donationMileage)
          this.donationMileage = 1000
          }
        else if (result.isDenied) {
          this.donationMileage = this.donationMileage + 500
          this.startDonation()
          }
        else if (result.dismiss === swal.DismissReason.cancel) {
          this.donationMileage = Math.max(500, this.donationMileage - 500)
          this.startDonation()
          }
        else {
          this.donationMileage = 1000
        }
      })
      }
    },
    // 도네이션 내용 입력 받기
    donate: function(mileageChange) {
      if (mileageChange > this.mileage) {
        swal.fire ({
          icon: 'error',
          title: '후원하기 실패',
          text: '마일리지가 부족합니다.'
      })
      } else {
        swal.fire({
          title: '댓글 내용을 입력해주세요!',
          input: 'text',
          inputAttributes: {
            autocapitalize: 'off'
          },
          confirmButtonText: '작성',
        })
        .then ( (result) => {
        // 본인의 마일리지 감소
        this.$store.dispatch('changeMileage', -mileageChange)
        // 상대 유저의 마일리지 증가
        const contexts = {
          id: this.articleUserId,
          mileage: mileageChange
        }
        this.$store.dispatch('donate', contexts)
        // 대상 평가의 포인트 갱신
        const contexts2 = {
          id: this.articleId,
          mileage: mileageChange
        }        
        this.$store.dispatch('updateArticlePoints', contexts2)
        // 슈퍼 코멘트 만들기
        this.createSuperComment(result.value, mileageChange)
        })
      }     
    },
    // 후원하기
    createSuperComment: function(superCommentContent, mileageChange) {
      // 작성글 없으면 후원 감사 뭔가 더 적어주고.
      if (!Object.keys(superCommentContent).length) {
        superCommentContent = '좋은 평가입니다!'
      }
      const contents = {
        thanks_content : mileageChange + ' 마일리지 후원 감사합니다!',
        content: superCommentContent,
        mileage: mileageChange
      }
      axios({
        method: 'post',
        //url: `http://127.0.0.1:8000/movies/${this.articleId}/comments/`,
        url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.commentDetail,
        headers: this.setToken(),
        data: contents,
      })      
      .then(() => {
        // 정렬을 위하여 필요한 행위
        this.getComments()
        this.$router.push({ name: 'ArticleDetail', params: { id: this.articleId }})
        swal.fire({
          position: 'center',
          icon: 'info',
          title: '후원 감사합니다!',
          html: '당신의 후원이 더 밝은 영화 문화를 조성합니다.<br>프로필 [내가 후원한 평가]에 추가되었습니다.',
          showConfirmButton: false,
          timer: 1200
        })
      })            
    },
    // 이 평가에 적힌 댓글 다 가져오기
    getComments: function () {
      axios({
        method: 'get',
        //url: URL + `/movies/${this.$route.params.id}/comments/list/`,
        url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.$route.params.id) + SERVER.ROUTES.movies.commentList,
        headers: this.setToken(),
      })
      .then((res)=>{
        console.log("getComments")
        console.log(res.data)
        this.comments = res.data
      })
    },
    // 평가 작성, 평가 작성 여부 갱신
    createComment: function () {
        if (!Object.keys(this.commentContent).length) {
          swal.fire ({
            icon: 'error',
            title: '평가 작성 실패',
            text: '내용을 입력해주세요.'
        })
        } else {
        const contents = {
        content: this.commentContent,
        }
        axios({
          method: 'post',
          //url: URL + `/movies/${this.articleId}/comments/`,
          url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.commentDetail,
          headers: this.setToken(),
          data: contents,
        })
        .then(() => {
          // 순서 정렬을 위해 라우터로 이동
          this.getComments()
          this.$router.push({ name: 'ArticleDetail', params: { id: this.articleId }})
          this.commentContent = ''
          })      
        }
    },

    selectCommentUpdate: function(id, commentUserId) {
      if (commentUserId === this.userId) {
      this.selectCommentUpdateBtn = !this.selectCommentUpdateBtn
      this.newCommentContent = this.commentContent
      this.selectedCommentId = id
      } else {
          swal.fire ({
          icon: 'error',
          title: '평가 수정 실패',
          text: '작성자가 아닙니다.'
      })
      }
    }, 

    // updateComment
    updateComment: function(id) {
      if (!Object.keys(this.newCommentContent).length) {
          swal.fire ({
            icon: 'error',
            title: '평가 작성 실패',
            text: '내용을 입력해주세요.'
        })
        } else {
        const contents = {
          id: this.articleId,
          commentId: id,
          content: this.newCommentContent,
        }
        axios({
          method: 'put',
          //url: `http://127.0.0.1:8000/movies/${this.articleId}/comments/`,
          url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.commentDetail,
          headers: this.setToken(),
          data: contents,
        })
        .then(() => {
          this.selectCommentUpdateBtn = !this.selectCommentUpdateBtn
          this.getComments()
          this.$router.push({ name: 'ArticleDetail', params: { id: this.articleId }})
          })
          .catch (() => {
          swal.fire ({
            icon: 'error',
            title: '댓글 수정 실패',
            text: '수정 권한이 없습니다.'
          })
          this.$router.go()
        })  
        }
        
    },       
    // deleteComment
    deleteComment: function(id) {
      swal.fire({
        title: '정말 댓글을 삭제하시겠습니까?',
        showDenyButton: true,
        confirmButtonText: '네',
        denyButtonText: `아니오`,
      }).then((result) => {
      if (result.isConfirmed) {
              const contents = {
        id: this.articleId,
        commentId: id
      }
      axios({
          method: 'DELETE',
          //url: URL + `/movies/${this.articleId}/comments/`,
          url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.commentDetail,
          headers: this.setToken(),
          data: contents,
        })
        .then(() => {
          swal.fire('댓글이 삭제되었습니다.', '', 'success')
          this.getComments()
          this.$router.push({ name: 'ArticleDetail', params: { id: this.articleId }})
        })
        .catch (err => {
          swal.fire ({
            icon: 'error',
            title: '댓글 삭제 실패',
            text: err.response.data.Unauthorized
          })
        })        
      } else {
        swal.fire('삭제를 취소하셨습니다.', '', 'info')
      }
    })
    },
    // 좋아요 추가하기
    addLikes: function () {
      axios({
        method: 'POST',
        //url: `http://127.0.0.1:8000/movies/${this.articleId}/likes/`,
        url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.likes,
        headers: this.setToken(),
      })
      .then(() => {
        this.isLike = true
        swal.fire({
          position: 'bottom-end',
          icon: 'info',
          title: '좋아요 추가',
          text: '프로필 [내가 좋아한 평가]에 추가되었습니다.',
          showConfirmButton: false,
          timer: 1200
        })
      })
    },
    // 좋아요 취소하기
    deleteLikes: function () {
      axios({
        method: 'DELETE',
        //url: `http://127.0.0.1:8000/movies/${this.articleId}/likes/`,
        url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.likes,
        headers: this.setToken(),
      })
      .then(() => {
        this.isLike = false
        swal.fire({
          position: 'bottom-end',
          icon: 'info',
          title: '좋아요 취소',
          text: '프로필 [내가 좋아한 평가]에서 제거되었습니다.',
          showConfirmButton: false,
          timer: 1200
        })
      })
    },
  },
  created() {
    // 시작 시점에 영화, 평가와 댓글 가져오기
    // 사용자 평가 정보 가져오기
    axios ({
      method: 'get',
      //url: URL + `/movies/${this.articleId}/article`,
      url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.getArticle,
      headers: this.setToken(),
    })
    .then(res => {
      this.movieTitle = res.data.movie.title
      this.articleUserId = res.data.user.id
      this.articleUsername = res.data.user.username
      this.articleNickname = res.data.user.nickname
      this.articleRate = res.data.rate
      this.articleTitle = res.data.title
      this.articleContent = res.data.content
      this.articleImage = res.data.user.image
    })
    // 사용자 좋아요 여부 정보 가져오기
    axios ({
      method: 'get',
      //url: `http://127.0.0.1:8000/movies/${this.articleId}/likes/`,
      url: SERVER.URL + SERVER.ROUTES.movies.home + String(this.articleId) + SERVER.ROUTES.movies.likes,
      headers: this.setToken(),
    })
    .then(res => {
      if (Object.keys(res.data).length) {
        this.isLike = true
      } else {
        this.isLike = false
      }
    })  
    // 기존에 적힌 평가 정보 가져오기
    this.getComments()

  }
}
</script>

<style scoped>


.box-poision-right {
  display: inline-block;
  position: static;
  /*right: 16px;*/
}

.articleDetialMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );    
  background-size: cover;
  min-height: 100vh;
  padding: 0% 12%;
  padding-top: 75px;
  z-index: -99;
}
.backdrop-bg-1 {
  padding-top: 60px;
  position: fixed; 
  top: 0; 
  left: 0; 
  margin: auto;
  opacity: 0.2;
  z-index: 0;
}

.article-detail-index{
  position: relative;
  z-index: 1;
}

.article-detail-profile {
  border: 0.5px solid black;
  border-radius: 4px;
}

.article-detail-form-info {
  margin-top: 10px;
  color: white;
  z-index: 2;
}

.article-detail-form-info-title {
  font-weight: bold;
  font-size: 1.5rem;
}


.article-detail-form {
  margin-top: 10px;
  z-index: 1;
}

#article-detail-form-profile {
  border: 0.5px solid black;
  border-radius: 4px;
}


.article-detail-form-star-box-else {
  display: inline-block;
}

.article-detail-form-star-box {
  display: inline-block;
  position: absolute;
  right:16px;
}

.article-detail-form-star {
  position: relative;
  float: right;
}

.article-detail-form-btns {
  position: absolute; 
  right: 16px; 
  bottom: 16px;
}

.article-detail-form-btn {
  margin-left: 10px;
  padding: 3px;
}

.separator-line {
  color: white;
}

.article-comment-form {
  background: rgb(62, 62, 64);
  color: white;
  border-radius: 10px;
}

.article-comment-form-profile {
  display: inline-block;
}

.article-comment-form-profile > img{
  border-radius: 40%;
}

.article-comment-form-input {
  display: inline;
  position: relative;
  height: 100px;
}

.article-comment-form-input-group {
  display: inline;
}

.article-comment-form-input-content {
  opacity: 0.5;
  display: inline;
} 

.article-comment-form-input-btn {
  float: right;
  position: absolute;
  right: 12px;
  bottom: 11px;
}

/* ====================== */

.article-comment-list {
  color: white;
}

.article-comment-list-item {
  background: rgb(62, 62, 64);
  color: white;
  margin-bottom: 10px;
  height: auto;
  line-height: auto;
  border-radius: 10px;
  display: flex;

}

.article-comment-list-item-profile {
  display: inline-block;
  margin: auto;
}

.article-comment-list-item-profile > img{
  border-radius: 40%;
  vertical-align: middle;
}

.article-comment-list-item-content-span {
  margin-right: 70px;
  vertical-align: middle;
}

.article-comment-list-item-content-span-time {
  margin-right: 10px;
  float: right
} 


#article-comment-list-item-content-btns {
  display: inline;  
  float: right;
  text-align: right;
}

.article-comment-list-item-content-btn {
  margin-right: 5px;
  font-size: 10px;
}

/* ====================== else ====================== */

.article-detail-form-else {
  margin-top: 10px;
  color: white;
  z-index: 1;
}


.article-detail-form-star-box {
  display: inline-block;
  line-height: 35px;
}

.article-detail-form-input-box-title {
  background-color: white;
  border-radius: 4px;
  opacity: 0.5;
  margin-bottom: 7px;
  padding: 5px;
} 

.article-detail-form-input-box-content {
  background-color: white;
  border-radius: 4px;
  opacity: 0.5;
  margin-bottom: 7px;
  padding: 5px;
} 

/* 버튼 */
.article-detail-form-input-box-btn {
  margin-bottom: 20px;
  display: inline;
}

.article-detail-form-input-box-btns {
  text-align: right;
}


/* 프로필이미지 관련 */
.article-detail-form-profile{
  float: left;
  margin-top: 12px;
  width: 12%;
}


#articleDetialMain  {
  color:white;
}

#commentText {
  color: white;
}

#movie-detail-article-form {
  position: relative;
  color: white;
  top: 10rem;
}

.movie-detail-article-form-title {
  color: white;
}

.movie-detail-article-form-input {
  padding: 10px; 
}

.movie-detail-article-form-input-btn{
  margin-bottom: 20px;
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
  width: 100%;
}

.post-content .post-container .post-detail .post-comment img.profile-photo-sm{
  margin-right: 10px;
}

.post-content .post-container .post-detail .post-comment .form-control{
  height: 30px;
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

