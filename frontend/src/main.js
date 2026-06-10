import { createApp } from 'vue'
import 'virtual:windi.css'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import 'virtual:windi.css'
import './style.css'

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
