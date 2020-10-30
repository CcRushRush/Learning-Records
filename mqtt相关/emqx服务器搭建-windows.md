## Windows搭建集群+nginx负载均衡（参考，不建议）：https://blog.csdn.net/weixin_44032502/article/details/107972171

### 每一个emqx节点修改./etc/emqx.conf
1. 集群发现模式，静态发现，启动后不用输加入集群命令
cluster.discovery = static 
2. 集群列表，配合上面static发现策略使用，这里是每一个节点名
cluster.static.seeds = emqx1@192.168.1.128,emqx2@192.168.1.135,emqx3@192.168.1.136
3. 节点名
node.name = emqx1@192.168.1.128
4. 集群通信端口段
node.dist_listen_min = 6369
node.dist_listen_max = 7369

### Emqx负载均衡--nginx配置(最好使用haproxy做tcp的负载均衡，nginx一般做短连接的负载均衡)
```
stream{
    upstream emqx_cluster {
		hash $remote_addr consistent;
        server 192.168.14.38:18083;max_fails=2 fail_timeout=30s;(表示允许请求失败次数为2次，超过2次后，暂停30秒)
        server 192.168.14.180:18083;
    }
 
    server{
       listen  18084 so_keepalive=on;
       proxy_connect_timeout 10s;
       proxy_timeout 20s;
       proxy_pass emqx_cluster;
    }
	
	upstream emqx_server {
		hash $remote_addr consistent;
		server 192.168.14.38:1883;
		server 192.168.14.180:1883;
    }
 
    server{
       listen  1882 so_keepalive=on;
       proxy_connect_timeout 10s;
       proxy_timeout 20s;
       proxy_pass emqx_server;
    }
}
```
访问nginx的8883端口，自动映射到emqx集群
