import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import router from '@/router'
import createPersistedState from "vuex-persistedstate";
import swal from 'sweetalert2'
import SERVER from '@/api/server'

//all auth용
import googleApi from '@/api/google'


Vue.use(Vuex)

export default new Vuex.Store({
  // 새로고침해도 state 정보 온존 
  plugins: [createPersistedState()],
  //plugins: [createPersistedState({storage: window.sessionStorage})], // 창 종료시 날려버림(테스트용)
  state: {
    // Oauth용 토큰
    token: null,
    // 계정 상황
    userId: null,
    isLogin: false,
    isSocialLogin: false,
    username: null,
    nickname: null,
    mileage: null,
    userExp: 0,
    image: null,
    introduction: null,
    // 작성 평가, 댓글 작성수 관련 (이벤트 등에 활용 가능)
    comments_count: 0,
    articles_count: 0,
    // 팔로잉, 팔로워 관련 (초회 후원등에 활용 가능)
    following: null,
    follower: null,
    // 자주 방문하는 페이지 영화 프리로딩
    movie: null,
    movies: [],
    moviesRecommend: [],
  },
  getters: {
    movie(state) {
      return state.movie
    },
    movies(state) {
      return state.movies
    },
    moviesRecommend(state) {
      return state.moviesRecommend
    },
    token: function() {
      const token = localStorage.getItem('jwt')
      return {
        Authorization: `JWT ${token}`,
      };
    },
  },
  mutations: {
    // 계정 상태 변경
    LOGIN(state, credentials) {
      state.username = credentials.username
      state.isLogin = true
    },
    SET_PREMIUM(state) {
      state.userExp = 1
    },
    LOGOUT(state) {
      state.isLogin = false
      state.userId = null
      state.isSocialLogin = false
      state.username = null
      state.nickname = null
      state.introduction = null
      state.mileage = null
      state.userExp = 0
      state.image = null
      state.token = null
      state.followers = null
      state.following = null
      state.comments_count = null
      state.articles_count = null
      localStorage.removeItem("jwt");
      localStorage.removeItem("googleToken")
    },
    // Oauth용
    SET_TOKEN(state, token) {
      state.token = token
      state.isSocialLogin = true
    },
    GET_USER_INFO(state, res) {
      state.userId = res.id
      state.nickname = res.nickname
      state.mileage = res.mileage
      state.userExp = res.exp
      state.introduction = res.introduction
      state.following = res.following
      state.followers = res.followers
      state.comments_count = res.comments_count
      state.articles_count = res.articles_count
      if (res.image) {
        //state.image = 'http://127.0.0.1:8000' + res.image  
        state.image = SERVER.ROUTES.image + res.image         
      } else {
        state.image = null
      }
    },
    GET_MY_PROFILE(state, data){
      state.nickname = data.nickname
      state.image = data.image
      state.introduction = data.introduction
    },
    CHANGE_MILEAGE(state, data) {
      state.mileage = data.mileage
    },
    GET_MOVIE(state, res) {
      state.movie = res
    },
    GET_MOVIES(state, res) {
      state.movies = res
    },
    GET_MOVIES_RECOMMEND(state, res) {
      state.moviesRecommend = res
    },
  },
  actions: {
    // 계정 상태 변경
    login({commit}, credentials) {
      axios({
        method: 'POST',
        //url: `${SERVER_URL}accounts/api-token-auth/`,
        //route: SERVER.ROUTES.accounts.login,
        //url: 'https://moviecurators.herokuapp.com/accounts/api-token-auth/',
        url: SERVER.URL + SERVER.ROUTES.accounts.login,
        data: credentials,
      })
      .then(res => {
        localStorage.setItem('jwt', res.data.token)
        commit('LOGIN', credentials)
        router.push({name: 'Home'})
      })
      .catch(() => {
        swal.fire ({
          icon: 'error',
          title: '로그인 실패',
          text: '잘못된 아이디 또는 패스워드입니다.',
          scrollbarPadding: false
          })    
      })
    },
    //OAUTH 간편 로그인
    googleLogin() {
      googleApi.login()
    },
    // 받은 토큰으로 state 세팅
    finalizeGoogleLogin({ commit }, access_token) {
      // localStorage와 state에 accessToken 세팅
      localStorage.setItem('googleToken', access_token)
      commit('SET_TOKEN', access_token)
      //commit('LOGIN')
      router.push('/')  // 홈으로 보내버리겠다.
    },
    getUserInfo({commit}, username) {
      axios({
        method: 'GET',
        //url: `URL + /accounts/${username}/get_user_info/`,
        url: SERVER.URL + SERVER.ROUTES.accounts.default + String(username) + SERVER.ROUTES.accounts.getUserInfo,
      })
      .then(res => {
        commit('GET_USER_INFO', res.data)
      })
      .catch(err => console.log(err))
    },
    getMyProfile: function ({ commit, state }) {
      axios({
        method: "GET",
        //url: `${SERVER_URL}profile/`,
        //route: SERVER.ROUTES.accounts.myProfileDetail
        url: SERVER.URL + SERVER.ROUTES.accounts.myProfileDetail
      })
      .then((res) => {
        const nickname = res.data.nickname
        const introduction = res.data.introduction
        var image = ''
        if (res.data.image) {
          //image = 'http://127.0.0.1:8000' + res.data.image     
          image = SERVER.ROUTES.image + res.data.image       
        } else {
          image = state.image
        }
        commit("GET_MY_PROFILE", { nickname, introduction, image })
      })
    },
    updateProfile: function ({ commit, getters, state }, credentials) {
      axios({
        method: "PUT",
        //url: `${SERVER_URL}accounts/profile/`,
        //route: SERVER.ROUTES.accounts.myProfileDetail,
        url: SERVER.URL + SERVER.ROUTES.accounts.myProfileDetail,
        data: credentials,
        headers: getters.token,
      })
      .then((res) => {
        // 대상 포함 여부
        const nickname = res.data.nickname
        const introduction = res.data.introduction
        var image = ''
        if (res.data.image) {
          //image = 'http://127.0.0.1:8000' + res.data.image   
          image = SERVER.ROUTES.image + res.data.image         // drf
          //image = state.image // 스프링
        } else {
          image = state.image
        }
        commit("GET_MY_PROFILE", { nickname, introduction, image })        
      })
      .catch((err) => {console.log(err.response.data.error)})
    },
    // 마일리지 충전
    changeMileage: function ({ commit, getters}, changeMileage) {
      const credentials = {
        mileage: changeMileage
      }
      axios({
        method: "PUT",
        //url: URL + `/accounts/mileage/`,
        //route: SERVER.URL.accounts.mileageChange,
        url: SERVER.URL + SERVER.ROUTES.accounts.mileageChange,
        data: credentials,
        headers: getters.token,
      })
      .then((res) => {
        const mileage = res.data.mileage
        commit("CHANGE_MILEAGE", { mileage })
        // 충전일 때만 표시
        if (changeMileage > 0) {
        swal.fire ({
          icon: 'success',
          title: '마일리지 충전 성공',
          text: changeMileage + ' 마일리지가 충전 되었습니다.',
          scrollbarPadding: false
          }) 
        }  
      })
      .catch(() => {
        swal.fire ({
          icon: 'error',
          title: '마일리지 충전 실패',
          text: '잘못된 요청입니다.',
          scrollbarPadding: false
          })    
      })
    },
    // 도네이션 (상대쪽에 마일리지 들어감)
    donate: function ({getters}, contexts) {
      const credentials = {
        mileage: contexts.mileage
      }
      axios({
        method: "PUT",
        //url: `${SERVER_URL}accounts/donate/${contexts.id}/`,
        url: SERVER.URL + SERVER.ROUTES.accounts.donate + String(contexts.id) + '/',
        data: credentials,
        headers: getters.token,
      })
    },
    // 평가 포인트 갱신 (마일리지)
    updateArticlePoints: function ({getters}, contexts) {
      const credentials = {
        mileage: contexts.mileage
      }
      axios({
        method: "PUT",
        //url: `${SERVER_URL}movies/donate/${contexts.id}/`,
        //route: SERVER.ROUTES.movies.pointChange + String(contexts.id),
        url: SERVER.URL + SERVER.ROUTES.movies.pointChange + String(contexts.id)+ '/',
        data: credentials,
        headers: getters.token,
      })
    },
    // 이슈 사항
    // 프로필 이미지 갱신
    // updateProfileImage({commit}, nickname){
    //   const credentials = {
    //     id: this.state.userId,
    //     username: this.state.username,
    //     nickname: nickname
    //   }
    //   axios({
    //     method: 'POST',
    //     //url: `${SERVER_URL}accounts/${this.state.userId}/profile/`, 응? 이거 왜 작동한거임??
    //     route: SERVER.URL.ROUTES.
    //     data: credentials,
    //   })
    //   .then(() => {
    //     commit('UPDATE_PROFILE')
    //   })
    // },
    // logout
    logout({commit}) {
      localStorage.removeItem('jwt')
      commit('LOGOUT')
      router.push({name: 'Home'})
      window.location.reload();  // 로그아웃시 강제 새로고침 => 각종 버그 방지
      // this.$router.go();
      // this.$forceUpdate();
    },
    checkLogin({commit}, token) {
      if (token) {
        commit('LOGIN')
      }
    },
    // MOVIES ACTIONS
    getMovie({commit}, tokenId) {
      axios({
        method: 'GET',
        //url: URL + '/movies/${tokenId.id}/',
        url: SERVER.URL + SERVER.ROUTES.movies.movieDetail + String(tokenId.id) + '/',
        headers: tokenId.token,
      })
      .then(res => {
        commit('GET_MOVIE', res.data)
      })
      .catch(err => console.log(err))
    },
    getMovies({commit}, token) {
      axios({
        method: 'GET',
        // '/movies/'
        url: SERVER.URL + SERVER.ROUTES.movies.home,
        headers: token,
      })
      .then(res => {
        commit('GET_MOVIES', res.data)
      })
      .catch(err => console.log(err))
    },
    getMoviesRecommend({commit}, tokenId) {
      axios({
        method: 'GET',
        //url: URL + '/movies/${tokenId.id}/recommend/',
        url: SERVER.URL + '/movies/' + String(tokenId.id) + SERVER.ROUTES.movies.movieRecommend,
        headers: tokenId.token,
      })
      .then(res => {
        commit('GET_MOVIES_RECOMMEND', res.data)
      })
      .catch(err => console.log(err))
    },

  },
})
