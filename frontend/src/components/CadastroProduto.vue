<script setup>
import { ref } from 'vue';
import api from '../services/api';

const form = ref({ nome: '', email: '', telefone: '', senha: '', tipo: 'COMPRADOR' });

const realizarCadastro = async () => {
  try {
    await api.post('/usuarios/cadastrar', form.value);
    alert("Usuário cadastrado! Agora faça login.");
    window.location.href = '/login'; // Ou use o router.push('/login')
  } catch (e) {
    alert(e.response?.data || "Erro no cadastro. Verifique os formatos.");
  }
};
</script>

<template>
  <div class="max-w-md mx-auto bg-white p-8 rounded-3xl shadow-xl border border-gray-100">
    <h2 class="text-2xl font-bold text-indigo-900 mb-6">Criar Nova Conta</h2>
    <form @submit.prevent="realizarCadastro" class="space-y-4">
      <input v-model="form.nome" placeholder="Nome Completo" class="w-full p-3 bg-gray-50 border rounded-xl" required />
      <input v-model="form.email" type="email" placeholder="E-mail" class="w-full p-3 bg-gray-50 border rounded-xl" required />
      <input v-model="form.telefone" placeholder="(XX) 9XXXX-XXXX" class="w-full p-3 bg-gray-50 border rounded-xl" required />
      <input v-model="form.senha" type="password" placeholder="Senha (mín. 6)" class="w-full p-3 bg-gray-50 border rounded-xl" required />
      
      <select v-model="form.tipo" class="w-full p-3 bg-gray-50 border rounded-xl font-bold text-gray-600">
        <option value="COMPRADOR">Sou Comprador</option>
        <option value="VENDEDOR">Sou Vendedor</option>
      </select>

      <button class="w-full bg-indigo-600 text-white py-4 rounded-2xl font-bold hover:bg-indigo-700 transition-all">
        Finalizar Cadastro
      </button>
    </form>
  </div>
</template>