<template>
  <div id = "homeMain">
    <home-reference-movie></home-reference-movie>
    <home-reference-curator></home-reference-curator>
    <home-reference-extra></home-reference-extra>
    <home-footer></home-footer>
  </div>
</template>

<script>
import HomeReferenceMovie from '@/components/home/HomeReferenceMovie'
import HomeReferenceCurator from '@/components/home/HomeReferenceCurator'
import HomeReferenceExtra from '@/components/home/HomeReferenceExtra'
import HomeFooter from '@/components/home/HomeFooter'

export default {
  name: 'Home',
  components: {
    HomeReferenceMovie,
    HomeReferenceCurator,
    HomeReferenceExtra,
    HomeFooter,
  },

}
</script>

<style scoped>
#homeMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );    
  background-size: cover;
  min-height: 100vh;
  padding: 0% 12%;
  padding-top: 75px;

}
</style>