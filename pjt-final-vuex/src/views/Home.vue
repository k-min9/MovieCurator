<template>
  <div id = "homeMain">
    <home-reference-movie></home-reference-movie>
    <home-reference-curator></home-reference-curator>
    <home-reference-extra></home-reference-extra>
  </div>
</template>

<script>
import HomeReferenceMovie from '@/components/home/HomeReferenceMovie'
import HomeReferenceCurator from '@/components/home/HomeReferenceCurator'
import HomeReferenceExtra from '@/components/home/HomeReferenceExtra'

export default {
  name: 'Home',
  components: {
    HomeReferenceMovie,
    HomeReferenceCurator,
    HomeReferenceExtra,
  },

}
</script>

<style scoped>
#homeMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );    
  background-size: cover;
  min-height: 100vh;
  padding: 0% 12%;
  padding-top: 105px;
}
</style>