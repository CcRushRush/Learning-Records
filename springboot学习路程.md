##springboot

优点：主需要投入到逻辑业务的代码编写中，快速开发

特点：基于spring的开发提供更快的入门体验，无需xml配置，springboot不是spring的增强，是更快的使用spring，相当于对spring的封装

核心功能：起步依赖，自动配置

代码实现步骤
1.创建maven工程
2.添加springboot的起步依赖
3.编写springboot引导类
4.编写controller
5.测试

快速构建springboot(idea)
创建项目的时候直接选择：Spring Initializer
添加依赖的时候，具体选择看个人，
如果是web工程选择：Web->Spring Web
SQL选择： MySQL driver，Mybatis Framework（这是mybatis的一些选择，如果是hibernate，选多一个spring data jpa）
Nosql一般选择redis


application.proterties一些配置
```xml
#数据库连接信息
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=
spring.datasource.username=root
spring.datasource.password=

#配置mybatis信息
#别名
mybatis.type-aliases-package=com.cc.pojo
#mapper扫描入口
mybatis.mapper-locations=classpath:mapper/*Mapper.xml

#配置redis
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=123456
```
