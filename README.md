<img src="/image/images.jpg" width=15%>

## 一.Lucky 的简介

**Lucky**是一款极简的开源Java框架，**Lucky**包含三大模块：

- **IOC容器层**：主要负责组件的创建于装配工作，Lucky采用扫描特定后缀名的包的方式来获得组件，去除了所有的xml配置，大幅度的简化了开发过程！
- **UI控制层**：提供了对MVC(模型 - 视图 - 控制器)架构的支持，用于开发灵活和松散耦合的Web应用程序
- **数据持久层**：支持多种数据库类型以及多数据源，且同时具备**半映射**与**全映射**结合的持久型框架特点。

1.下载地址

​	https://github.com/FK7075/lucky-ex/tree/noxml/lib

2.Lucky如何安装

 要使用Lucky，首先需要配置Lucky的运行环境。Lucky项目自身依赖于cglib和c3p0,所以在使用       Lucky前需要提前将cglib和c3p0的jar包导入classpath，然后再导入lucky-x.x.x.jar即可。目前Lucky还不支持Maven以及Gradle。所以需要手动下载lucky的jar包。

## 二.Lucky风格的组件扫描

Lucky采用扫描特定后缀名的包的方式来获得组件，再不做任何配置的情况下各个组件类需要在下表中约定的包中进行定义，否则Lucky将会无法扫描到该组件。

**1.Lucky中默认的组件定义**

Lucky允许用户使用特定的注解定义特定的组件，Lucky中常用的组件注解和棋默认的定义位置：

|  注解名称   |    约定包的后缀名     |                    注解作用                    |
| :---------: | :-------------------: | :--------------------------------------------: |
| @Controller |      controller       | 定义一个用于接受和处理http请求的**控制层**组件 |
|  @Service   |        service        |  定义一个用于处理业务逻辑的**业务逻辑层**组件  |
| @Repository | repository,mapper,dao |  定义一个用于与数据库交互的**数据持久层**组件  |
|   @Agent    |         agent         |      定义一个用于增强逻辑的**代理类**组件      |
| @Component  |    component,bean     |           定义一个**普通的IOC**组件            |

**例如：要定义一个类名为MyComponent的Component组件的步骤如下(其他组件的定义类似)**

根据上表可知Component组件的约定定义位置在以component和bean结尾的包中

1. 创建一个包名以component或bean结尾的包(不区分大小写)，例如：component、com.luck.mycomponent、com.testBean、bean......等
2. 在包中创建一个MyComponent类
3. 在类上使用@Component主注解进行标注。

## 三.使用Lucky的MVC层开发第一个Web应用

