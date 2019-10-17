# 本地部署（和服务器差不多）
*** ubuntu版本选择：Ubuntu server 16.04 LTS
## 安装 Ubuntu Server
具体安装过程请看视频：https://www.bilibili.com/video/av27095828/
1. 安装好后修改数据源
通过一下命令编辑数据源
```xml
vi /etc/apt/sources.list
```
先删除里面所有东西，然后添加以下
```xml
deb http://mirrors.aliyun.com/ubuntu/ xenial main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ xenial-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ xenial-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ xenial-backports main restricted universe multiverse
```
2. 更新数据源:apt-get update

3. 设置 Root 账户密码:sudo passwd root
4. 设置允许远程登录 Root  
输入命令:sudo vim /etc/ssh/sshd_config
```xml
#Authentication:
LoginGraceTime 120
#PermitRootLogin without-password     //注释此行
PermitRootLogin yes                             //加入此行
StrictModes yes
```
5. 重启服务:service ssh restart

## Linux 安装 Java
1. 下载jre(属于生产过程，jre版本自行选择，与下面的环境变量的jre版本一致，选择后缀名为：.tar.gz):
https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

2. 将jre包移动到服务器上，可以通过xftp或者其他
将jre包解压到：/usr/local/java；（如果/usr/local下没有java目录，就新建一个）

3. 配置系统环境变量
```xml
export JAVA_HOME=/usr/local/java
export JRE_HOME=/usr/local/java/jre1.8.0_231
export CLASSPATH=$JRE_HOME/lib/rt.jar:$JRE_HOME/lib/ext
export PATH=$PATH:$JRE_HOME/bin
```
4. 配置用户环境变量
在两个if语句中添加
```xml
export PATH=/usr/bin:/usr/sbin:/bin:/sbin:/usr/X11R6/bin
export JAVA_HOME=/usr/local/java             
export JRE_HOME=/usr/local/java/jre1.8.0_231
export CLASSPATH=$JRE_HOME/lib/rt.jar:$JRE_HOME/lib/ext
export PATH=$PATH:$JRE_HOME/bin
```
5. 运行一下语句已使环境变量生效：source /etc/profile
6. 通过java -version查看是否安装成功

## Linux 安装 Tomcat
1. 下载tomcat版本和jre版本一致，路径（选择后缀名为：.tar.gz）：https://tomcat.apache.org/download-80.cgi
2. 将tomcat移动到服务器上，可以通过xftp或者其他
3. 将tomcat包解压到：/usr/local

4. 进入tomcat的bin目录，使用命令：./startup.sh启动tomcat，在浏览器上输入服务器ip:port，可以运行tomcat。

## Linux 安装 MySQL
1. 安装 MySQL：apt-get install mysql-server
2. 配置远程访问：
    1. 修改配置文件：sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf
    2. 将bind-address = 127.0.0.1注释掉或者将127.0.0.1改为0.0.0.0
    3. 重启 MySQL：service mysql restart
    4. 在服务器上先登录一次MySQL：mysql -u root -p
    5. 授权 root 用户允许所有人连接：grant all privileges on *.* to 'root'@'%' identified by '你的 mysql root 账户密码';
