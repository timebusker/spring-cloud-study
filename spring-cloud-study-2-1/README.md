### [Spring-Cloud 分布式配置中心](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/)

- #### [Spring Cloud Config简介](#)
  
   + Spring Cloud Config为服务端和客户端提供了分布式系统的外部化配置支持。配置服务器为各应用的所有环境提供了一个中心化的外部配置。它实现了对服务端和客户端对Spring Environment和PropertySource抽象的映射，所以它除了适用于Spring构建的应用程序，也可以在任何其他语言运行的应用程序中使用。作为一个应用可以通过部署管道来进行测试或者投入生产，我们可以分别为这些环境创建配置，并且在需要迁移环境的时候获取对应环境的配置来运行。   
    
   +  配置服务器提供远程和本地化文件系统的存储方式两种方式管理配置内容，默认采用git来存储配置信息，有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容。

- #### [Spring Cloud Config简单使用](#)  
   + [Config Server 配置管理服务端](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-1-1)  
   
   + [Config Repostory 配置仓库](http://git.oschina.net/timebusker/spring--cloud-config-repo)  
   
   + [Config 微服务端](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-1-1)  


- #### [配置管理之客户端自动刷新]()



- #### [配置管理集群]()




### 相关文章
   - [Spring Cloud构建微服务架构（四）分布式配置中心](http://blog.didispace.com/springcloud4/)  
   - [Spring Cloud构建微服务架构（四）分布式配置中心（续）](http://blog.didispace.com/springcloud4-2//)  




