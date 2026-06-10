<template>
  <div class="min-h-screen flex bg-[#0f172a] text-slate-200 selection:bg-indigo-500/30">
    <CustomAlert ref="alertRef" title="注册提示" />
    
    <!-- Left Side: Introduction (Same as Login) -->
    <div class="hidden lg:flex lg:w-3/5 relative overflow-hidden items-center justify-center p-12">
      <div class="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-indigo-600/20 rounded-full blur-[120px] animate-pulse"></div>
      <div class="absolute bottom-[-10%] right-[-10%] w-[50%] h-[50%] bg-purple-600/20 rounded-full blur-[120px] animate-pulse" style="animation-delay: 2s;"></div>
      
      <div class="relative z-10 max-w-2xl">
        <h1 class="text-6xl font-bold tracking-tight text-white mb-6 leading-tight">
          加入我们的<br/>
          <span class="bg-clip-text text-transparent bg-gradient-to-r from-indigo-400 via-purple-400 to-pink-400">图书社区</span>
        </h1>
        <p class="text-xl text-slate-400 leading-relaxed mb-10">
          只需几秒钟即可创建一个账户，开启您的智慧借阅之旅。
        </p>
      </div>
    </div>

    <!-- Right Side: Register Form -->
    <div class="w-full lg:w-2/5 flex items-center justify-center p-8 bg-slate-900/50 backdrop-blur-xl border-l border-white/5">
      <div class="w-full max-w-md space-y-8">
        <div class="text-center lg:text-left">
          <h2 class="text-3xl font-bold text-white">创建新账户</h2>
          <p class="mt-2 text-slate-400">请填写以下信息以完成注册</p>
        </div>

        <form @submit.prevent="handleRegister" class="mt-8 space-y-6">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-slate-300 mb-1.5">用户名</label>
              <div class="relative group">
                <span class="absolute inset-y-0 left-0 pl-3.5 flex items-center text-slate-500 transition-colors group-focus-within:text-indigo-400">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                </span>
                <input v-model="form.username" type="text" required minlength="4" maxlength="20"
                  class="block w-full pl-11 pr-4 py-3 bg-slate-800/50 border border-slate-700 rounded-xl text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/50 focus:border-indigo-500 transition-all"
                  placeholder="4-20位用户名" />
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
                <input v-model="form.password" type="password" required minlength="6"
                  class="block w-full pl-11 pr-4 py-3 bg-slate-800/50 border border-slate-700 rounded-xl text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/50 focus:border-indigo-500 transition-all"
                  placeholder="不少于6位密码" />
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-slate-300 mb-1.5">确认密码</label>
              <div class="relative group">
                <span class="absolute inset-y-0 left-0 pl-3.5 flex items-center text-slate-500 transition-colors group-focus-within:text-indigo-400">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                  </svg>
                </span>
                <input v-model="form.confirmPassword" type="password" required
                  class="block w-full pl-11 pr-4 py-3 bg-slate-800/50 border border-slate-700 rounded-xl text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/50 focus:border-indigo-500 transition-all"
                  placeholder="请再次输入密码" />
              </div>
            </div>
          </div>

          <button type="submit" 
            class="w-full flex justify-center py-3.5 px-4 rounded-xl bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-500 hover:to-purple-500 text-white font-semibold shadow-lg shadow-indigo-600/20 active:scale-[0.98] transition-all duration-200">
            创建账户
          </button>
        </form>

        <div class="text-center text-sm text-slate-500 mt-10">
          已有账号？ <router-link to="/login" class="text-indigo-400 hover:text-indigo-300 font-medium transition-colors">点此登录</router-link>
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
  password: '',
  confirmPassword: ''
})

const handleRegister = async () => {
  if (form.password !== form.confirmPassword) {
    alertRef.value.show("验证失败", "两次输入的密码不一致")
    return
  }
  
  try {
    await api.post('/users/register', {
      username: form.username,
      password: form.password
    })
    alertRef.value.show("注册成功", "请使用新账号登录")
    setTimeout(() => {
      router.push('/login')
    }, 1500)
  } catch (e) {
    alertRef.value.show("注册失败", e.response?.data?.data || e.message || "注册遇到问题，请稍后再试")
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
