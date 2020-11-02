# Spring-activeMQ
  在业务逻辑的异步处理，系统解耦，分布式通信以及控制高并发的场景下，消息队列有着广泛的应用。本项目基于Spring这一平台，整合流行的开源消息队列中间件ActiveMQ,实现一个向ActiveMQ添加和读取消息的功能。并比较了两种模式：生产者-消费者模式和发布-订阅模式的区别。
包含的特性如下：  
  
1.开启activeMQ，访问http://localhost:8080/demo 

2 在项目中，我们为消息的生产者和发布者分别注册了两个消费者和订阅者，当有消息到达activeMQ时，消费者和订阅者会自动获取对应的消息，其中两个消费者会轮流消费消息，而两个订阅者会同时订阅所有消息；

 
3.填入要发送的消息，点击生产消息可以向消息队列添加一条消息，我们可以试着添加了四条消息，并观察控制台结果，可以发现每个消息只被某一个消费者接收； 

 
4.重复以上操作发布四条消息，可以看到订阅者的输出结果，表明每个发布的消息可以被两个订阅者全部接收；
 
   
5.以上结果表明，向队列生产的每条消息，只能被某一个消费者读取，而发布的消息，可以被每个订阅者重复读取，这是两种模式最大的区别，实际应用中要根据情况来选择。

![输入图片说明](https://images.gitee.com/uploads/images/2020/0911/103854_16a4d0de_1110335.png "2.png")
 
 ![输入图片说明](http://git.oschina.net/uploads/images/2016/1116/081301_8afc2c36_1110335.jpeg "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2016/1116/081309_5446619e_1110335.jpeg "在这里输入图片标题")

# ActiveMQ集群搭建
- 原理图：

![输入图片说明](https://git.oschina.net/uploads/images/2017/0925/175029_a0863fbb_1110335.png "QQ截图20170925175003.png")
- activeMQ集群服务器个数为单数，一主多从，主服务器选举方式依赖zookeeper,因此zookeeper集群若不可用，则activeMQ集群不可用。客户端请求与主服务器通信，从服务器不工作，但能同步主服务器的数据。主服务器宕机，自动选举一台从服务器为主服务器。集群要求至少一半以上服务器不能宕机。
- 一个activeMQ集群中同一时刻只有一台主服务器在工作，为避免单点故障，可以建立多个activeMQ集群，然后配置将他们桥接起来，所有集群内的队列、生产者消费者都可以共享，以达到负载均衡，横向扩展集群性能的目的。
### 搭建第一个activeMQ集群（activeMQ版本号5.14.5）
找一台服务器，把activeMQ的安装文件夹拷贝两次，分别进行配置：
### 修改conf/jetty.xml
```
<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
<!-- the default port number for the web console -->
<property name="host" value="0.0.0.0"/>
<property name="port" value="8361"/>
</bean>
```
这里配置web控制台端口为8361，另外两台分别改为8362、8363。
### 修改conf/activemq.xml持久化适配器
```
<persistenceAdapter>
<!-- kahaDB directory="${activemq.data}/kahadb"/ -->
<replicatedLevelDB directory="${activemq.data}/leveldb" replicas="3" bind="tcp://0.0.0.0:63631" zkAddress="192.168.1.81:2181,192.168.1.82:2182,192.168.1.83:2183" hostname="edu-mq-01" zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
```
```
<persistenceAdapter>
<!-- kahaDB directory="${activemq.data}/kahadb"/ -->
<replicatedLevelDB directory="${activemq.data}/leveldb" replicas="3" bind="tcp://0.0.0.0:63632"
zkAddress="192.168.1.81:2181,192.168.1.82:2182,192.168.1.83:2183" hostname="edu-mq-01" zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
```
```
<persistenceAdapter>
<!-- kahaDB directory="${activemq.data}/kahadb"/ -->
<replicatedLevelDB directory="${activemq.data}/leveldb" replicas="3" bind="tcp://0.0.0.0:63633"
zkAddress="192.168.1.81:2181,192.168.1.82:2182,192.168.1.83:2183" hostname="edu-mq-01" zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
```
zkAddress填zookeeper地址，ZkPath指集群选举信息在zookeeper的存放路径。
### 修改各节点的消息端口
```
<transportConnectors>
<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
<transportConnector name="openwire" uri="tcp://0.0.0.0:53531?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="amqp" uri="amqp://0.0.0.0:5662?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```
```
<transportConnectors>
<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
<transportConnector name="openwire" uri="tcp://0.0.0.0:53532?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="amqp" uri="amqp://0.0.0.0:5663?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="stomp" uri="stomp://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1884?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="ws" uri="ws://0.0.0.0:61615?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```
```
<transportConnectors>
<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
<transportConnector name="openwire" uri="tcp://0.0.0.0:53533?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="amqp" uri="amqp://0.0.0.0:5664?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="stomp" uri="stomp://0.0.0.0:61615?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1885?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="ws" uri="ws://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```
注意端口号不要冲突。
依次启动节点，观察zookeeper内数据的值，可以判断当前主节点是哪一台服务器。可以关闭部分服务器来测试集群的高可用性。
### java连接activeMQ
```
failover:(tcp://192.168.252.128:53531,tcp://192.168.252.128:53532,tcp://192.168.252.128:53533)?randomize=false
```
### 集群的扩展
由于一个activeMQ集群只有一个主服务器在工作，对消息的吞吐量是有限的。要想进行扩展，则需要把多个集群桥接起来，这样连接到任意一个集群，即可与整个集群的队列，生产者，消费者之间进行通信。在activemq.xml新增：
```
<networkConnectors>
<networkConnector
uri="static:(tcp://192.168.1.101:53531,tcp://192.168.1.101:53532,tcp://192.168.1.101:53533)"
duplex="false"/>
</networkConnectors>
```
以上配置即可完成到tcp://192.168.1.101:53531,tcp://192.168.1.101:53532,tcp://192.168.1.101:53533集群的链接。注意要相互配。

### 附录：中央技术储备仓库（Central Technique Reserve Repository）

#### 基础篇:职业化，从做好OA系统开始
1. [Spring boot整合Mybatis实现增删改查（支持多数据源）](https://gitee.com/shenzhanwang/SSM)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
2. [Struts2,Hibernate,Spring三大框架的整合实现增删改查](https://gitee.com/shenzhanwang/S2SH)
3. [Spring,SpringMVC和Hibernate的整合实现增删改查](https://gitee.com/shenzhanwang/SSH)
4. [Spring boot整合activiti工作流引擎实现OA开发](https://gitee.com/shenzhanwang/Spring-activiti)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
5. [Spring发布与调用REST风格的WebService](https://gitee.com/shenzhanwang/Spring-REST)
6. [Spring boot整合Axis调用SOAP风格的web服务](https://gitee.com/shenzhanwang/Spring-axis)
7. [Spring boot整合Apache Shiro实现RBAC权限控制](https://gitee.com/shenzhanwang/Spring-shiro)
8. [使用Spring security实现RBAC权限控制](https://gitee.com/shenzhanwang/spring-security-demo)
9. [Spring整合Jasig CAS框架实现单点登录](https://gitee.com/shenzhanwang/Spring-cas-sso)

#### 中级篇：中间件的各种姿势
10. [Spring boot整合mongoDB文档数据库实现增删改查](https://gitee.com/shenzhanwang/Spring-mongoDB)
11. [Spring连接Redis实现缓存](https://gitee.com/shenzhanwang/Spring-redis)
12. [Spring连接图存数据库Neo4j实现增删改查](https://gitee.com/shenzhanwang/Spring-neo4j)
13. Spring boot整合列存数据库hbase实现增删改查
14. [Spring平台整合消息队列ActiveMQ实现发布订阅、生产者消费者模型（JMS）](https://gitee.com/shenzhanwang/Spring-activeMQ)
15. [Spring boot整合消息队列RabbitMQ实现四种消息模式（AMQP）](https://gitee.com/shenzhanwang/Spring-rabbitMQ)
16. Spring boot整合kafka 2.1.0实现大数据消息管道
17. [Spring boot整合websocket实现即时通讯](https://gitee.com/shenzhanwang/Spring-websocket)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
18. [Spring security整合oauth2实现token认证](https://gitee.com/shenzhanwang/Spring-security-oauth2)
19. [Spring MVC整合FastDFS客户端实现文件上传](https://gitee.com/shenzhanwang/Spring-fastdfs)
20. 23种设计模式，源码、注释、使用场景 
21. [使用ETL工具Kettle的实例](https://gitee.com/shenzhanwang/Kettle-demo)
22. Git指南和分支管理策略 
23. 使用数据仓库进行OLAP数据分析（Mysql+Kettle+Zeppelin）
#### 高级篇：架构之美
24. [zookeeper原理、架构、使用场景和可视化](https://gitee.com/shenzhanwang/zookeeper-practice)
25. Spring boot整合Apache dubbo v2.7.5实现分布式服务治理（SOA架构） ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") 
>  包含组件Spring boot v2.2.2+Dubbo v2.7.5+Nacos v1.1.1
<a href="https://images.gitee.com/uploads/images/2020/0114/084731_fd0b7a82_1110335.gif" target="_blank">效果图</a>
26. 使用Spring Cloud Alibaba v2.1.0实现微服务架构（MSA架构）![输入图片说明](https://img.shields.io/badge/-%E6%8B%9B%E7%89%8C-yellow.svg)   
>  包含组件Nacos+Feign+Gateway+Ribbon+Sentinel+Zipkin
<a href="https://images.gitee.com/uploads/images/2020/0106/201827_ac61db63_1110335.gif" target="_blank">效果图</a>
27. 使用jenkins+centos+git+maven搭建持续集成环境自动化部署分布式服务 
28. 使用docker+compose+jenkins+gitlab+spring cloud实现微服务的编排、持续集成和动态扩容 
29. 使用FastDFS搭建分布式文件系统（高可用、负载均衡）
30. 搭建高可用nginx集群和Tomcat负载均衡 
31. 使用mycat实现Mysql数据库的主从复制、读写分离、分表分库、负载均衡和高可用 
32. [Spring boot整合Elastic search实现全文检索和大数据分析](https://gitee.com/shenzhanwang/Spring-elastic_search) ![输入图片说明](https://img.shields.io/badge/-%E6%8B%9B%E7%89%8C-yellow.svg "在这里输入图片标题")
#### 特别篇：分布式事务和并发控制
33. 基于可靠消息最终一致性实现分布式事务（activeMQ）
34. Spring boot dubbo整合seata实现分布式事务![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
> 包含组件nacos v1.1.0 + seata v0.7.1 +spring boot dubbo v2.7.5
<a href="https://images.gitee.com/uploads/images/2020/0119/112233_62a33a77_1110335.gif" target="_blank">效果图</a>
35. Spring cloud alibaba v2.1.0整合seata实现分布式事务 ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
> 包含组件nacos v1.1.0 + seata v0.7.1 +spring cloud alibaba v2.1.0
<a href="https://images.gitee.com/uploads/images/2020/0119/134408_ee14a016_1110335.gif" target="_blank">效果图</a>
36. 并发控制：数据库锁机制和事务隔离级别的实现![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") 
37. 并发控制：使用redis实现分布式锁  ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
38. 并发控制：使用zookeeper实现分布式锁 
39. 并发控制：Java多线程编程实例
40. 并发控制：使用netty实现高性能NIO通信 
### 视频演示&PPT讲解
- 第一讲：技术架构演进史和分布式系统
- 第二讲 分布式服务治理（SOA和微服务）的搭建方法
- 第三讲：分布式事务的原理和实现（事务消息、TCC、seata）
- 第四讲：消息队列的使用讲解（activeMQ、rabbitMQ，kafka）
- 第五讲：分布式锁的三种实现（zookeeper、mysql、redis）
- 第六讲：elastic search全文检索和大数据分析的实现(ELK平台)
- 第七讲：分布式缓存redis、文件系统（fastdfs，hdfs）、数据库（mycat，hbase）和负载均衡（nginx）的原理介绍

### 购买入口
<a href="http://www.vmfaka.net/list/UZvwyHjbu" target="_blank">我的网店</a>

<a href="http://www.vmfaka.net/list/fdxxX9PpS0s" target="_blank">全部源码</a>

<a href="http://www.vmfaka.net/list/fdxxWN5jUUs" target="_blank">全部视频和136页PPT</a>