package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.lucky.jacklamb.annotation.aop.Expand;

public class MyMain {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		ProxyFactory factory=ProxyFactory.createProxyFactory();
		PointRun pu1=new PointRun(PointImplOne.class);
		PointRun pu2=new PointRun(PointImplTwo.class);
		List<PointRun> createPointRuns = PointRunFactory.createPointRuns(ExpandClass.class);
		createPointRuns.add(pu1);createPointRuns.add(pu2);
		User user=(User) factory.getProxy(User.class, createPointRuns);
		user.print("OOK",234);
		System.out.println();
		user.printf("dffdfdf");
		
		
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
	
	@Expand(mateMethod="*",params= {"ind:1","23.5","[params]"})
	public void m1(String ss,Double dou,Object[] params) {
		System.out.println("ExpandClass-M1->## ss="+ss+",dou="+dou+",param="+Arrays.toString(params));
	}
	
	@Expand(params= {"ind:1","true","[method]"})
	public void m2(String ss,boolean bool,Method method) {
		System.out.println("ExpandClass-M2->@@ ss="+ss+",bool="+bool+",method="+method);
	}
}