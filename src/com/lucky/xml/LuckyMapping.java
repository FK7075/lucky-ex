//package com.lucky.xml;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LuckyMapping {
//	
//	private String id;//IOC beanΨһ��ʶ
//	private String methodid; //MethodΨһ��ʶ
//	private String methodname; //Method��
//	private List<String> paramlist=new ArrayList<>();//�����б�
//
//	/**
//	 * Controller������Ψһ��ʶ
//	 * @return
//	 */
//	public String getMethodid() {
//		return methodid;
//	}
//	/**
//	 * Controller������Ψһ��ʶ
//	 * �����ڷ�����@RequestMapping
//	 * @param methodid
//	 */
//	public void setMethodid(String methodid) {
//		this.methodid = methodid;
//	}
//	/**
//	 * ControllerΨһ��ʶ
//	 * @return
//	 */
//	public String getId() {
//		return id;
//	}
//	/**
//	 * ����Controller��Ψһ��ʶ
//	 * ����Controller�ϵ�@RequestMapping
//	 * @param id
//	 */
//	public void setId(String id) {
//		this.id = id;
//	}
//	
//	/**
//	 * Controller�����ķ�����
//	 * @return
//	 */
//	public String getMethodname() {
//		return methodname;
//	}
//	
//	/**
//	 * ��ǰ�����ķ�����
//	 * @param methodname
//	 */
//	public void setMethodname(String methodname) {
//		this.methodname = methodname;
//	}
//	/**
//	 * �����Ĳ����б�
//	 * @return
//	 */
//	public List<String> getParamlist() {
//		return paramlist;
//	}
//	/**
//	 * ��ǰ�����Ĳ����б������
//	 * @param paramlist
//	 */
//	public void setParamlist(List<String> paramlist) {
//		this.paramlist = paramlist;
//	}
//	
//	@Override
//	public String toString() {
//		return "LuckyMapping [id=" + id + ", methodid=" + methodid + ", methodname=" + methodname + ", paramlist="
//				+ paramlist + "]";
//	}
//
//
//}
