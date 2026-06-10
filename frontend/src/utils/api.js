import axios from 'axios'
import router from '../router'

const api = axios.create({
  baseURL: '/api',
  timeout: 5000
})

api.interceptors.response.use(
  response => {
    if (response.data.code === 200) {
      return response.data
    }
    if (response.data.code === 403) {
      localStorage.removeItem('user')
      router.push('/login')
    }
    // Handle specific error codes if needed
    return Promise.reject(new Error(response.data.message || 'Error'))
  },
  error => {
    if (error.response && error.response.status === 403) {
      localStorage.removeItem('user')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

export default api
