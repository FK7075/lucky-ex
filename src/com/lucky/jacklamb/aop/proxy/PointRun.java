package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import com.lucky.jacklamb.annotation.aop.Expand;
import com.lucky.jacklamb.enums.Location;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.tcconversion.typechange.JavaConversion;

public class PointRun {
	
	private Point point;
	
	private String[] mateClass;
	
	private String[] mateMethod;
	
	public PointRun(Point point) {
		Method proceedMethod;
		try {
			proceedMethod = point.getClass().getDeclaredMethod("proceed", Chain.class);
			Expand exp = proceedMethod.getAnnotation(Expand.class);
			this.point = point;
			this.mateClass = exp.mateClass();
			this.mateMethod = exp.mateMethod();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public PointRun(Class<?> pointClass) {
		Method proceedMethod;
		try {
			proceedMethod =pointClass.getDeclaredMethod("proceed", Chain.class);
			Expand exp = proceedMethod.getAnnotation(Expand.class);
			Constructor<?> constructor = pointClass.getConstructor();
			constructor.setAccessible(true);
			this.point = (Point) constructor.newInstance();
			this.mateClass = exp.mateClass();
			this.mateMethod = exp.mateMethod();
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

	public PointRun(Object expand, Method method) {
		Expand exp = method.getAnnotation(Expand.class);
		this.point=conversion(expand,method,exp.location());
		this.mateClass = exp.mateClass();
		this.mateMethod = exp.mateMethod();
	}

	public String[] getIdScope() {
		return mateClass;
	}

	public void setIdScope(String[] idScope) {
		this.mateClass = idScope;
	}

	public String[] getPathScope() {
		return mateMethod;
	}

	public void setPathScope(String[] pathScope) {
		this.mateMethod = pathScope;
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
		return standardStart(method);
	}
	
	/**
	 * ����mateMethod�������֤
	 * @param method ��ǰMethod
	 * @return
	 */
	private boolean standardStart(Method method) {
		String methodName=method.getName();
		Parameter[] parameters = method.getParameters();
		for(String str:mateMethod) {
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
	 * @param methodStr ���������õı�׼������
	 * @return
	 */
	private boolean standardName(String mothodName,String methodStr) {
		if(methodStr.startsWith("!")) {
			return !(mothodName.equals(methodStr.substring(1)));
		}else if(methodStr.startsWith("*")) {
			return mothodName.endsWith(methodStr);
		}else if(methodStr.endsWith("*")) {
			return mothodName.startsWith(methodStr);
		}else {
			return mothodName.equals(methodStr);
		}
	}
	
	/**
	 * ������+����������֤
	 * @param mothodName ��ǰ�����ķ�����
	 * @param parameters ��ǰ�����Ĳ����б�
	 * @param methodStr ���������õı�׼������+����
	 * @return
	 */
	private boolean standardMethod(String mothodName,Parameter[] parameters,String methodStr) {
		int indexOf = methodStr.indexOf("(");
		String methodNameStr;
		boolean pass=true;
		String[] methodParamStr=methodStr.substring(indexOf+1, methodStr.length()-1).split(",");
		if(methodStr.startsWith("!")) {
			if(methodParamStr.length!=parameters.length)
				return true;
			methodNameStr=methodStr.substring(1, indexOf);
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
			methodNameStr=methodStr.substring(0, indexOf);
			for(int i=0;i<methodParamStr.length;i++) {
				if(!(methodParamStr[i].equals(parameters[i].getType().getSimpleName()))) {
					pass=false;
					break;
				}
			}
			return standardName(mothodName,methodNameStr)&&pass;
		}
	}
	
	public static void main(String[] args) {
		String methodStr="show(String,int)";
		int indexOf = methodStr.indexOf("(");
		String[] methodParamStr=methodStr.substring(indexOf+1, methodStr.length()-1).split(",");
		System.out.println(Arrays.toString(methodParamStr));
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
				Expand exp=expandMethod.getAnnotation(Expand.class);
				Parameter[] parameters = expandMethod.getParameters();
				Object[] expandParams=new Object[parameters.length];
				String[] expression = exp.params();
				if(expression.length!=expandParams.length)
					throw new RuntimeException("��ǿ�����Ĳ������ô������ò���������ƥ�䣡����λ�ã�"+expandMethod+" @Expand(params="+Arrays.toString(expression)+")");
				setParams(parameters,expandParams,expression);
				try {
					expandMethod.setAccessible(true);
					return expandMethod.invoke(expand, expandParams);
				} catch (IllegalAccessException e) {
					throw new RuntimeException("IllegalAccessException", e);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("IllegalArgumentException", e);
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
					if(exp.startsWith("ref:")) {//ȡ�����е�ֵ
						if("ref:".equals(exp.trim())) 
							expandParams[i]=ApplicationBeans.createApplicationBeans().getBean(parameters[i].getType());
						else 
							expandParams[i]=ApplicationBeans.createApplicationBeans().getBean(exp.substring(4));
						
					}else if(exp.startsWith("ind:")) {//��ʵ�����еĲ����б�ֵ
						indexStr=exp.substring(4).trim();
						index=Integer.parseInt(indexStr);
						if(index<1||index>params.length)
							throw new RuntimeException("����ı��ʽ���������ʽ�е��������������б�������Χ������λ�ã�"+expandMethod+"  @Expand(params="+Arrays.toString(expression)+")->"+exp);
						expandParams[i]=params[index-1];	
					} else if(exp.equals("[params]")){//�����б�
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
