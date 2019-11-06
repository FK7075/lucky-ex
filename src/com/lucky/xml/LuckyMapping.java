//package com.lucky.xml;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LuckyMapping {
//	
//	private String id;//IOC bean唯一标识
//	private String methodid; //Method唯一标识
//	private String methodname; //Method名
//	private List<String> paramlist=new ArrayList<>();//参数列表
//
//	/**
//	 * Controller方法的唯一标识
//	 * @return
//	 */
//	public String getMethodid() {
//		return methodid;
//	}
//	/**
//	 * Controller方法的唯一标识
//	 * 类似于方法上@RequestMapping
//	 * @param methodid
//	 */
//	public void setMethodid(String methodid) {
//		this.methodid = methodid;
//	}
//	/**
//	 * Controller唯一标识
//	 * @return
//	 */
//	public String getId() {
//		return id;
//	}
//	/**
//	 * 设置Controller的唯一标识
//	 * 类似Controller上的@RequestMapping
//	 * @param id
//	 */
//	public void setId(String id) {
//		this.id = id;
//	}
//	
//	/**
//	 * Controller方法的方法名
//	 * @return
//	 */
//	public String getMethodname() {
//		return methodname;
//	}
//	
//	/**
//	 * 当前方法的方法名
//	 * @param methodname
//	 */
//	public void setMethodname(String methodname) {
//		this.methodname = methodname;
//	}
//	/**
//	 * 方法的参数列表
//	 * @return
//	 */
//	public List<String> getParamlist() {
//		return paramlist;
//	}
//	/**
//	 * 当前方法的参数列表的类型
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
