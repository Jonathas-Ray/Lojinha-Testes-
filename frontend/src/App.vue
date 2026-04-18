<script setup>
import { ref, onMounted } from 'vue';
import Vitrine from './components/Vitrine.vue';
import Login from './components/Login.vue';

const telaAtual = ref('vitrine');
const logado = ref(false);

onMounted(() => {
  if (localStorage.getItem('user_auth')) {
    logado.value = true;
  }
}); 

const handleLoginSucesso = () => {
  logado.value = true;
  telaAtual.value = 'vitrine';
};

const realizarLogout = () => {
  localStorage.removeItem('user_auth');
  logado.value = false;
  telaAtual.value = 'login';
};
</script>

<template>
  <div class="min-h-screen bg-gray-50 font-sans antialiased">
    <header class="bg-indigo-700 text-white shadow-md sticky top-0 z-50">
      <div class="container mx-auto px-4 py-3 flex justify-between items-center">
        <h1 class="text-xl font-black tracking-tighter uppercase cursor-pointer" @click="telaAtual = 'vitrine'">
          Lojinha FX
        </h1>
        
        <nav class="flex gap-4 items-center">
          <button 
            @click="telaAtual = 'vitrine'" 
            class="px-3 py-1 rounded-md transition-colors hover:bg-indigo-800"
            :class="{'bg-indigo-900 font-bold': telaAtual === 'vitrine'}"
          >
            Vitrine
          </button>
          
          <button 
            v-if="!logado"
            @click="telaAtual = 'login'" 
            class="px-4 py-1 bg-white text-indigo-700 rounded-md font-bold hover:bg-gray-100 transition-colors shadow-sm"
          >
            Login
          </button>

          <button 
            v-else
            @click="realizarLogout"
            class="text-xs bg-red-500 px-3 py-1 rounded hover:bg-red-600 font-bold uppercase"
          >
            Sair
          </button>
        </nav>
      </div>
    </header>

    <main class="container mx-auto py-8 px-4">
      <transition name="fade" mode="out-in">
        <div :key="telaAtual">
          <Vitrine v-if="telaAtual === 'vitrine'" />
          <Login v-else-if="telaAtual === 'login'" @sucesso="handleLoginSucesso" />
        </div>
      </transition>
    </main>

    <footer class="mt-auto py-6 text-center text-gray-400 text-xs border-t border-gray-100">
      &copy; 2026 Lojinha FX — Sistema de Testes Industrializado.
    </footer>
  </div>
</template>

<style>
.fade-enter-active, .fade-leave-active { transition: opacity 0.2s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>