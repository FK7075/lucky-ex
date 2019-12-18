package com.lucky.jacklamb.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class MultipartFile {
	
	private InputStream originalFileInpueStream;//�û��ϴ����ļ���Ӧ��������
	private String uploadFileName;//�ļ��ϴ�������������ļ���
	private String fileType;//�ļ�����
	private String projectPath;//��Ŀ��·��
	
	/**
	 * 
	 * @param part
	 * @param projectPath
	 */
	public MultipartFile(Part part,String projectPath){
		try {
			this.originalFileInpueStream=(FileInputStream) part.getInputStream();
			String disposition=part.getHeader("Content-Disposition");
			this.fileType=disposition.substring(disposition.lastIndexOf("."));
			this.uploadFileName=UUID.randomUUID().toString().replaceAll("-", "")+getFileType();
			this.projectPath=projectPath;
		} catch (ClassCastException | IOException e) {
			throw new RuntimeException("������Ϣ����ȷ���޷�����MultipartFile��ʵ����");
		}
	}
	
	/**
	 * 
	 * @param originalFileInpueStream
	 * @param projectPath
	 * @param fileType
	 */
	public MultipartFile(InputStream originalFileInpueStream,String projectPath,String fileType) {
		this.originalFileInpueStream=originalFileInpueStream;
		this.fileType=fileType;
		this.uploadFileName=UUID.randomUUID().toString().replaceAll("-", "")+getFileType();
		this.projectPath=projectPath;
	}
	
	/**
	 * ����ϴ��ļ�������
	 * @return
	 */
	public String getFileType() {
		return this.fileType;
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
	 * @param docRelativePath ��д�Է������ļ�Ŀ¼Ϊ��Ŀ¼�����·��
	 * @throws IOException
	 */
	public void copyToDocRelativePath(String docRelativePath) throws IOException {
		File file=new File(projectPath+"/"+docRelativePath);
		copy(file);
	}
	
	/**
	 * ���ļ����Ƶ�ϵͳ������λ����
	 * @param filepath ����·��
	 * @throws IOException
	 */
	public void copyToFilePath(String filepath) throws IOException {
		File file=new File(filepath);
		copy(file);
	}
	
	private void copy(File file) throws IOException {
		if(!file.isDirectory())
			file.mkdirs();
		FileOutputStream outfile=new FileOutputStream(file.getAbsoluteFile()+"/"+uploadFileName);//projectPath+"/"+docRelativePath+"/"+uploadFileName);
		byte[] buffer=new byte[1024*6];
		int length=0;
		while((length=originalFileInpueStream.read(buffer))!=-1) {
			outfile.write(buffer, 0, length);
			outfile.flush();
		}
		outfile.close();
	}
	
	/**
	 * �ļ�����
	 * @param response Response����
	 * @param file Ҫ���ص��ļ�
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletResponse response,File file) throws IOException {
		if(!file.exists())
			throw new RuntimeException("�Ҳ����ļ�,�޷�������ز�����"+file.getAbsolutePath());
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
	
	/**
	 * �ر��ļ�������
	 * @throws IOException
	 */
	public void close() throws IOException {
		if(originalFileInpueStream!=null)
			originalFileInpueStream.close();
	}
	
	 

}
