### [Eureka Server的高可用](#)

- ### 搭建双节点注册中心
   + 在[注册中心入门示例](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-1-1/spring-cloud-study-1-1-1)中，我们只需要一个简单的Spring Boot Web项目，简单的两步就能搭建一个单节点注册中心，
   + Eureka Server除了单点运行之外，还可以通过运行多个实例，并进行互相注册的方式来实现高可用的部署，所以我们只需要将Eureke Server的**eureka.client.serviceUrl.defaultZone**与其他实例交叉配置就能实现高可用部署，对于双节点的大致架构图为：    
   ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/eureka双机.png?raw=true)  
   + 在该eureka双节点中，eureka server 1和eureka server 2的实行交叉注册策略，当客户端任何一个eureka client端点注册到eureka server的任何一个节点上，注册信息都将被同步到另一个节点，这样就可以实现双机热备。效果图如下：  
   ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/81.png?raw=true)    
   ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/82.png?raw=true)    

- ### 搭建多节点注册中心
   + 深入理解 Eureka 
      - Eureka Server的同步遵循着一个非常简单的原则：**只要有一条边将节点连接，就可以进行信息传播与同步**。  
        在[搭建双节点注册中心](#)中，我们便是以eureka server 1和eureka server 2两两相互连接实现了 Eureka Server 沿着节点进行信息传播与同步。
      - Eureka Server具备**单向的服务传播与同步机制**，在一些对服务发现有限制的情况下，可以利用这样的机制进行服务注册与发现的单向控制。
        为了验证Eureka Server的单向信息同步机制，我们将[搭建双节点注册中心](#)中的eureka server2 不指向eureka server 1进行测试。  
        ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/eureka双机-single.png?raw=true)  
        ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/81-single.png?raw=true)    
        ![image](https://github.com/timebusker/spring-cloud-study/raw/master/static/0/82-single.png?raw=true)  
  

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
     在做测试的时候，我们往往会在一个主机上部署多个 Eureka 实例，通过实现伪集群进行测试，为了测试顺利，我们需要**[为每一个实例自定义一个域名和端口]()**
   
   - **最高可用性集群**可以通过任意一个节点和其他所有节点实现交叉注册机制，这样可以在原来**两两注册**的前提下进一步提高注册**服务分片的高可用性**