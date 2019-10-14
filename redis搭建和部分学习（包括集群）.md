## redis安装流程
 1. 在官网下载redis:https://redis.io/download
 2. 解压到相应的目录：tar -zxvf redis-4.0.14.tar.gz
 3. 进入到解压目录，编译redis:make MALLOC=libc
 4. 同时在目录下安装redis到相应的目录：make PREFIX=/usr/local/redis install
 5. 启动redis服务器端:./redis-server
 6. 启动redis客户端：./redis-cli
 7. 在解压目录将redis.conf 复制到和安装redis目录的bin目录同级
	1. daemonize no表示:不是守护进程，将no改为yes,一般设置为yes
	2. bind 127.0.0.1：代表只能本机访问，如果需要别的也要访问，需要注释掉
	3. loglevel  :日志记录级别
	4. requirepass ：设置密码,一般需要设置密码
	5. maxclients :设置客户机连接的限制 当maxclient 0，证明没有限制
	6. valotile-lru ：设计定时删除最不常用的数据

## redis基本数据类型
### 1. String 命令
赋值：set key_name value，如果key存在，就覆盖，不存在就存
            setnx key value ，如果key存在就不写进
取值：get key
删除：del key
应用：保存json数据，做计数器，保存图片

### 2. Hash
存储：hset key field value；key相当于对象，field相当于属性
            hmset users:1 id 20164141 name cc age 23
取值：hget key field
            hgetall users:1
删除：hdel key field
应用：bean对象

### 3. list 相当于Java的LinkedList
赋值：lpush key value1 [];从左侧添加
           rpush key value1 [];从右侧添加
取值：lrange key start end;     // -1表示最后一个数
应用：列数据显示，关注列表，粉丝列表，分页，消息队列，任务队列

### 4. set 相当于java的hashtable, 数据不重复,无序集合
存值： sadd key value []
取值：smenbers key 返回所有成员
           srandmenber key [count] 返回一个或多个随机成员  //适合用于抽奖
           spop key  [count] 移除并返回集合中的一个或多个随机元素  //适合用于抽奖
           sinter key source1 source2 将source1和source2共有的元素返回并放入destination
应用：随机抽奖，返回共同关注

### 5. zset 有序不重复集合，根据值排序
存值：zadd key score number // score->分数 ，number->科目
取值：zrange key start end
应用：分数排行榜

### 6. 订阅功能
subscribe channel 订阅给定的一个或多个频道的信息
psubscribe pattern  订阅一个或多个符合给定模式的频道
发布信息给订阅的指定频道
publish channel message
退订频道
unsubscribe channel 退订给定的频道
punsubscribe channel 退订所有给定模式的频道
应用场景：即时聊天，群聊，实时信息

### 7. redis事务
1 可以一次执行多个命令
2 将命令序列化，然后按顺序执行，执行过程中，不允许其他命令插入
应有：转账
***使用watch监听key,当key被改动，当前事务会被取消

### redis多数据库
数据库切换 select 数据库
移动数据到指定的数据库：move key  select 数据库

### redis淘汰策略

### redis持久化：rdb快照，aof

## redis缓存与数据库一致性
 1. 实时同步
如果在redis查询不到key,就查询数据库，无论数据库返回是否为空，都插入到redis
 2. 异步队列
使用中间件：kafka等消息中间件
做消息队列
 3. 使用阿里的同步工具canal
mysql主从复制
 4. 采用udf自定义函数的方式
在数据库中写触发器
 5. redis脚本，使用lua解析器执行脚本

### 缓存穿透
缓存穿透，是指查询一个数据库一定不存在的数据。正常的使用缓存流程大致是，数据查询先进行缓存查询，如果key不存在或者key已经过期，再对数据库进行查询，并把查询到的对象，放进缓存。如果数据库查询对象为空，则不放进缓存。

### 实现高可用高并发有两种方法：垂直扩展，水平扩展
垂直扩展：提高单台服务器的性能，如：更换更大的服务器，增加CPU核数，升级网卡，扩容扩内存
水平扩展：增加服务器数量

### 主从复制
1. 为数据提供多个副本，实现高可用
2. 实现读写分离（主节点负责写数据，从节点负责读数据，主节点定期把数据同步到从节点保证数据的一致性）


## redis cluster 集群
### 集群创建
 1. 在usr/local目录下创建reids_cluster文件夹
 2. 在redis_cluster文件夹目录下创建6个目录，命名分别为7001~7006
 3. 把原先安装的redis中的redis.conf和src拷贝到7001~7006每一个文件夹中
 4. 修改每个redis.conf，主要修改的字段有
	1. 绑定服务器的id：bind 服务器id
	2. 端口号：port 端口号；建议与文件夹同名
	3. 开启后台运行：daemonize yes
	4. 启用集群：cluster-enabled yes
 5. 为了方便一次性启动所有redis服务器，写一个脚本redis.sh如下:
```sh
./7001/src/redis-server ./7001/redis.conf
./7002/src/redis-server ./7002/redis.conf
./7003/src/redis-server ./7003/redis.conf
./7004/src/redis-server ./7004/redis.conf
./7005/src/redis-server ./7005/redis.conf
./7006/src/redis-server ./7006/redis.conf
```
 6. 将redis安装包下的redis-trib.rb拷贝到usr/local/bin目前下
 7. 启动集群命令：redis-trib.rb create --replicas 1 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7006
 8. 将redis安装包下src里面的redis-cli拷贝到usr/local/bin目前下
 9. 启动redis客户端：redis-cli -h 服务器ip -c -h 端口号

### redis集群配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:cache="http://www.springframework.org/schema/cache"

       xsi:schemaLocation="http://www.springframework.org/schema/beans 
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context 
                           http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/cache
                           http://www.springframework.org/schema/cache/spring-cache.xsd">
    <!--    引入redis配置信息-->
    <context:property-placeholder location="classpath*:redis.properties"/>


    <!--    连接池信息配置-->
    <bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <!--        最大连接数-->
        <property name="maxTotal" value="${redis.maxTotal}"/>
        <!--        最大空闲数-->
        <property name="maxIdle" value="${redis.maxIdle}"/>
        <!--        最大等待时间-->
        <property name="maxWaitMillis" value="${redis.maxWait}"/>
    </bean>
    <!--    &lt;!&ndash;    spring整合单个redis&ndash;&gt;-->
    <!--    <bean id="jedisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">-->
    <!--        &lt;!&ndash;服务器地址&ndash;&gt;-->
    <!--        <property name="hostName" value="${redis.host}"/>-->
    <!--        &lt;!&ndash;       端口号&ndash;&gt;-->
    <!--        <property name="port" value="${redis.port}"/>-->
    <!--        &lt;!&ndash;        密码&ndash;&gt;-->
    <!--        <property name="password" value="${redis.password}"/>-->
    <!--        &lt;!&ndash; 连超时设置. &ndash;&gt;-->
    <!--        <property name="timeout" value="${redis.timeout}"/>-->
    <!--        &lt;!&ndash;        连接池配置&ndash;&gt;-->
    <!--        <property name="poolConfig" ref="jedisPoolConfig"/>-->
    <!--    </bean>-->

    <!--    rediscluster搭建-->
    <bean id="redisClusterConfiguration" class="org.springframework.data.redis.connection.RedisClusterConfiguration">
        <property name="maxRedirects" value="6"/>
        <property name="clusterNodes">
            <set>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="7001"/>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="7002"/>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="7003"/>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="7004"/>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="7005"/>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="7006"/>
                </bean>
            </set>

        </property>
    </bean>
    <!--key序列化-->
    <bean id="stringRedisSerializer" class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
    <!--value序列化-->
    <bean id="jdkSerializationRedisSerializer"
          class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer"/>
    <!--    hash key 系列化设置-->
    <bean id="hashKeySerializer" class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
    <!--    单个redis配置Template-->
    <!--    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">-->
    <!--        <property name="connectionFactory" ref="jedisConnectionFactory"/>-->
    <!--        &lt;!&ndash;对各种数据进行序列化方式的选择&ndash;&gt;-->
    <!--        <property name="keySerializer" ref="stringRedisSerializer"/>-->
    <!--        <property name="valueSerializer" ref="jdkSerializationRedisSerializer"/>-->
    <!--        <property name="hashKeySerializer" ref="hashKeySerializer"/>-->
    <!--    </bean>-->
    <!-- Redis连接工厂     -->
    <bean id="redis4CacheConnectionFactory"
          class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <constructor-arg name="clusterConfig" ref="redisClusterConfiguration"/>
        <property name="timeout" value="${redis.timeout:100000}"/>
        <property name="poolConfig" ref="jedisPoolConfig"/>
    </bean>

    <!-- 集群Resis使用模板 -->
    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
        <property name="connectionFactory" ref="redis4CacheConnectionFactory"/>
        <property name="keySerializer" ref="stringRedisSerializer"/>
        <property name="valueSerializer" ref="jdkSerializationRedisSerializer"/>
        <property name="hashKeySerializer" ref="hashKeySerializer"/>
    </bean>

    <!--    redis注解方式-->
    <!--Spring缓存管理器-->
    <bean id="redisCacheManager" class="org.springframework.data.redis.cache.RedisCacheManager">
        <constructor-arg name="redisOperations" ref="redisTemplate"/>
        <property name="defaultExpiration" value="10000"/>
        <!--缓存管理器的名称-->
        <property name="expires">
            <map>
                <entry key="userCache" value="1000000"/>
            </map>
        </property>
    </bean>
    <!-- 启用缓存注解功能，这个是必须的，否则注解不会生效，另外，该注解一定要声明在spring主配置文件中才会生效,这个cacheManager
     必须指向redis配置里面的 RedisCacheManager-->
    <cache:annotation-driven cache-manager="redisCacheManager"/>
</beans>
```
