import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
Vue.use(Vuetify);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  //vuetify install CDN >> index.html도 체크
  vuetify: new Vuetify(), 
  render: h => h(App)
}).$mount('#app')
