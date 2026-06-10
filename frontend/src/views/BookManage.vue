<template>
  <div>
    <div class="space-y-8">
      
      <!-- Page Header -->
      <div class="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h2 class="text-3xl font-bold text-white tracking-tight">{{ isAdmin ? '图书资源中心 (管理员)' : '全校图书概览' }}</h2>
          <p class="text-slate-400 mt-1">探索、借阅并开启您的智慧之旅</p>
        </div>
        <div class="flex flex-col sm:flex-row gap-3">
          <div class="relative group">
            <span class="absolute inset-y-0 left-0 pl-3.5 flex items-center text-slate-500 transition-colors group-focus-within:text-indigo-400">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </span>
            <input v-model="searchQuery" @input="debounceSearch" type="text" 
              class="w-full sm:w-64 pl-10 pr-4 py-2.5 bg-slate-800/50 border border-slate-700 rounded-xl text-sm text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/50 focus:border-indigo-500 transition-all"
              placeholder="搜索书名或作者..." />
          </div>
          <button v-if="isAdmin" @click="openModal()" 
            class="bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-2.5 rounded-xl shadow-lg shadow-indigo-600/20 transition-all active:scale-95 flex items-center justify-center space-x-2">
            <span class="text-xl">+</span>
            <span class="font-semibold">新增图书资源</span>
          </button>
        </div>
      </div>

      <!-- Stats or Info Cards (Optional, adds premium feel) -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div class="p-6 rounded-2xl bg-white/5 border border-white/10 backdrop-blur-sm">
          <div class="flex items-center justify-between mb-4">
            <div class="p-2 rounded-lg bg-indigo-500/10 text-indigo-400">📚</div>
            <span class="text-xs font-bold text-slate-500 uppercase tracking-wider">总量</span>
          </div>
          <div class="text-2xl font-bold text-white">{{ books.length }}</div>
          <div class="text-xs text-slate-500 mt-1">馆藏图书总量</div>
        </div>
      </div>

      <!-- Data Table Card -->
      <div class="bg-slate-900/50 border border-white/5 rounded-2xl shadow-xl overflow-hidden backdrop-blur-md min-h-[500px]">
        <div class="overflow-x-auto">
          <table class="w-full text-left border-collapse">
            <thead>
              <tr class="border-b border-white/10 bg-white/5">
                <th class="px-6 py-4 text-xs font-bold text-slate-400 uppercase tracking-widest">书名信息</th>
                <th class="px-6 py-4 text-xs font-bold text-slate-400 uppercase tracking-widest">作者</th>
                <th class="px-6 py-4 text-xs font-bold text-slate-400 uppercase tracking-widest">建议价格</th>
                <th class="px-6 py-4 text-right text-xs font-bold text-slate-400 uppercase tracking-widest">操作空间</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-white/5">
              <tr v-for="book in books" :key="book.id" class="hover:bg-white/5 transition-colors group">
                <td class="px-6 py-4">
                  <div class="flex items-center space-x-3">
                    <div class="w-10 h-10 rounded-lg bg-slate-800 border border-white/5 flex items-center justify-center text-lg shadow-inner">📖</div>
                    <div>
                      <div class="font-bold text-white tracking-wide">{{ book.title }}</div>
                      <div class="text-xs text-slate-500">编号: #{{ book.id }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4 text-sm text-slate-300 font-medium">{{ book.author }}</td>
                <td class="px-6 py-4">
                  <span class="text-indigo-400 font-bold font-mono">¥{{ book.price.toFixed(2) }}</span>
                </td>
                <td class="px-6 py-4 text-right">
                  <div class="flex items-center justify-end space-x-2">
                    <button @click="borrowBook(book.id)" 
                      class="px-4 py-1.5 rounded-lg bg-green-500/10 text-green-400 hover:bg-green-500/20 text-xs font-bold transition-all border border-green-500/20">
                      一键借阅
                    </button>
                    <template v-if="isAdmin">
                      <button @click="openModal(book)" class="p-2 text-slate-400 hover:text-white transition-colors">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                        </svg>
                      </button>
                      <button @click="deleteBook(book.id)" class="p-2 text-slate-400 hover:text-red-400 transition-colors">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                        </svg>
                      </button>
                    </template>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-if="books.length === 0" class="p-12 text-center">
          <div class="text-4xl mb-4">💨</div>
          <div class="text-slate-500">资源库目前为空</div>
        </div>
      </div>

      <!-- Modern Modal -->
      <transition name="modal-fade">
        <div v-if="showModal" class="fixed inset-0 flex items-center justify-center z-[100] p-4">
          <div class="absolute inset-0 bg-slate-950/60 backdrop-blur-md" @click="showModal = false"></div>
          <div class="relative w-full max-w-md bg-slate-900 border border-white/10 rounded-3xl p-8 shadow-2xl transition-all duration-300">
            <h3 class="text-2xl font-bold text-white mb-6 flex items-center space-x-3">
              <span class="p-2 rounded-xl bg-indigo-500/20 text-indigo-400">✨</span>
              <span>{{ isEditing ? '编辑资源' : '录入新书' }}</span>
            </h3>
            <form @submit.prevent="saveBook" class="space-y-5">
              <div class="space-y-1.5">
                <label class="text-xs font-bold text-slate-500 uppercase ml-1">图书名称</label>
                <input v-model="form.title" placeholder="请输入完整书名" 
                  class="w-full bg-slate-800/50 border border-white/5 rounded-xl px-4 py-3 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition" 
                  required />
              </div>
              <div class="space-y-1.5">
                <label class="text-xs font-bold text-slate-500 uppercase ml-1">作者</label>
                <input v-model="form.author" placeholder="原著/编译作者" 
                  class="w-full bg-slate-800/50 border border-white/5 rounded-xl px-4 py-3 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition" 
                  required />
              </div>
              <div class="space-y-1.5">
                <label class="text-xs font-bold text-slate-500 uppercase ml-1">价格</label>
                <div class="relative">
                  <span class="absolute left-4 top-3.5 text-slate-500 font-mono">¥</span>
                  <input v-model="form.price" type="number" step="0.01" placeholder="0.00" 
                    class="w-full bg-slate-800/50 border border-white/5 rounded-xl pl-8 pr-4 py-3 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition" 
                    required />
                </div>
              </div>
              
              <div class="flex space-x-3 pt-4">
                <button type="button" @click="showModal = false" 
                  class="flex-1 px-6 py-3 bg-slate-800 hover:bg-slate-700 text-slate-300 font-bold rounded-xl transition">
                  返回
                </button>
                <button type="submit" 
                  class="flex-1 px-6 py-3 bg-indigo-600 hover:bg-indigo-500 text-white font-bold rounded-xl shadow-lg shadow-indigo-600/20 transition active:scale-95">
                  立即提交
                </button>
              </div>
            </form>
          </div>
        </div>
      </transition>

    </div>
    <!-- Fixed Components -->
    <div>
      <CustomAlert ref="alertRef" title="操作提示" message="操作成功" />
      <CustomConfirm ref="confirmRef" message="确定要永久删除这本书吗？" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import api from '../utils/api'
import CustomAlert from '../components/CustomAlert.vue'
import CustomConfirm from '../components/CustomConfirm.vue'

const books = ref([])
const showModal = ref(false)
const alertRef = ref(null)
const confirmRef = ref(null)
const isEditing = ref(false)
const searchQuery = ref('')
let searchTimer = null
const form = reactive({ id: null, title: '', author: '', price: 0 })

const isAdmin = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.role === 'ADMIN'
})

const fetchBooks = async () => {
  try {
    const res = await api.get('/books', {
      params: { 
        page: 1, 
        size: 100, // 为演示搜索效果，此处适当加大 size 或后续通过分页联动实现
        query: searchQuery.value 
      }
    })
    books.value = res.data.list
  } catch (e) {
    console.error(e)
  }
}

const debounceSearch = () => {
  clearTimeout(searchTimer)
  searchTimer = setTimeout(() => {
    fetchBooks()
  }, 300)
}

const openModal = (book = null) => {
  isEditing.value = !!book
  if (book) {
    Object.assign(form, book)
  } else {
    Object.assign(form, { id: null, title: '', author: '', price: 0 })
  }
  showModal.value = true
}

const borrowBook = async (bookId) => {
  try {
    const res = await api.post(`/borrows/${bookId}`)
    alertRef.value.show("成功借阅", "书籍已成功申请，请前往'我的借阅'查看")
  } catch (e) {
    alertRef.value.show("无法借阅", e.message || "您可能已经拥有此书的待归还记录")
  }
}

const saveBook = async () => {
  try {
    if (isEditing.value) {
      await api.put('/books', form)
    } else {
      await api.post('/books', form)
    }
    showModal.value = false
    fetchBooks()
    alertRef.value.show("同步成功", "资源库已更新")
  } catch (e) {
    alertRef.value.show("提交异常", e.message || "请检查网络或数据格式")
  }
}

const deleteBook = async (id) => {
  const confirmed = await confirmRef.value.show()
  if (confirmed) {
    try {
      await api.delete(`/books/${id}`)
      fetchBooks()
      alertRef.value.show("移除成功", "该图书资源已从系统中永久抹除")
    } catch (e) {
       alertRef.value.show("删除失败", e.message || "服务器连接中断")
    }
  }
}

onMounted(fetchBooks)
</script>

<style scoped>
.modal-fade-enter-active, .modal-fade-leave-active {
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}
.modal-fade-enter, .modal-fade-leave-to {
  opacity: 0;
  transform: scale(0.95);
}
</style>
