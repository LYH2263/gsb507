import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../components/Layout.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/',
    component: Layout,
    redirect: '/books',
    children: [
      {
        path: 'books',
        component: () => import('../views/BookManage.vue')
      },
      {
        path: 'users',
        component: () => import('../views/UserManage.vue')
      },
      {
        path: 'my-borrows',
        component: () => import('../views/BorrowHistory.vue')
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: '/',
  routes
})

router.beforeEach((to, from, next) => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  if (to.path !== '/login' && to.path !== '/register' && !user.username) {
    next('/login')
  } else {
    next()
  }
})

export default router
