import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//vuetify install CDN(https://unnamed-it.tistory.com/70)
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
Vue.use(Vuetify);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify: new Vuetify(), //vuetify install CDN >> index.html도 체크
  render: h => h(App)
}).$mount('#app')
