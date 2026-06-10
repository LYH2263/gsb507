# Build Stage
FROM node:20-alpine AS builder
WORKDIR /app

# 先拷贝 package.json
COPY package.json ./

# 设置镜像源并安装
RUN npm config set registry https://registry.npmmirror.com && \
    npm install

# 拷贝其余源代码（.dockerignore 会过滤 node_modules）
COPY . .

# 执行构建
RUN npm run build

# Production Stage
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
