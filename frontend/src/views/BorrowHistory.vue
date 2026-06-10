<template>
  <div>
    <div class="space-y-8">
      
      <div class="flex flex-col md:flex-row md:items-end justify-between gap-4">
        <div>
          <h2 class="text-3xl font-bold text-white tracking-tight">{{ isAdmin ? '借阅流水概览' : '我的个人借阅' }}</h2>
          <p class="text-slate-400 mt-1">记录每一次智慧的流通与流转</p>
        </div>
        <div class="flex items-center space-x-4">
            <button v-if="!isAdmin" @click="openProfileModal" class="px-4 py-2 bg-slate-800 hover:bg-slate-700 text-slate-300 rounded-xl text-xs font-bold uppercase tracking-wider transition-colors border border-white/10">
                修改个人信息
            </button>
            <div v-if="!isAdmin" class="bg-indigo-500/10 border border-indigo-500/20 px-4 py-2 rounded-xl flex items-center space-x-3 backdrop-blur-sm">
            <div class="flex flex-col">
                <span class="text-[10px] text-slate-500 font-bold uppercase tracking-wider">借出总量</span>
                <span class="text-white font-black text-xl leading-none">{{ activeBorrowsCount }}</span>
            </div>
            <div class="w-px h-8 bg-white/10"></div>
            <div class="text-indigo-400 text-xs font-medium">待归还</div>
            </div>
        </div>
      </div>

      <div class="bg-slate-900/50 border border-white/5 rounded-3xl shadow-2xl overflow-hidden backdrop-blur-md">
        <table class="w-full text-left">
          <thead>
            <tr class="bg-white/5 border-b border-white/10 uppercase tracking-widest text-[10px] font-black text-slate-500">
              <th class="px-8 py-5">文献摘要</th>
              <th v-if="isAdmin" class="px-8 py-5">借阅人</th>
              <th class="px-8 py-5">当前状态</th>
              <th class="px-8 py-5">流转周期</th>
              <th class="px-8 py-5 text-right">管控命令</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-white/5">
            <tr v-for="record in records" :key="record.id" class="hover:bg-white/5 transition-all group">
              <td class="px-8 py-5">
                <div class="flex items-center space-x-4">
                  <div class="w-12 h-12 rounded-xl bg-slate-800 border border-white/10 flex items-center justify-center text-lg shadow-2xl">
                    {{ record.status === 'BORROWED' ? '📍' : '✅' }}
                  </div>
                  <div>
                    <div class="font-bold text-slate-100 group-hover:text-white transition-colors">{{ record.bookTitle }}</div>
                    <div class="text-[10px] text-slate-500 uppercase tracking-tighter">流水号: {{ record.id }}</div>
                  </div>
                </div>
              </td>
              <td v-if="isAdmin" class="px-8 py-5">
                <div class="flex items-center space-x-2">
                    <div class="w-6 h-6 rounded-full bg-indigo-500/20 border border-indigo-500/30 flex items-center justify-center text-[10px] font-black text-indigo-400">
                        {{ record.username ? record.username[0].toUpperCase() : '?' }}
                    </div>
                    <span class="text-sm font-bold text-slate-300">{{ record.username }}</span>
                </div>
              </td>
              <td class="px-8 py-5">
                <span v-if="record.status === 'BORROWED'" class="px-3 py-1 bg-amber-500/10 text-amber-500 border border-amber-500/20 rounded-lg text-[9px] font-black tracking-widest uppercase">
                  持有中
                </span>
                <span v-else class="px-3 py-1 bg-slate-500/10 text-slate-400 border border-slate-500/10 rounded-lg text-[9px] font-black tracking-widest uppercase">
                  已归还
                </span>
              </td>
              <td class="px-8 py-5">
                <div class="text-xs text-slate-400 flex flex-col space-y-1">
                  <span class="flex items-center"><i class="text-[8px] mr-1">借出:</i> {{ formatDate(record.borrowTime) }}</span>
                  <span v-if="record.returnTime" class="flex items-center text-green-500/80"><i class="text-[8px] mr-1 font-bold">归还:</i> {{ formatDate(record.returnTime) }}</span>
                </div>
              </td>
              <td class="px-8 py-5 text-right">
                <button v-if="record.status === 'BORROWED'" @click="returnBook(record.id)" 
                  class="px-5 py-2 bg-indigo-600 hover:bg-indigo-500 text-white text-[11px] font-black uppercase tracking-widest rounded-xl shadow-lg shadow-indigo-600/20 transition-all active:scale-90">
                  一键归还
                </button>
                <span v-else class="text-[10px] font-bold text-slate-600 uppercase italic">已归档</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <div v-if="records.length === 0" class="p-20 text-center space-y-4">
        <div class="text-6xl filter grayscale opacity-30">📪</div>
        <div class="text-slate-500 font-medium tracking-wide">目前暂无流水数据</div>
      </div>
    </div>
    <!-- Fixed Components -->
    <div>
      <CustomAlert ref="alertRef" title="结果通知" message="操作已处理" />
      
      <!-- Profile Modal -->
      <transition name="modal-bounce">
        <div v-if="showProfileModal" class="fixed inset-0 flex items-center justify-center z-[100] p-4 text-left">
          <div class="absolute inset-0 bg-slate-950/70 backdrop-blur-lg" @click="showProfileModal = false"></div>
          <div class="relative w-full max-w-sm bg-slate-900 border border-white/10 rounded-[2rem] p-8 shadow-2xl overflow-hidden text-left">
            <h3 class="text-2xl font-black text-white mb-6 tracking-tight">修改个人信息</h3>
            <form @submit.prevent="updateProfile" class="space-y-6">
               <div class="space-y-2">
                <label class="text-[10px] font-black text-slate-500 uppercase tracking-widest ml-1">新密码</label>
                <input v-model="profileForm.password" type="password" placeholder="若不修改请留空" 
                  class="w-full bg-white/5 border border-white/10 rounded-2xl px-5 py-3.5 text-white placeholder-slate-600 focus:outline-none focus:ring-2 focus:ring-indigo-500 transition" />
              </div>
              <div class="flex space-x-3 pt-4">
                <button type="button" @click="showProfileModal = false" class="flex-1 px-6 py-4 text-slate-400 font-bold hover:text-white transition uppercase text-[10px] tracking-widest">取消</button>
                <button type="submit" class="flex-1 px-6 py-4 bg-indigo-600 hover:bg-indigo-500 text-white font-black rounded-2xl shadow-xl shadow-indigo-600/20 active:scale-95 transition uppercase text-[10px] tracking-widest">确认修改</button>
              </div>
            </form>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, reactive } from 'vue'
import api from '../utils/api'
import CustomAlert from '../components/CustomAlert.vue'

const records = ref([])
const alertRef = ref(null)

const isAdmin = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.role === 'ADMIN'
})

const activeBorrowsCount = computed(() => {
  return records.value.filter(r => r.status === 'BORROWED').length
})

const formatDate = (dateStr) => {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
}

const fetchRecords = async () => {
  try {
    const endpoint = isAdmin.value ? '/borrows/all' : '/borrows/my'
    const res = await api.get(endpoint)
    records.value = res.data
  } catch (e) {
    console.error(e)
  }
}

const returnBook = async (id) => {
  try {
    await api.put(`/borrows/return/${id}`)
    fetchRecords()
    alertRef.value.show("归还圆满", "您的诚信记录已同步至系统")
  } catch (e) {
    console.error(e)
    if (alertRef.value) {
      alertRef.value.show("异常中断", e.message || "服务器忙，请稍候重试")
    }
  }
}

const showProfileModal = ref(false)
const profileForm = reactive({ password: '' })

const openProfileModal = () => {
    profileForm.password = ''
    showProfileModal.value = true
}

const updateProfile = async () => {
    try {
        await api.put('/users/profile', profileForm)
        showProfileModal.value = false
        alertRef.value.show("更新成功", "个人信息已修改，下次登录生效")
    } catch (e) {
        alertRef.value.show("更新失败", e.message || "请稍后重试")
    }
}

onMounted(fetchRecords)
</script>


<style scoped>
.modal-bounce-enter-active {
  animation: bounce-in 0.4s;
}
.modal-bounce-leave-active {
  animation: bounce-in 0.3s reverse;
}
@keyframes bounce-in {
  0% { transform: scale(0.9); opacity: 0; }
  70% { transform: scale(1.05); }
  100% { transform: scale(1); opacity: 1; }
}
</style>
