<template>
  <transition name="fade">
    <div v-if="visible" class="fixed inset-0 z-50 flex items-center justify-center pointer-events-none">
      <div class="absolute inset-0 bg-black/30 backdrop-blur-sm transition-opacity" @click="close"></div>
      <div class="pointer-events-auto bg-white/90 backdrop-blur-md rounded-xl shadow-2xl p-6 w-80 transform transition-all border border-white/50 text-center">
        <h3 class="text-lg font-bold text-gray-800 mb-2">{{ displayTitle }}</h3>
        <p class="text-gray-600 mb-6">{{ displayMessage }}</p>
        <button @click="close" 
          class="w-full bg-gradient-to-r from-purple-500 to-indigo-600 text-white font-semibold py-2 rounded-lg shadow-md hover:shadow-lg transition transform hover:-translate-y-0.5">
          确定
        </button>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  title: { type: String, default: '提示' },
  message: { type: String, required: true }
})

const displayTitle = ref('')
const displayMessage = ref('')
const visible = ref(false)

const show = (title, message) => {
  displayTitle.value = title || props.title
  displayMessage.value = message || props.message
  visible.value = true
}
const close = () => visible.value = false

defineExpose({ show, close })
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
