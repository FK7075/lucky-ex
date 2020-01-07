<div align=center><img src="/image/images.png" width="150"/></div>

## 一.Lucky 的简介

Lucky是一款用于开发Java Web项目的轻量级**全栈型**框架。Lucky遵从**约定大于配置**原则，所以Lucky的使用**无需**进行**任何的xml配置**，开箱即用！Lucky支持**RestFul风格**开发，支持**多数据源**开发，支持**内嵌Tomcat**(jar包部署)。内置的数据持久层同时具备**全映射与半映射的特点**。集万千宠爱于一身，只为让开发变得轻松优雅！

1.下载地址

 https://github.com/FK7075/lucky-ex/tree/noxml/lucky

2.Lucky如何安装

在Github中提供了两个版本的Lucky供大家下载。

1.【convenient-便捷版】

便捷版的jar包中包含了Lucky的源码，同时也包含了Lucky依赖项的源码，所以选用Lucky便捷版开发时就不需要在导入其他的依赖项了，便捷版convenient文件夹中有以下三个文件

|                文件名                |                             作用                             |
| :----------------------------------: | :----------------------------------------------------------: |
| **lucky-noxml-convenient-1.0.0.ja**r |          Lucky便捷版jar包，导入到classpath即可使用           |
|            **maven.bat**             | 将便捷版Lucky导入本地Maven仓库的批处理文件，提供对Maven支持  |
|             **pom.txt**              | 运行maven.bat后便可以在Maven项目中使用Lucky，pom.txt中是Lucky的依赖 |

2.【pure-纯净版】

纯净版jar包中只用Lucky的源码，不包含Lucky依赖项的源码，所以选用Lucky纯净版开发时还需要导入其他依赖包，不过在pure文件夹中也提供的所有的第三方包，如果需要更替版本，请到相应的官方网站中自行下载！

【**c3p0**(c3p0-x.x.x.x.jar和mchange-commons-java-x.x.xx.jar)】

【**cglib**(cglib-nodep-x.x.x.jar)】

如果要使用内嵌Tomcat进行开发还需要引入相关的依赖包

【**apache-file**(commons-fileupload-x.x.x.jar和commons-io-x.x.jar)】、

【**embed-tomcat** (annotations-api.jar，ecj-x.x.x.jar ，servlet-api.jar，tomcat-dbcp.jar，tomcat-embed-core.jar，tomcat-embed-el.jar，tomcat-embed-jasper.jar，tomcat-embed-websocket.jar )

pure文件夹中的文件功能与上面的表格中介绍的大同小异，不在赘述！





## 二.Lucky组件介绍

在开发中，Lucky允许用户定义以下七种组件，不同组件定义不同的功能块，不同组件之间通过IOC容器进行调度与组合。

1. **AppConfig组件**：用于修改默认约定和运行时的某些参数。

2. **Controller组件**：用于接收和响应Http请求。

3. **Service组件**：用于处理业务逻辑。

4. **Repository组件**：使用传统方式与数据持久层交互。

5. **Mapper组件**：使用Mapper接口方式与数据持久层交互

6. **Component组件**：普通的ioc组件。

7. **Agent组件**：aop组件，用于定义一系列的增强，用于功能的横向扩展。

   

   ## 三.Lucky的默认约定

   Lucky中有以下两中默认约定

   **1.七大组件的位置约定**

   这个约定用于确定组件的定义位置，每种组件必须写在特定的位置，这样才能确保Lucky在启动的时候能够准确的找到并启用他们。在**默认约定中**七大组件必须写在**以特定名字结尾的包**中才能被Lucky识别。

   以下是八大组件的默认约定位置：

   |    组件名称    |                     约定位置                      |    配合使用的注解     |                 约定位置举例说明                 |
   | :------------: | :-----------------------------------------------: | :-------------------: | :----------------------------------------------: |
   | **Appconfig**  |        定义在包名以**appconfig结尾**的包中        | 继承ApplicationConfig | 【*appconfig】com.lucky.appconfig，myappconfig等 |
   | **Controller** |       定义在包名以**controller**结尾的包中        |  @Controller注解标注  |  【*controller】controller，com.mycontroller等   |
   |  **Service**   |         定义在包名以**service**结尾的包中         |   @Service注解标注    |   【*service】service，com.lucky.testservice等   |
   | **Repository** | 定义在包名以【**repository**或**dao**】结尾的包中 |                       |                                                  |
   |   **Mapper**   |         定义在包名以**mapper**结尾的包中          |                       |                                                  |
   | **Component**  | 定义在包名以【**component**或**bean**】结尾的包中 |  @Component注解标注   |  【*component或 *bean】com.bean,com.component等  |
   |   **Agent**    |          定义在包名以**agent**结尾的包中          |    @Agent注解标注     |    【*agent】com.agent,com.lucky.testagent等     |

   **2.数据库配置文件的位置约定**

   数据库配置文件的格式是**.properties**在默认约定中，数据库配置文件的名称为**db.properties**,位置为**classpath**下！当然，数据库配置文件也不是必须的，后面数据库章节会介绍一种是用配置类的写法！

   ## 四.七大组件的使用

   ### 1.Controller组件

在Lucky中Controller组价的职能是**用于接收和响应Http请求**

