<script setup>
import { ref } from 'vue';
import { authService } from '../services/api';

const email = ref('');
const senha = ref('');
const erro = ref('');
const carregando = ref(false);

const realizarLogin = async () => {
  if (!email.value || !senha.value) {
    erro.value = "Preencha todos os campos.";
    return;
  }

  carregando.value = true;
  erro.value = '';

  try {
    // Chamando sua rota /usuarios/login do Spring Boot
    await authService.login(email.value, senha.value);
    alert("Login realizado com sucesso! Você já pode testar as rotas protegidas.");
    // Aqui você poderia redirecionar o usuário ou mudar o estado do App.vue
  } catch (e) {
    erro.value = e.response?.data || "Falha na autenticação. Verifique seu e-mail e senha.";
  } finally {
    carregando.value = false;
  }
};
</script>

<template>
  <div class="max-w-md mx-auto bg-white p-8 rounded-3xl shadow-2xl border border-gray-100">
    <div class="text-center mb-8">
      <h2 class="text-3xl font-extrabold text-indigo-900">Bem-vindo</h2>
      <p class="text-gray-500 text-sm mt-2">Acesse sua conta para gerenciar a Lojinha</p>
    </div>

    <form @submit.prevent="realizarLogin" class="space-y-6">
      <div>
        <label class="block text-xs font-bold uppercase tracking-wider text-gray-400 mb-2">E-mail</label>
        <input 
          v-model="email"
          type="email" 
          placeholder="exemplo@email.com"
          class="w-full px-4 py-3 rounded-xl bg-gray-50 border border-gray-200 focus:ring-2 focus:ring-indigo-500 outline-none transition-all"
        />
      </div>

      <div>
        <label class="block text-xs font-bold uppercase tracking-wider text-gray-400 mb-2">Senha</label>
        <input 
          v-model="senha"
          type="password" 
          placeholder="••••••••"
          class="w-full px-4 py-3 rounded-xl bg-gray-50 border border-gray-200 focus:ring-2 focus:ring-indigo-500 outline-none transition-all"
        />
      </div>

      <p v-if="erro" class="text-red-500 text-xs font-bold bg-red-50 p-3 rounded-lg border border-red-100 italic">
        {{ erro }}
      </p>

      <button 
        type="submit"
        :disabled="carregando"
        class="w-full bg-indigo-600 text-white py-4 rounded-2xl font-black uppercase tracking-widest hover:bg-indigo-700 active:scale-95 transition-all shadow-lg disabled:opacity-50"
      >
        <span v-if="carregando">Autenticando...</span>
        <span v-else>Entrar no Sistema</span>
      </button>
    </form>
  </div>
</template>