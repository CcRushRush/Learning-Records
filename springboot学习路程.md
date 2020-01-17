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
#springboot配置druid连接池
1. 在数据库配置文件加上
```xml
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

# 初始化大小，最小，最大
spring.datasource.initialSize=5
spring.datasource.minIdle=5
spring.datasource.maxActive=20
# 配置获取连接等待超时的时间
spring.datasource.maxWait=60000
# 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
spring.datasource.timeBetweenEvictionRunsMillis=60000
# 配置一个连接在池中最小生存的时间，单位是毫秒
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.validationQuery=SELECT 1 FROM DUAL
spring.datasource.testWhileIdle=true
spring.datasource.testOnBorrow=false
spring.datasource.testOnReturn=false
# 打开PSCache，并且指定每个连接上PSCache的大小
spring.datasource.poolPreparedStatements=true
spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
# 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
spring.datasource.filters=stat,wall,log4j
# 通过connectProperties属性来打开mergeSql功能；慢SQL记录
spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
```
2. 写一个configure类,代码如下
```java
package com.lango.demo.configure;

import javax.activation.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @author: cc
 * @version: 2020年1月16日 下午3:38:58
 */
@Configuration
public class DruidConfiguration {

	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		// 添加IP白名单
		// servletRegistrationBean.addInitParameter("allow",
		// "192.168.25.125,127.0.0.1");
		// 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
		// servletRegistrationBean.addInitParameter("deny", "192.168.25.123");
		// 添加控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "lango");
		servletRegistrationBean.addInitParameter("loginPassword", "lango");
		// 是否能够重置数据
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * 配置服务过滤器
	 *
	 * @return 返回过滤器配置对象
	 */
	@Bean
	public FilterRegistrationBean statFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则
		filterRegistrationBean.addUrlPatterns("/*");
		// 忽略过滤格式
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
		return filterRegistrationBean;
	}
}

```
