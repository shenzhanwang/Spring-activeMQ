# Spring-activeMQ
  在业务逻辑的异步处理，系统解耦，分布式通信以及控制高并发的场景下，消息队列有着广泛的应用。本项目基于Spring这一平台，整合流行的开源消息队列中间件ActiveMQ,实现一个向ActiveMQ添加和读取消息的功能。并比较了两种模式：生产者-消费者模式和发布-订阅模式的区别。
  包含的特性如下：
  
1. 给消息的生产者和发布者分别注册了两个消费者和订阅者，当有消息到达activeMQ时，消费者和订阅者会自动获取对应的消息，可以在控制台看到结果；

2.使用时，将war文件放入tomcat的webapps目录下，启动服务器，开启activeMQ，访问http://localhost:8080/Spring-activeMQ/demo  ，如下图：

 ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/QQ%E6%88%AA%E5%9B%BE20161109084315.jpg)
 
3.填入要发送的消息，点击生产消息可以向消息队列添加一条消息，我们这里添加了四条消息，并观察控制台结果如下 

 ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/QQ%E6%88%AA%E5%9B%BE20161109084634.jpg)
 
 4.重复以上操作发布四条消息，可以看到订阅者的输出如下：
 
  ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/QQ%E6%88%AA%E5%9B%BE20161109084912.jpg)
  
 5.以上结果表明，向队列生产的每条消息，只能被某一个消费者读取，而发布的消息，可以被每个订阅者重复读取，这是两种模式最大的区别。
 
  ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/QQ%E6%88%AA%E5%9B%BE20161109085633.jpg)
   ![alt text](https://github.com/shenzhanwang/Spring-activeMQ/blob/master/%E6%88%AA%E5%9B%BE/QQ%E6%88%AA%E5%9B%BE20161109085653.jpg)



 



