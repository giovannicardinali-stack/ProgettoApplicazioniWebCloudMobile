# Fase 1: Build dell'applicazione React
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Fase 2: Servire i file statici con Nginx
FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
# Copiamo la cartella buildata di React dentro la cartella che Nginx usa per mostrare i siti web
COPY --from=builder /app/dist /usr/share/nginx/html
# Se usi React Router (Vite usa la cartella 'dist' invece di 'build', adatta se necessario)
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
