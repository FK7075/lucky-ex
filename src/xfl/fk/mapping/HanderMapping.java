package xfl.fk.mapping;

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
import java.util.Set;

import xfl.fk.annotation.Agent;
import xfl.fk.annotation.App;
import xfl.fk.annotation.Autowired;
import xfl.fk.annotation.Controller;
import xfl.fk.annotation.Dao;
import xfl.fk.annotation.Expand;
import xfl.fk.annotation.Real;
import xfl.fk.annotation.RequestMapping;
import xfl.fk.annotation.Service;
import xfl.fk.aop.CreateProxyObject;
import xfl.fk.aop.Location;
import xfl.fk.servlet.Model;
import xfl.fk.sqldao.SqlCore;
import xfl.fk.sqldao.SqlCoreFactory;
import xfl.fk.utils.LuckyManager;
import xfl.fk.xml.LuckyMapping;
import xfl.fk.xml.LuckyXml;
import xfl.fk.xml.LuckyXmlConfig;

public class HanderMapping {

	private Set<String> controllerMaps;
	private Map<String, String> pre_suf;
	private List<String> setter_pre_suf;
	private List<String> classNames;//普通ioc组件的类的全路径
	private List<String> mapperclassNames;//mapper接口的全路径
	private Map<String, Object> beans;//IOC容器
	private Map<String,ControllerAndMethod> urlMap;//Url映射容器
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

	// 初始化容器
	public HanderMapping() {
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
		if(pack.size()!=0) {
			for (String str : pack) {
				scanIocComponent(str);
			}
		}
		if(scanmappers.size()!=0) {
			for (String str : scanmappers) {
				scanMapper(str);
			}
		}
	}
	
	public void scanIocComponent(String basePackage) {
		doScan(basePackage,false);
	}
	
	public void scanMapper(String basePackage) {
		doScan(basePackage,true);
	}


	/**
	 * 基于注解的组件扫描，扫描得到所有IOC组件和Mapper组件
	 * @param basePackage 需要扫描的文件夹的绝对路径
	 * @param isMapper 是否是Mapper组件
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
		String[] filesStr = files.list();// 获得该文件夹下的所有文件和文件夹的绝对路径
		for (String path : filesStr) {// 遍历
			File file = new File(path_f+"/"+ path);
			if (file.isDirectory()) {// 判断该文件是否是文件夹
				doScan(basePackage + "." + path,isMapper);// 是文件夹则迭代
			} else {
				if(isMapper) {
					if(file.getName().contains(".class"))
						mapperclassNames.add(basePackage + "." + file.getName());// 是文件则将mapper接口的全路径加到mapperclassNames 中
				}
				else {
					if(file.getName().contains(".class"))
						classNames.add(basePackage + "." + file.getName());// 是文件则将普通组件的全路径加到classNames 中
				}
			}
		}
	}

	/**
	 * 基于Xml文件的组件扫描，扫描得到所有IOC组件
	 */
	public void doScanToXml() {
		/*
		 * bug描述:xml配置文件方式创建对象，首先会加载类对象而且给对象赋上基本类型的值，创建完成后进行依赖注入
		 * 注入时会以ioc容器中的对象为基准，在数组，map中注入时可能会覆盖原先的值，所以在一个容器中最好不要混合使用 基本值与引用值的注入
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
	 * 实例化所有IOC组件，并将他们以K-V的形式放入IOC容器Map<beanID,bean>
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
	 * 实例化Mapper接口的代理对象，并将它们放入IOC容器中
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
				System.err.println("xfl_fk:无法获取数据库信息。。。。");
			}
		}
	}


	/**
	 * DI的实现，IOC组件的拼装@Auotwired,类型检查+ID查找
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
					} else if((!"request".equals(auto.value()))&&(!"response".equals(auto.value()))) {
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
	 * AOP实现，重新为Controller注入代理的Service对象
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
	 * URL-Controller映射的XML实现
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
	 * URL-Controller映射的注解实现
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
	 * 在执行Controller方法时为对象注入全局的Request对象 Response对象和Session对象
	 * @param obj controller对象
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
			}
		}
	}

	/**
	 * 需要手写动态代理方法实现Aop
	 */
	public void doProxy() {
		// 得到代理对象，存入aopMap
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
	 * 手写普通方法实现Aop 找到扩展类中的扩展方法，此方法用于增强目标方法
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
	 * 手写普通方法实现Aop 找到目标类，对此类中被标记的目标方法进行反复的增强，从而达到增强目标对象的效果
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
	 * 为App组件的依赖注入注解模式
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
		 * bug描述：只能进行普通值的注入，暂不支持引用值的注入
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
			if (fis.isAnnotationPresent(xfl.fk.annotation.LuckyArray.class)) {
				xfl.fk.annotation.LuckyArray array = fis.getAnnotation(xfl.fk.annotation.LuckyArray.class);
				String[] vals = array.values();
				String[] types = array.types();
				List<Object> arrayobj = new ArrayList<>();
				for (int i = 0; i < vals.length; i++) {
					arrayobj.add(typeCast(vals[i], types[i]));
				}
				fis.setAccessible(true);
				fis.set(objApp, arrayobj.toArray());
			} else if (fis.isAnnotationPresent(xfl.fk.annotation.LuckyList.class)) {
				xfl.fk.annotation.LuckyList list = fis.getAnnotation(xfl.fk.annotation.LuckyList.class);
				String type = list.type();
				String[] vals = list.values();
				List<Object> listobj = new ArrayList<>();
				for (int i = 0; i < vals.length; i++) {
					listobj.add(typeCast(vals[i], type));
				}
				fis.setAccessible(true);
				fis.set(objApp, listobj);
			} else if (fis.isAnnotationPresent(xfl.fk.annotation.LuckySet.class)) {
				xfl.fk.annotation.LuckySet set = fis.getAnnotation(xfl.fk.annotation.LuckySet.class);
				String type = set.type();
				String[] vals = set.values();
				Set<Object> setobj = new HashSet<>();
				for (int i = 0; i < vals.length; i++) {
					setobj.add(typeCast(vals[i], type));
				}
				fis.setAccessible(true);
				fis.set(objApp, setobj);
			} else if (fis.isAnnotationPresent(xfl.fk.annotation.LuckyMap.class)) {
				xfl.fk.annotation.LuckyMap map = fis.getAnnotation(xfl.fk.annotation.LuckyMap.class);
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
	 * 字符串转Class
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
	 * 类型转换
	 * @param goal 字符串类型值
	 * @param type 目标类型的字符串形式'int','Integer'...
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
