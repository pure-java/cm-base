##### Dockerfile 

一个快速构建镜像的工具，是docker中镜像文件的的描述文件，描述如何构建一个docker镜像

在建立Docker镜像的过程中,DockerFile 按层编译执行，每个指令的编译将去搜寻缓存里存在的，而不是创立一个新的镜像。

#####属性

基本语句

From：第一条指令指定一个基本的镜像源，从公共库拉取一个镜像源。并且From必须是第一行。

    FROM <image>
    FROM <image>:<tag>
    FROM <image>@<digest>

MAINTAINER：设置作者。

    MAINTAINER <name>
LABEL：设置标签，采用键值对的形式。

    LABEL <key>=<value> <key>=<value> <key>=<value>

RUN：运行类linux 命令。

    RUN <command>
    RUN ["executable", "param1", "param2"]

EXPOSE：用来指定容器的监听端口，但是不绑定端口
    
    EXPOSE <port> [<port>...]

ENV：设置环境变量的键值。
    
    ENV <key> <value>
    ENV <key>=<value>

WORKDIR：设置工作目录，也是当前COPY ADD ENV 的路径

    WORKDIR /path/to/workdir

ADD、COPY：复制文件。

    COPY <src> <dest>
    ADD <src> <dest>

ADD在和COPY相同的基础上，增加：

    1、ADD 允许 <src>是一个 URL。
    
    2、ADD 的<src>是一个压缩格式文档<src>将会解压缩复制。
    
    虽然ADD 比COPY功能多，但是还是推荐使用COPY作为文件的复制，因为ADD的行为有些越界，下载会使用CURL以及make命令。
    
    所以使用 COPY除非你确信你需要 ADD。

CMD 与 Entrypoint
    1、CMD 和 Entrypoint一般用于制作具备后台服务的镜像, 如启动nginx，php-fpm, mysql 等。
    
    2、DockerFile应至少指定一个CMD命令或Entrypoint。
    
    3、都可以指定shell或exec函数调用的方式执行命令。
    
    4、DockerFile run 启动镜像之后便会退出容器，需要一个长时间运行的命令，使得容器一直执行。
    
    CMD ["executable","param1","param2"] （运行一个可执行的文件并提供参数）
    
    CMD ["param1","param2"] （为ENTRYPOINT指定参数）
    
    CMD command param1 param2 (以”/bin/sh -c”的方法执行的命令)
    
    ENTRYPOINT ["executable", "param1", "param2"] (首选执行形式)
    
    ENTRYPOINT command param1 param2 (以”/bin/sh -c”的方法执行的命令)

区别:

1、一个Dockerfile只能有一个CMD/ENTRYPOINT指令，如果有超过一个CMD将只启动并有效最后一个。

2、CMD在运行时会被command覆盖, ENTRYPOINT不会被运行时的command覆盖。

3、如果在Dockerfile中同时写了entrypoint和cmd则，docker在build过程中会将cmd中指定的内容作为entrypoint的参数。

需要初始化运行多个命令，彼此之间可以使用 && 隔开，但最后一个须要为无限运行的命令

注意
开始时RUN apt-get update 最后要清除apt 的缓存并移除 /var/lib/apt/lists 文件下的内容，使得镜像文件小。