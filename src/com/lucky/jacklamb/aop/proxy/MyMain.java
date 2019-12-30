package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.enums.Location;

public class MyMain {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		ProxyFactory factory=ProxyFactory.createProxyFactory();
		List<PerformMethod> performMethods=new ArrayList<>();
		Class<?> clzz=User.class;
		
		//构造PerformMethod对象
		PerformMethod pms=new PerformMethod();
		pms.setTargetClass(clzz);
		pms.addTargetMethodName(clzz.getDeclaredMethod("print",String.class),clzz.getDeclaredMethod("printf",String.class));
		Method method1=ExpandClass.class.getDeclaredMethod("m1");
		MethodRun methodRun=new MethodRun(new ExpandClass(), method1, null, Location.BEFORE);
		pms.setMethodRun(methodRun);
		
		//构造PerformMethod对象
		PerformMethod pms1=new PerformMethod();
		pms1.setTargetClass(User.class);
		pms1.addTargetMethodName(clzz.getDeclaredMethod("printf",String.class));
		Method method2=ExpandClass.class.getDeclaredMethod("m2");
		MethodRun methodRun2=new MethodRun(new ExpandClass(), method2, null,Location.AFTER);
		pms1.setMethodRun(methodRun2);
		performMethods.add(pms);performMethods.add(pms1);
		
		//构造PointRun对象
		List<Method> methods=new ArrayList<>();
		methods.add(clzz.getDeclaredMethod("print",String.class));
		PointRun pointRun1=new PointRun(new PointImplOne(), methods);
		
		//构造PointRun对象
		List<Method> methods1=new ArrayList<>();
		methods1.add(clzz.getDeclaredMethod("printf",String.class));
		PointRun pointRun2=new PointRun(new PointImplTwo(), methods1);
		
		//使用PerformMethod对象和PointRun对象得到真实对象的代理对象，并执行方法
		User user=(User) factory.getProxy(User.class, performMethods,pointRun1,pointRun2);
		user.print("OKO");
		System.out.println();
		user.printf("NO");
	}

}

class PointImplOne implements Point{

	@Override
	public Object proceed(Chain chain) {
		Object result;
		System.out.println("22222222222222");
		result=chain.proceed();
		System.out.println("33333333333333");
		return result;
	}
	
}

class PointImplTwo implements Point{

	@Override
	public Object proceed(Chain chain) {
		Object result = null;
		try {
			result=chain.proceed();
			
		}catch(Throwable e) {
			System.out.println("捕获异常，执行异常处理操作");
		}
		return result;

	}
	
}



class User{
	
	public void print(String info) {
		System.out.println("METHOD:print->"+info);
	}
	
	public void printf(String info) {
		int i=9/0;
		System.out.println("METHOD:printf->"+info);
	}
	
}

class ExpandClass{
	public void m1() {
		System.out.println("ExpandClass-M1");
	}
	
	public void m2() {
		System.out.println("ExpandClass-M2");
	}
}