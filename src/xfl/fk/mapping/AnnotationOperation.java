package xfl.fk.mapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import xfl.fk.annotation.Download;
import xfl.fk.annotation.RequestParam;
import xfl.fk.annotation.RestParam;
import xfl.fk.annotation.Upload;
import xfl.fk.aop.RequestMethod;
import xfl.fk.file.MultipartFile;
import xfl.fk.servlet.Model;
import xfl.fk.utils.LuckyUtils;

@SuppressWarnings("all")
public class AnnotationOperation {
	
	/**
	 * 得到具体的MultipartFile
	 * @param request
	 * @param formName
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private MultipartFile uploadMutipar(Model model,String formName) throws IOException, ServletException {
		Part part = model.getRequest().getPart(formName);
		String projectPath=model.getRealPath("");
		return new MultipartFile(part,projectPath);
	}
	
	/**
	 * 基于MultipartFile的多文件上传
	 * @param request request对象
	 * @param response response对象
	 * @param method 将要执行的Controller方法
	 * @return 由Controller方法参数名和其对应的值所组成的Map(针对MultipartFile)
	 * @throws IOException
	 * @throws ServletException
	 */
	private Map<String,MultipartFile> moreUploadMutipar(Model model, Method method) throws IOException, ServletException{
		Map<String,MultipartFile> map=new HashMap<>();
		Parameter[] parameters = method.getParameters();
		for(Parameter par:parameters) {
			if(MultipartFile.class.isAssignableFrom(par.getType())) {
				map.put(getParamName(par), uploadMutipar(model,getParamName(par)));
			}
		}
		return map;
	}
	
	/**
	 * 执行文件上传操作(将上传的文件写入服务器的具体位置)
	 * @param request request对象
	 * @param response response对象
	 * @param formName 表单上<input type="file">的"name"属性值
	 * @param path 要上传到服务器的哪个文件夹？
	 * @param type 允许上传的文件类型
	 * @param maxSize 允许上传文件的最大大小
	 * @return 上传后服务器上的文件名
	 * @throws IOException
	 * @throws ServletException
	 */
	private String upload(Model model,String formName,String path,String type,int maxSize) throws IOException, ServletException {
		Part part = model.getRequest().getPart(formName);
		String disposition = part.getHeader("Content-Disposition");
		if (disposition.indexOf(".") != -1) {
			// 获得文件后缀名
			String suffix = disposition.substring(disposition.lastIndexOf("."), disposition.length() - 1);
			if(!"".equals(type)) {
				if(!type.toLowerCase().contains(suffix.toLowerCase())) {
					throw new RuntimeException("xfl_fk__:上传的文件格式"+suffix+"不合法！合法的文件格式为："+type);
				}
			}
			// 随机的生成文件名（时间+随机数）
			long time = new Date().getTime();
			int ran=(int)(Math.random()*(9999-1000)+1000);
			String filename = time+""+ran+ suffix;
			// 获取上传的文件名
			InputStream is = part.getInputStream();
			FileInputStream fis=(FileInputStream) is;
			if(maxSize!=0) {
				int size=fis.available();
				int filesize=size/1024;
				if(filesize>maxSize) {
					throw new RuntimeException("xfl_fk__:上传文件的大小("+filesize+"kb)超出设置的最大值"+maxSize+"kb");
				}
			}
			// 动态获取服务器的路径
			String serverpath = model.getRealPath(path);
			File file = new File(serverpath);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(serverpath + "/" + filename);
			byte[] bty = new byte[1024];
			int length = 0;
			while ((length = is.read(bty)) != -1) {
				fos.write(bty, 0, length);
			}
			fos.close();
			is.close();
			return filename;
		} else {
			return null;
		}
	}
	

	
	/**
	 * 批量文件上传@Upload注解方式
	 * @param request request对象
	 * @param response response对象
	 * @param method 将要执行的Controller方法
	 * @return 上传后的文件名与表单name属性所组成的Map
	 * @throws IOException
	 * @throws ServletException
	 */
	private Map<String,String> moreUpload(Model model, Method method) throws IOException, ServletException{
		Map<String,String> fileMap=new HashMap<String,String>();
		if(method.isAnnotationPresent(Upload.class)) {
			Upload upload=method.getAnnotation(Upload.class);
			String[] files=upload.names();
			String[] savePaths=upload.filePath();
			String types=upload.type();
			int maxSize=upload.maxSize();
			if(savePaths.length==1) {
				for (String str : files) {
					fileMap.put(str, upload(model,str,savePaths[0],types,maxSize));
				}
			}else {
				int x=0;
				for (String str : files) {
					fileMap.put(str, upload(model,str,savePaths[x++],types,maxSize));
				}
			}
		}
		return fileMap;
	}
	
	/**
	 * 处理对象Pojo参数类型
	 * @param request request对象
	 * @param response response对象
	 * @param method 将要执行的Controller方法
	 * @param uploadMap 
	 * @return Controller方法参数名与参数值所组成的Map(针对Pojo类型的参数)
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws ServletException
	 */
	private Map<String,Object> pojoParam(Model model,Method method, Map<String, String> uploadMap) throws InstantiationException, IllegalAccessException, IOException, ServletException{
		Map<String,Object> map=new HashMap<>();
		Parameter[] parameters = method.getParameters();
		for(Parameter param:parameters) {
			if(!MultipartFile.class.isAssignableFrom(param.getType())
					&&param.getType().getClassLoader()!=null
					&&!ServletRequest.class.isAssignableFrom(param.getType())
					&&!ServletResponse.class.isAssignableFrom(param.getType())
					&&!HttpSession.class.isAssignableFrom(param.getType())
					&&!Model.class.isAssignableFrom(param.getType())) {
				Class<?> pojoclzz=param.getType();
				Object pojo=pojoclzz.newInstance();
				Field[] fields=pojoclzz.getDeclaredFields();
				createObject(model,pojo);
				for(Field fi:fields) {
					fi.setAccessible(true);
					Object fi_obj=fi.get(pojo);
					if(uploadMap.containsKey(fi.getName())&&fi_obj==null) 
						fi.set(pojo, uploadMap.get(fi.getName()));
				}
				map.put(getParamName(param), pojo);
			}
			
		}
		return map;
		
	}
	/**
	 * 文件下载操作@Download
	 * @param request
	 * @param response
	 * @param method
	 * @throws IOException
	 */
	public void download(Model model, Method method) throws IOException {
		Download dl = method.getAnnotation(Download.class);
		String fileName = dl.name();
		String filePath = dl.filePath();
		String file = model.getRequestPrarmeter(fileName); // 客户端传递的需要下载的文件名
		String path = model.getRealPath(filePath) + file; // 默认认为文件在当前项目的根目录
		FileInputStream fis = new FileInputStream(path);
		model.getResponse().setCharacterEncoding("utf-8");
		model.getResponse().setHeader("Content-Disposition", "attachment; filename=" + file);
		ServletOutputStream out = model.getResponse().getOutputStream();
		byte[] bt = new byte[1024];
		int length = 0;
		while ((length = fis.read(bt)) != -1) {
			out.write(bt, 0, length);
		}
		out.close();
	}
	
	/**
	 * 得到将要执行的Controller方法的参数列表的值
	 * @param request request对象
	 * @param response reaponse对象
	 * @param method 将要执行的Controller方法
	 * @param reqMethod 
	 * @return 将要执行的Controller方法的参数列表
	 * @throws IOException
	 * @throws ServletException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Object getControllerMethodParam(Model model, Method method,Map<String,String>restMap) throws IOException, ServletException, InstantiationException, IllegalAccessException {
		Parameter[] parameters = method.getParameters();
		Object[] args = new Object[parameters.length];
		Map<String,String> uploadMap=moreUpload(model,method);
		Map<String,MultipartFile> multiUploadMap=moreUploadMutipar(model,method);
		Map<String,Object> pojoMap=pojoParam(model,method,uploadMap);
		for(int i=0;i<parameters.length;i++) {
			if(uploadMap.containsKey(getParamName(parameters[i]))&&String.class.isAssignableFrom(parameters[i].getType())) {
				args[i]=uploadMap.get(getParamName(parameters[i]));
			}else if(multiUploadMap.containsKey(getParamName(parameters[i]))&&MultipartFile.class.isAssignableFrom(parameters[i].getType())) {
				args[i]=multiUploadMap.get(getParamName(parameters[i]));
			}else if(pojoMap.containsKey(getParamName(parameters[i]))){
				args[i]=pojoMap.get(getParamName(parameters[i]));
			}else if(ServletRequest.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model.getRequest();
			}else if(HttpSession.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model.getResponse();
			}else if(ServletResponse.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model.getSession();
			}else if(Model.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model;
			}else if(parameters[i].isAnnotationPresent(RestParam.class)){
				RestParam rp=parameters[i].getAnnotation(RestParam.class);
				String restKey=rp.value();
				args[i]=LuckyUtils.typeCast(restMap.get(restKey),parameters[i].getType().getSimpleName());
			}else {
				if(parameters[i].getType().isArray()) {
					args[i]=model.getArray(getParamName(parameters[i]), parameters[i].getType());
				}else {
					if(model.getArray(getParamName(parameters[i]), parameters[i].getType())!=null) {
						args[i]=model.getArray(getParamName(parameters[i]), parameters[i].getType())[0];
					}
				}
			}
		}
		return args;
	}
	

	
	/**
	 * 为Controller方法中的pojo属性注入request域中对应的值
	 * @param request 
	 * @param pojo
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object createObject(Model model,Object pojo) throws InstantiationException, IllegalAccessException {
		Field[] fields=pojo.getClass().getDeclaredFields();
		for(Field fie:fields) {
			fie.setAccessible(true);
			if(LuckyUtils.isJavaClass(fie.getType())) {
				if(fie.getType().isArray()) {
					fie.set(pojo,model.getArray(fie.getName(), fie.getType()));
				}else {
					if(model.getArray(fie.getName(), fie.getType())!=null) {
						fie.set(pojo,model.getArray(fie.getName(), fie.getType())[0]);
					}
				}
			}else {
				Object object=fie.getType().newInstance();
				object=createObject(model,object);
				fie.set(pojo, object);
			}
		}
		return pojo;
	}
	
	/**
	 * 得到一个参数的标记参数名
	 * @param param
	 * @return
	 */
	private String getParamName(Parameter param) {
		if(param.isAnnotationPresent(RequestParam.class)) {
			RequestParam rp=param.getAnnotation(RequestParam.class);
			return rp.value();
		}else {
			return param.getName();
		}
	}
}
