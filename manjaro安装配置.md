# Learning-Records
The Way of Growth

### 使用中国源并切换到稳定分支

```
sudo pacman-mirrors -c China --api -B stable
```

### 增加archlinuxcn库和antergos库

```
echo -e "[archlinuxcn]\nSigLevel = TrustAll\nServer = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/\$arch\n[antergos]\nSigLevel = TrustAll\nServer = https://mirrors.tuna.tsinghua.edu.cn/antergos/\$repo/\$arch"|sudo tee -a /etc/pacman.conf
```

### 系统全面更新
```
sudo pacman -Syyu --noconfirm
```

### 安装archlinuxcn签名钥匙&antergos签名钥匙&某些软件
```
sudo pacman -Sy --noconfirm archlinuxcn-keyring antergos-keyring wget yaourt git conky jq vim clang cmake powerline-fonts autojump
```

### 处理更新后的乱码问题
```
sudo pacman -Sy --noconfirm wqy-zenhei ttf-fireflysung
```

### github加速
```
echo -e "151.101.72.249 global-ssl.fastly.Net\n192.30.253.118 github.com"|sudo tee -a /etc/hosts
```


### 删除无用的软件(for kde)
```
sudo pacman -R --noconfirm steam-manjaro libreoffice-fresh firefox
```


### 安装常用软件
```
sudo pacman -Sy --noconfirm filezilla electronic-wechat fcitx-sogoupinyin pandoc shadowsocks google-chrome fish shadowsocks-libev wps-office create_ap pavucontrol nutstore seahorse virtualbox-guest-iso fcitx-im fcitx-configtool fcitx-cloudpinyin netease-cloud-music telegram-desktop xterm bomi-git go zip unzip arj lzop cpio unrar thunar xfce4-terminal dia inkscape gimp ttf-wps-fonts anydesk
```


### 配置fcitx
```
echo -e "export GTK_IM_MODULE=fcitx\nexport QT_IM_MODULE=fcitx\nexport XMODIFIERS=@im=fcitx">>~/.xprofile
```



### 编程开发
```
sudo pacman -Sy --noconfirm atom eclipse-jee openjdk8-doc openjdk8-src 
```

### 清理所有的缓存文件
```
sudo pacman -Scc 
```

> 下面是可选项
---

### 安装网易云音乐

```
sudo pacman -S netease-cloud-music
```

### 安装搜狗输入法

```
sudo pacman -S fcitx-im
sudo pacman -S fcitx-configtool
sudo pacman -S fcitx-sogoupinyin
# 注：添加输入法配置文件 sudo vim ~/.xprofile
export GTK_IM_MODULE=fcitx
export QT_IM_MODULE=fcitx
export XMODIFIERS="@im=fcitx"
```

### 安装 Apache
```
sudo pacman -S apache
#设置开机启动和重启 Apache 服务
sudo systemctl enable httpd
sudo systemctl restart httpd
```

### 安装 Mysql
```
sudo pacman -S mysql
```

### 初始化MariaDB数据目录，没有这步 mysql 就不能用
```
sudo mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
```

### 查看mysql状态
```
sudo systemctl status mysqld
```

### 开机启动mysql服务
```
sudo systemctl enable mysqld
sudo systemctl start mysqld
```
### 设置mysql root用户密码
```
sudo mysql_secure_installation
```
