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
	
	private FileInputStream originalFileInpueStream;//�û��ϴ����ļ���Ӧ��������
	private String uploadFileName;//�ļ��ϴ�������������ļ���
	private String disposition;//�ϴ��ļ���Э����Ϣ
	private String projectPath;//��Ŀ��·��
	
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
	 * ����ϴ��ļ�������
	 * @return
	 */
	public String getFileType() {
		String type=disposition.substring(disposition.lastIndexOf("."), disposition.length()-1);
		return type;
	}
	
	/**
	 * ����ϴ�������������ļ���
	 * @return
	 */
	public String getFileName() {
		return uploadFileName;
	}
	
	/**
	 * ���ļ����Ƶ��������ϵ�path��
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
	 * �ļ�����
	 * @param response Response����
	 * @param file Ҫ���ص��ļ�
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletResponse response,File file) throws IOException {
		@SuppressWarnings("resource")
		InputStream bis = new BufferedInputStream(new FileInputStream(file));
        //ת�룬����ļ�����������  
        String filename = URLEncoder.encode(file.getName(),"UTF-8");  
        //�����ļ�����ͷ  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        //1.�����ļ�ContentType���ͣ��������ã����Զ��ж������ļ�����    
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
	 * ����ϴ��ļ��Ĵ�С
	 * @return
	 * @throws IOException 
	 */
	public int getFileSize() throws IOException {
		return originalFileInpueStream.available();
	}
	
	/**
	 * ����ļ���Ӧ��InputStream
	 * @return
	 */
	public InputStream getInputStream() {
		return originalFileInpueStream;
	}
	
	 

}
