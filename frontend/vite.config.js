import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3000,
    host: true, 
    // Necessário pro Docker
    proxy: {
      '/api': {
        target: 'http://lojinha-app:8080', 
        // Nome do serviço no Docker Compose
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})