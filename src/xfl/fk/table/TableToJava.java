package xfl.fk.table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import xfl.fk.utils.LuckyManager;

/**
 * 负责将数据库中的表转化为对应的JavaBean
 * @author fk-7075
 *
 */
public class TableToJava {
	/**
	 * 创建JavaBean(配置方式url,不可以解析中文)
	 */
	public static void generateJavaSrc() {
		List<GetJavaSrc> src=JavaFieldGetSet.getMoreJavaSrc();
		String srcpath=LuckyManager.getPropCfg().getSrcPath();
		String packpath=LuckyManager.getPropCfg().getPackages().replaceAll("\\.", "/");
		String url=srcpath+"/"+packpath;
		writerToJava(src,url,false,true);
	}
	
	/**
	 * 创建JavaBean(配置方式url,手动输入表名,不可以解析中文)
	 * @param tables 表名
	 */
	public static void b_generateJavaSrc(String...tables) {
		List<GetJavaSrc> src=JavaFieldGetSet.getAssignJavaSrc(tables);
		String srcpath=LuckyManager.getPropCfg().getSrcPath();
		String packpath=LuckyManager.getPropCfg().getPackages().replaceAll("\\.", "/");
		String url=srcpath+"/"+packpath;
		writerToJava(src,url,false,false);
	}
	
	/**
	 * 创建JavaBean(手动输入url,手动输入表名,可以解析中文)
	 * @param srcPath src文件绝对路径
	 * @param tables 需要逆向工程生成javabean的表
	 */
	public static void a_generateJavaSrc(String srcPath,String...tables) {
		List<GetJavaSrc> src=JavaFieldGetSet.getAssignJavaSrc(tables);
		String packpath=LuckyManager.getPropCfg().getPackages().replaceAll("\\.", "/");
		String url=srcPath+"/"+packpath;
		writerToJava(src,url,true,false);
	}
	/**
	 * 创建JavaBean(手动输入url,可以解析中文)
	 * @param srcPath src文件夹的绝对路径
	 */
	public static void generateJavaSrc(String srcPath) {
		List<GetJavaSrc> src=JavaFieldGetSet.getMoreJavaSrc();
		String packpath=LuckyManager.getPropCfg().getPackages().replaceAll("\\.", "/");
		String url=srcPath+"/"+packpath;
		writerToJava(src,url,true,true);
	}
	
	/**
	 * 生成java源文件
	 * @param src 源代码类
	 * @param path 位置
	 * @param isManual 是否手动
	 * @param ispackBox 是否生成PackBox
	 */
	private static void writerToJava(List<GetJavaSrc> src,String path,boolean isManual,boolean ispackBox) {
		File file=new File(path);
		if(!file.exists())
			file.mkdirs();
		BufferedWriter bw=null;
		for (GetJavaSrc getJavaSrc : src) {
			try {
				bw=new BufferedWriter(new FileWriter(new File(path+"/"+getJavaSrc.getClassName()+".java")));
				System.out.println(path+"/"+getJavaSrc.getClassName()+".java");
				bw.write(getJavaSrc.getPack());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getImpor());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getJavaSrc());
				bw.write(getJavaSrc.getToString());
				bw.close();
			} catch (IOException e) {
				if(isManual)
					System.err.println("xflfk:逆向工程生成JavaBean失败，请检查相关的配置！\"Package\"!");
				else
					System.err.println("xflfk:逆向工程生成JavaBean失败，请检查相关的配置！\"SrcPath\" And \"Package\"!");
				e.printStackTrace();
			}
		}
		if(ispackBox) {
			PackBoxSrc p=PackBoxSrc.getPackBoxSrc();
			try {
				bw=new BufferedWriter(new FileWriter(new File(path+"/"+p.getClassName()+".java")));
				System.out.println(path+"/"+p.getClassName()+".java");
				bw.write(p.getPack());
				bw.write(p.getImpor());
				bw.write(p.getField());
				bw.write(p.getGetset());
				bw.write(p.getEnd());
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
