package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.lucky.jacklamb.annotation.aop.Expand;
import com.lucky.jacklamb.enums.Location;

public class MyMain {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		ProxyFactory factory=ProxyFactory.createProxyFactory();
		PointRun pu1=new PointRun(PointImplOne.class);
		PointRun pu2=new PointRun(PointImplTwo.class);
		List<PointRun> createPointRuns = PointRunFactory.createPointRuns(ExpandClass.class);
		createPointRuns.add(pu1);createPointRuns.add(pu2);
		User user=(User) factory.getProxy(User.class, createPointRuns);
		Field declaredField = user.getClass().getField("username");
		declaredField.setAccessible(true);
		declaredField.set(user, "Lucky");
		System.out.println(user.username);
		user.print("OOK",234);
		
		
	}

}

class PointImplOne extends Point{
	
	public PointImplOne() {}

	@Expand
	public Object proceed(Chain chain) {
		Object result;
		System.out.println("真实方法名："+method.getName());
		result=chain.proceed();
		System.out.println("方法参数列表："+Arrays.toString(params));
		return result;
	}
	
}

class PointImplTwo extends Point{

	public PointImplTwo() {}
	
	@Expand
	public Object proceed(Chain chain) {
		Object result = null;
		try {
			System.out.println("TWO-B");
			result=chain.proceed();
			System.out.println("TWO-A");
			
		}catch(Throwable e) {
			System.out.println("捕获异常，执行异常处理操作");
			e.printStackTrace();
		}
		return result;

	}
	
}



class User{
	
	public String username;

	public String print(String info,int index) {
		System.out.println("METHOD:print=="+info+",index="+index);
		return info;
	}
	
	public void printf(String info) {
//		int i=9/0;
		System.out.println("METHOD:printf==@@"+info);
	}
	
}

class ExpandClass{
	
	public ExpandClass() {}
	
	@Expand(params= {"ind:1","23.5","[params]"},location=Location.AFTER)
	public void m1(String ss,Double dou,Object[] params) {
		System.out.println("ExpandClass-M1->## ss="+ss+",dou="+dou+",param="+Arrays.toString(params));
	}
	
	@Expand(params= {"ind:1","true","[method]"})
	public void m2(String ss,boolean bool,Method method) {
		System.out.println("ExpandClass-M2->@@ ss="+ss+",bool="+bool+",method="+method);
	}
}