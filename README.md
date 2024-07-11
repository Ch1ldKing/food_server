# 这是food的后端项目
## 想要部署，需要打开后等待maven安装依赖
### 需要的数据库PostgreSQL
### 部署postgreSQL
1. `docker pull postgre`
2. `docker volume create postgre-data`
3. `docker run -id --name=postgresql -v postgre-data:/var/lib/postgresql/data -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e LANG=C.UTF-8 postgres`
   委托形式的数据库实体类
#### 在安装好PostgreSQL后，管理员账户密码都设为postgres
##### 运行src/database.sql