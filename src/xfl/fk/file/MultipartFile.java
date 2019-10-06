package xfl.fk.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class MultipartFile {
	
	private FileInputStream originalFileInpueStream;//用户上传的文件对应的输入流
	private String uploadFileName;//文件上传到服务器后的文件名
	private String disposition;//上传文件的协议信息
	private String projectPath;//项目的路径
	
	public MultipartFile(Part part,String projectPath){
		try {
			this.originalFileInpueStream=(FileInputStream) part.getInputStream();
			int ran=(int)(Math.random()*(9999-1000)+1000);
			this.disposition=part.getHeader("Content-Disposition");
			this.uploadFileName=new Date().getTime()+""+ran+getFileType();
			this.projectPath=projectPath;
		} catch (ClassCastException | IOException e) {
			return;
		}
	}
	
	/**
	 * 获得上传文件的类型
	 * @return
	 */
	public String getFileType() {
		String type=disposition.substring(disposition.lastIndexOf("."), disposition.length()-1);
		return type;
	}
	
	/**
	 * 获得上传到服务器后的文件名
	 * @return
	 */
	public String getFileName() {
		return uploadFileName;
	}
	
	/**
	 * 将文件复制到服务器上的path下
	 * @param path
	 * @throws IOException 
	 */
	public void copyToPath(String filepath) throws IOException {
		File file=new File(projectPath+"/"+filepath);
		if(!file.isDirectory())
			file.mkdirs();
		FileOutputStream outfile=new FileOutputStream(projectPath+"/"+filepath+"/"+uploadFileName);
		byte[] buffer=new byte[1024*6];
		int length=0;
		while((length=originalFileInpueStream.read(buffer))!=-1) {
			outfile.write(buffer, 0, length);
		}
		outfile.flush();
		outfile.close();
	}
	
	/**
	 * 文件下载
	 * @param response Response对象
	 * @param file 要下载的文件
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletResponse response,File file) throws IOException {
		@SuppressWarnings("resource")
		InputStream bis = new BufferedInputStream(new FileInputStream(file));
        //转码，免得文件名中文乱码  
        String filename = URLEncoder.encode(file.getName(),"UTF-8");  
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;
        byte[] bt = new byte[1024*6];
        while((len = bis.read(bt)) != -1){  
            out.write(bt,0,len);  
            out.flush();  
        }  
        out.close();  
	}
	
	/**
	 * 获得上传文件的大小
	 * @return
	 * @throws IOException 
	 */
	public int getFileSize() throws IOException {
		return originalFileInpueStream.available();
	}
	
	/**
	 * 获得文件对应的InputStream
	 * @return
	 */
	public InputStream getInputStream() {
		return originalFileInpueStream;
	}
	
	 

}
