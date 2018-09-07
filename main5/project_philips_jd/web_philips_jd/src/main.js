// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css'
import './mock';
import globalEvent from './assets/js/globalEvent'

Vue.use(ElementUI,{size: 'small'});

Vue.prototype.$globalEvent = globalEvent;

const API_ROOT = process.env.NODE_ENV === 'development'?"http://10.1.7.166:9090":"http://www.baidu.com";
//const API_ROOT = process.env.NODE_ENV === 'development'?"http://127.0.0.1:8080":"http://www.baidu.com";

const BASE_API_ROOT = axios.create({
  baseURL: API_ROOT
});

Vue.prototype.$http = BASE_API_ROOT;


router.beforeEach((to,form,next) => {
	if(to.meta.title){
		document.title = to.meta.title
	}
	next();
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
  // render: h => h(App)
});
