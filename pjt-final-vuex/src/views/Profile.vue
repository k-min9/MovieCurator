<template>
  <div id="profileMain">
    <profile-info></profile-info>
    <profile-curator-list></profile-curator-list>
    <profile-article-list></profile-article-list>
  </div>
</template>

<script>
import ProfileInfo from '@/components/profile/ProfileInfo'
import ProfileCuratorList from '@/components/profile/ProfileCuratorList'
import ProfileArticleList from '@/components/profile/ProfileArticleList'

export default {
  name: 'Profile',
  components: {
    ProfileInfo, 
    ProfileCuratorList,
    ProfileArticleList,
  }

}
</script>

<style scoped>
#profileMain {
  background: radial-gradient( closest-corner at 50% 70%, #111115, #16151A, #26272F );
  background-size: cover;
  min-height: 100vh;
  margin: 0;
  padding: 0;
  padding-top: 60px;
}

</style>