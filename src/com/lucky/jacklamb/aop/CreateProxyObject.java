package com.lucky.jacklamb.aop;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.lucky.jacklamb.annotation.Real;
import com.lucky.jacklamb.enums.Location;

@SuppressWarnings("all")
public class CreateProxyObject {

	/**
	 * 获得增强后的代理对象
	 * @param iclzz 真实对象所继承的接口的Class对象
	 * @param raw 真实对象
	 * @param now 代理对象
	 * @param nowmethod 代理对象的增强方法
	 * @param rawmethod 增强对象的要增强方法
	 * @param location 增强方式(前置增强，后置增强)
	 * @return 方法增强之后的代理对象
	 */
	public Object getProxyObject(Class<?> iclzz, Object raw, Object now, Method nowmethod, Method rawmethod,
			Location location) {
		Class<?>[] clazz = { iclzz };
		return Proxy.newProxyInstance(raw.getClass().getClassLoader(), clazz, new InvocationHandler() {
			
			private boolean isFirst=true;
			private Object[] obj;
			
			//类型转换
			private Object typeCast(String goal, String type) {
				if(goal.indexOf("#")==0)
					return goal;
				switch (type) {
				case "int":
					return Integer.parseInt(goal);
				case "Integer":
					return Integer.parseInt(goal);
				case "Double":
					return Double.parseDouble(goal);
				case "double":
					return Double.parseDouble(goal);
				default:
					return goal;
				}
			}
			public Map<Integer, Object> getRwMap(Object[] obj) {
				Map<Integer, Object> rwmap = new HashMap<>();
				for (int i = 0; i < obj.length; i++) {
					if (obj[i] instanceof String) {
						String info = (String) obj[i];
						if (info.contains("#")) {
							rwmap.put(i, info.replaceAll("#", ""));
						}
					}
				}
				return rwmap;
			}
			
			//得到扩展方法的执行时参数
			public Object[] getNowMap( Method nowmthod,Method rwmethod,Object[] rw_objs) throws IOException, ClassNotFoundException {
				Real real=rwmethod.getAnnotation(Real.class);
				Parameter[] params=rwmethod.getParameters();
				Class<?>[] clzz=nowmethod.getParameterTypes();
				String[] message=real.parameter();
				Object[] object=new Object[message.length];
				for(int i=0;i<message.length;i++) {
					object[i]=typeCast(message[i],clzz[i].getSimpleName());
				}
				Map<Integer, Object> rwmap = getRwMap(object);
				Map<String, Integer> map = new HashMap<>();
				int i = 0;
				for (Parameter para : params) {
					map.put(para.getName(), i++);
				}
				for (Map.Entry<Integer, Object> entry : rwmap.entrySet()) {
					if (map.containsKey(entry.getValue())) {
						object[entry.getKey()] = rw_objs[map.get(entry.getValue())];
					}
				}
				return object;
			}
			
			//判断实现类中的方法与接口方法的对应规则
			public boolean isSameMethod(Method raw,Method iraw) {
				//方法名
				String rawStr=raw.getName();
				String irawStr=iraw.getName();
				//参数列表
				Class<?>[] rawClzz=raw.getParameterTypes();
				Class<?>[] irawClzz=iraw.getParameterTypes();
				if(rawStr.equals(irawStr)&&rawClzz.length==irawClzz.length) {
					boolean isOk=true;
					for(int i=0;i<rawClzz.length;i++) {
						if(!rawClzz[i].getName().equals(irawClzz[i].getName()))
							isOk=false;
					}
					return isOk;
				}else
					return false;
			}

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object ret = null;
				if (isSameMethod(rawmethod,method)) {
					Object[] object = getNowMap(nowmethod, rawmethod, args);
					if (location == Location.BEFORE) {
						nowmethod.invoke(now, object);
						ret = method.invoke(raw, args);
					}
					if (location == Location.AFTER) {
						ret = method.invoke(raw, args);
						nowmethod.invoke(now, object);
					}
					if(location==Location.CONDITIONS) {
						boolean isPass=(boolean) nowmethod.invoke(now, object);
						if(isPass)
							ret = method.invoke(raw, args);
						else
							ret=null;
					}
				} else {
					ret = method.invoke(raw, args);
				}
				return ret;
			}
		});
	}

}
