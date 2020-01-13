package com.lucky.jacklamb.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
 
/**
 * ѹ������ö��
 * 
 * @author Log
 *
 */
enum CompressType {
//	GZIP������UNIXϵͳ���ļ�ѹ������Linux�о�����ʹ�õ�*.gz���ļ�������GZIP��ʽ
	ZIP, JAR, GZIP
}
 
public class ZipUtils {
 
	public static void main(String[] args) {
//		compress(CompressType.ZIP);
		unZip("D:/solrJ-search.zip", "D:/solrJ-search-fk");
//		unZip("ѹ��.jar", "ѹ����jar");
	}
 
	public static void compress(CompressType type) {
		if (type == CompressType.ZIP) {
			zip("ѹ��", "ѹ��.zip", CompressType.ZIP);
		} else if (type == CompressType.JAR) {
			zip("ѹ��", "ѹ��.jar", CompressType.JAR);
		}
	}
 
	public static void zip(String inputFile, String outputFile, CompressType type) {
		zip(new File(inputFile), new File(outputFile), type);
	}
 
	/**
	 * ��ʼ��ѹ������Ϣ����ʼ����ѹ��
	 * 
	 * @param inputFile  ��Ҫѹ�����ļ����ļ���
	 * @param outputFile ѹ������ļ�
	 * @param type       ѹ������
	 */
	public static void zip(File inputFile, File outputFile, CompressType type) {
		ZipOutputStream zos = null;
		try {
			if (type == CompressType.ZIP) {
				zos = new ZipOutputStream(new FileOutputStream(outputFile));
			} else if (type == CompressType.JAR) {
				zos = new JarOutputStream(new FileOutputStream(outputFile));
			} else {
				zos = new ZipOutputStream(new FileOutputStream(outputFile));
			}
			// ����ѹ����ע��
			zos.setComment("From Log");
			zipFile(zos, inputFile, null);
			System.err.println("ѹ�����!");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ѹ��ʧ��!");
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
 
	/**
	 * ����ǵ����ļ�����ô��ֱ�ӽ���ѹ����������ļ��У���ô�ݹ�ѹ�������ļ�������ļ�
	 * 
	 * @param zos       ѹ�������
	 * @param inputFile ��Ҫѹ�����ļ�
	 * @param path      ��Ҫѹ�����ļ���ѹ�������·��
	 */
	public static void zipFile(ZipOutputStream zos, File inputFile, String path) {
		if (inputFile.isDirectory()) {
			// ��¼ѹ�������ļ���ȫ·��
			String p = null;
			File[] fileList = inputFile.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				// ���·��Ϊ�գ�˵���Ǹ�Ŀ¼
				if (path == null || path.isEmpty()) {
					p = file.getName();
				} else {
					p = path + File.separator + file.getName();
				}
				// ��ӡ·��
				System.out.println(p);
				// �����Ŀ¼�ݹ���ã�ֱ�������ļ�Ϊֹ
				zipFile(zos, file, p);
			}
		} else {
			zipSingleFile(zos, inputFile, path);
		}
	}
 
	/**
	 * ѹ�������ļ���ָ��ѹ������
	 * 
	 * @param zos       ѹ�������
	 * @param inputFile ��Ҫѹ�����ļ�
	 * @param path      ��Ҫѹ�����ļ���ѹ�������·��
	 * @throws FileNotFoundException
	 */
	public static void zipSingleFile(ZipOutputStream zos, File inputFile, String path) {
		try {
			InputStream in = new FileInputStream(inputFile);
			zos.putNextEntry(new ZipEntry(path));
			write(in, zos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * ��ѹѹ������ָ��Ŀ¼
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public static void unZip(String inputFile, String outputFile) {
		unZip(new File(inputFile), new File(outputFile));
	}
 
	/**
	 * ��ѹѹ������ָ��Ŀ¼
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public static void unZip(File inputFile, File outputFile) {
		if (!outputFile.exists()) {
			outputFile.mkdirs();
		}
 
		ZipFile zipFile = null;
		ZipInputStream zipInput = null;
		ZipEntry entry = null;
		OutputStream output = null;
		InputStream input = null;
		File file = null;
		try {
			zipFile = new ZipFile(inputFile);
			zipInput = new ZipInputStream(new FileInputStream(inputFile));
			String path = outputFile.getAbsolutePath() + File.separator;
			while ((entry = zipInput.getNextEntry()) != null) {
				// ��ѹ���ļ����ȡָ����ѹ���ļ���������
				input = zipFile.getInputStream(entry);
 
				// ƴװѹ������ʵ�ļ�·��
				String fileName = path + entry.getName();
				System.out.println(fileName);
 
				// �����ļ�ȱʧ��Ŀ¼����Ȼ�ᱨ�쳣���Ҳ���ָ���ļ���
				file = new File(fileName.substring(0, fileName.lastIndexOf(File.separator)));
				file.mkdirs();
 
				// �����ļ������
				output = new FileOutputStream(fileName);
 
				// д����ѹ���ļ�����
				write(input, output);
				output.close();
			}
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
 
				if (zipInput != null) {
					zipInput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	/**
	 * ��������д�뵽������ķ��㷽�� ��ע�⡿�������ֻ��ر����������Ҷ�д��ɺ������������flush()������������ر��������
	 * 
	 * @param input
	 * @param output
	 */
	private static void write(InputStream input, OutputStream output) {
		int len = -1;
		byte[] buff = new byte[1024];
		try {
			while ((len = input.read(buff)) != -1) {
				output.write(buff, 0, len);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
}