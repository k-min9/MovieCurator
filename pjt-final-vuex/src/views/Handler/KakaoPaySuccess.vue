<template>
  <div>
    결제 성공! <br>
    홈으로 이동 중...
  </div>
</template>

<script>
import axios from 'axios'
import swal from 'sweetalert2'
import router from '@/router'
import SERVER from '@/api/server'

export default {
  name: 'KakaoPaySuccess',
  methods: {
    // 토큰
    setToken() {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`
      }
      return config
    },
    // 결제 성공시 정보 받아오기
    kakaoPayRequest: function (pg_token){
      // 결제 내용 저장
    axios({
        method: 'POST',
        headers: this.setToken(),
        //url: `http://127.0.0.1:8080/kakaoPay/success/`,
        url: SERVER.URL + '/kakaoPay/success/',
        params: {
          pgToken: pg_token,
        },
        //headers: this.setToken(),
      })
      .then((res) => {
        // console.log('PaySuccess')
        // console.log(res.data)
        swal.fire ({
          icon: 'success',
          title: '후원 감사합니다.',
          //text: '서버가 혼잡합니다. 다시 시도해 주세요.'
          text: res.data.item_name + '회원으로 전환되었습니다.'
        })
        router.push('/') 
      })
      .catch (() => {
        swal.fire ({
          icon: 'error',
          title: '결제 실패',
          //text: '서버가 혼잡합니다. 다시 시도해 주세요.'
          text: pg_token
        })
      })
    },
  },
  created() {
    console.log('Success')
    console.log(this.$route.query.pg_token)
    this.kakaoPayRequest(this.$route.query.pg_token)
  }
}
</script>

<style>

</style>