## 一.Lucky NoXml的简介

Lucky是一款开源免费的Java MVC框架，Lucky使用可自定义的 ***特定包后缀  +  @注解*** 的方式定义和管理系统的不同模块，去除了所有的xml配置，大幅度的简化了开发过程！Lucky中还自带有一套支持多种数据库类型以及多数据源的__半映射__与___全映射___结合的持久型框架，给用户提供了一整套的web项目开发的解决方案。



​	**1.下载地址**

​		<https://www.github.com/FK7075/Lucky>

​	**2.Lucky如何安装**

> > 1.Lucky如何安装

 要使用Lucky，首先需要配置Lucky的运行环境。Lucky项目自身依赖于cglib和c3p0,所以在使用       Lucky前需要提前将cglib和c3p0的jar包导入classpath，然后再导入lucky-x.x.x.jar即可。目前Lucky还不支持Maven以及Gradle。所以需要手动下载lucky的jar包。

## 二.Lucky中的组件扫描

​	Lucky使用特定包后缀+注解的方式定义和管理模块，即使用包区分模块，不同后缀名包中的类就是一组相同的组件

​	***1.Lucky中的组件***

Lucky允许用户使用特定的注解定义特定的组件，Lucky中常用的组件注解有：

|  注解名称   |      默认包后缀       |                    注解作用                    |
| :---------: | :-------------------: | :--------------------------------------------: |
| @Controller |      controller       | 定义一个用于接受和处理http请求的**控制层**组件 |
|  @Service   |        service        |  定义一个用于处理业务逻辑的**业务逻辑层**组件  |
| @Repository | repository,mapper,dao |  定义一个用于与数据库交互的**数据持久层**组件  |
|   @Agent    |         agent         |      定义一个用于增强逻辑的**代理类**组件      |
| @Component  |    component,bean     |             定义一个普通的IOC组件              |

***2.Lucky中的组件扫描机制***

Lucky只会扫描包名以特定后缀名结尾的包来得到系统运行时所需要的的IOC组件






