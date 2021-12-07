<template>
  <div>
    Please wait...
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import qs from 'qs'

export default {
  name: 'AuthHandler',
  methods: mapActions(['finalizeGoogleLogin']),
  created() {
    const { access_token } = qs.parse(window.location.hash.substr(1))
    // 같은 결과(토큰)를 반환
    // const access_token2 = location.href.match('access_token=(.+?)&')[1];
    this.finalizeGoogleLogin(access_token)
  }
}
</script>

<style>

</style>