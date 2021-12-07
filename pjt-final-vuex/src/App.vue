<template>
  <div class="appMain">
    <nav-bar></nav-bar>
    <router-view :key="$route.path"></router-view>
  </div>
</template>

<script>
import NavBar from '@/NavBar.vue'

export default {
  name:'App',
  components: {
    NavBar
  },

}
</script>

<style lang="scss">

@import url(//spoqa.github.io/spoqa-han-sans/css/SpoqaHanSansNeo.css);
* {
    font-family: "Spoqa Han Sans Neo", "sans-serif";
}

@import 'bootstrap/scss/bootstrap';

</style>