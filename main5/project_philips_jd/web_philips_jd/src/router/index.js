import Vue from 'vue'
import Router from 'vue-router'
import Home from '../Home'
import NotFound from '../404'
import Login from '../Login'
import childrenRouter from './children'

Vue.use(Router);

export default new Router({
  mode: 'history',
  // base: '/dist',
  routes: [
    {
      path: '/',
      name: 'JDRoot',
      component: Home,
    },
    {
      path: '/Login',
      component: Login,
      name: 'Login',
      meta: {title: '登录页'}
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      children: childrenRouter
    },
    {
      path: '/404',
      component: NotFound,
      name: 'NotFound',
      hidden: true,
      meta: {
        title: '找不到页面'
      }
    },
    {
      path: '*',
      name: '*',
      hidden: true,
      redirect: {path: '/404'}
    }
  ]
})
