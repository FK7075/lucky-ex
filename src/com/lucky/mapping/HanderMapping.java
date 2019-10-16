package com.lucky.mapping;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lucky.annotation.Agent;
import com.lucky.annotation.App;
import com.lucky.annotation.Autowired;
import com.lucky.annotation.Controller;
import com.lucky.annotation.Dao;
import com.lucky.annotation.Expand;
import com.lucky.annotation.Real;
import com.lucky.annotation.RequestMapping;
import com.lucky.annotation.Service;
import com.lucky.aop.CreateProxyObject;
import com.lucky.enums.Location;
import com.lucky.servlet.Model;
import com.lucky.sqldao.SqlCore;
import com.lucky.sqldao.SqlCoreFactory;
import com.lucky.utils.LuckyManager;
import com.lucky.xml.LuckyMapping;
import com.lucky.xml.LuckyXml;
import com.lucky.xml.LuckyXmlConfig;

import java.util.Set;

public class HanderMapping {

	private PackageScan ps;
	private Set<String> controllerMaps;
	private Map<String, String> pre_suf;
	private List<String> setter_pre_suf;
	private List<String> classNames;//��ͨioc��������ȫ·��
	private List<String> mapperclassNames;//mapper�ӿڵ�ȫ·��
	private Map<String, Object> beans;//IOC����
	private Map<String,ControllerAndMethod> urlMap;//Urlӳ������
	private Map<String, Object> handerMaps2;
	private Map<String, Object> aopMap;
	private Map<String, Method> expandmethodMap;
	private CreateProxyObject aop = new CreateProxyObject();
	private String encod;
	
	
	public String getEncod() {
		return encod;
	}

	public void setEncod(String encod) {
		this.encod = encod;
	}

	public List<String> getMapperclassNames() {
		return mapperclassNames;
	}

	public List<String> getSetter_pre_suf() {
		return setter_pre_suf;
	}

	public void setBeans(Map<String, Object> beans) {
		this.beans = beans;
	}

	public Map<String, String> getPre_suf() {
		return pre_suf;
	}

	public List<String> getClassNames() {
		return classNames;
	}

	public Map<String, Method> getExpandmethodMap() {
		return expandmethodMap;
	}

	public Map<String, Object> getAopMap() {
		return aopMap;
	}

	public Set<String> getControllerMaps() {
		return controllerMaps;
	}

	public Map<String, Object> getHanderMaps2() {
		return handerMaps2;
	}

	public Map<String, Object> getBeans() {
		return beans;
	}

	// ��ʼ������
	public HanderMapping() {
		ps=new PackageScan();
		pre_suf = new HashMap<>();
		setter_pre_suf = new ArrayList<>();
		controllerMaps = new HashSet<String>();
		classNames = new ArrayList<String>();
		mapperclassNames = new ArrayList<String>();
		beans = new HashMap<String, Object>();
		handerMaps2 = new HashMap<String, Object>();
		urlMap=new HashMap<>();
		aopMap = new HashMap<String, Object>();
		expandmethodMap = new HashMap<String, Method>();
		List<String> pack = LuckyManager.getPropCfg().getScans();
		List<String> scanmappers=LuckyManager.getPropCfg().getScans_mapper();
		if(pack.isEmpty()) {
			ps.loadComponent(classNames);
		}else {
			for (String str : pack) {
				scanIocComponent(str);
			}
		}
		if(scanmappers.isEmpty()) {
			ps.loadMapper(mapperclassNames);
		}else {
			for (String str : scanmappers) {
				scanMapper(str);
			}
		}
	}
	
	/**
	 * ����IOC���
	 * @param basePackage ��Ҫɨ��İ��ľ���·��
	 */
	public void scanIocComponent(String basePackage) {
		doScan(basePackage,false);
	}
	
	/**
	 * ����Mapper�ӿ����
	 * @param basePackage ��Ҫɨ��İ��ľ���·��
	 */
	public void scanMapper(String basePackage) {
		doScan(basePackage,true);
	}
	
	


	/**
	 * ����ע������ɨ�裬ɨ��õ�����IOC�����Mapper���
	 * @param basePackage ��Ҫɨ����ļ��еľ���·��
	 * @param isMapper �Ƿ���Mapper���
	 */
	private void doScan(String basePackage,boolean isMapper) {
		String path_f;
		URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
		if (url == null) {
			url = this.getClass().getClassLoader().getResource("");
			path_f = url.getPath() + "/" + basePackage.replaceAll("\\.", "/");
		} else {
			path_f = url.getFile();
		}
		File files = new File(path_f);
		String[] filesStr = files.list();// ��ø��ļ����µ������ļ����ļ��еľ���·��
		for (String path : filesStr) {// ����
			File file = new File(path_f+"/"+ path);
			if (file.isDirectory()) {// �жϸ��ļ��Ƿ����ļ���
				doScan(basePackage + "." + path,isMapper);// ���ļ��������
			} else {
				if(isMapper) {
					if(file.getName().contains(".class"))
						mapperclassNames.add(basePackage + "." + file.getName());// ���ļ���mapper�ӿڵ�ȫ·���ӵ�mapperclassNames ��
				}
				else {
					if(file.getName().contains(".class"))
						classNames.add(basePackage + "." + file.getName());// ���ļ�����ͨ�����ȫ·���ӵ�classNames ��
				}
			}
		}
	}

	/**
	 * ����Xml�ļ������ɨ�裬ɨ��õ�����IOC���
	 */
	public void doScanToXml() {
		/*
		 * bug����:xml�����ļ���ʽ�����������Ȼ�����������Ҹ������ϻ������͵�ֵ��������ɺ��������ע��
		 * ע��ʱ����ioc�����еĶ���Ϊ��׼�������飬map��ע��ʱ���ܻḲ��ԭ�ȵ�ֵ��������һ����������ò�Ҫ���ʹ�� ����ֵ������ֵ��ע��
		 */
		List<LuckyXml> list = LuckyXmlConfig.loadLuckyXmlConfig().getBeans();
		try {
			for (LuckyXml lucky : list) {
				Class<?> clzz = Class.forName(lucky.getCpath());
				String noparam = lucky.getNo_parameter();
				Constructor<?> co = clzz.getDeclaredConstructor();
				co.setAccessible(true);
				Object obj = co.newInstance();
				Map<String, String> nv = lucky.getNv();
				for (Map.Entry<String, String> entry : nv.entrySet()) {
					Field field = clzz.getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj, typeCast(entry.getValue(), field.getType().getSimpleName()));
				}
				for (Entry<String, List<Object>> entry : lucky.getN_array().entrySet()) {
					Field field = clzz.getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj, entry.getValue().toArray());
				}
				for (Map.Entry<String, List<Object>> entry : lucky.getN_list().entrySet()) {
					Field field = clzz.getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj, entry.getValue());
				}
				for (Map.Entry<String, Set<Object>> entry : lucky.getN_set().entrySet()) {
					Field field = clzz.getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj, entry.getValue());
				}
				for (Map.Entry<String, Map<Object, Object>> entry : lucky.getN_map().entrySet()) {
					Field field = clzz.getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj, entry.getValue());
				}
				if (noparam == null)
					beans.put(lucky.getId(), obj);
				else if (noparam != null) {
					Method method = clzz.getDeclaredMethod(noparam);
					beans.put(lucky.getId(), method.invoke(obj));
				}
			}
			for (LuckyXml cl : list) {
				Map<String, String> nr = cl.getNr();
				Object obj1 = beans.get(cl.getId());
				for (Map.Entry<String, String> entry : nr.entrySet()) {
					Object obj2 = beans.get(entry.getValue());
					Field field = obj1.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj1, obj2);
				}
				for (Entry<String, List<String>> entry : cl.getN_array_ref().entrySet()) {
					List<Object> array_list = new ArrayList<>();
					for (String ref_array : entry.getValue()) {
						array_list.add(beans.get(ref_array));
					}
					Field field = obj1.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj1, array_list.toArray());
				}
				for (Entry<String, List<String>> entry : cl.getN_list_ref().entrySet()) {
					List<Object> list_r = new ArrayList<>();
					for (String ref_list : entry.getValue()) {
						list_r.add(beans.get(ref_list));
					}
					Field field = obj1.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj1, list_r);
				}
				for (Entry<String, Set<String>> entry : cl.getN_set_ref().entrySet()) {
					Set<Object> set_r = new HashSet<>();
					for (String ref_set : entry.getValue()) {
						set_r.add(beans.get(ref_set));
					}
					Field field = obj1.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj1, set_r);
				}
				for (Entry<String, Map<Object, String>> entry : cl.getN_map_ref().entrySet()) {
					Map<Object, Object> map = new HashMap<>();
					for (Entry<Object, String> enty : entry.getValue().entrySet()) {
						map.put(enty.getKey(), beans.get(enty.getValue()));
					}
					Field field = obj1.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj1, map);
				}
				for (Entry<String, Map<String, Object>> entry : cl.getN_ref_map().entrySet()) {
					Map<Object, Object> map = new HashMap<>();
					for (Entry<String, Object> enty : entry.getValue().entrySet()) {
						map.put(beans.get(enty.getKey()), entry.getValue());
					}
					Field field = obj1.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj1, map);
				}
				for (Entry<String, Map<String, String>> entry : cl.getN_ref_map_ref().entrySet()) {
					Map<Object, Object> map = new HashMap<>();
					for (Entry<String, String> enty : entry.getValue().entrySet()) {
						map.put(beans.get(enty.getKey()), beans.get(enty.getValue()));
					}
					Field field = obj1.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(obj1, map);
				}
				beans.put(cl.getId(), obj1);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
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
	 * ʵ��������IOC���������������K-V����ʽ����IOC����Map<beanID,bean>
	 */
	public void doInstance() {
		for (String str : classNames) {
			String classStr = str.replace(".class", "");
			try {
				Class<?> clzz = Class.forName(classStr);
				if (clzz.isAnnotationPresent(Controller.class)) {
					Object instanse1 = clzz.newInstance();
					Controller controller = clzz.getAnnotation(Controller.class);
					String key1;
					if ("".equals(controller.value())) {
						key1 = clzz.getSimpleName();
					} else {
						key1 = controller.value();
					}
					this.controllerMaps.add(key1);
					beans.put(key1, instanse1);
				} else if (clzz.isAnnotationPresent(Service.class)) {
					Object instanse2 = clzz.newInstance();
					Service service = clzz.getAnnotation(Service.class);
					String key2;
					if ("".equals(service.value()))
						key2 = clzz.getSimpleName();
					else
						key2 = service.value();
					beans.put(key2, instanse2);
				} else if (clzz.isAnnotationPresent(Dao.class)) {
					Object instanse3 = clzz.newInstance();
					Dao dao = clzz.getAnnotation(Dao.class);
					String key3;
					if ("".equals(dao.value()))
						key3 = clzz.getSimpleName();
					else
						key3 = dao.value();
					beans.put(key3, instanse3);
				} else if (clzz.isAnnotationPresent(Agent.class)) {
					Object instanse4 = clzz.newInstance();
					String key4 = clzz.getSimpleName();
					beans.put(key4, instanse4);
				} else if (clzz.isAnnotationPresent(Expand.class)) {
					Object instanse5 = clzz.newInstance();
					String key5 = clzz.getSimpleName();
					beans.put(key5, instanse5);
				} else if (clzz.isAnnotationPresent(Real.class)) {
					Object instanse6 = clzz.newInstance();
					String key6 = clzz.getSimpleName();
					beans.put(key6, instanse6);

				} else if (clzz.isAnnotationPresent(App.class)) {
					Object instanse7 = autowired(clzz);
					App app = clzz.getAnnotation(App.class);
					String key7 = "";
					if (!"".equals(app.id())) {
						key7 = app.id();
					} else if (!"".equals(app.value())) {
						key7 = app.value();
					} else {
						key7 = clzz.getSimpleName();
					}
					beans.put(key7, instanse7);
				} else {
					continue;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ʵ����Mapper�ӿڵĴ�����󣬲������Ƿ���IOC������
	 */
	public void doMapper() {
		if(!mapperclassNames.isEmpty()&&mapperclassNames!=null) {
			try {
				SqlCore sql=SqlCoreFactory.getSqlCore();
				beans.put("lucky_xfl_fk_cl_7075_0721_58314_SqlCore", sql);
				for(String str:mapperclassNames) {
					String classStr = str.replace(".class", "");
					try {
						Class<?> clzz=Class.forName(classStr);
						if(clzz.isInterface()) {
							Object interf=sql.getMapper(clzz);
							beans.put(clzz.getName(), interf);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}catch(RuntimeException e) {
				System.err.println("xfl_fk:�޷���ȡ���ݿ���Ϣ��������");
			}
		}
	}


	/**
	 * DI��ʵ�֣�IOC�����ƴװ@Auotwired,���ͼ��+ID����
	 */
	public void doAutowired() {
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object instance = entry.getValue();
			Class<?> clzz = instance.getClass();
			Field[] fields = clzz.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Autowired.class)) {
					Autowired auto = field.getAnnotation(Autowired.class);
					field.setAccessible(true);
					if ("".equals(auto.value())) {
						Class<?> fcl=field.getType();
						for(Entry<String,Object> ent:beans.entrySet()) {
							Class<?> beclass=ent.getValue().getClass();
							if(fcl.isAssignableFrom(beclass)) {
								Object obj = ent.getValue();
								try {
									field.set(instance, obj);
									break;
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
					} else if((!"request".equals(auto.value()))&&(!"response".equals(auto.value()))&&(!"session".equals(auto.value())&&(!"model".equals(auto.value())))) {
						Object obj = beans.get(auto.value());
						try {
							field.set(instance, obj);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
	}

	/**
	 * AOPʵ�֣�����ΪControllerע������Service����
	 */
	public void doNowAutowired() {
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Class<?> clazz = entry.getValue().getClass();
			if (clazz.isAnnotationPresent(Controller.class)) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(Autowired.class)) {
						Autowired auto = field.getAnnotation(Autowired.class);
						field.setAccessible(true);
						Class<?> fcl=field.getType();
						if("".equals(auto.value())) {
							for(Entry<String,Object> ent:beans.entrySet()) {
								Class<?> bcl=ent.getValue().getClass();
								if(fcl.isAssignableFrom(bcl)) {
									Object aotuobj=ent.getValue();
									try {
										field.set(entry.getValue(), aotuobj);
									} catch (IllegalArgumentException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
							}
						}else {
							try {
								field.set(entry.getValue(), beans.get(auto.value()));
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					} else {
						continue;
					}

				}
			}
		}
	}

	/**
	 * URL-Controllerӳ���XMLʵ��
	 * @return
	 */
	public Map<String, ControllerAndMethod> UrlHandingXml() {
		LuckyXmlConfig xmlCfg = LuckyXmlConfig.loadLuckyXmlConfig();
		List<List<LuckyMapping>> list = xmlCfg.getMvcxml().getMapping();
		this.setter_pre_suf.addAll(xmlCfg.getMvcxml().getSetterstylenew());
		this.encod=xmlCfg.getMvcxml().getEncoding();
		for (Entry<String, String> entry : xmlCfg.getMvcxml().getControllerstyle().entrySet()) {
			this.pre_suf.put(entry.getKey(), entry.getValue());
		}
		for (List<LuckyMapping> mappinglist : list)
			for (LuckyMapping mapping : mappinglist) {
				ControllerAndMethod come=new ControllerAndMethod();
				String url_c=mapping.getId();
				Object obj = beans.get(url_c);
				come.setController(obj);
				String url_m = mapping.getMethodid();
				List<String> paras = mapping.getParamlist();
				Class<?>[] parameterTypes = new Class<?>[paras.size()];
				try {
					for (int i = 0; i < parameterTypes.length; i++) {
						parameterTypes[i] = typeCast(paras.get(i));
					}
					Method method = obj.getClass().getDeclaredMethod(mapping.getMethodname(), parameterTypes);
					come.setMethod(method);
					controllerMaps.add(url_c);
					handerMaps2.put(url_m, method.getName());
					urlMap.put(url_m, come);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return urlMap;
	}

	/**
	 * URL-Controllerӳ���ע��ʵ��
	 * @return
	 */
	public Map<String, ControllerAndMethod> UrlHanding() {
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Object instance = entry.getValue();
			Class<?> clzz = instance.getClass();
			if (clzz.isAnnotationPresent(Controller.class)) {
				String url_c;
				if(clzz.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping crm=clzz.getAnnotation(RequestMapping.class);
					url_c=crm.value();
				}else {
					url_c="/";
				}
				Method[] publicMethods = clzz.getDeclaredMethods();
				for(Method method:publicMethods) {
					if(method.isAnnotationPresent(RequestMapping.class)) {
						ControllerAndMethod come=new ControllerAndMethod();
						come.setController(entry.getValue());
						RequestMapping mrm=method.getAnnotation(RequestMapping.class);
						String url_m;
						if(mrm.value().contains("->")) {
							int end=mrm.value().indexOf("->");
							url_m=mrm.value().substring(0,end);
						}else {
							url_m=mrm.value();
						}
						come.setMethod(method);
						urlMap.put(url_c+url_m, come);
						handerMaps2.put(url_c+url_m, method.getName());
					}else {
						continue;
					}
				}
			}
		}
		return urlMap;
	}

	/**
	 * ��ִ��Controller����ʱΪ����ע��ȫ�ֵ�Request���� Response����Session�����Model����
	 * @param obj controller����
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void autowReqAdnResp(Object obj,Model model) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Autowired.class)) {
				Autowired aw = field.getAnnotation(Autowired.class);
				String val = aw.value();
				if ("request".equals(val)) {
					field.setAccessible(true);
					field.set(obj,model.getRequest());
				}
				if ("response".equals(val)) {
					field.setAccessible(true);
					field.set(obj, model.getResponse());
				}
				if ("session".equals(val)) {
					field.setAccessible(true);
					field.set(obj, model.getSession());
				}
				if("model".equals(val)) {
					field.setAccessible(true);
					field.set(obj, model);
				}
			}
		}
	}

	/**
	 * ��Ҫ��д��̬������ʵ��Aop
	 */
	public void doProxy() {
		// �õ�������󣬴���aopMap
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			if (entry.getValue().getClass().isAnnotationPresent(Agent.class)) {
				Agent ag = entry.getValue().getClass().getAnnotation(Agent.class);
				Method method = null;
				try {
					method = entry.getValue().getClass().getMethod(ag.methodname());
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Object[] args = {};
					Object obj = method.invoke(entry.getValue(), args);
					aopMap.put(ag.classname(), obj);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for (Map.Entry<String, Object> entry : aopMap.entrySet()) {
			beans.remove(entry.getKey());
			beans.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * ��д��ͨ����ʵ��Aop �ҵ���չ���е���չ�������˷���������ǿĿ�귽��
	 */
	public void findExpandMethod() {
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Class<?> clzz = entry.getValue().getClass();
			if (clzz.isAnnotationPresent(Expand.class)) {
				Method[] methods = clzz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(Expand.class)) {
						Expand ex = method.getAnnotation(Expand.class);
						String key = clzz.getSimpleName() + "/" + ex.value();
						expandmethodMap.put(key, method);
					}
				}
			}
		}
	}

	/**
	 * ��д��ͨ����ʵ��Aop �ҵ�Ŀ���࣬�Դ����б���ǵ�Ŀ�귽�����з�������ǿ���Ӷ��ﵽ��ǿĿ������Ч��
	 * @throws ClassNotFoundException
	 */
	public void pourProxyObject() throws ClassNotFoundException {
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Class<?> clzz = entry.getValue().getClass();
			if (clzz.isAnnotationPresent(Real.class)) {
				Real real = clzz.getAnnotation(Real.class);
				Class<?> iclass = Class.forName(real.value());
				Method[] methods = clzz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(Real.class)) {
						Real re = method.getAnnotation(Real.class);
						Object raw = entry.getValue();
						Object now = beans.get(re.classname());
						String met = re.method();
						String method1 = re.classname() + "/" + met;
						Method m = expandmethodMap.get(method1);
						Location loca = re.location();
						Object oo = aop.getProxyObject(iclass, raw, now, m, method, loca);
						entry.setValue(oo);
					}
				}
			}
		}
	}


	/**
	 * ΪApp���������ע��ע��ģʽ
	 * @param appClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private Object autowired(Class<?> appClass)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		/*
		 * bug������ֻ�ܽ�����ֵͨ��ע�룬�ݲ�֧������ֵ��ע��
		 */
		App app = appClass.getAnnotation(App.class);
		String[] fields = app.fields();
		String[] values = app.values();
		Object objApp = appClass.newInstance();
		for (int i = 0; i < fields.length; i++) {
			Field fk = appClass.getDeclaredField(fields[i]);
			fk.setAccessible(true);
			fk.set(objApp, typeCast(values[i], fk.getType().getSimpleName()));
		}
		Field[] fies = appClass.getDeclaredFields();
		for (Field fis : fies) {
			if (fis.isAnnotationPresent(com.lucky.annotation.LuckyArray.class)) {
				com.lucky.annotation.LuckyArray array = fis.getAnnotation(com.lucky.annotation.LuckyArray.class);
				String[] vals = array.values();
				String[] types = array.types();
				List<Object> arrayobj = new ArrayList<>();
				for (int i = 0; i < vals.length; i++) {
					arrayobj.add(typeCast(vals[i], types[i]));
				}
				fis.setAccessible(true);
				fis.set(objApp, arrayobj.toArray());
			} else if (fis.isAnnotationPresent(com.lucky.annotation.LuckyList.class)) {
				com.lucky.annotation.LuckyList list = fis.getAnnotation(com.lucky.annotation.LuckyList.class);
				String type = list.type();
				String[] vals = list.values();
				List<Object> listobj = new ArrayList<>();
				for (int i = 0; i < vals.length; i++) {
					listobj.add(typeCast(vals[i], type));
				}
				fis.setAccessible(true);
				fis.set(objApp, listobj);
			} else if (fis.isAnnotationPresent(com.lucky.annotation.LuckySet.class)) {
				com.lucky.annotation.LuckySet set = fis.getAnnotation(com.lucky.annotation.LuckySet.class);
				String type = set.type();
				String[] vals = set.values();
				Set<Object> setobj = new HashSet<>();
				for (int i = 0; i < vals.length; i++) {
					setobj.add(typeCast(vals[i], type));
				}
				fis.setAccessible(true);
				fis.set(objApp, setobj);
			} else if (fis.isAnnotationPresent(com.lucky.annotation.LuckyMap.class)) {
				com.lucky.annotation.LuckyMap map = fis.getAnnotation(com.lucky.annotation.LuckyMap.class);
				String key_type = map.key_type();
				String value_type = map.value_type();
				String[] ks = map.keys();
				String[] vs = map.values();
				Map<Object, Object> mapobj = new HashMap<>();
				for (int i = 0; i < ks.length; i++) {
					mapobj.put(typeCast(ks[i], key_type), typeCast(vs[i], value_type));
				}
				fis.setAccessible(true);
				fis.set(objApp, mapobj);
			}
		}
		return objApp;
	}

	/**
	 * �ַ���תClass
	 * @param pclass
	 * @return
	 * @throws ClassNotFoundException
	 */
	private Class<?> typeCast(String pclass) throws ClassNotFoundException {
		switch (pclass) {
		case "int":
			return int.class;
		case "double":
			return double.class;
		case "long":
			return long.class;
		case "folat":
			return float.class;
		case "byte":
			return byte.class;
		case "boolean":
			return boolean.class;
		default:
			return Class.forName(pclass);
		}
	}

	/**
	 * ����ת��
	 * @param goal �ַ�������ֵ
	 * @param type Ŀ�����͵��ַ�����ʽ'int','Integer'...
	 * @return
	 */
	private Object typeCast(String goal, String type) {
		switch (type) {
		case "int":
			return Integer.parseInt(goal);
		case "Integer":
			return Integer.parseInt(goal);
		case "Double":
			return Double.parseDouble(goal);
		case "double":
			return Double.parseDouble(goal);
		case "long":
			return Long.parseLong(goal);
		case "Long":
			return Long.parseLong(goal);
		case "float":
			return Float.parseFloat(goal);
		case "Float":
			return Float.parseFloat(goal);
		case "byte":
			return Byte.parseByte(goal);
		case "Byte":
			return Byte.parseByte(goal);
		case "boolean":
			return Boolean.parseBoolean(goal);
		case "Boolean":
			return Boolean.parseBoolean(goal);
		default:
			return goal;
		}
	}
}
