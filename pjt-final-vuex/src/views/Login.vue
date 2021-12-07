<template>

<div class="loginMain">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div>
                    <form onsubmit="event.preventDefault()" class="box">
                        <h1>로그인</h1>
                        <!-- @keyup.enter="login" -->
                        <p class="text-muted"> 아이디와 비밀번호를 입력해주세요!</p> 
                        <input type="text" id="username" v-model="credentials.username" placeholder="아이디" ref="focushere" autocomplete="off">  
                        <input type="password" id="password" v-model="credentials.password" placeholder="비밀번호" autocomplete="off"> 
                        <!-- <a class="forgot text-muted" href="#">Forgot password?</a>  -->
                        <button @click="login">로그인</button>

                      <!-- 구글 로그인 -->
                        <div class="divider d-flex align-items-center my-4">
                            <p class="text-center fw-bold mx-3 mb-0 text-muted">간편 로그인으로 검색기능이 제공됩니다</p>
                        </div>

                        <div class="social-login">
                            <img class="social-login-google" @click="google_login" src="@/assets/images/google.jpg" alt="google">
                        </div>

                      <!-- 회원 가입 -->
                        <div class="divider d-flex align-items-center my-4">
                            <p class="text-center fw-bold mx-3 mb-0 text-muted">회원가입하여 [Movie Curators]의 일원이 되시면, 평가/댓글 쓰기, 좋아요, 후원하기, 프로필 등 다양한 기능이 제공됩니다!</p>
                        </div>

                        <router-link :to="{ name: 'SignUp'}">
                            <button class="search-button">회원가입으로 이동<i class="fas fa-arrow-circle-right mx-1"></i></button>
                        </router-link>  
   
                  </form>
              </div>
          </div>
      </div>
  </div>
</div>

</template>

<script>
import swal from 'sweetalert2'

export default {
  name: 'Login',
  data: function () {
    return {
      credentials:{
        username: null, 
        password: null, 
      }
    }
  },
  methods: {
    login: function () {
        if (!this.credentials.username) {
            swal.fire ({
            icon: 'error',
            title: '로그인 실패',
            text: '유저 이름을 입력해주세요.'
        })
        } else if (!this.credentials.password) {
            swal.fire ({
            icon: 'error',
            title: '로그인 실패',
            text: '비밀번호를 입력해주세요.'
        })
        } else {
            this.$store.dispatch('login', this.credentials)
            this.$store.dispatch('getUserInfo', this.credentials.username)
        }
    },
    google_login: function() {
      this.$store.dispatch('googleLogin')
    }
  },
  mounted(){
    this.$refs.focushere.focus();
  }
}
</script>

<style scoped>

.loginMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F ); 
  background-size: cover;
  min-height: 100vh;
}

.box {
  text-align: center;
  transition: 0.25s;
  margin-top: 100px;
}


.box input[type="text"], 
.box input[type="password"] {
  border: 0;
    background: none;
    display: block;
    margin: 20px auto;
    text-align: center;
    border: 2px solid #3498db;
    padding: 10px 10px;
    width: 250px;
    outline: none;
    color: white;
    border-radius: 24px;
    transition: 0.25s
}

.box h1 {
    color: white;
    text-transform: uppercase;
    font-weight: 500
}

.box input[type="text"]:focus,
.box input[type="password"]:focus {
    width: 300px;
    border-color: #2ecc71
}

.box button {
    border: 0;
    background: none;
    display: block;
    margin: 20px auto;
    text-align: center;
    border: 2px solid #2ecc71;
    padding: 14px 40px;
    outline: none;
    color: white;
    border-radius: 24px;
    transition: 0.25s;
    cursor: pointer
}


/* 구글 소셜 로그인 관련 */

.social-login img {
  object-fit: cover;
  width: 200px;
  border-radius: 5px;
}

.social-login-google:hover {
  cursor: pointer;
}


.box button:hover {
    background: #2ecc71
}


.forgot {
    text-decoration: underline
}


.divider:after,
.divider:before {
  content: "";
  flex: 1;
  height: 1px;
  background: #eee;
}

/* 회원가입 이동 버튼*/
.search-button {
    border: 0;
    background: none;
    display: block;
    margin: 20px auto;
    text-align: center;
    border: 2px solid #2ecc71;
    padding: 14px 40px;
    outline: none;
    color: white;
    border-radius: 24px;
    transition: 0.25s;
    cursor: pointer
}

.search-button:hover {
    background: #2ecc71
}


</style>