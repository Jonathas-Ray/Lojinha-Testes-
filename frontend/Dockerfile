FROM node:18-alpine AS build-stage
WORKDIR /app
COPY package*.json ./
# 'npm ci' para garantir que as versões do lock sejam respeitadas
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]