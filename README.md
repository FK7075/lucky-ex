

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/images.png)

@[TOC](Lucky文档目录)


## 一.Lucky 的简介

Lucky是一款用于开发JavaWeb项目的轻量级框编写的架，是本人借鉴ssm以及Spring Boot等一些优秀框架的特点编写的一款全栈型框架，也是我在框架学习过程中的一个总结！Lucky-Noxml版去除了所有的xml配置，使用全注解的开发模式，极大的简化开发，使用组最少的配置完成最多的功能！提供了对RestFul，多数据源，WebSocket,Ioc,Aop,内嵌Tomcat等技术的支持,集万千宠爱于一身，只为让开发变得轻松优雅！

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

【**log4j**(log4j-x.x.xx.jar,slf4j-api-x.x.x.jar和slf4j-log4jxx-x.x.x.jar)】

如果要使用内嵌Tomcat进行开发还需要引入相关的依赖包

【**apache-file**(commons-fileupload-x.x.x.jar和commons-io-x.x.jar)】、

【**embed-tomcat** (annotations-api.jar，ecj-x.x.x.jar ，servlet-api.jar，tomcat-dbcp.jar，tomcat-embed-core.jar，tomcat-embed-el.jar，tomcat-embed-jasper.jar，tomcat-embed-websocket.jar )

pure文件夹中的文件功能与上面的表格中介绍的大同小异，不在赘述！





## 二.Lucky组件介绍

在开发中，Lucky允许用户定义以下九种组件，不同组件定义不同的功能块，不同组件之间通过IOC容器进行调度与组合。

1. **AppConfig组件**：用于修改默认约定和运行时的某些参数。
2. **Controller组件**：用于接收和响应Http请求。
3. **Service组件**：用于处理业务逻辑。
4. **Repository组件**：使用传统方式与数据持久层交互。
5. **Mapper组件**：使用Mapper接口方式与数据持久层交互
6. **Component组件**：普通的ioc组件。
7. **Aspect组件**：aop组件，用于定义一系列的增强，用于功能的横向扩展。
8. **WebSocket组件**：WebSocket组件，用于定义一个接受webSocket请求的组件
9. **ExceptionHander**：异常处理组件，用于处理由Controller组件所产生的异常

## 三.Lucky的两种约定

### 1.自动扫描约定(默认)

​	自动扫描约定是一种十分宽泛的约定，lucky启动时会自动扫描项目路径中的所有文件来得到并管理用户创建的九大组件，自动完成依赖注入以及动态代理等工作，用户只需要按照规定定义好组件既可以立即使用，在此模式下不需要使用以往大量的xml配置，也没有任何其他要求！一切自然而然的简单！

### **2.后缀扫描约定**

相比**自动扫描约定**这个**后缀扫描约**多了些许的条件限制，在这种约定模式下，指定的组件需要编写在指定的位置，这样才能确保Lucky在启动的时候能够准确的找到并启用他们。在**后缀扫描约定**中**九大组件必须写在**以特定名字结尾的包中才能被Lucky识别。

以下是九大组件在**后缀扫描模式**下的默认约定位置：

|      组件名称       |                     约定位置                      |                   配合使用的注解                   |                约定位置举例说明                |
| :-----------------: | :-----------------------------------------------: | :------------------------------------------------: | :--------------------------------------------: |
|    **AppConfig**    |                     任意位置                      |                 @AppConfig注解标注                 |                    任意位置                    |
|   **Controller**    |       定义在包名以**controller**结尾的包中        |                @Controller注解标注                 | 【*controller】controller，com.mycontroller等  |
|     **Service**     |         定义在包名以**service**结尾的包中         |                  @Service注解标注                  |  【*service】service，com.lucky.testservice等  |
|   **Repository**    | 定义在包名以【**repository**或**dao**】结尾的包中 |                @Repository注解标注                 |             【*repository和 *dao】             |
|     **Mapper**      |         定义在包名以**mapper**结尾的包中          |                  @Mapper注解标注                   |                  【*mapper】                   |
|    **Component**    | 定义在包名以【**component**或**bean**】结尾的包中 |                 @Component注解标注                 | 【*component或 *bean】com.bean,com.component等 |
|      **Agent**      |          定义在包名以**agent**结尾的包中          |                   @Agent注解标注                   |   【*agent】com.agent,com.lucky.testagent等    |
| **ExceptionHander** |     定义在包名以**exceptionhande**r结尾的包中     | @ExceptionHander注解标注，并继承LuckyExceptionHand |              【*exceptionhander】              |
|    **WebSocket**    |          定义在包名以websocket结尾的包中          |               使用Tomcat中的编写规范               |                 【*websocket】                 |

## 四.Lucky的配置文件和配置类

### 	1.配置文件

​			lucky使用.ini文件作为配置文件，.ini文件由**节+KV**组成节写在[]中，节代表一个特定的模块，在一个接下可以配置多个键值对，在不同的节中可以使用相同的key.

​			lucky的配置文件名固定为appconfig.ini，且必须放在classpath(src)文件夹下，在appconfig.ini中可以配置的内容如下：

#### 1.Lucky的标准配置文件

appconfig.ini

```ini
#多数据源配置
[DataSources]
dataSources=ZheJiang,GuangDong

#数据库配置
[Jdbc]
##数据库驱动##
driverClass=com.mysql.jdbc.Driver
##数据库地址##
jdbcUrl=jdbc:mysql://127.0.0.1:3306/jacklamb?useUnicode=true&characterEncoding=utf8
##登录名##
user=root
##密码##
password=123456
##连接池无空闲连接可用时，一次性创建的新连接数 默认值：3
acquireIncrement=3
##连接池初始化时创建的连接数 默认值：3
initialPoolSize=3
##连接池中拥有的最大连接数 默认值：15
maxPoolSize=15
##连接池保持的最小连接数
minPoolSize=3
##连接的最大空闲时间,如果超过这个时间，某个数据库连接还没有被使用，则会断开掉这个连接，如果为0，则永远不会断开连接。
maxidleTime=0
##这个配置主要时为了减轻连接池的负载，配置不为 0 则会将连接池中的连接数量保持到minPoolSize，为 0 则不处理
maxConnectionAge=0
##当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出 SQLException,如设为0则无限期等待。单位毫秒。Default: 0
checkoutTimeout=0
##连接池为数据源缓存的PreparedStatement的总数。由于PreparedStatement属于单个Connection
##所有这个数量应该根据应用中平均连接数乘以每个连接的平均PreparedStatement来计算。为 0 的时候不缓存，同时maxStatementsPerConnection的配置无效
maxStatements=0
##连接池为数据源单个Connection缓存的PreparedStatement数，
##这个配置比maxStatements更有意义，因为它缓存的服务对象是单个数据连接，如果配置的好，肯定是可以提高性能的。为 0 的时候不缓存
maxStatementsPerConnection=0
##设置为true时，每一次SQL操作都将会使用不同的Connection对象，默认false##
poolMethod=false
##是否打印SQL日志##
log=false
##是否打印格式化的SQL日志##
formatSqlLog=false
##是否开启缓存##
cache=false
##逆向工程,配置用于存放生成的实体类的包##
reversePackage=com.jacklamb.lucky.pojo
##项目classpath(src)的绝对路径##
srcpath=C:\Users\DELL\git\lucky-ex\src
##自动建表，配置需要建表机制操作的实体类的包路径##
createTables=table1,table2,teble3
##自动建表操作具体要操作的实体类##
table1=com.jacklamb.lucky.entity.Book
table2=com.jacklamb.lucky.entity.Stort
table3=com.jacklamb.lucky.entity.Authod

##配置Sql的ini文件的位置
[Sql-Ini]
path=sql.ini

#开启后缀扫描,以reset:开始表示重置原始配置后再添加，无前缀则为追加
[Suffix Scan]
controller=controller,mycontroller
service=reset:service,myservice
repository=repository
aspect=aspect
component=component
websocket=websocket
pojo=pojo

#更换Tomcat配置
[Tomcat]
##接收请求的端口
port=8080
##session超时时间（分钟）
sessionTimeout=30
##RealPath，静态文件所在的文件夹（ap 为绝对路径的写法）
docBase=WebContext/
ap-docBase=C:/Fk7075/
##tomcat运行时临时文件所在的文件夹（ap 为绝对路径的写法）
baseDir=tomcat/tmp/
ap-baseDir=D:/tomcat/tmp/
##项目路径
contextPath= 
webapp=/WebContent/
##tomcat用于监听关机命令的端口
closePort=8005
##tomcat用于关机的命令
shutdown=SHUTDOWN

#添加Servlet
[Servlet]
servletName=servletClass
s1=c1
[Servlet Mapping]
servletName=/user/*,/admin/*
s1=/

#添加Filter
[Filter]
filterName=filterClass
f1=c1
[Filter Mapping]
filterName=/*
f1=/query/*

#添加Listener
[Listener]
l1=ListenerClass1
l2=ListenerClass2

#配置web设置
[Web]
#URL编码格式
encoding=UTF-8
#是否开启静态资源管理器
openStaticResourceManage=true
#是否开启POST请求类型转换(_method)
postChangeMethod=true
#全局资源IP限制
globalResourcesIpRestrict=192.168.3.3,192.168.3.4
#静态资源Ip限制
staticResourcesIpRestrict=192.168.3.3,192.168.3.4
[StaticHander]
/user/login=/user/login.html
/admin/update=/admin/update.html
#全局的响应前后缀配置
[HanderPrefixAndSuffix]
prefix=/WEN-INF/
suffix=.jsp
#指定资源的Ip限制,一个资源只能被指定的ip访问
[specifiResourcesIpRestrict]
/user/query/=192.168.3.3,192.168.3.4
/file/test.jpg=192.168.3.3,192.168.3.4
```

### 2.ini文件解析器

#### 1.ini文件解析器INIConfig

以上是appconfig.ini中可以配置的内容以及解释，另外我们也可以在appconfig.ini文件中配置一些自定义的参数，Lucky中内置了一个.ini文件解析器INIConfig类，以下是INIConfig的API

```java
/**
 * ini文件解析器
 */
public class INIConfig {
	
    /**
     * 无参构造器，使用该构造方法则默认解析src下的appconfig.ini文件
     */
	public INIConfig()
	
    /**
     * 有参构造器，使用该构造方法解析项目中的任意一个ini文件
     * @param path 带解析ini文件相对src的相对路径
     */
	public INIConfig(String path)
	
	/**
	 * 得到某个人指定节下的所有的key-value值组成的Map
	 * @param section 节的名称
	 * @return
	 */
	public  Map<String,String> getSectionMap(String section) 
	
	/**
	 * 得到某个指定节下指定key的value值
	 * @param section
	 * @param key
	 * @return
	 */
	public  String getValue(String section,String key)
	
	/**
	 * 得到一个具体类型的Value
	 * @param section 节名称
	 * @param key key名
	 * @param clazz 指定类型的Class
	 * @return
	 */
	public  <T> T getValue(String section,String key,Class<T> clazz)
	
	/**
	 * 得到一个String[]形式的value
	 * @param section 节名称
	 * @param key key名称
	 * @param separator 分隔符
	 * @return
	 */
	public  String[] getArray(String section,String key,String separator)
	
	/**
	 * 得到一个String[]形式的value
	 * @param section 节名称
	 * @param key key名称
	 * @return
	 */
	public  String[] getArray(String section,String key)
	
	/**
	 * 得到一个指定类型数组形式的value
	 * @param section 节名称
	 * @param key key名称
	 * @param changTypeClass 数组类型Class
	 * @param separator 分隔符
	 * @return
	 */
	public  <T> T[] getArray(String section,String key,Class<T> changTypeClass,String       separator)
	
	/**
	 * 得到一个指定类型集合形式的value
	 * @param section 节名
	 * @param key key名
	 * @param collectionClass 集合类型
	 * @param genericClass 泛型类型
	 * @param separator 分隔符
	 * @return
	 */
	public  <T extends Collection<M>,M> T getCollection(String section,String               key,Class<T> collectionClass,Class<M> genericClass,String separator)
	
	/**
	 * 得到App节下指定类型集合形式的value
	 * @param key key名
	 * @param collectionClass 集合类型
	 * @param genericClass 泛型类型
	 * @param separator 分隔符
	 * @return
	 */
	public  <T extends Collection<M>,M> T getAppCollection(String key,Class<T>               collectionClass,Class<M> genericClass,String separator)
	
	/**
	 * 得到App节下指定类型集合形式的value
	 * @param key key名
	 * @param collectionClass 集合类型
	 * @param genericClass 泛型类型
	 * @return
	 */
	public  <T extends Collection<M>,M> T getAppCollection(String key,Class<T>               collectionClass,Class<M> genericClass)
	
	/**
	 * 得到App节下String类型集合形式的value
	 * @param key key名
	 * @param collectionClass 集合类型
	 * @return
	 */
	public  <T extends Collection<String>> T getAppCollection(String key,Class<T>           collectionClass)
	
	/**
	 * 得到指定节下指定类型集合形式的value
	 * @param section 节名
	 * @param key key名
	 * @param collectionClass 集合类型
	 * @param genericClass 泛型类型
	 * @return
	 */
	public  <T extends Collection<M>,M> T getCollection(String section,String 		         key,Class<T> collectionClass,Class<M> genericClass) 
	
	/**
	 * 得到指定节下String类型集合形式的value
	 * @param section 节名
	 * @param key key名
	 * @param collectionClass 集合类型
	 * @return
	 */
	public  <T extends Collection<String>> T getCollection(String section,String 		     key,Class<T> collectionClass) 
	
	/**
	 * 得到一个指定类型数组形式的value
	 * @param section 节名称
	 * @param key key名称
	 * @param changTypeClass 数组类型Class
	 * @return
	 */
	public  <T> T[] getArray(String section,String key,Class<T> changTypeClass)
	
	/**
	 * 将某个节下的配置信息封装为一个特定的对象
	 * @param clazz 对象的Class
	 * @return
	 */
	public  <T> T getObject(Class<T> clzz) 

	/**
	 * 将某个节下的配置信息封装为一个特定的对象
	 * @param clazz 对象的Class
	 * @param section 节名称
	 * @return
	 */
	public  <T> T getObject(Class<T> clazz,String section) 
	
	/**
	 * 打印配置文件中的所有配置信息
	 */
	public void printIniMap() 


```

#### 2.使用INIConfig类解析ini文件

3.1 在com.lucky包下创建一个名为test.ini的文件，内容如下

com/lucky/test.ini

```ini
[Test]
str=String-Test
double=14.5
date=2020-12-12
arr=23,34,12,55,67,23

[Good]
id=1
name=辣条
price=3
production=2020-12-14
overdue=2022-12-14
list=1,2,3,4,5,5
set=1.1,2.2,2.2,3.3
user=@S:User

[User]
username=付康
password=PA$$W0RD
```

3.2 使用ini文件解析器INIConfig解析test.ini文件

```java

public class INIConfigTest {
	
	@Test
	@SuppressWarnings("unchecked")
	public void printTest() {
		
		INIConfig ini=new INIConfig("com/lucky/test.ini");
		//打印test.ini文件的所有内容
		ini.printIniMap();
		
		//获取Double类型的[Test]-double
		Double test_double = ini.getValue("Test", "double",Double.class);
		System.out.printf("\ndouble=(Double)%s",test_double);
		
		//获取java.util.Date类型的[Test]->date
		Date test_date = ini.getValue("Test", "date",Date.class);
		System.out.printf("\ndate=(java.util.Date)%s",test_date);
		
		//获取String类型的[Test]->arr
		String string_arr=ini.getValue("Test", "arr");
		System.out.printf("\narr=(String)%s",string_arr);
		
		//获取String[]类型的[Test]->arr
		String[] array_arr=ini.getArray("Test", "arr");
		System.out.printf("\narr=(String[])%s",Arrays.toString(array_arr));
		
		//获取Integer[]类型的[Test]->arr
		Integer[] int_array_arr=ini.getArray("Test", "arr",int.class);
		System.out.printf("\narr=(Integer[])%s",Arrays.toString(int_array_arr));
		
		//获取List<String>类型的[Test]->arr
		List<String> list_array_arr=ini.getCollection("Test", "arr", List.class);
		System.out.printf("\narr=(List<String>)%s",list_array_arr);
		
		//获取List<Double>类型的[Test]->arr
		List<Double> listd_array_arr=ini.getCollection("Test", "arr", List.class,Double.class);
		System.out.printf("\narr=(List<Double>)%s",listd_array_arr);
		
		//获取Set<Integer>类型的[Test]->str
		Set<Integer> seti_array_arr=ini.getCollection("Test", "arr", Set.class,Integer.class);
		System.out.printf("\narr=(Set<Integer>)%s",seti_array_arr);
		
		//获取com.lucky.pojo.User类型的[User]
		User user=ini.getObject(User.class);
		System.out.printf("\n[User]==>%s",user);
		
		//获取com.lucky.pojo.Good类型的[Good],如果该类的属性为对象，则可以在.ini对应该属性key的value前加上@S:Section即可快速引入文件中的另一个对象
		Good good=ini.getObject(Good.class);
		System.out.printf("\n[Good]==>%s",good);
	}
	
}

控制台输出：
[User]
	password=PA$$W0RD
	username=付康
[Test]
	str=String-Test
	date=2020-12-12
	arr=23,34,12,55,67,23
	double=14.5
[Good]
	set=1.1,2.2,2.2,3.3
	overdue=2022-12-14
	production=2020-12-14
	price=3
	name=辣条
	id=1
	list=1,2,3,4,5,5
	user=@S:User

double=(Double)14.5
date=(java.util.Date)Sat Dec 12 00:00:00 CST 2020
arr=(String)23,34,12,55,67,23
arr=(String[])[23, 34, 12, 55, 67, 23]
arr=(Integer[])[23, 34, 12, 55, 67, 23]
arr=(List<String>)[23, 34, 12, 55, 67, 23]
arr=(List<Double>)[23.0, 34.0, 12.0, 55.0, 67.0, 23.0]
arr=(Set<Integer>)[34, 67, 23, 55, 12]
[User]==>User(username=付康, password=PA$$W0RD)
[Good]==>Good(user=User(username=付康, password=PA$$W0RD), id=1, name=辣条, price=3.0, production=2020-12-14, overdue=2022-12-14, list=[1, 2, 3, 4, 5, 5], set=[1.1, 2.2, 3.3])


```

## 五.Controller组件

### 1.Controller注解

@Controller注解用于声明一个接受http请求的接受器，功能类似于Servlet，@Controller注解的源码如下:                       该注解的通常与@RequestMapping注解一起配合使用，其中的属性在之后的章节会一一介绍。

```java
/**
 * 在MVC中此用于标识一个Controller组件
 * 	value：单独使用此注解是用来定义一个IOC组件
 * 	prefix：MVC中的视图定位的前缀(eg: /WEB_INF/jsp/)
 * 	suffix:MVC中的视图定位的后缀(eg: .jsp)
 * -------------------------------------------
 * 	使用"return String"的方式设置转发或重定向的目的地(返回值为String的方法)
 * 	1.转发到页面：无前缀 return page
 * 	2.转发到Controller方法:return forward:method
 *	3.重定向到页面：return page:pageing
 *	4.重定向到Controller方法：return redirect:method
 * @author fk-7075
 *
 */
/**
 * 定义一个Controller组件
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
	
	/**
	 * 为该Controller组件指定一个唯一ID，默认会使用[首字母小写类名]作为组件的唯一ID
	 * @return
	 */
	String value() default "";
	
	/**
	 * 指定一些合法访问的ip地址，来自其他ip地址的请求将会被拒绝
	 * @return
	 */
	String[] ip() default {};
	
	/**
	 * 指定一些合法访问的ip段，来自其他ip地址的请求将会被拒绝
	 * @return
	 */
	String[] ipSection() default "";
	
	/**
	 * 指定对Controller中所有方法的返回值处理策略
	 * 1.Rest.NO(默认选项)：转发与重定向处理,只对返回值类型为String的结果进行处理
	 * a.转发到页面：无前缀 return page
     * b.转发到Controller方法:return forward:method
     * c.重定向到页面：return page:pageing
     * d.重定向到Controller方法：return redirect:method
     * 2.Rest.TXT：将返回值封装为txt格式，并返回给客户端
     * 3.Rest.JSON：将返回值封装为json格式，并返回给客户端
     * 4.Rest.XML：将返回值封装为xml格式，并返回给客户端
	 * @return
	 */
	Rest rest() default Rest.NO;
	
	/**
	 * 视图定位的前缀(eg: /WEB_INF/jsp/),只有在rest=Rest.NO时发挥作用
	 * @return
	 */
	String prefix() default "";
	
	/**
	 * 视图定位的后缀(eg: .jsp),只有在rest=Rest.NO时发挥作用
	 * @return
	 */
	String suffix() default "";
}

```

### 2.第一个Controller

需求：用户访问 http://localhost:8080/lucky/hello ,程序向浏览器返回并打印字符串“Lucky Hello World！”

#### 2.1 编写HelloController类

定义一个Controller组件**HelloController**，代码如下：

```java
package com.lucky.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.lucky.jacklamb.annotation.ioc.Controller;
import com.lucky.jacklamb.annotation.mvc.RequestMapping;

@Controller
@RequestMapping("lucky")
public class HelloController {
	
	//Response对象会在请求到达时被自动注入
	private HttpServletResponse response;
	
	@RequestMapping("/hello")
	public void hello() throws IOException {

		response.getWriter().println("Lucky Hello World!");
		
	}

}

```

#### 2.2 编写Lucky启动类

2.在<u>**最外层的包下**</u>定义一个Lucky的启动类TestApplication,在其中编写一个main方法，方法中调用LuckyApplication.run(Class clzz)方法，启动内嵌Tomcat。项目结构图如下：

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l1.png)



代码如下：

```java
package com.lucky;

import com.lucky.jacklamb.start.LuckyApplication;

public class TestApplication {
	
	public static void main(String[] args) {
		LuckyApplication.run(TestApplication.class);
	}

}
```

3.运行启动类，打开浏览器访问 http://localhost:8080/lucky/hello 即可看到效果：

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l2.png)

### 3.Controller属性以及参数获取

#### 3.1 属性

​	在Controller中可以声明一些特殊的属性，这些属性不需要用户自己初始化，Lucky在封装请求的时候会自动初始化并且自动为Controller注入。这些特殊的属性如下：

```java
//响应对象
private Model model;
//Request对象
private HttpServletRequest request;
//Response对象
private HttpServletResponse response;
//Session对象
private HttpSession session;
//ServletContext对象
private ServletContext aplication;
```

​	**作为测试**，可以在HelloController中声明这些属性，然后在fieldTest方法中依次打印这些对象来印证Lucky的Controller属性自动注入机制，代码如下：

```java
package com.lucky.controller;

@Controller
@RequestMapping("lucky")
public class HelloController {
	
	private Model model;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ServletContext aplication;
    
    ...
	
	@RequestMapping("/controllerField")
	public void fieldTest() {
		System.out.println("Model ==> "+model);
		System.out.println("HttpServletRequest ==> "+request);
		System.out.println("HttpServletResponse"+response);
		System.out.println("HttpSession"+session);
		System.out.println("ServletContext"+aplication);
	}

}

启动TestApplication,在浏览器中输入 http://localhost:8080/lucky/controllerField,可以在控制中看到如下输出：
控制台:

...............

CURR-REQUEST ==> [GET] /lucky/controllerField
    
Model ==> com.lucky.jacklamb.servlet.Model@79dc4515
HttpServletRequest ==> org.apache.catalina.connector.RequestFacade@4c70a46a
HttpServletResponseorg.apache.catalina.connector.ResponseFacade@52a4fae5
HttpSessionorg.apache.catalina.session.StandardSessionFacade@d1b38ae
ServletContextorg.apache.catalina.core.ApplicationContextFacade@167430f5
```

#### 3.2 获取客户端参数

​	**1.获取git请求中的参数**

​		1.在HelloController中添加一个param方法，具体代码如下：

```java
@Controller
@RequestMapping("lucky")
public class HelloController {

    ..............
	
	@RequestMapping("/param")
	public void param(String id,Double price) {
		System.out.println("id="+id);
		System.out.println("price="+price);
	}

}
```

​		2.在Postman中对该方法进行测试(http://localhost:8080/lucky/param?id=1d-123&price=12.6)

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l3.png)

​		3.测试结果

```
控制台：
CURR-REQUEST ==> [GET] /lucky/param
id=id-123
price=12.6

```

​	**2.获取Post请求中的参数**

​		1.将请求类型更换为POST再次进行测试：

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l4.png)

​		2.测试结果

```
控制台：
CURR-REQUEST ==> [POST] /lucky/param
id=id-123
price=12.6

```

​	**3.将请求参数封装到对象**

​	Lucky支持将请求参数直接封装为对象（参数名必须与实体的属性名相同才能完成赋值），创建一个实体类User,并且在HelloController中添加一个pojo方法，具体代码如下：

User类：

```java
package com.lucky.pojo;

import lombok.Data;

@Data
public class User {
    
	private String id;
	private String username;
	private String password;
	private Integer age;

}
```

HelloController的pojo方法：

```java

@Controller
@RequestMapping("lucky")
public class HelloController {
	
	............
	
	@RequestMapping("/pojo")
	public void param(User user) {
		System.out.println(user);
	}
	

}
```

使用Postm进行测试：

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l5.png)

控制台：

```
CURR-REQUEST ==> [POST] /lucky/pojo
User(id=id-8864, username=Lucy, password=PA$$W0RD, age=24)
```



### 4.文件上传下载

#### 	4.1 使用@Upload完成文件上传

​		在Lucky中文件上传是一件极为简单的事情，我们只需要在方法上加上@Upload,并设置@Upload中的names以及filePath。其中names为文件的name属性(可以配置多个，多个用“,隔开)，filePath为文件上传到服务器上的位置(docBase的相对路径)，文件上传到服务器之后为了解决文件同名的问题Lucky会将文件重命名，新的文件名是一串UUID字符串。这个新的文件名会被注入到与@Upload的names属性对应的Controller方法参数中。

在HelloController中添加一个upload方法来完成文件上传操作，具体代码如下：

```java
@Controller
@RequestMapping("lucky")
public class HelloController {

    ........
    
	@Upload(names="file",filePath="upload/images/")
	@RequestMapping("/file")
	public void upload(String file) {
		System.out.println(file);
	}

}

```

在Postman中进行测试

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l6.png)

项目文件：

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l7.png)

控制台：

```
CURR-REQUEST ==> [POST] /lucky/file
4971e7c6-963f-460d-bd67-7cd2acc56a6e.jpg

//可以看出上传到服务器的文件的文件名与控制台中输出的一致
```

#### 	4.2 使用MultipartFile类完成文件上传

​	文件上传也可以使用MultipartFile类来完成，使用这个类时需要在Controller方法中声明MultipartFile对象，方法参数名必须与文件的name属性相同，然后使用MultipartFile对象的方法来完成文件上传，具体实现如下：

​	在HelloController中添加一个multipart方法，具体代码如下：

```java
@Controller
@RequestMapping("lucky")
public class HelloController {

	........
	
	@RequestMapping("multipart")
	public void multipart(MultipartFile file) throws IOException {
		//将文件拷贝到服务器的具体位置
		file.copyToDocRelativePath("upload/mufile/");
		//打印文件名
		System.out.println(file.getFileName());
	}

}

```

​	在Postman中进行测试

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l8.png)

​	项目文件：

![image](https://github.com/FK7075/lucky-ex/blob/noxml/image/l9.png)

​	控制台：

```
CURR-REQUEST ==> [POST] /lucky/multipart
30905c87e264408189af5acb95d4f2b5.jpg

//可以看出上传到服务器的文件的文件名与控制台中输出的一致
```



#### 	4.3 使用@Download完成文件下载

#### 	4.4 使用MultipartFile类完成文件下载

### 5.RestFul风格







