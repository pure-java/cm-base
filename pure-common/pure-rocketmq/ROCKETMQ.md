

### rocketmq

####  各组件介绍

1. nameserver

   nameserver 各节点不互相通信；管理broker集群、consumer集群、producer集群。

   路由注册与剔除：Broker主动轮询nameserver节点，发送心跳包；并且nameserver间隔10s读取broker 表，如果距上一次发送心跳超过120s，把该broker移除；或者broker主动退出。

   ```
   broker注册：向nameserver提供了BrokerId、Broker地址、Broker名称、Broker所属集群名称、broker拥有的topic信息。
   nameserver在处理broker的心跳时，采用读写锁保证线程安全：多并发的处理读请求与单线程的处理心跳请求。
   nameserver在剔除broker时，将可能剔除没有broker的集群、topic、BrokerData。
   ```

   客户端路由发现：producer（发送第一条消息）和consumer（启动时）从nameserver获取topic路由信息。

   ```
   client会间隔30秒从nameserver获取topic路由信息。如果在30s内产生的无法感知的异常或故障，producer采用重试机制(只针对普通消息，有序消息不会重试)，并且增加了故障隔离、延迟隔离机制；对于有序消息，将会经过短暂无效后变为有序。
   ```

    

2. broker

   创建和维护 topic queue信息，并告诉nameserver，该broker上的topic相关信息。

   接收producer、consumer请求，并且维护客户端的topic订阅信息。

   提供主从同步、持久化、查询服务。

   ```txt
   broker topic：
   	topic的创建分为集群和单节点创建；集群创建时，集群中所有broker都会创建topic 数量相同的queue；单节点创建则只会在指定的broker中创建topic；broker接收到更新或创建topic请求后，会向nameserver发送更新或创建topic的请求，nameserver则将broker topic的信息更新。
   ```

   ```txt
   主从同步：rocketmq主从同步不支持主从切换，主节点宕机后，从节点不会接管消息发送，但可以提供消息读取。当主节点堆积消息超过物理内存的40%后，将会从从节点拉取消息进行消费。
   producer发送消息到主节点，主节点通过同步或异步的方式将数据写入从节点。
   consumer消费时，会记录消费到的位置（consumerOffset），并且提交给主节点或从节点，并且从节点会定时同步消费者相关消息（消费者配置、消费者offset、延迟、订阅组配置 ）。
   
   ```

   ```txt
   持久化：rocket持久化到文件系统中，顺序写入到commitLog中，使用indexFile提供对commitLog的索引，以供数据检索。consumer读取消息时，从queue中获取到消息在commitLog中的偏移量，在随机从commitLog中读取数据。
   ```

   

3. topic

   topic是一个逻辑上的概念，由具体的 topic queue 组成。

   ```
   集群模式下创建的topic，每个broker都有相同数量的queue，指定具体broker则只在该broker创建topic以及queue；
   consumer可以指定具体queue或使用分配策略获取能消费的queue；
   producer生产的消息发送到的queue可以指定具体queue，或根据分配策略进行分配。
   ```

   * tag和keys

     tag对topic内的消息进行区分，keys对topic的消息进行业务标识，以便定位消息

4. producer

   消息生产者，与nameserver和broker都建立了长连接，但是只与broker建立了心跳机制；多个producer组成producer集群。

   根据分配策略将消息发送到对应的broker queue中。

   支持消息重发机制：默认重试2次。

   ```
   分配策略(队列选择器)：使用 MessageQueueSelector，指定将消息发送到计算出的queue中。
   策略有：
   1. hash选择器(SelectMessageQueueByHash)，使用参数来决定queue。
   2. 随机选择器(SelectMessageQueueByRandom)，随机选择queue。
   3. 机房选择器(SelectMessageQueueByMachineRoom)，未提供默认实现。
   ```

   

5. consumer

   consumer与nameserver和broker建立长连接，nameserver不可连接之后自动连接下一个nameserver，直到有可用连接为止，并自动重连；

   与broker建立心跳机制，如果broker不可连接，则通知同一个消费者组下的所有消费者，进行queue重平衡。

   ```
   consumer集群节点数与queue数量关系非常紧密。应该保证consumer数量小于等于queue，以保证每一个consumer都能消费数据。
   ```

   

#### 消费者

消息重复消费

```
使用业务ID，在消费消息时，将业务ID存储在redis或数据库中，保证消息不被重复消费。
在消费消息过程中，出现异常，需要将业务ID持久化数据删除，以保证下次消费重试。
```

