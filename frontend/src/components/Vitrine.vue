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

const deletarProduto = async (id) => {
    const senha = prompt("Confirme sua senha para excluir:");
    if (!senha) return;
    
    try {
        await produtoService.deletar(id, senha); // Requisito 6
        alert("Produto removido!");
        carregarDados();
    } catch (err) {
        alert(err.response?.data || "Erro ao deletar");
    }
};

onMounted(carregarDados);
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-3 gap-6 p-4">
    <div v-if="carregando" class="col-span-full text-center py-10">
      <span class="animate-pulse text-indigo-600 font-bold">Consultando estoque...</span>
    </div>
    
    <div v-for="p in produtos" :key="p.id" class="bg-white p-5 rounded-2xl shadow-sm border border-gray-100 relative">
      <span class="absolute top-3 right-3 bg-indigo-50 text-indigo-600 text-[10px] px-2 py-1 rounded-full font-bold uppercase">
        {{ p.tipo }}
      </span>

      <h3 class="font-bold text-gray-800">{{ p.nome }}</h3>
      <p class="text-sm text-gray-500 my-2">{{ p.descricao }}</p>
      
      <p class="text-[10px] font-bold" :class="p.estoque > 0 ? 'text-green-500' : 'text-red-500'">
        {{ p.estoque > 0 ? `Restam ${p.estoque} unidades` : 'Esgotado' }}
      </p>

      <div class="flex justify-between items-center mt-4 border-t pt-4">
        <span class="text-green-600 font-black">R$ {{ p.preco.toFixed(2) }}</span>
        
        <div class="flex gap-2">
            <button @click="deletarProduto(p.id)" class="text-red-400 hover:text-red-600 text-xs font-bold">
                Excluir
            </button>
            <button 
                :disabled="p.estoque <= 0"
                class="bg-indigo-600 text-white text-xs px-3 py-2 rounded-lg hover:bg-indigo-700 disabled:opacity-30"
            >
                Comprar
            </button>
        </div>
      </div>
    </div>
  </div>
</template>