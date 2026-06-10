<template>
  <div class="flex h-screen bg-[#0f172a] text-slate-200 overflow-hidden">
    <!-- Sidebar -->
    <aside class="w-64 bg-slate-900/50 backdrop-blur-xl border-r border-white/5 flex flex-col relative z-20">
      <div class="p-8">
        <div class="flex items-center space-x-3 mb-2">
          <div class="w-8 h-8 rounded-lg bg-gradient-to-br from-indigo-500 to-purple-600 flex items-center justify-center shadow-lg shadow-indigo-500/20">
            <span class="text-white font-bold text-lg">B</span>
          </div>
          <h1 class="text-xl font-bold text-white tracking-tight">校园图书</h1>
        </div>
        <p class="text-[10px] text-slate-500 uppercase tracking-widest font-semibold px-1">图书管理系统</p>
      </div>

      <nav class="flex-1 px-4 space-y-1">
        <router-link to="/books" 
          class="flex items-center space-x-3 px-4 py-3 rounded-xl transition-all duration-200 group hover:bg-white/5"
          active-class="bg-indigo-600/10 text-indigo-400 border-l-2 border-indigo-500 !rounded-l-none">
          <i class="text-xl group-hover:scale-110 transition-transform">📚</i>
          <span class="font-medium">图书资源库</span>
        </router-link>

        <router-link to="/my-borrows" 
          class="flex items-center space-x-3 px-4 py-3 rounded-xl transition-all duration-200 group hover:bg-white/5"
          active-class="bg-indigo-600/10 text-indigo-400 border-l-2 border-indigo-500 !rounded-l-none">
          <i class="text-xl group-hover:scale-110 transition-transform">📖</i>
          <span class="font-medium">我的借阅</span>
        </router-link>

        <router-link v-if="isAdmin" to="/users" 
          class="flex items-center space-x-3 px-4 py-3 rounded-xl transition-all duration-200 group hover:bg-white/5"
          active-class="bg-indigo-600/10 text-indigo-400 border-l-2 border-indigo-500 !rounded-l-none">
          <i class="text-xl group-hover:scale-110 transition-transform">👤</i>
          <span class="font-medium">系统用户</span>
        </router-link>
      </nav>

      <div class="p-4 border-t border-white/5 bg-slate-900/80">
        <div class="flex items-center space-x-3 px-4 py-3 mb-2">
          <div class="w-8 h-8 rounded-full bg-slate-800 border border-white/10 flex items-center justify-center text-xs font-bold text-indigo-400">
            {{ userInitial }}
          </div>
          <div class="flex-1 min-w-0">
            <p class="text-sm font-medium text-white truncate">{{ username }}</p>
            <p class="text-[10px] text-slate-500 uppercase">{{ isAdmin ? '管理员' : '学生' }}</p>
          </div>
        </div>
        <button @click="logout" 
          class="flex items-center space-x-3 w-full px-4 py-2.5 text-slate-400 hover:text-red-400 hover:bg-red-400/10 rounded-xl transition-all duration-200">
          <i class="text-lg">🚪</i>
          <span class="text-sm font-medium">安全退出</span>
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 relative overflow-y-scroll overflow-x-hidden scroll-stable">
      <!-- Decorative background elements -->
      <div class="fixed top-0 right-0 w-[500px] h-[500px] bg-indigo-600/5 rounded-full blur-[120px] pointer-events-none"></div>
      <div class="fixed bottom-0 left-64 w-[400px] h-[400px] bg-purple-600/5 rounded-full blur-[120px] pointer-events-none"></div>
      
      <div class="relative z-10 p-8 min-h-full flex flex-col">
        <transition name="page-fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </div>
    </main>
  </div>
</template>

<script setup>
import router from '../router'
import { computed } from 'vue'


const user = computed(() => JSON.parse(localStorage.getItem('user') || '{}'))
const username = computed(() => user.value.username || '未设置')
const isAdmin = computed(() => user.value.role === 'ADMIN')
const userInitial = computed(() => (username.value[0] || '?').toUpperCase())

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
.page-fade-enter-active,
.page-fade-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
.page-fade-enter {
  opacity: 0;
  transform: translateY(10px);
}
.page-fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* Custom Scrollbar */
::-webkit-scrollbar {
  width: 6px;
}
::-webkit-scrollbar-track {
  background: transparent;
}
::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 10px;
}
::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.1);
}

.scroll-stable {
  scrollbar-gutter: stable;
}
</style>
