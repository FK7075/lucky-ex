<img src="https://github.com/FK7075/lucky-ex/blob/noxml/image/images.jpg" width=15%>

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

Lucky采用扫描特定后缀名的包的方式来获得组件，再不做任何配置的情况下各个组件类需要在下表中特定的包中进行定义，否则Lucky将会无法扫描到该组件。

**1.Lucky中默认的组件定义**

Lucky允许用户使用特定的注解定义特定的组件，Lucky中常用的组件注解和棋默认的定义位置：

|  注解名称   |      默认包后缀       |                    注解作用                    |
| :---------: | :-------------------: | :--------------------------------------------: |
| @Controller |      controller       | 定义一个用于接受和处理http请求的**控制层**组件 |
|  @Service   |        service        |  定义一个用于处理业务逻辑的**业务逻辑层**组件  |
| @Repository | repository,mapper,dao |  定义一个用于与数据库交互的**数据持久层**组件  |
|   @Agent    |         agent         |      定义一个用于增强逻辑的**代理类**组件      |
| @Component  |    component,bean     |           定义一个**普通的IOC**组件            |

**2.默认配置下定义一组组件**

以Controller组件为例：

1. Controller组件的默认包后缀为controller,所以需要创建一个名字以controller(不区分大小写)结尾的包。

   例如：controller、com.lucky.test.mycontroller、com.main.MyController

2. 包创建好之后，就可以创建类了，类的名字没有限制但必须要在类上加上一个@Controller注解

   例如：在com.lucky.test.mycontroller包下创建一个名为TestController的Controller

   ```java
   package com.lucky.test.mycontroller;
   
   import com.lucky.jacklamb.annotation.ioc.Controller;
   /**
    * 定义一个Controller组件
    * @author FK7075
    */
   @Controller
   public class TestController {
       
   }
   
   
   package com.lucky.test.mycontroller;
   
   import com.lucky.jacklamb.ioc.ApplicationBeans;
   /**
    * 测试组件是否已经加入到IOC容器
    * @author FK7075
    */
   public class MainTest{
   	public static void main(String[] args) {
   		ApplicationBeans apps=ApplicationBeans.createApplicationBeans();
   		apps.printBeans();
   	}
   }
   ```

   3.创建带有主方法的MainTest类以及如上代码，运行查看输出信息如下(Controller组件注册成功)：

   ```text
   --------------------------GitHub：FK7075----------------------
   
               .--,       .--,
              ( (  \.---./  ) )
               '.__/o   o\__.'
                  {=  ^  =}
                   >  -  <
                  /       \
                 //       \\
                //|   .   |\\
                "'\       /'"_.-~^`'-.
                   \  _  /--'         `
                 ___)( )(___
                (((__) (__)))    Hello World!
   
   ---------------------------Lucky[NOXML版]------------------------
   
   [2019-12-09 16:14:35]  [SCAN-OK]->Controller组件:{testController=com.lucky.test.mycontroller.TestController@49c2faae}
   [2019-12-09 16:14:35]  [SCAN-OK]->Service组件:{}
   [2019-12-09 16:14:35]  [SCAN-OK]->Repository组件:{}
   [2019-12-09 16:14:35]  [SCAN-OK]->Mapper组件:{}
   [2019-12-09 16:14:35]  [SCAN-OK]->Component组件:{}
   [2019-12-09 16:14:35]  [SCAN-OK]->Agent组件:{}
   [2019-12-09 16:14:35]  [SCAN-OK]->URL-ControllerMethod映射关系:{}
   ```

   



