<template>
  <div class="min-h-screen flex bg-[#0f172a] text-slate-200 selection:bg-indigo-500/30">
    <CustomAlert ref="alertRef" title="登录错误" message="用户名或密码错误" />
    
    <!-- Left Side: Introduction -->
    <div class="hidden lg:flex lg:w-3/5 relative overflow-hidden items-center justify-center p-12">
      <!-- Animated Background elements -->
      <div class="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-indigo-600/20 rounded-full blur-[120px] animate-pulse"></div>
      <div class="absolute bottom-[-10%] right-[-10%] w-[50%] h-[50%] bg-purple-600/20 rounded-full blur-[120px] animate-pulse" style="animation-delay: 2s;"></div>
      
      <div class="relative z-10 max-w-2xl">
        <div class="inline-flex items-center space-x-2 px-3 py-1 rounded-full bg-indigo-500/10 border border-indigo-500/20 text-indigo-400 text-sm font-medium mb-6">
          <span class="relative flex h-2 w-2">
            <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-indigo-400 opacity-75"></span>
            <span class="relative inline-flex rounded-full h-2 w-2 bg-indigo-500"></span>
          </span>
          <span>轻量级框架结业考核项目</span>
        </div>
        <h1 class="text-6xl font-bold tracking-tight text-white mb-6 leading-tight">
          现代轻量级<br/>
          <span class="bg-clip-text text-transparent bg-gradient-to-r from-indigo-400 via-purple-400 to-pink-400">图书管理系统</span>
        </h1>
        <p class="text-xl text-slate-400 leading-relaxed mb-10">
          基于 Spring Boot 2.7 + MyBatis + Spring Security 构建的高性能后端，结合 Vue 2.7 + WindiCSS 打造的极致交互体验。
        </p>
        
        <div class="grid grid-cols-2 gap-6">
          <div class="p-6 rounded-2xl bg-white/5 border border-white/10 backdrop-blur-sm">
            <div class="text-2xl mb-2">🚀</div>
            <div class="font-semibold text-white">全栈容器化</div>
            <div class="text-sm text-slate-500">一键部署，生产级交付标准</div>
          </div>
          <div class="p-6 rounded-2xl bg-white/5 border border-white/10 backdrop-blur-sm">
            <div class="text-2xl mb-2">🛡️</div>
            <div class="font-semibold text-white">RBAC 权限</div>
            <div class="text-sm text-slate-500">全流程安全拦截与角色授权</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Right Side: Login Form -->
    <div class="w-full lg:w-2/5 flex items-center justify-center p-8 bg-slate-900/50 backdrop-blur-xl border-l border-white/5">
      <div class="w-full max-w-md space-y-8">
        <div class="text-center lg:text-left">
          <h2 class="text-3xl font-bold text-white">欢迎回来</h2>
          <p class="mt-2 text-slate-400">请输入您的凭据以访问控制台</p>
        </div>

        <form @submit.prevent="handleLogin" class="mt-8 space-y-6">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-slate-300 mb-1.5">用户名</label>
              <div class="relative group">
                <span class="absolute inset-y-0 left-0 pl-3.5 flex items-center text-slate-500 transition-colors group-focus-within:text-indigo-400">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                </span>
                <input v-model="form.username" type="text" required
                  class="block w-full pl-11 pr-4 py-3 bg-slate-800/50 border border-slate-700 rounded-xl text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/50 focus:border-indigo-500 transition-all"
                  placeholder="请输入用户名" />
              </div>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-slate-300 mb-1.5">密码</label>
              <div class="relative group">
                <span class="absolute inset-y-0 left-0 pl-3.5 flex items-center text-slate-500 transition-colors group-focus-within:text-indigo-400">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                  </svg>
                </span>
                <input v-model="form.password" type="password" required
                  class="block w-full pl-11 pr-4 py-3 bg-slate-800/50 border border-slate-700 rounded-xl text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/50 focus:border-indigo-500 transition-all"
                  placeholder="请输入密码" />
              </div>
            </div>
          </div>


          <button type="submit" 
            class="w-full flex justify-center py-3.5 px-4 rounded-xl bg-indigo-600 hover:bg-indigo-500 text-white font-semibold shadow-lg shadow-indigo-600/20 active:scale-[0.98] transition-all duration-200">
            立即进入系统
          </button>
        </form>

        <div class="text-center text-sm text-slate-500 mt-10">
          还没有账号？ <router-link to="/register" class="text-indigo-400 hover:text-indigo-300 font-medium transition-colors">立即注册</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import router from '../router'
import api from '../utils/api'
import CustomAlert from '../components/CustomAlert.vue'

const alertRef = ref(null)
const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  try {
    const formData = new FormData()
    formData.append('username', form.username)
    formData.append('password', form.password)
    const res = await api.post('/login', formData)
    // res is the Result object from api.js interceptor
    localStorage.setItem('user', JSON.stringify({ username: form.username, role: res.data }))
    router.push('/')
  } catch (e) {
    alertRef.value.show("登录失败", e.message || "用户名或密码错误")
  }
}
</script>

<style scoped>
@keyframes pulse {
  0%, 100% { opacity: 0.15; }
  50% { opacity: 0.25; }
}
.animate-pulse {
  animation: pulse 8s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>
