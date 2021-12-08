<template>
<div class="signUpMain">
  <div class="container">   <!-- @keyup.enter="signup" -->
      <div class="row">
          <div class="col-md-12">
              <div>
                  <form onsubmit="event.preventDefault()" class="box">
                      <h1>회원가입</h1>
                      <p class="text-muted"> 가입하실 아이디와 비밀번호를 입력해주세요!</p> 
                      <input type="text" id="username" v-model="credentials.username" placeholder="아이디" ref="focushere" autocomplete="off"> 
                      <input type="password" id="password" v-model="credentials.password" placeholder="비밀번호" autocomplete="off"> 
                      <input type="password" id="passwordConfirmation" v-model="credentials.passwordConfirmation" placeholder="비밀번호 확인" autocomplete="off">  
                      <button @click="signup">회원가입</button>
                  </form>
              </div>
          </div>
      </div>
  </div>
</div>

</template>

<script>
import axios from 'axios'
import swal from 'sweetalert2'
import SERVER from '@/api/server'

export default {
  name: 'SignUp',
  data: function () {
    return {
      credentials: {
        username: null,
        password: null,
        passwordConfirmation: null,
      }
    }
  },
  methods: {
    signup: function () {
        if (!this.credentials.username) {
          swal.fire ({
            icon: 'error',
            title: '가입 실패',
            text: '유저 이름을 입력해주세요.'
        })
        } else if (!this.credentials.password) {
          swal.fire ({
            icon: 'error',
            title: '가입 실패',
            text: '비밀번호를 입력해주세요.'
        })
        } else if (this.credentials.password != this.credentials.passwordConfirmation) {
          swal.fire ({
            icon: 'error',
            title: '가입 실패',
            text: '비밀번호가 일치하지 않습니다.'
        })
        } else {
        const contents = {
        username: this.credentials.username,
        nickname: this.credentials.username,
        password: this.credentials.password,
        passwordConfirmation: this.credentials.passwordConfirmation,
        }
        axios({
          method: 'POST',
          //url: 'http://127.0.0.1:8000/accounts/signup/',
          url: SERVER.URL + SERVER.ROUTES.accounts.signup,
          data: contents,
        })
        .then(
          () => {
            this.$store.dispatch('login', this.credentials)
            this.$store.dispatch('getUserInfo', this.credentials.username)
          }              
        )
        .catch(err =>{
          console.log(err.response)
          swal.fire ({
              icon: 'error',
              title: '가입 실패',
              text: '가입 실패'//err.response.data.error
              })      
          })
        }
    },
  },
  mounted(){
    this.$refs.focushere.focus();
  }
}

</script>

<style scoped>

.signUpMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F ); 
  background-size: cover;
  min-height: 100vh;
}

.box {
    text-align: center;
    transition: 0.25s;
    margin-top: 100px
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

.box button:hover {
    background: #2ecc71
}

</style>