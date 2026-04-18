import axios from 'axios';

// Configuração centralizada para usar o proxy do Vite e evitar erros de CORS
const api = axios.create({
    baseURL: '/api'
});

export const authService = {
    // Mantendo seu método de Basic Auth para o Spring Security
    login(email, senha) {
        const auth = btoa(`${email}:${senha}`);
        return api.post('/usuarios/login', {}, {
            headers: { 'Authorization': `Basic ${auth}` }
        });
    }
};

export const produtoService = {
    listar() {
        return api.get('/produtos');
    },
    // Necessário implementar o CRUD do Vendedor ainda
    criar(produtoDto) {
        return api.post('/produtos', produtoDto);
    }
};

export default api;