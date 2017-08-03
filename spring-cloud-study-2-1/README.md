### [Spring-Cloud 分布式配置中心](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/)

- #### [Spring Cloud Config简介](#)
  
   + Spring Cloud Config为服务端和客户端提供了分布式系统的外部化配置支持。配置服务器为各应用的所有环境提供了一个中心化的外部配置。它实现了对服务端和客户端对Spring Environment和PropertySource抽象的映射，所以它除了适用于Spring构建的应用程序，也可以在任何其他语言运行的应用程序中使用。作为一个应用可以通过部署管道来进行测试或者投入生产，我们可以分别为这些环境创建配置，并且在需要迁移环境的时候获取对应环境的配置来运行。   
    
   +  配置服务器提供远程和本地化文件系统的存储方式两种方式管理配置内容，默认采用git来存储配置信息，有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容。

- #### [Spring Cloud Config简单使用](#)  
   + [Config Server 配置管理服务端](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-1-1) 
     * 基础架构：
         - 远程 Git 仓库： 用来存储配置文件的地方， 上例中我们用来存储针对应用名为quickstart的多环境配置文件：  
         quickstart-{profile}.properties。 
         - Config Server: 分布式配置中心，在工程中指定了所要连接的Git仓库位置以及账户、密码等连接信息。
         - 本地Git仓库：在Config Server的文件系统中，每次客户端请求获取配置信息时，Config Server从Git仓库中获取最新配置到
         本地，然后在本地Git仓库中读取并返回 。当远程仓库无法获取时， 直接将本地内容返回。
         - 微服务端：具体的微服务应用，它们指定了ConfigServer的地址，从而实现从外部化获取应用 自己要用的配置信息。这些应用在              启动的时候，会向Config Server请求获取配置信息来进行加载。
         - Config Server巧妙地通过git clone将配置信息存于本地，起到了缓存的作用，即使当Git服务端无法访问的时候，依然可以取ConfigServer中的缓存内容进行使用。
   
   + [Config Repostory 配置仓库](http://git.oschina.net/timebusker/spring--cloud-config-repo)  
   
   + [Config 微服务端](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-1-1)  
     * 客户端应用从配置管理中获取配置信息遵从下面的执行流程：
       - 应用启动时，根据bootstrap.proper巨es中配置的应用名{app巨cation}、环境名{profile}、 分支名{label}, 向ConfigServer请求获取配置信息；
       - Config Server根据自己维护的Git仓库信息和客户端传递过来的配置定位信息去查找配置信息；
       - 通过g江 clone命令将找到的配置信息下载到ConfigServer的文件系统中；
       - Config Server创建Spring的ApplicationContext实例，并从Git本地仓库中加载配置文件， 最后将这些配置内容读取出来返回给客户端应用；
       - 客户端应用在获得外部配置文件后加载到客户端的ApplicationContext实例，该配置内容的优先级高于客户端Jar包内部的配置内容， 所以在Jar包中重复的内容将不再被加载。
       

- #### [配置刷新](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-2-2)
   - 在[简单使用示例](https://github.com/timebusker/spring-cloud-study/tree/master/spring-cloud-study-2-1/spring-cloud-study-2-1-1)中，我们只需要简单的配置信息就能实现分布式配置中心的核心的核心功能——分布式配置推送。但是在测试过程中发现，但我们通过git更新配置文件，并提交到git远程仓库时，分布式配置中心的配置在访问时可以实现刷新，但是微服务端的配置信息并不会改变。本示例就是需要显示微服务端的配置信息刷新功能。  

- #### [配置管理集群]()




### 相关文章
   - [Spring Cloud构建微服务架构（四）分布式配置中心](http://blog.didispace.com/springcloud4/)  
   - [Spring Cloud构建微服务架构（四）分布式配置中心（续）](http://blog.didispace.com/springcloud4-2//)  




