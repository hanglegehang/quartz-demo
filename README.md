#  Spring 整合 Quartz 实现动态定时任务

1. 动态增加定时任务
2. 对定时任务进行修改
3. 暂停定时任务
4. 恢复暂停的定时任务

Quartz可以用来做什么？
Quartz是一个任务调度框架。比如你遇到这样的问题

想每月25号，信用卡自动还款

想每年4月1日自己给当年暗恋女神发一封匿名贺卡

想每隔1小时，备份一下自己的爱情动作片 学习笔记到云盘

这些问题总结起来就是：在某一个有规律的时间点干某件事。并且时间的触发的条件可以非常复杂（比如每月最后一个工作日的17:50），复杂到需要一个专门的框架来干这个事。 Quartz就是来干这样的事，你给它一个触发条件的定义，它负责到了时间点，触发相应的Job起来干活。

但是很多时候，我们常常会遇到需要动态的添加或修改任务，而spring中所提供的定时任务组件却只能够通过修改xml中trigger的配置才能控制定时任务的时间以及任务的启用或停止，这在带给我们方便的同时也失去了动态配置任务的灵活性。最理想的是在与spring整合的同时又能实现动态任务的添加、删除及修改配置。

### ps:项目基于Maven
### 演示方法：用tomcat部署


```
1. 查看已经部署的定时任务
http://localhost:8080/list
2. 初始化定时任务
http://localhost:8080/init
3. 暂停定时任务
http://localhost:8080/stop
4. 恢复暂停的定时任务
http://localhost:8080/resume
```
> spring/spring.xml 配置
> 
> 1. 传统方式


```
	<!-- 使用MethodInvokingJobDetailFactoryBean，任务类可以不实现Job接口，通过targetMethod指定调用方法-->
	<bean id="taskJob" class="cn.huashantech.lawyer.service.DataConversionTask"/>

	<bean id="jobDetail" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
		<!--false表示等上一个任务执行完后再开启新的任务-->
		<property name="concurrent" value="false"/>
		<property name="targetObject" ref="taskJob"/>
		<property name="targetMethod" value="run"/>
	</bean>

	<!--  调度触发器 -->
	<bean id="myTrigger"
		  class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
		<property name="jobDetail" ref="jobDetail"/>
		<property name="cronExpression" value="0/5 * * * * ?"/>
	</bean>
```
> 2. 动态方式所需配置

```
<!-- 调度工厂 -->
	<bean  id="scheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
```

### 核心类
> cn.huashantech.lawyer.service.DynamicTask.Main

```
以注解方式注入调度工厂
@Autowired
private Scheduler scheduler;

```
![](http://oj8v2br1f.bkt.clouddn.com/Jietu20171103-195921.jpg)

关于Quartz详细解释参考：http://www.cnblogs.com/drift-ice/p/3817269.html

借鉴来源：http://blog.csdn.net/u014723529/article/details/51291289
