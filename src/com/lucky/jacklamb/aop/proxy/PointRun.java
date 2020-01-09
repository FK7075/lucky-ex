package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import com.lucky.jacklamb.annotation.aop.After;
import com.lucky.jacklamb.annotation.aop.AopParam;
import com.lucky.jacklamb.annotation.aop.Around;
import com.lucky.jacklamb.annotation.aop.Before;
import com.lucky.jacklamb.enums.Location;
import com.lucky.jacklamb.exception.IllegaAopparametersException;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.tcconversion.typechange.JavaConversion;

public class PointRun {
	
	private Point point;
	
	private String pointCutClass;
	
	private String pointCutMethod;
	
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
			this.pointCutClass = exp.pointCutClass();
			this.pointCutMethod = exp.pointCutMethod();
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
			this.pointCutClass = exp.pointCutClass();
			this.pointCutMethod = exp.pointCutMethod();
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
			this.pointCutClass = before.pointCutClass();
			this.pointCutMethod = before.pointCutMethod();
		}else if(method.isAnnotationPresent(After.class)) {
			After after=method.getAnnotation(After.class);
			this.point=conversion(expand,method,Location.AFTER);
			this.pointCutClass = after.pointCutClass();
			this.pointCutMethod = after.pointCutMethod();
		}
	}

	public String getMateClass() {
		return pointCutClass;
	}

	public void setMateClass(String mateClass) {
		this.pointCutClass = mateClass;
	}

	public String getMateMethod() {
		return pointCutMethod;
	}

	public void setMateMethod(String mateMethod) {
		this.pointCutMethod = mateMethod;
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
		String[] pointcutStr=pointCutMethod.split(",");
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
				try {
					expandMethod.setAccessible(true);
					return expandMethod.invoke(expand, setParams(expandMethod));
				} catch (IllegalAccessException e) {
					throw new RuntimeException("IllegalAccessException", e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("�������Ͳ�ƥ��!����ǿ�������������޷���Ŀ�귽�������б��ȡ�Ĳ���������λ�ã�"+expandMethod+ e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException("InvocationTargetException", e);
				}
			}
			
			//������ǿ������ִ�в���-@AopParam����
			private Object[] setParams(Method expandMethod) {
				int index;
				String aopParamValue,indexStr;
				Parameter[] parameters = expandMethod.getParameters();
				Object[] expandParams=new Object[parameters.length];
				for(int i=0;i<parameters.length;i++) {
					if(!parameters[i].isAnnotationPresent(AopParam.class))
						throw new IllegaAopparametersException("�޷�ʶ���AOP������ǰ����ǿ�������ǿ�д����޷�ʶ��Ĳ���������ԭ�򣺿���û��ʹ��@AopParamע���ע����������λ�ã�"+expandMethod);
					aopParamValue=parameters[i].getAnnotation(AopParam.class).value();
					if(aopParamValue.startsWith("ref:")) {//ȡIOC�����е�ֵ
						if("ref:".equals(aopParamValue.trim())) 
							expandParams[i]=ApplicationBeans.createApplicationBeans().getBean(parameters[i].getType());
						else 
							expandParams[i]=ApplicationBeans.createApplicationBeans().getBean(aopParamValue.substring(4));
						
					}else if(aopParamValue.startsWith("ind:")) {//Ŀ�귽���еĲ����б�ֵ��ָ��λ�õĲ���ֵ
						indexStr=aopParamValue.substring(4).trim();
						try {
							index=Integer.parseInt(indexStr);
						}catch(NumberFormatException e) {
							throw new RuntimeException("����ı��ʽ���������ʽ�е��������Ϸ�������ֻ��Ϊ����������λ�ã�"+expandMethod+"@AopParam("+aopParamValue+")=>err");
						}
						if(index<1||index>params.length)
							throw new RuntimeException("����ı��ʽ���������ʽ�е��������������б�������Χ������λ�ã�"+expandMethod+"@AopParam("+aopParamValue+")=>err");
						expandParams[i]=params[index-1];	
					} else if(aopParamValue.equals("[params]")){//���������б�
						expandParams[i]=params;
					}else if(aopParamValue.equals("[method]")) {//Method����
						expandParams[i]=method;
					}else if(aopParamValue.equals("[target]")) {//Ŀ�����һ������ʵ��
						expandParams[i]=target;
					} else{//��ֵͨ
						expandParams[i]=JavaConversion.strToBasic(aopParamValue, parameters[i].getType());
					}
				}
				return expandParams;
			}
		};
		return cpoint;
	}
	
}
