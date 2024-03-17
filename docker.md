## 安装yum工具依赖
yum install -y yum-utils \
device-mapper-persistent-data \
lvm2 --skip-broken
## 设置国内docker镜像
yum-config-manager \
--add-repo \
https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
sed -i 's/download.docker.com/mirrors.aliyun.com\/docker-ce/g' /etc/yum.repos.d/docker-ce.repo
yum makecache fast
## 安装docker
yum install -y docker-ce

## 关闭防火墙或放开指定端口
### 关闭防火墙
systemctl stop firewalld
### 关闭防火墙自启动
systemctl disable firewalld
## docker操作
### 启停docker相关
systemctl start docker
systemctl stop docker
systemctl restart docker
### 查看docker版本
docker -v
### 配置Docker运行时镜像加速器
#### CentOS 7
```shell 
sudo cp /lib/systemd/system/docker.service /etc/systemd/system/docker.service
sudo sed -i "s|ExecStart=/usr/bin/docker daemon|ExecStart=/usr/bin/docker daemon --registry-mirror=https://aooc7t0v.mirror.aliyuncs.com|g" /etc/systemd/system/docker.service
sudo sed -i "s|ExecStart=/usr/bin/dockerd|ExecStart=/usr/bin/dockerd --registry-mirror=https://aooc7t0v.mirror.aliyuncs.com|g" /etc/systemd/system/docker.service
sudo systemctl daemon-reload
sudo service docker restart            
```

### 从镜像站拉取镜像
docker pull nginx
### 查看镜像列表
docker images -a
```shell
[root@MiWiFi-RA81-srv ~]# docker images -a
REPOSITORY   TAG       IMAGE ID       CREATED       SIZE
nginx        latest    605c77e624dd   2 years ago   141MB
``` 

### 保存镜像
sudo docker save -o nginx.tar nginx:latest

### 本地load镜像
docker load -i nginx.tar

### 启动docker container
docker run --name my-nginx -d -p 8080:80 nginx:latest #
docker run --name my-nginx -d -p 8080:80 -v nginx-html:/usr/share/nginx/html nginx:latest #添加挂载宿主机Volume卷
docker run --name my-nginx -d -p 8080:80 -v [宿主机目录]:/usr/share/nginx/html nginx:latest #添加宿主机目录挂载
docker run --name my-nginx -d -p 8080:80 -v [宿主机文件]:/etc/nginx/nginx.conf nginx:latest #添加宿主机文件挂载
docker run --name my-redis -d -p 8080:80 redis:latest
> 8080端口映射到容器内80端口

### 以bash形式进入容器
docker exec -it my-nginx bash


### 创建Volume数据卷
docker volume create nginx-config

## Dockerfile

## RabbitMQ
### 拉取创建RabbitMQ容器
docker pull rabbitmq:3-management
docker run -d --hostname my-rabbitMQ --name my-rabbit -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=demo -e RABBITMQ_DEFAULT_PASS=demo rabbitmq:3-management