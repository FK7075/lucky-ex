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
	 * �����ǿ��Ĵ������
	 * @param iclzz ��ʵ�������̳еĽӿڵ�Class����
	 * @param raw ��ʵ����
	 * @param now �������
	 * @param nowmethod ����������ǿ����
	 * @param rawmethod ��ǿ�����Ҫ��ǿ����
	 * @param location ��ǿ��ʽ(ǰ����ǿ��������ǿ)
	 * @return ������ǿ֮��Ĵ������
	 */
	public Object getProxyObject(Class<?> iclzz, Object raw, Object now, Method nowmethod, Method rawmethod,
			Location location) {
		Class<?>[] clazz = { iclzz };
		return Proxy.newProxyInstance(raw.getClass().getClassLoader(), clazz, new InvocationHandler() {
			
			private boolean isFirst=true;
			private Object[] obj;
			
			//����ת��
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
			
			//�õ���չ������ִ��ʱ����
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
			
			//�ж�ʵ�����еķ�����ӿڷ����Ķ�Ӧ����
			public boolean isSameMethod(Method raw,Method iraw) {
				//������
				String rawStr=raw.getName();
				String irawStr=iraw.getName();
				//�����б�
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
