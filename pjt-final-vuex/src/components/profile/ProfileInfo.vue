<template>
<div>
  <div class="wrapper">
    <div class="profile-card">
      <div class="profile-card__img">
        <div v-if="image === null">
          <img src="@/assets/images/profile_basic.jpg" alt="profileImage">
        </div>
        <div v-else>
          <img :src="image" alt="profileImage">
        </div>
      </div>

      <div class="profile-card__cnt">
        <div v-if="nickname !== ''">
          <div class="profile-card__name">{{ nickname }} 님</div>
        </div>
        <div v-else>
          <div class="profile-card__name">닉네임을 설정해주세요.</div>
        </div>
        <div class="profile-card__txt"><strong>{{introduction}}</strong></div>

        <div class="profile-card-loc">
          <i class="fas fa-coins mx-1"></i>        
          <span class="profile-card-loc__txt">마일리지: {{ mileage }}</span>
        </div>


        <div class="profile-card-inf">
          <div class="profile-card-inf__item">
            <div class="profile-card-inf__title">{{articles_count}}</div>
            <div class="profile-card-inf__txt">작성 평가</div>
          </div>
          <div class="profile-card-inf__item">
            <div class="profile-card-inf__title">{{comments_count}}</div>
            <div class="profile-card-inf__txt">작성 댓글</div>
          </div>
          <div class="profile-card-inf__item">
            <div class="profile-card-inf__title">{{following.length}}</div>
            <div class="profile-card-inf__txt">후원 횟수</div>
          </div>
          <div class="profile-card-inf__item">
            <div class="profile-card-inf__title">{{followers.length}}</div>
            <div class="profile-card-inf__txt">후원 받은 수</div>
          </div>
        </div>
      
      <div class="profile-card-ctr">
        <button class="profile-card__button button--blue js-message-btn" data-bs-toggle="modal" data-bs-target="#ChangeProfileBtn" @click="initModal">프로필 수정</button>
        <button class="profile-card__button button--orange" @click="buyMileage">마일리지 충전</button>    <!-- 마일리지 충전 페이지로 넘어갈 수 있도록   -->
      </div>
        
      </div>
    </div>
  </div>
<!-- modal -->
  <div class="modal fade" id="ChangeProfileBtn" tabindex="-1" aria-labelledby="profileModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">프로필 수정</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <div class="mb-3">
              
              <!-- 아이디 -->
              <div class="update-modal mb-3">
                <label for="user_id" class="col-form-label">ID:</label>
                <input type="text" class="form-control" id="user_id" v-model="username" disabled>
              </div>
              
              <!-- 닉네임 -->
              <div v-if="nickname !== ''" class="update-modal mb-3">
                <label for="nickname" class="col-form-label">닉네임:</label>
                <input type="text" class="form-control" id="nickname" 
                v-model="credentials.newNickname" autocomplete="off">
              </div>

              <!-- 닉네임 else -->
              <div v-else class="update-modal mb-3">
                <label for="nickname" class="col-form-label">닉네임:</label>
                <input type="text" class="form-control" id="nickname" 
                v-model="credentials.newNickname" placeholder="닉네임을 등록해 주세요 :)" autocomplete="off">
              </div>

              <!-- 자기소개 -->
              <div class="update-modal mb-3">
                <label for="introduction" class="col-form-label">자기소개:</label>
                <input type="text" class="form-control" id="introduction" v-model="credentials.newIntroduction" autocomplete="off">
              </div>

              <!-- 프로필 사진 -->
              <div class="update-modal mb-3">
                <label for="profile-photo" class="col-form-label">사진:</label>
                <input type="file" class="form-control" id="profile-photo" @change="getNewImage">
              </div>


            </div>

          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
          <button type="button" class="btn btn-primary" @click="updateProfile" data-bs-dismiss="modal">수정</button>
        </div>
      </div>
    </div>
  </div>
<!-- modal end -->
</div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name:'ProfileInfo',
  data: function(){
    return {
      credentials: {
        newNickname: '',
        newImage: '',
        newIntroduction: '',
      },
      SERVER_URL: 'http://127.0.0.1:8000/',
    }
  },
  methods: {
    // 맨 처음에 modal 띄울 때 기본적으로 가지고 있는 UserInfo 보여주기(username, nickname, image, introduction)
    initModal: function(){
      this.credentials.newNickname = this.nickname
      this.credentials.newImage = ''
      this.credentials.newIntroduction = this.introduction
    },
    updateProfile: function(){
      const formData = new FormData()
      formData.append('image', this.credentials.newImage)
      formData.append('nickname', this.credentials.newNickname)
      formData.append('introduction', this.credentials.newIntroduction)
      this.$store.dispatch('updateProfile', formData)
    },
    getNewImage: function(event) {
      this.credentials.newImage = event.target.files[0]
    },
    buyMileage: function() {
      const mileageAdd = 1500
      //const mileageAdd = 500 + Math.floor(Math.random() * 500) // 500~1000랜덤 마일리지 하고 싶어지면 이거
      this.$store.dispatch('changeMileage', mileageAdd)
    }
  },
  computed: {
    ...mapState(['username', 'nickname', 'mileage', 'image', 'introduction', 'following', 'followers', 'comments_count', 'articles_count'])
  },
  created(){
    this.initModal()
    this.$store.dispatch('getUserInfo', this.username)
  },
}
</script>

<style lang="scss" scoped>

// 프로필 수정 modal
.modal-update {
  margin-bottom: 20px;
}

* {
  box-sizing: border-box;
}

body {
  font-family: 'Quicksand', sans-serif;
  color: #324e63;
}

a, a:hover {
  text-decoration: none;
}

.icon {
  display: inline-block;
  width: 1em;
  height: 1em;
  stroke-width: 0;
  stroke: currentColor;
  fill: currentColor;
}

.wrapper {
  height: auto;
  padding: 50px 20px;
  padding-top: 100px;
  @media screen and (max-width: 768px) {
    height: auto;
    min-height: 100vh;
    padding-top: 100px;
  }

}

.profile-card {
  width: 100%;
  min-height: 460px;
  margin: auto;
  box-shadow: 0px 8px 60px -10px rgba(13,28,39,0.6);
  background: #fff;
  border-radius: 12px;
  max-width: 700px;
  position: relative;

  &.active {
    .profile-card__cnt {
      filter: blur(6px);
    }

    .profile-card-message,
    .profile-card__overlay {
      opacity: 1;
      pointer-events: auto;
      transition-delay: .1s;
    }

    .profile-card-form {
      transform: none;
      transition-delay: .1s;
    }
  }

  &__img {
    width: 150px;
    height: 150px;
    margin-left: auto;
    margin-right: auto;
    transform: translateY(-50%);
    border-radius: 50%;
    overflow: hidden;
    position: relative;
    z-index: 4;
    box-shadow: 0px 5px 50px 0px rgb(108, 68, 252), 0px 0px 0px 7px rgba(107, 74, 255, 0.5);

    @media screen and (max-width: 576px) {
      width: 120px;
      height: 120px;
    }

    img {
      display: block;
      width: 100%;
      height: 100%;
      object-fit: cover;
      border-radius: 50%;
    }

  }

  &__cnt {
    margin-top: -35px;
    text-align: center;
    padding: 0 20px;
    padding-bottom: 40px;
    transition: all .3s;
  }

  &__name {
    font-weight: 700;
    font-size: 24px;
    color: #6944ff;
    margin-bottom: 15px;
  }

  &__txt {
    font-size: 18px;
    font-weight: 500;
    color: #324e63;
    margin-bottom: 15px;

    strong {
      //color: #ff2846;
      font-weight: 700;
    }

  }

  &-loc {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 18px;
    font-weight: 600;

    &__icon {
      display: inline-flex;
      font-size: 27px;
      margin-right: 10px;
      //color: #6944ff;
    }

  }

  &-inf {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    align-items: flex-start;
    margin-top: 35px;

    &__item {
      padding: 10px 35px;
      min-width: 150px;

      @media screen and (max-width: 768px) {
        padding: 10px 20px;
        min-width: 120px;
      }

    }

    &__title {
      font-weight: 700;
      font-size: 27px;
      //color: #6944ff;
      color: #324e63;
    }

    &__txt {
      font-weight: 500;
      margin-top: 7px;
    }

  }


  &__button {
    background: none;
    border: none;
    font-family: 'Quicksand', sans-serif;
    font-weight: 700;
    font-size: 19px;
    margin: 15px 35px;
    padding: 15px 40px;
    min-width: 201px;
    border-radius: 50px;
    min-height: 55px;
    color: #fff;
    cursor: pointer;
    backface-visibility: hidden;
    transition: all .3s;

    @media screen and (max-width: 768px) {
      min-width: 170px;
      margin: 15px 25px;
    }

    @media screen and (max-width: 576px) {
      min-width: inherit;
      margin: 0;
      margin-bottom: 16px;
      width: 100%;
      max-width: 300px;

      &:last-child {
        margin-bottom: 0;
      }
    }

    &:focus {
      outline: none!important;
    }

    @media screen and (min-width: 768px) {
      &:hover {
        transform: translateY(-5px);
      }
    }

    &:first-child {
      margin-left: 0;
    }

    &:last-child {
      margin-right: 0;
    }

    &.button--blue {
      background: linear-gradient(45deg, #1da1f2, #0e71c8);
      box-shadow: 0px 4px 30px rgba(19, 127, 212, 0.4);

      &:hover {
        box-shadow: 0px 7px 30px rgba(19, 127, 212, 0.75);
      }

    }

    &.button--orange {
      background: linear-gradient(45deg, #d5135a, #f05924);
      box-shadow: 0px 4px 30px rgba(223, 45, 70, 0.35);

      &:hover {
        box-shadow: 0px 7px 30px rgba(223, 45, 70, 0.75);
      }

    }

    &.button--gray {
      box-shadow: none;
      background: #dcdcdc;
      color: #142029;
    }

  }

  &-message {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    padding-top: 130px;
    padding-bottom: 100px;
    opacity: 0;
    pointer-events: none;
    transition: all .3s;
  }

  &-form {
    box-shadow: 0 4px 30px rgba(15, 22, 56, 0.35);
    max-width: 80%;
    margin-left: auto;
    margin-right: auto;
    height: 100%;
    background: #fff;
    border-radius: 10px;
    padding: 35px;
    transform: scale(.8);
    position: relative;
    z-index: 3;
    transition: all .3s;

    @media screen and (max-width: 768px) {
      max-width: 90%;
      height: auto;
    }

    @media screen and (max-width: 576px) {
      padding: 20px;
    }

    &__bottom {
      justify-content: space-between;
      display: flex;

      @media screen and (max-width: 576px) {
        flex-wrap: wrap;
      }

    }

  }

  textarea {
    width: 100%;
    resize: none;
    height: 210px;
    margin-bottom: 20px;
    border: 2px solid #dcdcdc;
    border-radius: 10px;
    padding: 15px 20px;
    color: #324e63;
    font-weight: 500;
    font-family: 'Quicksand', sans-serif;
    outline: none;
    transition: all .3s;

    &:focus {
      outline: none;
      border-color: #8a979e;
    }

  }

  &__overlay {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    pointer-events: none;
    opacity: 0;
    background: rgba(22, 33, 72, 0.35);
    border-radius: 12px;
    transition: all .3s;
  }

}

</style>