Docker环境部署
通过脚本下载dockers：
curl -fsSL get.docker.com -o get-docker.sh
sudo sh get-docker.sh --mirror Aliyun

Dockers镜像加速：
在/etc/docker/daemon.json 中写入如下内容（如果文件不存在请新建该文件）
{
  "registry-mirrors": [
    "https://registry.docker-cn.com"
  ]
}
重新启动服务。
systemctl daemon-reload
systemctl restart docke

Docker compose 下载
运行一下命令行
curl -L https://get.daocloud.io/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
修改权限chmod +x /usr/local/bin/docker-compose
