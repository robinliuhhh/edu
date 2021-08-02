import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Index from '../components/Index.vue'
import Course from '../components/Course.vue'
import Setting from '../components/Setting.vue'

Vue.use(VueRouter)
Vue.use(Vuex)

const videoDetail = () => import('../components/videoDetail.vue');

  const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
    meta: {
      title: '首页'
    }
  },
  {
    path: '/Course',
    name: 'Course',
    component: Course,
    meta: {
      title: '课程简介页'
    }
  },
  {
    path: '/videoDetail',
    name: 'videoDetail',
    component: videoDetail,
    meta: {
      title: '视频播放页'
    }
  },
  {
    path: '/setting',
    name: 'Setting',
    component: Setting,
    meta: {
      title: '设置页'
    }
  }
  //{
    // path: '/about',
    // name: 'About',
    // // route level code-splitting
    // // this generates a separate chunk (about.[hash].js) for this route
    // // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  //}
]

const router = new VueRouter({
  routes
})

export default router

