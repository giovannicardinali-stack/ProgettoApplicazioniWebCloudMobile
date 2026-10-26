import axios from "axios";

//serve ad evitare di ricavare il token manualmente ad ogni richiesta

const api = axios.create({
    baseURL: '/',
    withCredentials: true,
})

//funzione per recuperare il token dallo storage
{
api.interceptors.request.use((config) => {
    //prende il token dal local storage, in cui era stato salvato al momento del login
    const token = localStorage.getItem('token');
    if(token){
        //imposta il token ottenuto nell'header della richiesta
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
})
    }

export default api;