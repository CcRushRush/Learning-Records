## ubuntu 环境做emqx负载均衡

### docker 拉取 emqx镜像
docker pull emqx

### docker创建虚拟网络(由于目前只有一台虚拟机，如果有多台，可以不用这一步)
sudo docker network create -d bridge --subnet=172.0.0.0/16 emqx_bridge

### 启动三个emqx容器
docker run -d --hostname emqx01 --name emqx01 --network emqx_bridge --ip 172.0.0.2 -p 60001:1883 -p 60004:8083 -p 60010:8883 -p 60007:8084 -p 60016:18083 -p 60013:11883 -v /etc/localtime:/etc/localtime:ro emqx/emqx:latest

docker run -d --hostname emqx02 --name emqx02 --network emqx_bridge --ip 172.0.0.3 -p 60002:1883 -p 60005:8083 -p 60011:8883 -p 60008:8084 -p 60017:18083 -p 60014:11883 -v /etc/localtime:/etc/localtime:ro emqx/emqx:latest

docker run -d --hostname  emqx03 --name emqx03 --network emqx_bridge --ip 172.0.0.4 -p 60003:1883 -p 60006:8083 -p 60012:8883 -p 60009:8084 -p 60018:18083 -p 60015:11883 -v /etc/localtime:/etc/localtime:ro emqx/emqx:latest

### 集群构建
sudo docker exec -it emqx02 sh
bin/emqx_ctl  cluster join emqx01@172.0.0.2
exit

sudo docker exec -it emqx03 sh
bin/emqx_ctl  cluster join emqx01@172.0.0.2
exit


### ubuntu 安装 haproxy
apt-get install haproxy -y

### haproxy的轮询策略相关说明
①roundrobin，表示简单的轮询，这个不多说，这个是负载均衡基本都具备的；
② static-rr，表示根据权重，建议关注；
③leastconn，表示最少连接者先处理，建议关注；
④ source，表示根据请求源IP，这个跟Nginx的IP_hash机制类似我们用其作为解决session问题的一种方法，建议关注；
⑤ri，表示根据请求的URI；
⑥rl_param，表示根据请求的URl参数’balance url_param’ requires an URL parameter name；
⑦hdr(name)，表示根据HTTP请求头来锁定每一次HTTP请求；
⑧rdp-cookie(name)，表示根据据cookie(name)来锁定并哈希每一次TCP请求。

### 修改haproxy配置文件，路径：/etc/haproxy/haproxy.cfg，内容如下
```
defaults
  log                     global
  option                  dontlognull
  option http-server-close
  retries                 3
  timeout http-request    10s
  timeout queue           1m
  timeout connect         10s
  timeout client          1m
  timeout server          1m
  timeout http-keep-alive 10s
  timeout check           10s

# front花呗渡边纲1883端口
frontend emqtt-front
  bind *:1885
  mode tcp
  default_backend emqtt-backend

# backend分发前台请求
backend emqtt-backend
   balance roundrobin
   server emqx01 127.0.0.2:60001 check      #节点名 ip:port
   server emqx02 127.0.0.3:60002 check
   server emqx03 127.0.0.4:60003 check

# dashboard控制面板
frontend emqtt-admin-front
  bind *:18085
  mode http
  default_backend emqtt-admin-backend

backend emqtt-admin-backend
  mode http
  balance roundrobin
  server emqx01 127.0.0.2:60016 check
  server emqx02 127.0.0.3:60017 check
  server emqx03 127.0.0.4:60018 check
```
### 启动命令
service haproxy start

### 停止
service haproxy stop
