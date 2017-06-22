### [Eureka Server的高可用](#)

- ### 搭建双节点注册中心
   + 在[注册中心入门示例](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-1-1)中，我们只需要一个简单的Spring Boot Web项目，简单的两步就能搭建一个单节点注册中心，
   + Eureka Server除了单点运行之外，还可以通过运行多个实例，并进行互相注册的方式来实现高可用的部署，所以我们只需要将Eureke Server的**eureka.client.serviceUrl.defaultZone**与其他实例交叉配置就能实现高可用部署，对于双节点的大致架构图为：    
   ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/eureka双机.png?raw=true)  
   + 在该eureka双节点中，eureka server 1和eureka server 2的实行交叉注册策略，当客户端任何一个eureka client端点注册到eureka server的任何一个节点上，注册信息都将被同步到另一个节点，这样就可以实现双机热备。  

- ### 搭建多节点注册中心
   + [Eureka简介及原理](http://www.itmuch.com/spring-cloud-1/) 
     ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/Eureka官方架构图.png?raw=true)   
     以上是Eureka官方的架构图，大致描述了Eureka集群的工作过程。图中包含的组件非常多，可能比较难以理解，我们用通俗易懂的语言解释一下：  
       * Application Service 相当于服务提供者，Application Client相当于服务消费者；
       * Make Remote Call，可以简单理解为调用RESTful API；
       * us-east-1c、us-east-1d等都是zone，它们都属于us-east-1这个region；
       * Eureka Client是一个Java客户端，用于简化与Eureka Server的交互；
       * Eureka Server提供服务发现的能力，各个微服务启动时，会通过Eureka Client向Eureka Server进行注册自己的信息（例如网络信息），Eureka Server会存储该服务的信息；
       * 微服务启动后，会周期性地向Eureka Server发送心跳（默认周期为30秒）以续约自己的信息。如果Eureka Server在一定时间内没有接收到某个微服务节点的心跳，Eureka Server将会注销该微服务节点（默认90秒）；
       * 每个Eureka Server同时也是Eureka Client，多个Eureka Server之间通过复制的方式完成服务注册表的同步；
       * Eureka Client会缓存Eureka Server中的信息。即使所有的Eureka Server节点都宕掉，服务消费者依然可以使用缓存中的信息找到服务提供者；
       * Eureka通过心跳检测、健康检查和客户端缓存等机制，提高了系统的灵活性、可伸缩性和可用性。    

   + 深入理解 Eureka 
      - Eureka Server的同步遵循着一个非常简单的原则：**只要有一条边将节点连接，就可以进行信息传播与同步**。  
        在[搭建双节点注册中心](#)中，我们便是以eureka server 1和eureka server 2两两相互连接实现了 Eureka Server 沿着节点进行信息传播与同步。
      - Eureka Server具备**单向的服务传播与同步机制**，在一些对服务发现有限制的情况下，可以利用这样的机制进行服务注册与发现的单向控制。
        为了验证Eureka Server的单向信息同步机制，我们将[搭建双节点注册中心](#)中的eureka server2 不指向eureka server 1进行测试。  
        ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/eureka双机-single.png?raw=true)  

      - **两两注册**的方式可以实现集群中节点完全对等的效果，实现**最高可用性集群**，任何一台注册中心故障都不会影响服务的注册与发现。高可用设计示例：  
        ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/eureka高可用.png?)

- ### [注意](#)
   - EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN   THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE;  
     是由于Eureka进入了保护模式,在保护模式下，Eureka Server将会尝试保护其服务注册表中的信息，暂时不会注销服务注册表中的服务。     
     ```
     eureka.server.enable-self-preservation=false
     ```

   - 多Eureka Server节点时，服务节点一直处于不可用的服务分片（unavailable-replicas）节点上；  
     ```
     eureka.instance.prefer-ip-address=true  
     eureka.instance.preferIpAddress=true  
     eureka.client.register-with-eureka=false  
     ```

   - Network 的链路就近原则，导致服务节点一直处于不可用的服务分片（unavailable-replicas）
     在做测试的时候，我们往往会在一个主机上部署多个 Eureka 实例，通过实现伪集群进行测试，为了测试顺利，我们需要  
     **[为每一个实例自定义一个域名和端口]()**
   
   - **最高可用性集群**可以通过任意一个节点和其他所有节点实现交叉注册机制，这样可以在原来**两两注册**的前提下进一步提高注册**服务分片的高可用性**  
     ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/eureka高可用-（1-N）.png?)