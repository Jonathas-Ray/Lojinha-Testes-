import axios from 'axios';

const api = axios.create({
    baseURL: '/api'
});

// Interceptor para injetar Basic Auth em cada chamada automática
api.interceptors.request.use(config => {
    const auth = localStorage.getItem('user_auth');
    if (auth) {
        config.headers.Authorization = `Basic ${auth}`;
    }
    return config;
});

export const authService = {
    async login(email, senha) {
        const auth = btoa(`${email}:${senha}`);
        const res = await api.post('/usuarios/login', {}, {
            headers: { 'Authorization': `Basic ${auth}` }
        });
        // Salva para persistir o login entre as telas
        localStorage.setItem('user_auth', auth);
        return res;
    }
};

export const produtoService = {
    listar: () => api.get('/produtos'),
    criar: (dto) => api.post('/produtos', dto)
};

export default api;