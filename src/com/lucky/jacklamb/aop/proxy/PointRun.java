package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import com.lucky.jacklamb.annotation.aop.After;
import com.lucky.jacklamb.annotation.aop.Around;
import com.lucky.jacklamb.annotation.aop.Before;
import com.lucky.jacklamb.enums.Location;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.tcconversion.typechange.JavaConversion;

public class PointRun {
	
	private Point point;
	
	private String aspect;
	
	private String pointcat;
	
	public Method method;
	
	/**
	 * ʹ��һ��Point������PointRun
	 * @param point
	 */
	public PointRun(Point point) {
		Method proceedMethod;
		try {
			proceedMethod = point.getClass().getDeclaredMethod("proceed", Chain.class);
			Around exp = proceedMethod.getAnnotation(Around.class);
			this.point = point;
			this.aspect = exp.aspect();
			this.pointcat = exp.pointcat();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * ʹ��Point���Ͷ����Class������PointRun
	 * @param pointClass
	 */
	public PointRun(Class<?> pointClass) {
		Method proceedMethod;
		try {
			proceedMethod =pointClass.getDeclaredMethod("proceed", Chain.class);
			Around exp = proceedMethod.getAnnotation(Around.class);
			Constructor<?> constructor = pointClass.getConstructor();
			constructor.setAccessible(true);
			this.point = (Point) constructor.newInstance();
			this.aspect = exp.aspect();
			this.pointcat = exp.pointcat();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * ʹ����ǿ���ʵ������+��ǿ����Method������PointRun
	 * @param expand ��ǿ��ʵ��
	 * @param method ��ǿ(����)
	 */
	public PointRun(Object expand, Method method) {
		this.method=method;
		if(method.isAnnotationPresent(Before.class)) {
			Before before=method.getAnnotation(Before.class);
			this.point=conversion(expand,method,Location.BEFORE);
			this.aspect = before.aspect();
			this.pointcat = before.pointcat();
		}else if(method.isAnnotationPresent(After.class)) {
			After after=method.getAnnotation(After.class);
			this.point=conversion(expand,method,Location.AFTER);
			this.aspect = after.aspect();
			this.pointcat = after.pointcat();
		}
	}

	public String getMateClass() {
		return aspect;
	}

	public void setMateClass(String mateClass) {
		this.aspect = mateClass;
	}

	public String getMateMethod() {
		return pointcat;
	}

	public void setMateMethod(String mateMethod) {
		this.pointcat = mateMethod;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	/**
	 * ���鵱ǰ�����Ƿ���ϸ�Point��ִ�б�׼
	 * @param method
	 * @return
	 */
	public boolean standard(Method method) {
		try {
			return standardStart(method);
		}catch(StringIndexOutOfBoundsException e) {
			throw new RuntimeException("��������ô��󣬴���λ�ã�"+method+" ->@Before/@After/@Around(pointcat=>err)", e);
		}
	}
	
	/**
	 * ����mateMethod�������֤
	 * @param method ��ǰMethod
	 * @return
	 */
	private boolean standardStart(Method method) {
		String methodName=method.getName();
		Parameter[] parameters = method.getParameters();
		String[] pointcutStr=pointcat.split(",");
		if(Arrays.asList(pointcutStr).contains("public")) {
			//�Ƿ�������public,���������public�������з�public��������ִ�и���ǿ
			if(method.getModifiers()!=1)
				return false;
		}
		for(String str:pointcutStr) {
			if("*".equals(str)) {
				return true;
			}else if(str.contains("(")&&str.endsWith(")")){
				if(standardMethod(methodName,parameters,str))
					return true;
			}else {
				if(standardName(methodName,str))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * ��������֤
	 * @param mothodName ��ǰ�����ķ�����
	 * @param pointcut ���������õı�׼������
	 * @return
	 */
	private boolean standardName(String mothodName,String pointcut) {
		if(pointcut.startsWith("!")) {
			return !(mothodName.equals(pointcut.substring(1)));
		}else if(pointcut.startsWith("*")) {
			return mothodName.endsWith(pointcut.substring(1));
		}else if(pointcut.endsWith("*")) {
			return mothodName.startsWith(pointcut.substring(0, pointcut.length()-1));
		}else {
			return mothodName.equals(pointcut);
		}
	}
	
	/**
	 * ������+����������֤
	 * @param mothodName ��ǰ�����ķ�����
	 * @param parameters ��ǰ�����Ĳ����б�
	 * @param pointcut ���������õı�׼������+����
	 * @return
	 */
	private boolean standardMethod(String mothodName,Parameter[] parameters,String pointcut) {
		int indexOf = pointcut.indexOf("(");
		String methodNameStr;
		boolean pass=true;
		String[] methodParamStr=pointcut.substring(indexOf+1, pointcut.length()-1).split(",");
		if(pointcut.startsWith("!")) {
			if(methodParamStr.length!=parameters.length)
				return true;
			methodNameStr=pointcut.substring(1, indexOf);
			for(int i=0;i<methodParamStr.length;i++) {
				if(!(methodParamStr[i].equals(parameters[i].getType().getSimpleName()))) {
					pass=false;
					break;
				}
			}
			return !(standardName(mothodName,methodNameStr)&&pass);
		}else {//û��  ��
			if(methodParamStr.length!=parameters.length)
				return false;
			methodNameStr=pointcut.substring(0, indexOf);
			for(int i=0;i<methodParamStr.length;i++) {
				if(!(methodParamStr[i].equals(parameters[i].getType().getSimpleName()))) {
					pass=false;
					break;
				}
			}
			return standardName(mothodName,methodNameStr)&&pass;
		}
	}
	
	/**
	 * ʹ����ǿ���ִ�в�������Point
	 * @param expand ��ǿ��ʵ��
	 * @param expandMethod ��ǿ�෽��
	 * @param location ��ǿλ��
	 * @return
	 */
	private Point conversion(Object expand, Method expandMethod,Location location) {
		Point cpoint=new Point() {
			
			@Override
			public Object proceed(Chain chain) {
				if(location==Location.BEFORE) {
					perform(expand,expandMethod);
					return chain.proceed();
				}else if(location==Location.AFTER) {
					Object result=chain.proceed();
					perform(expand,expandMethod);
					return result;
				}
				return null;
			}
			
			//ִ����ǿ����
			private Object perform(Object expand, Method expandMethod) {
				String[] expression = null;
				if(expandMethod.isAnnotationPresent(Before.class)) {
					expression=expandMethod.getAnnotation(Before.class).params();
				}else if(expandMethod.isAnnotationPresent(After.class)) {
					expression=expandMethod.getAnnotation(After.class).params();
				}
				Parameter[] parameters = expandMethod.getParameters();
				Object[] expandParams=new Object[parameters.length];
				if(expression.length!=expandParams.length)
					throw new RuntimeException("��ǿ�����Ĳ������ô������ò���������ƥ�䣡����λ�ã�"+expandMethod+" @Around(params="+Arrays.toString(expression)+")");
				setParams(parameters,expandParams,expression);
				try {
					expandMethod.setAccessible(true);
					return expandMethod.invoke(expand, expandParams);
				} catch (IllegalAccessException e) {
					throw new RuntimeException("IllegalAccessException", e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("�������Ͳ�ƥ��!����ǿ�������������޷���Ŀ�귽�������б��ȡ�Ĳ���������λ�ã�"+expandMethod+",@Before/@After/@Around(params=>err)", e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException("InvocationTargetException", e);
				}
			}
			
			//������ǿ������ִ�в���
			private void setParams(Parameter[] parameters,Object[] expandParams,String[] expression) {
				parameters = expandMethod.getParameters();
				int i=0;
				String indexStr;
				int index;
				for(String exp:expression) {
					if(exp.startsWith("ref:")) {//ȡIOC�����е�ֵ
						if("ref:".equals(exp.trim())) 
							expandParams[i]=ApplicationBeans.createApplicationBeans().getBean(parameters[i].getType());
						else 
							expandParams[i]=ApplicationBeans.createApplicationBeans().getBean(exp.substring(4));
						
					}else if(exp.startsWith("ind:")) {//Ŀ�귽���еĲ����б�ֵ��ָ��λ�õĲ���ֵ
						indexStr=exp.substring(4).trim();
						index=Integer.parseInt(indexStr);
						if(index<1||index>params.length)
							throw new RuntimeException("����ı��ʽ���������ʽ�е��������������б�������Χ������λ�ã�"+expandMethod+"  @Around(params="+Arrays.toString(expression)+")->"+exp);
						expandParams[i]=params[index-1];	
					} else if(exp.equals("[params]")){//���������б�
						expandParams[i]=params;
					}else if(exp.equals("[method]")) {//Method����
						expandParams[i]=method;
					} else{//��ֵͨ
						expandParams[i]=JavaConversion.strToBasic(exp, parameters[i].getType());
					}
					i++;
				}
			}
		};
		return cpoint;
	}
}
