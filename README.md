# Job-submission-system-Java-server-
作业提交系统（Java服务端）

## 使用工程

### 配置

Intelij IDEA2019.2.4、MySQL8.0.17.0、aoache-tomcat7.0.99、jdk-8u91、javaEE7

### 步骤

1、观看工程中的文件：**运行前必看.txt**

2、IDEA->Java Enterprise->RESTful Web Service

3、配置SDK、JavaEE、Aplication Server(相关版本参考上述)

4、添加Web Application和Maven支持

5、复制相关文件

## 主要技术

Jersey(JAX-RS-2.1.1)、MVC、JDBC、REST、Web Application4.0、Maven

## 接口文档

http://doclever.cn/controller/public/public.html#5e3154566bd54308f04a1694

## 主要功能

### 用户

登入、登出、注册用户、修改密码

### 管理员

考核的增、删、改、查

作业的增、删、改、查、批量删除、修改作业标记（审核状态）

### 学生

考核的查看

作业的增、改、查

### 文件

上传、下载

### 解决XSS、CSRF、SQL注入等问题

### 利用过滤器、拦截器拦截未登入请求、进行权限管理等

## 关于工程的一些说明

### application

主要对类进行注册

### authority

权限管理，未完成

### controller

实现api，调用Service层接口，使用Jersey框架实现REST

### CSRF

解决CSRF攻击，拦截token非法请求

### dao

数据库持久层，使用JDBC

### dynamicBinding

实现注解的动态绑定

### exception

实现统一异常处理，自定义异常抛出到客户端，类型保存在InfoCode枚举中

### filter

#### request

请求过滤器：实现跨域、未登入请求

#### response

响应过滤器： 实现跨域

### format

实现自定义输入输出格式转化（JSON<->Class）

### interceptor

#### reader

请求拦截器：绑定@Student注解，实现对学生一些权限的拦截

#### writer

响应拦截器

### JWT

实现JWT的生成、解密等

### model

model层

### service

service层，调用dao层接口

### SQLInjection

解决SQL注入，本工程已使用prepareStatement，里面是法二和法三（经过测试）

### XSS

解决XSS攻击（未测试）

## 版本

1.0

## 学习资料

https://www.jianshu.com/u/231b43e2c05f


