# Spring-activeMQ
  在业务逻辑的异步处理，系统解耦，分布式通信以及控制高并发的场景下，消息队列有着广泛的应用。本项目基于Spring这一平台，整合流行的开源消息队列中间件ActiveMQ,实现一个向ActiveMQ添加和读取消息的功能。并比较了两种模式：生产者-消费者模式和发布-订阅模式的区别。
包含的特性如下：  
  
1.使用时，将war文件放入tomcat的webapps目录下，启动服务器，开启activeMQ，访问http://localhost:8080/Spring-activeMQ/demo  ，我们可以在页面顶端看到一个黑色的控制台，用于监控消息的内容，如下图：
 ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/1.jpg)  
 

2 在项目中，我们为消息的生产者和发布者分别注册了两个消费者和订阅者，当有消息到达activeMQ时，消费者和订阅者会自动获取对应的消息，可以在前端控制台看到结果（前端页面控制台是基于websocket全双工通信协议实现的，可用于将服务器端的信息主动推送到浏览器，在本项目中不做重点介绍）；

 
3.填入要发送的消息，点击生产消息可以向消息队列添加一条消息，我们可以试着添加了四条消息，并观察控制台结果，可以发现每个消息只被某一个消费者接收； 

 
4.重复以上操作发布四条消息，可以看到订阅者的输出结果，表明每个发布的消息可以被两个订阅者全部接收；
 
   
5.以上结果表明，向队列生产的每条消息，只能被某一个消费者读取，而发布的消息，可以被每个订阅者重复读取，这是两种模式最大的区别，实际应用中要根据情况来选择。
 
  ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/QQ%E6%88%AA%E5%9B%BE20161109085633.jpg)
   ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/QQ%E6%88%AA%E5%9B%BE20161109085653.jpg)

### 附录：个人作品索引目录（持续更新）

#### 基础篇:职业化，从做好OA系统开始
1. [SpringMVC,Mybatis,Spring三大框架的整合实现增删改查](https://gitee.com/shenzhanwang/SSM)
2. [Struts2,Hibernate,Spring三大框架的整合实现增删改查](https://gitee.com/shenzhanwang/S2SH)
3. [Spring,SpringMVC和Hibernate的整合实现增删改查](https://gitee.com/shenzhanwang/SSH)
4. [Spring平台整合activiti工作流引擎实现OA开发](https://gitee.com/shenzhanwang/Spring-activiti)
5. [Spring发布与调用REST风格的WebService](https://gitee.com/shenzhanwang/Spring-REST)
6. [Spring整合Apache Shiro框架，实现用户管理和权限控制](https://gitee.com/shenzhanwang/Spring-shiro)
7. [使用Spring security做权限控制](https://gitee.com/shenzhanwang/spring-security-demo)
8. [Spring整合Jasig CAS框架实现单点登录](https://gitee.com/shenzhanwang/Spring-cas-sso)
#### 中级篇：中间件的各种姿势
9. [Spring连接mongoDB数据库实现增删改查](https://gitee.com/shenzhanwang/Spring-mongoDB)
10. [Spring连接Redis实现缓存](https://gitee.com/shenzhanwang/Spring-redis)
11. [Spring连接图存数据库Neo4j实现增删改查](https://gitee.com/shenzhanwang/Spring-neo4j)
12. [Spring平台整合消息队列ActiveMQ实现发布订阅、生产者消费者模型（JMS）](https://gitee.com/shenzhanwang/Spring-activeMQ)
13. [Spring整合消息队列RabbitMQ实现四种消息模式（AMQP）](https://gitee.com/shenzhanwang/Spring-rabbitMQ)
14. Spring框架的session模块实现集中式session管理（未开源）
15. [Spring整合websocket实现即时通讯](https://gitee.com/shenzhanwang/Spring-websocket)
16. 使用Spring boot整合mybatis,rabbitmq,redis,mongodb实现增删改查（未开源）
17. [Spring MVC整合FastDFS客户端实现文件上传](https://gitee.com/shenzhanwang/Spring-fastdfs)
18. 23种设计模式，源码、注释、使用场景（未开源）
19. 使用ETL工具Kettle的实例（未开源）
20. Git指南和分支管理策略（未开源）
#### 高级篇：架构之美
21. [搭建zookeeper集群提供目录服务](https://gitee.com/shenzhanwang/zookeeperjiqundajian)
22. 使用ubuntu+apache+SVN+SVNadmin+maven+Nexus+Hudson搭建持续集成环境（未开源）
23. 使用jenkins+centos+git+maven搭建持续集成环境自动化部署分布式服务（未开源）
24. Spring框架整合dubbo框架实现分布式服务治理（SOA架构）（未开源）
25. Spring框架整合dubbox实现微服务架构（MSA架构）（未开源）
26. 使用Spring Cloud实现微服务架构（MSA架构）（未开源）
27. 使用FastDFS搭建分布式文件系统（高可用、负载均衡）（未开源）
28. 搭建高可用nginx集群和Tomcat负载均衡（未开源）
29. 搭建可扩展的ActiveMQ高可用集群（未开源）
30. 实现Mysql数据库的主从复制、读写分离、分表分库、负载均衡和高可用（未开源）
31. 搭建高可用redis集群实现分布式缓存（未开源）
32. [Spring整合SolrJ实现全文检索](https://gitee.com/shenzhanwang/Spring-solr)
#### 特别篇：分布式事务和并发控制
33. 基于可靠消息最终一致性实现分布式事务（activeMQ）（未开源）
34. 使用TCC框架实现分布式事务（dubbo版）（未开源）
35. 使用TCC框架实现分布式事务（Spring Cloud版）（未开源）
36. 决战高并发：数据库锁机制和事务隔离级别的使用（未开源）
37. 决战高并发：Java多线程编程实例（未开源）
38. 决战高并发：使用netty实现高性能NIO通信（未开源）

### 捐赠区
![输入图片说明](https://images.gitee.com/uploads/images/2018/0719/154323_12a5c89c_1110335.jpeg "mm_facetoface_collect_qrcode_1531986023521.jpg")


 



