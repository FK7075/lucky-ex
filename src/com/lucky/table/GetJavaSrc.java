package com.lucky.table;

/**
 * ��װ��һ�����ӦJavaBean������Դ����
 * @author fk-7075
 *
 */
public class GetJavaSrc {
	private String className;//����
	private String pack;//���ڰ�
	private String impor;//����İ�
	private String javaSrc;//Դ����
	private String toString;//toString����
	private String constructor;//�޲ι�����Դ��
	private String parameterConstructor;//�����������Ե��вι�����


	
	public String getConstructor() {
		return constructor;
	}


	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}


	public String getParameterConstructor() {
		return parameterConstructor;
	}


	public void setParameterConstructor(String parameterConstructor) {
		this.parameterConstructor = parameterConstructor;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getPack() {
		return pack;
	}


	public void setPack(String pack) {
		this.pack = pack;
	}


	public String getImpor() {
		return impor;
	}


	public void setImpor(String impor) {
		this.impor = impor;
	}


	public String getJavaSrc() {
		return javaSrc;
	}


	public void setJavaSrc(String javaSrc) {
		this.javaSrc = javaSrc;
	}


	public String getToString() {
		return toString;
	}


	public void setToString(String toString) {
		this.toString = toString;
	}
	

	@Override
	public String toString() {
		return pack+"\n"+impor+"\n"+javaSrc+toString;
	}


}
