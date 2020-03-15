package com.lucky.jacklamb.aop.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import com.lucky.jacklamb.aop.expandpoint.CacheExpandPoint;
import com.lucky.jacklamb.aop.proxy.Chain;
import com.lucky.jacklamb.servlet.Model;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

/**
 * asm����
 * @author fk-7075
 *
 */
public class ASMUtil {
	
	 private static boolean sameType(Type[] types, Class<?>[] clazzes) {
	        // ������ͬ
	        if (types.length != clazzes.length) {
	            return false;
	        }
	 
	        for (int i = 0; i < types.length; i++) {
	            if (!Type.getType(clazzes[i]).equals(types[i])) {
	                return false;
	            }
	        }
	        return true;
	    }
	 
	    /**
	     * 
	     * <p>
	     * ��ȡ�����Ĳ�����
	     * </p>
	     * 
	     * @param m
	     * @return
	     */
	    public static String[] getMethodParamNames(final Method m) {
	        final String[] paramNames = new String[m.getParameterTypes().length];
	        final String n = m.getDeclaringClass().getName();
	        ClassReader cr = null;
	        try {
	            cr = new ClassReader(n);
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	        cr.accept(new ClassVisitor(Opcodes.ASM5) {
	            @Override
	            public MethodVisitor visitMethod(final int access,
	                    final String name, final String desc,
	                    final String signature, final String[] exceptions) {
	                final Type[] args = Type.getArgumentTypes(desc);
	                // ��������ͬ���Ҳ���������ͬ
	                if (!name.equals(m.getName())
	                        || !sameType(args, m.getParameterTypes())) {
	                    return super.visitMethod(access, name, desc, signature,
	                            exceptions);
	                }
	                MethodVisitor v = super.visitMethod(access, name, desc,
	                        signature, exceptions);
	                return new MethodVisitor(Opcodes.ASM5, v) {
	                    @Override
	                    public void visitLocalVariable(String name, String desc,
	                            String signature, Label start, Label end, int index) {
	                        int i = index - 1;
	                        // ����Ǿ�̬���������һ���ǲ���
	                        // ������Ǿ�̬���������һ����"this"��Ȼ����Ƿ����Ĳ���
	                        if (Modifier.isStatic(m.getModifiers())) {
	                            i = index;
	                        }
	                        if (i >= 0 && i < paramNames.length) {
	                            paramNames[i] = name;
	                        }
	                        super.visitLocalVariable(name, desc, signature, start,
	                                end, index);
	                    }
	 
	                };
	            }
	        }, 0);
	        return paramNames;
	    }
	 
	    public static void main(String[] args) throws SecurityException,
	            NoSuchMethodException {
	        String[] s = getMethodParamNames(ASMUtil.class.getMethod(
	                "getMethodParamNames", Method.class));
	        System.out.println(Arrays.toString(s));
	 
	        s = getMethodParamNames(ASMUtil.class.getDeclaredMethod("sameType",
	                Type[].class, Class[].class));
	        System.out.println(Arrays.toString(s));
	        
	        int i=0;
	        for(String str:s) {
	        	
	        	System.out.println(str);
	        	System.out.println(s[i]);
	        	i++;
	        	
	        }
	 
	        s=getMethodParamNames(CacheExpandPoint.class.getDeclaredMethod("cacheResult", Chain.class));
	        System.out.println(Arrays.toString(s));
	        // ��String��Object��thread��jdk�Դ����Ͳ�������
	        
	        s=getMethodParamNames(ASMUtil.class.getDeclaredMethod("ttt",Model.class));
	        System.out.println(Arrays.toString(s));
	    }

	    public void ttt(Model haha) {
	    	
	    }

}
