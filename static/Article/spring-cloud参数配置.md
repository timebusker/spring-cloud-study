### [spring-cloud参数配置](#)

- **eureka.client.registry-fetch-interval-seconds**  
   - 表示eureka client间隔多久去拉取服务注册信息，默认为30秒，对于api-gateway，如果要迅速获取服务注册状态，可以缩小该值，比如5秒

- **eureka.instance.lease-expiration-duration-in-seconds**  
  - 表示eureka server至上一次收到client的心跳之后，等待下一次心跳的超时时间，在这个时间内若没收到下一次心跳，则将移除该instance。
  - 默认为90秒
  - 如果该值太大，则很可能将流量转发过去的时候，该instance已经不存活了。
  - 如果该值设置太小了，则instance则很可能因为临时的网络抖动而被摘除掉。
  - 该值至少应该大于leaseRenewalIntervalInSeconds

- **eureka.instance.lease-renewal-interval-in-seconds**  
   - 表示eureka client发送心跳给server端的频率。如果在leaseExpirationDurationInSeconds后，server端没有收到client的心跳，则将摘除该instance。除此之外，如果该instance实现了HealthCheckCallback，并决定让自己unavailable的话，则该instance也不会接收到流量。
   - 默认30秒

- **eureka.server.enable-self-preservation**  
   - 是否开启自我保护模式，默认为true
   - 默认情况下，如果Eureka Server在一定时间内没有接收到某个微服务实例的心跳，Eureka Server将会注销该实例（默认90秒）。但是当网络分区故障发生时，微服务与Eureka Server之间无法正常通信，以上行为可能变得非常危险了——因为微服务本身其实是健康的，此时本不应该注销这个微服务。
   - Eureka通过“自我保护模式”来解决这个问题——当Eureka Server节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，Eureka Server就会保护服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。
   - 综上，自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留），也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定。

- **eureka.server.eviction-interval-timer-in-ms**  
   - eureka server清理无效节点的时间间隔，默认60000毫秒，即60秒

- **eureka.instance.prefer-ip-address**
   - true:可以将IP注册到Eureka Server上(自动为我们获取第一个非回环IP地址,多网卡时需要注意)
   - false:将机器的主机名注册到Eureka Server上
   
- **eureka.instance.ip-address**
  - 指定实例客户端的主机IP，当***eureka.instance.prefer-ip-address***为**true**时，以该IP注册到eureka server

- **spring.application.name**
  - 通过spring.application.name属性，我们可以指定微服务的名称后续在调用的时候只需要使用该名称就可以进行服务的访问。

- **eureka.client.healthcheck.enabled**
   - 是否需要开启自身健康检查（true/false，true时需要spring-boot-starter-actuator依赖）

- **eureka.instance.hostname**
  - 指定实例客户端的主机名称

- **eureka.instance.instance-id**
  - 设置实例的编号，默认采用主机名和实例端口，建议使用主机IP、实例端口和服务名称 

- **eureka.client.register-with-eureka**
   - 是否将自身实例注册到注册服务器（true/false），注册地址为自身应用指定的注册地址

- **eureka.client.fetch-registry**
  - 是否从注册服务器获取注册信息（true/false）

- **eureka.environment**
  - 指定软件运行环境








