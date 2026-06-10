<template>
  <div>
    <div class="space-y-8">
      
      <div class="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h2 class="text-3xl font-bold text-white tracking-tight">核心用户矩阵</h2>
          <p class="text-slate-400 mt-1">管理系统访问权级与核心账户资产</p>
        </div>
        <button @click="openModal()" 
          class="bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-2.5 rounded-xl shadow-lg shadow-indigo-600/20 transition-all active:scale-95 flex items-center justify-center space-x-2">
          <span class="text-xl">+</span>
          <span class="font-semibold">接入新用户</span>
        </button>
      </div>

      <!-- User Grid/Table -->
      <div class="bg-slate-900/50 border border-white/5 rounded-3xl shadow-2xl overflow-hidden backdrop-blur-md min-h-[500px]">
        <div class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="bg-white/5 border-b border-white/10 uppercase tracking-widest text-[10px] font-black text-slate-500">
                <th class="px-8 py-5">核心账户</th>
                <th class="px-8 py-5">权级标识</th>
                <th class="px-8 py-5">入驻时间</th>
                <th class="px-8 py-5 text-right">管理策略</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-white/5">
              <tr v-for="user in users" :key="user.id" class="hover:bg-white/5 transition-all group">
                <td class="px-8 py-5">
                  <div class="flex items-center space-x-4">
                    <div class="w-10 h-10 rounded-2xl bg-gradient-to-br from-indigo-500/20 to-purple-600/20 border border-indigo-500/30 flex items-center justify-center text-sm font-black text-indigo-400">
                      {{ user.username[0].toUpperCase() }}
                    </div>
                    <div>
                      <div class="font-bold text-slate-100 uppercase tracking-wide group-hover:text-white transition-colors">{{ user.username }}</div>
                      <div class="text-[10px] text-slate-500">内部ID: {{ user.id }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-8 py-5">
                  <span class="px-3 py-1 rounded-lg text-[9px] font-black tracking-[0.2em] border"
                    :class="user.role === 'ADMIN' ? 'bg-purple-500/10 text-purple-400 border-purple-500/20' : 'bg-green-500/10 text-green-400 border-green-500/20'">
                    {{ user.role === 'ADMIN' ? '核心管理员' : '普通学生' }}
                  </span>
                </td>
                <td class="px-8 py-5">
                  <span class="text-sm font-mono text-slate-400">{{ new Date(user.createTime).toLocaleDateString() }}</span>
                </td>
                <td class="px-8 py-5 text-right space-x-1">
                  <button @click="openModal(user)" class="p-2 text-slate-500 hover:text-indigo-400 transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                    </svg>
                  </button>
                  <button @click="deleteUser(user.id)" class="p-2 text-slate-500 hover:text-red-400 transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Modern User Modal -->
      <transition name="modal-bounce">
        <div v-if="showModal" class="fixed inset-0 flex items-center justify-center z-[100] p-4">
          <div class="absolute inset-0 bg-slate-950/70 backdrop-blur-lg" @click="showModal = false"></div>
          <div class="relative w-full max-w-sm bg-slate-900 border border-white/10 rounded-[2rem] p-8 shadow-2xl overflow-hidden">
            <div class="absolute top-0 right-0 p-8 transform translate-x-1/2 -translate-y-1/2 bg-indigo-500/10 rounded-full w-48 h-48 blur-[40px]"></div>
            
            <h3 class="text-2xl font-black text-white mb-8 tracking-tight">
              {{ isEditing ? '修改账户' : '权限分发' }}
            </h3>
            
            <form @submit.prevent="saveUser" class="space-y-6 relative z-10">
              <div class="space-y-2">
                <label class="text-[10px] font-black text-slate-500 uppercase tracking-widest ml-1">身份凭证</label>
                <input v-model="form.username" type="text" placeholder="账户名" 
                  class="w-full bg-white/5 border border-white/10 rounded-2xl px-5 py-3.5 text-white placeholder-slate-600 focus:outline-none focus:ring-2 focus:ring-indigo-500 transition" 
                  required :disabled="isEditing" />
              </div>
              
              <div class="space-y-2">
                <label class="text-[10px] font-black text-slate-500 uppercase tracking-widest ml-1">访问口令 <span v-if="isEditing" class="text-slate-600 lowercase font-normal">(若不修改请留空)</span></label>
                <input v-model="form.password" type="password" placeholder="••••••••" 
                  class="w-full bg-white/5 border border-white/10 rounded-2xl px-5 py-3.5 text-white placeholder-slate-600 focus:outline-none focus:ring-2 focus:ring-indigo-500 transition" 
                  :required="!isEditing" />
              </div>

              <div class="space-y-2">
                <label class="text-[10px] font-black text-slate-500 uppercase tracking-widest ml-1">矩阵角色</label>
                <select v-model="form.role" class="w-full bg-slate-800 border border-white/10 rounded-2xl px-5 py-3.5 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500 appearance-none cursor-pointer">
                  <option value="USER">普通学生 (STUDENT)</option>
                  <option value="ADMIN">核心管理员 (ADMIN)</option>
                </select>
              </div>

              <div class="flex space-x-3 pt-4">
                <button type="button" @click="showModal = false" class="flex-1 px-6 py-4 text-slate-400 font-bold hover:text-white transition uppercase text-[10px] tracking-widest">
                  取消
                </button>
                <button type="submit" class="flex-1 px-6 py-4 bg-indigo-600 hover:bg-indigo-500 text-white font-black rounded-2xl shadow-xl shadow-indigo-600/20 active:scale-95 transition uppercase text-[10px] tracking-widest">
                  确认生效
                </button>
              </div>
            </form>
          </div>
        </div>
      </transition>
    </div>
    <!-- Fixed Components -->
    <div>
      <CustomAlert ref="alertRef" title="系统通告" message="指令已执行" />
      <CustomConfirm ref="confirmRef" message="此操作将永久抹除该用户信息，确认继续？" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import api from '../utils/api'
import CustomAlert from '../components/CustomAlert.vue'
import CustomConfirm from '../components/CustomConfirm.vue'

const users = ref([])
const showModal = ref(false)
const isEditing = ref(false)
const alertRef = ref(null)
const confirmRef = ref(null)

const form = reactive({
  id: null,
  username: '',
  password: '',
  role: 'USER'
})

const fetchUsers = async () => {
  try {
    const res = await api.get('/users')
    users.value = res.data
  } catch (e) {
    console.error(e)
  }
}

const openModal = (user = null) => {
  isEditing.value = !!user
  if (user) {
    form.id = user.id
    form.username = user.username
    form.password = ''
    form.role = user.role
  } else {
    form.id = null
    form.username = ''
    form.password = ''
    form.role = 'USER'
  }
  showModal.value = true
}

const saveUser = async () => {
  try {
    if (isEditing.value) {
      await api.put('/users', form)
    } else {
      await api.post('/users', form)
    }
    showModal.value = false
    fetchUsers()
    alertRef.value.show("矩阵已同步", "用户信息已实时生效")
  } catch (e) {
    alertRef.value.show("权限异常", "请核实管理员最高权限")
  }
}

const deleteUser = async (id) => {
  const confirmed = await confirmRef.value.show()
  if (confirmed) {
    try {
      await api.delete(`/users/${id}`)
      fetchUsers()
      alertRef.value.show("擦除成功", "该账户已从数据库中剥离")
    } catch (e) {
      const msg = e.message || "服务器底层异常";
      alertRef.value.show("执行失败", msg)
    }
  }
}

onMounted(fetchUsers)
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
