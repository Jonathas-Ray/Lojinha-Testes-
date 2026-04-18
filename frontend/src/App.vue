<script setup>
import { ref } from 'vue';
import Vitrine from './components/Vitrine.vue';

// Estado para controlar qual tela exibir (simulação de rotas minimalista)
const telaAtual = ref('vitrine');

// Nome da loja para consistência visual
const nomeLoja = "Lojinha FX";
</script>

<template>
  <div class="min-h-screen bg-gray-50 font-sans antialiased">
    <header class="bg-indigo-700 text-white shadow-md sticky top-0 z-50">
      <div class="container mx-auto px-4 py-3 flex justify-between items-center">
        <h1 class="text-xl font-black tracking-tighter uppercase cursor-pointer" @click="telaAtual = 'vitrine'">
          {{ nomeLoja }}
        </h1>
        
        <nav class="flex gap-4">
          <button 
            @click="telaAtual = 'vitrine'" 
            class="px-3 py-1 rounded-md transition-colors hover:bg-indigo-800"
            :class="{'bg-indigo-900 font-bold': telaAtual === 'vitrine'}"
          >
            Vitrine
          </button>
          <button 
            @click="telaAtual = 'login'" 
            class="px-4 py-1 bg-white text-indigo-700 rounded-md font-bold hover:bg-gray-100 transition-colors shadow-sm"
            :class="{'ring-2 ring-indigo-300': telaAtual === 'login'}"
          >
            Login
          </button>
        </nav>
      </div>
    </header>

    <main class="container mx-auto py-8 px-4">
      <transition name="fade" mode="out-in">
        <div :key="telaAtual">
          <Vitrine v-if="telaAtual === 'vitrine'" />

          <div v-else class="max-w-md mx-auto mt-10">
            <div class="bg-white p-8 rounded-2xl shadow-xl border border-gray-100 text-center">
              <div class="mb-4 inline-flex p-3 bg-indigo-50 text-indigo-600 rounded-full">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                </svg>
              </div>
              <h2 class="text-2xl font-bold text-gray-800 mb-2">Acesso Restrito</h2>
              <p class="text-gray-500 mb-6 text-sm">
                Conecte-se para gerenciar seus produtos ou finalizar compras.
              </p>
              <button 
                class="w-full bg-indigo-600 text-white py-3 rounded-xl font-bold hover:bg-indigo-700 transition-all shadow-lg"
                @click="alert('Aqui chamaremos o authService.login()')"
              >
                Ir para Formulário
              </button>
              <button 
                @click="telaAtual = 'vitrine'" 
                class="mt-4 text-xs text-gray-400 hover:text-indigo-600 font-medium"
              >
                Voltar para a Loja
              </button>
            </div>
          </div>
        </div>
      </transition>
    </main>

    <footer class="mt-auto py-6 text-center text-gray-400 text-xs border-t border-gray-100">
      &copy; 2026 {{ nomeLoja }} — Sistema de Testes Industrializado.
    </footer>
  </div>
</template>

<style>
/* Animação suave de troca de tela */
.fade-enter-active, .fade-leave-active { transition: opacity 0.2s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>