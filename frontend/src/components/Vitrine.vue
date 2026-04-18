<script setup>
import { ref, onMounted } from 'vue';
import { produtoService } from '../services/api';

const produtos = ref([]);
const carregando = ref(true);

const carregarDados = async () => {
    try {
        const res = await produtoService.listar();
        produtos.value = res.data;
    } catch (err) {
        console.error("Falha na conexão com o back-end.", err);
    } finally {
        carregando.value = false;
    }
};

onMounted(carregarDados);
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-3 gap-6 p-4">
    <div v-if="carregando" class="col-span-full text-center py-10">
      <span class="animate-pulse text-indigo-600 font-bold">Consultando estoque...</span>
    </div>
    
    <div v-for="p in produtos" :key="p.id" class="bg-white p-5 rounded-xl shadow-sm border border-gray-100">
      <h3 class="font-bold text-gray-800">{{ p.nome }}</h3>
      <p class="text-sm text-gray-500 my-2">{{ p.descricao }}</p>
      <div class="flex justify-between items-center mt-4">
        <span class="text-green-600 font-black">R$ {{ p.preco.toFixed(2) }}</span>
        <button class="bg-indigo-600 text-white text-xs px-3 py-2 rounded-lg hover:bg-indigo-700 transition-colors">
          Adicionar
        </button>
      </div>
    </div>
  </div>
</template>