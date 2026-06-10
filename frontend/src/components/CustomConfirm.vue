<template>
  <transition name="fade">
    <div v-if="visible" class="fixed inset-0 z-50 flex items-center justify-center">
      <div class="absolute inset-0 bg-black/30 backdrop-blur-sm transition-opacity" @click="cancel"></div>
      <div class="pointer-events-auto bg-white/90 backdrop-blur-md rounded-xl shadow-2xl p-6 w-80 transform transition-all border border-white/50 text-center">
        <h3 class="text-lg font-bold text-gray-800 mb-2">操作确认</h3>
        <p class="text-gray-600 mb-6">{{ message }}</p>
        <div class="flex space-x-4">
            <button @click="cancel" 
              class="flex-1 bg-gray-200 text-gray-700 font-semibold py-2 rounded-lg shadow-sm hover:bg-gray-300 transition">
              取消
            </button>
            <button @click="confirm" 
              class="flex-1 bg-gradient-to-r from-red-500 to-pink-600 text-white font-semibold py-2 rounded-lg shadow-md hover:shadow-lg transition transform hover:-translate-y-0.5">
              确定
            </button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  message: { type: String, required: true }
})

const visible = ref(false)
let resolvePromise = null

const show = () => {
  visible.value = true
  return new Promise((resolve) => {
    resolvePromise = resolve
  })
}

const confirm = () => {
    visible.value = false
    if (resolvePromise) resolvePromise(true)
}

const cancel = () => {
    visible.value = false
    if (resolvePromise) resolvePromise(false)
}

defineExpose({ show })
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
