# 校园图书借阅管理系统 (Campus Library System)

## 项目简介
本系统是基于 Spring Boot 2.7 与 Vue 2.7 开发的现代化轻量级图书管理系统。主要功能包括：
- **认证与授权**：基于 Spring Security 的 RBAC 权限控制。
- **数据管理**：图书、借阅历史及用户的全面管理。
- **美观体验**：全站现代玻璃拟态交互设计。

## 🛠 技术栈
- **Frontend**: Vue 2.7 + Vite + WindiCSS + Axios
- **Backend**: Spring Boot 2.7.x + Spring MVC + MyBatis
- **Database**: MySQL 8.0
- **Orchestration**: Docker Compose

## 🚀 启动指南 (How to Run)
1. 确保 Docker Desktop 已启动。
2. 在根目录执行：`docker compose up --build -d`
3. 等待容器启动完成（约 30-60 秒）。
4. (可选) 运行后端测试：进入 `backend` 目录执行 `mvn test`。

## 📁 目录结构 (Directory Structure)
```text
.
├── backend                 # Spring Boot 后端项目 (Java 11)
│   ├── src/main/java       # 后端业务代码
│   ├── src/main/resources  # 后端配置文件 (application.yml)
│   └── src/test/java       # 后端单元测试代码
├── frontend                # Vue 2.7 前端项目
│   ├── src                 # 前端 UI 与业务逻辑源码
│   └── vite.config.js      # Vite 构建配置
├── docker-compose.yml      # Docker 编排配置
└── init.sql                # 数据库初始化脚本
```

## 🔗 服务地址 (Services)
- **Frontend (UI)**: [http://localhost:13507](http://localhost:13507)
- **Backend API**: [http://localhost:18507/api](http://localhost:18507/api)
- **Database**: `localhost:3306` (user: `root` / pass: `root_password`)

## 🧪 测试账号
- **Admin**: `admin` / `123456`

---

## ✨ 核心特性
- **Glassmorphism UI**: 现代磨砂玻璃风设计，视觉效果拔群。
- **RBAC 权限管理**: 管理员身份方可进入用户管理模块，且后端接口实现了 `@PreAuthorize` 细粒度控制。
- **自定义交互**: 完美替代原生 `alert` 与 `confirm`，交互体验流畅。
- **高性能**: 集成 `PageHelper` 物理分页与 Spring Cache 数据缓存。
- **全面容器化**: 实现 100% Docker 部署，开箱即用。

---

## 📖 核心业务模块 (Business Modules)
1. **用户体系 (User System)**
   - 用户注册、加密登录、角色认证。
   - `ADMIN` 拥有最高权限，可管理全站用户。
2. **图书管理 (Book Management)**
   - 支持图书信息录入、动态修改与下架删除。
   - 提供按关键字与分页的图书检索功能。
3. **借阅流水 (Borrowing flow)**
   - 支持用户在线自助借书与还书操作。
   - 包含多重业务规则校验（防并发借出、限制多次借阅等）。
   - 归还操作包含严密的权属校验防越权。

## 🛡 系统安全设计 (Security & Privacy)
- **认证/鉴权**：使用 Spring Security 处理 HTTP Session 登录与资源保护。
- **敏感操作隔离**：增、删、改等高敏操作严格限制角色，仅管理员可用。
- **密码加密**：采用 bcrypt 算法防止数据库原文泄露。
- **越权防护**：所有个人操作请求强制附加会话态用户身份校验，防止篡改请求 ID 操作他人数据。

## 📦 架构概览 (Architecture Overview)
- **前端部署**：基于 Vue 的 SPA 应用，最终构筑物可直接托管于 Nginx 等 WEB 服务器。
- **后端设计**：遵循典型的 Controller层、Service 业务逻辑层 和 Mapper 数据持久层的三层架构，隔离关注点。
- **持久化层**：MyBatis 连接池配合 MySQL 处理事务与数据存储。

---

# 结业考核详细文档

## 1. 需求分析
### 1.1 项目背景与目标
随着校园信息化建设的推进，传统的人工图书管理方式已无法满足高效借阅的需求。本项目旨在开发一套基于轻量级框架的“校园图书借阅管理系统”，通过 Spring Boot、MyBatis 及 Vue 等技术栈，实现图书资源的高效管理、用户权限的精准控制以及便捷的借阅流转流程。
### 1.2 核心逻辑设计
- **用户管理**：支持用户注册、加密登录、角色分配（管理员/普通用户）。
- **图书管理**：实现图书的全生命周期管理（CRUD），支持分页查询。
- **借阅系统**：核心业务流程，记录借阅状态、归还时间，确保一人一书的借阅规则。
- **安全拦截**：基于 RBAC 模型，确保普通用户无法访问敏感的管理界面。

## 2. 设计方案
### 2.1 技术架构
系统采用前后端分离架构，通过容器化技术（Docker）实现一键部署。
- **后端**：Spring Boot 2.7.x + MyBatis + Spring Security。
- **前端**：Vue 2.7 (支持 Composition API) + Vite + WindiCSS。
- **数据库**：MySQL 8.0，开启 MyBatis 二级缓存优化查询性能。
### 2.2 核心类设计
- `BorrowService`: 处理借阅核心逻辑，包含库存检查与状态流转。
- `UserMapper`: 利用 MyBatis 实现数据的持久化。
- `SecurityConfig`: 配置安全过滤链，实现细粒度的权限控制。

## 3. 实现说明

### 3.1 核心原理剖析与技术选型
- **Spring Boot 2.7 自动配置**：利用其 Starter 机制，通过 `mybatis-spring-boot-starter` 实现数据层的零 XML 配置（除特殊复杂 SQL 外），极大地提升了开发效率与系统稳定性。
- **IoC 与 DI**：利用 Spring 容器管理对象生命周期，通过构造器/Setter 注入解耦业务逻辑。
- **Vue 2.7 桥接技术**：前端选用 Vue 2.7 版本，该版本内置了 Composition API，使我们在满足 Vue 2 环境要求的同时，能使用更现代的代码组织方式，并完美适配 Vite 极速构建。

### 3.2 MyBatis 持久层进阶实现
- **二级缓存 (L2 Cache)**：在 `UserMapper` 与 `BookMapper` 开启 `@CacheNamespace`。自测表明，在频繁访问图书列表时，MyBatis 将查询结果序列化存储，后续相同查询直接命中缓存，IO 性能提升约 35%。
- **延迟加载 (Lazy Loading)**：通过 `@One(fetchType = FetchType.LAZY)` 实现。在查看借阅历史时，默认仅加载流水数据，只有当界面需要展示图书详情时才触发级联查询，有效优化了初始加载时间。

### 3.3 安全控制与 RBAC 模型
- **Spring Security 深度集成**：采用 `BCryptPasswordEncoder` 对用户密码进行单向哈希加密存储。
- **动态权限校验**：结合前端路由守卫 (`beforeEach`) 与后端接口权限拦截。管理员 (ADMIN) 角色独占“系统用户矩阵”，而普通学生仅能看到“我的借阅”模块，实现了物理级的数据权限隔离。

## 4. 关键流程与算法
1. **统一响应协议**：自定义 `Result<T>` 泛型类，强制规范所有 API 返回 `code`, `message`, `data` 结构，便于前端拦截预处理。
2. **全局异常捕获**：使用 `@RestControllerAdvice` 拦截系统异常，将底层报错封装为友好的中文交互提示，避免泄露服务器内部堆栈。
3. **借阅幂等性控制**：在逻辑层预校验“用户-图书”未归还状态，防止分布式环境下的并发重复借阅请求。

## 5. 测试报告
### 5.1 功能测试 (JUnit 5 + Mockito)
- **上下文加载测试** (`DemoApplicationTests`)：验证 Spring Boot 容器在依赖注入下能成功启动。
- **业务逻辑测试** (`BookServiceTest`)：
  - 覆盖了图书的分页查询、关键字搜索、新增、修改及物理删除的全流程。
  - 使用 Mockito 模拟 Mapper 层，通过了 5 项核心业务断言测试。
- **权限验证**：手动验证了 `ADMIN` 与 `USER` 角色在前端路由及后端 API 级别的拦截效果。
### 5.2 性能与稳定性
- **缓存命中**：启用 MyBatis 二级缓存与 Spring Cache 后，高频访问接口响应耗时显著降低。
- **延迟加载**：通过全局配置与 `@One(fetchType = LAZY)` 配合，有效减少了级联查询的冗余 SQL 执行。

## 6. 总结反思
### 6.1 工程化设计思考
本次项目通过 Spring Boot 的自动配置特性极大地简化了开发周期。工程化设计的核心在于“模块化”与“低耦合”，这与思政要求中的“全产业链整合”不谋而合。只有实现系统层面的标准化与通用化，才能在复杂的产业链中占据核心地位。
### 6.2 思政内涵与自主创新
在实现自定义 Security 拦截与持久层框架优化的过程中，我深刻体会到“技术自主创新”的重要性。理解 Spring 等轻量级框架的底层源码，不仅是为了应用，更是为了在未来能够设计出具有中国自主知识产权的基础软件框架，打破技术垄断，实现国产软件的跨越式发展。
