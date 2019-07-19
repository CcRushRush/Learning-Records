# Learning-Records
The Way of Growth

#使用中国源并切换到稳定分支
sudo pacman-mirrors -c China --api -B stable

#增加archlinuxcn库和antergos库
echo -e "[archlinuxcn]\nSigLevel = TrustAll\nServer = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/\$arch\n[antergos]\nSigLevel = TrustAll\nServer = https://mirrors.tuna.tsinghua.edu.cn/antergos/\$repo/\$arch"|sudo tee -a /etc/pacman.conf

#系统全面更新
sudo pacman -Syyu --noconfirm

#安装archlinuxcn签名钥匙&antergos签名钥匙&某些软件
sudo pacman -Sy --noconfirm archlinuxcn-keyring antergos-keyring wget yaourt git conky jq vim clang cmake powerline-fonts autojump

#github加速(下面“定时更新racaljk的hosts”脚本已包含)
echo -e "151.101.72.249 global-ssl.fastly.Net\n192.30.253.118 github.com"|sudo tee -a /etc/hosts

#使用jetbrains激活码要在hosts中屏蔽(下面“定时更新racaljk的hosts”脚本已包含)
echo "0.0.0.0 account.jetbrains.com"|sudo tee -a /etc/hosts

#定时更新racaljk的hosts
cd /etc/cron.hourly&&sudo touch update-hosts&&sudo chmod a+x update-hosts&&echo -e '#!/bin/sh\nLOG=/var/log/update-hosts.log\nif ping -c 1 151.101.72.133 2>&1 >/dev/null ;then\nwget https://raw.githubusercontent.com/racaljk/hosts/master/hosts -qO /tmp/hosts\necho "* $(date) * update hosts success">>$LOG\nmv /tmp/hosts /etc/hosts\nelse\necho "* $(date) * no internet access">>$LOG\nfi\necho "0.0.0.0 account.jetbrains.com">>/etc/hosts'|sudo tee /etc/cron.hourly/update-hosts&&sudo /etc/cron.hourly/update-hosts

#for kde
sudo pacman -R --noconfirm steam-manjaro libreoffice-fresh firefox

#安装常用软件sudo usermod -G vboxusers -a niracler此命令中的niracler为当前用户名

sudo pacman -Sy --noconfirm filezilla electronic-wechat fcitx-sogoupinyin franz pandoc shadowsocks google-chrome fish shadowsocks-libev wps-office create_ap pavucontrol nutstore seahorse virtualbox-ext-oracle virtualbox-guest-iso fcitx-im fcitx-configtool fcitx-cloudpinyin netease-cloud-music telegram-desktop xterm bilidan bomi-git go zip unzip arj lzop cpio unrar thunar engrampa-thunar-plugin xfce4-terminal dia inkscape gimp ttf-wps-fonts anydesk


#配置fcitx
echo -e "export GTK_IM_MODULE=fcitx\nexport QT_IM_MODULE=fcitx\nexport XMODIFIERS=@im=fcitx">>~/.xprofile

#把var目录中某些目录挂载到tmpfs
echo -e "tmpfs /var/log tmpfs defaults,noatime 0 0\ntmpfs /var/tmp tmpfs defaults,noatime 0 0\ntmpfs /var/spool tmpfs defaults,noatime 0 0"|sudo tee -a /etc/fstab&&sudo mount -a


#编程开发
sudo pacman -Sy --noconfirm atom eclipse-jee openjdk8-doc openjdk8-src  jetbrains-toolbox gitkraken
jetbrains-toolbox安装jetbrains全系列可以跑满带宽



#播放器
bomi 加速减速+ -
mpv  加速减速[ ]
bilidan 可以调用mpv播放bilibili 减少网页播放导致的耗电 参数-q 1 for the lowest, -q 4 for HD


#清理所有的缓存文件
sudo pacman -Scc 

#安装网易云音乐
sudo pacman -S netease-cloud-music

#安装搜狗输入法
sudo pacman -S fcitx-im
sudo pacman -S fcitx-configtool
sudo pacman -S fcitx-sogoupinyin
注：添加输入法配置文件 sudo vim ~/.xprofile
export GTK_IM_MODULE=fcitx
export QT_IM_MODULE=fcitx
export XMODIFIERS="@im=fcitx"

#安装git客户端
sudo yaourt GitKraken
