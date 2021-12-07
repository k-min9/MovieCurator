import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import SignUp from '../views/SignUp.vue'
import MovieList from '../views/MovieList.vue'
import Profile from '../views/Profile.vue'
import Curator from '../views/Curator.vue'
import Search from '../views/Search.vue'
import AuthHandler from '../views/AuthHandler.vue'
import MovieDetail from '../views/MovieDetail.vue'
import ArticleDetail from '../views/ArticleDetail.vue'
import CuratorDetail from '../views/CuratorDetail.vue'
import NotFound from '../views/NotFound.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component : Home
  },
  {
    path: '/oauth2/google/callback',
    name: 'AuthHandler',
    component: AuthHandler,
  },
  {
    path: '/curator/',
    name: 'Curator',
    component: Curator
  },
  {
    path: '/accounts/login/',
    name: 'Login',
    component: Login
  },
  {
    path: '/accounts/signup/',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/:id/profile/',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/movielist/',
    name: 'MovieList',
    component: MovieList
  },
  {
    path: '/search/',
    name: 'Search',
    component: Search
  },
  {
    path: '/movie/:id',
    name: 'MovieDetail',
    component: MovieDetail
  },
  {
    path: '/article/:id',
    name: 'ArticleDetail',
    component: ArticleDetail
  },
  {
    path: '/curator/:id',
    name: 'CuratorDetail',
    component: CuratorDetail
  },
  {
    // 올바르지 못한 주소 404 일괄 처리
    path: '/404',
    name: 'NotFound',
    component: NotFound,
  },
  {
    path: '*',
    redirect: '/404'
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() { 
    return { x: 0, y: 0 } 
  },
})

export default router
