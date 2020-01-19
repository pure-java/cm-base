docker-compose 是一个基于原有镜像，快速构建容器的工具，需要在安装有docker、docker-compose 软件的服务器上运行。

通过编辑 docker-compose.yml 文件，声明需要创建的容器，然后在服务器上运行该文件成功则自动创建docker容器。


##### docker-compose 参数 

-f ： 指定 docker-compose.yml 文件的路径，不指定则在当前目录查找该文件

-p ： 指定 docker-compose 项目名，默认以当前目录作为项目名，也就是存储 docker-compose.yml 文件的目录

--verbose ：显示更多输出

#####docker-compose命令

需要当前目录下有 docker-compose.yml 文件或指定文件路径 

docker-compose up
                
     Usage: up [options] [SERVICE...]
     
     Options:
         -d                         后台运行，输出容器的名字.
                                    Incompatible with --abort-on-container-exit.
         --no-color                  单色输出.
         --no-deps                  不启动link服务.
         --force-recreate           强制重新创建compose服务.‘’：，即使没有任何改变。重新创建后启动容器
                                    Incompatible with --no-recreate.
         --no-recreate               如果容器已经存在，不重新创建.
                                    Incompatible with --force-recreate.
         --no-build                 不创建重启，即使镜像不存在.
         --build                    重新创建镜像，然后生成容器.
         --abort-on-container-exit  任何容器停止，自动停止所有容器.
                                    Incompatible with -d.
         -t, --timeout TIMEOUT      超时时间. (default: 10)
         --remove-orphans           移除compose文件中未定义服务的容器

docker-compose build ： 构建镜像

docker-compose config ： 校验和查看compose文件配置
    
    Options:
        -q, --quiet     只验证配置，不输出。 当配置正确时，不输出任何内容，当文件配置错误，输出错误信息。
        --services      打印服务名，一行一个
    
docker-compose create ： 为服务创建容器.只是单纯的create，还需要使用start启动compose

    Options:
        --force-recreate       重新创建容器，即使他的配置和镜像没有改变，不兼容--no-recreate参数
        --no-recreate          如果容器已经存在，不需要重新创建. 不兼容--force-recreate参数
        --no-build             不创建镜像，即使缺失.
        --build                创建容器前，生成镜像.

docker-compose up ： 运行当前目录下的docker-compose.yml，执行创建容器操作，控制台输出创建容器日志，通过ctrl+D 关闭日志，创建的docker容器自动关闭

docker-compose down ：停止和删除容器、网络、卷、镜像，这些内容是通过docker-compose up命令创建的. 默认值删除 容器 网络，可以通过指定 rmi volumes参数删除镜像和卷

    Options:
        --rmi type          删除镜像，类型必须是:
                            'all': 删除compose文件中定义的所以镜像.
                            'local': 删除镜像名为空的镜像
         -v, --volumes      删除卷
                            attached to containers.
        --remove-orphans    删除未在compose文件中定义的服务的容器
    
docker-compose up -d ：在后台运行当前目录下的docker-compose.yml，执行创建容器操作，不会在控制台输出日志。  

docker-compose events ：输出docker-compose 事件的日志，当执行docker-compose命令操作时，docker-compose even命令就会监控日志

    Options:
        --json      输出事件日志，json格式

docker-compose logs -f ： 查看输出日志

docker-compose stop ：停止 compose 文件里面声明的服务

docker-compose restart ：重启 compose 文件里面声明的服务

docker-compose start ：启动 compose 文件里面声明的服务

docker-compose pause ：暂停容器服务. docker-compose pause 暂停所有服务. docker-compose pause web 暂停web服务的容器。
docker-compose unpause ：恢复容器服务. docker-compose pause 恢复所有服务. docker-compose pause web 恢复web服务的容器。

docker-compose kill ： kill  compose 文件里面声明的服务

docker-compose ps ： 查看 compose 文件里面声明的服务

docker-compose rm ：删除 compose 文件里面声明的服务

docker-compose port SERVICE PRIVATE_PORT ：输出 compose 文件里声明服务的共有端口，docker-compose port mysql 3306

docker-compose scale ：设置服务启动数量  docker-compose scale web=2 worker=3


    

#####docker-compose.yml  元素
            
image：指定为镜像名称或镜像 ID

build：指定 Dockerfile 所在文件夹的路径

depends_on：解决容器的依赖、启动先后的问题

expose：暴露端口，但不映射到宿主机，只被连接的服务访问

ports：端口映射，可以指定端口映射和随机映射。

network_mode：设置网络模式

extra_hosts：指定额外的 host 名称映射信息，启动后会在hosts文件自动追加

dns：自定义 DNS 服务器。可以是一个值，也可以是一个列表。

environment：设置环境变量

secrets：存储敏感数据。

volumes：数据卷所挂载路径设置。可以设置宿主机路径 （HOST:CONTAINER） 或加上访问模式 （HOST:CONTAINER:ro）

command：覆盖容器启动后默认执行的命令。

sysctls：配置容器内核参数。