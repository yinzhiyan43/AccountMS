package org.dc.penguinMVC.core.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.dc.module.system.controller.LoginController;
public final class ClassesUtils {
 
    private ClassesUtils() {
    }
 
    /**
     * 
     * <p>比较参数类型是否一致</p>
     *
     * @param types asm的类型({@link Type})
     * @param clazzes java 类型({@link Class})
     * @return
     */
    private static boolean sameType(Type[] types, Class<?>[] clazzes) {
        // 个数不同
        if (types.length != clazzes.length) {
            return false;
        }
 
        for (int i = 0; i < types.length; i++) {
            if(!Type.getType(clazzes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }
 
 
    /**
     * 
     * <p>获取方法的参数名</p>
     *
     * @param m
     * @return
     */
    public static String[] getMethodParamNames(final Method m,InputStream is) {
        final String[] paramNames = new String[m.getParameterTypes().length];
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassReader cr = null;
        try {
            cr = new ClassReader(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cr.accept(new ClassVisitor(Opcodes.ASM4, cw) {
            @Override
            public MethodVisitor visitMethod(final int access,
                    final String name, final String desc,
                    final String signature, final String[] exceptions) {
                final Type[] args = Type.getArgumentTypes(desc);
                // 方法名相同并且参数个数相同
                if (!name.equals(m.getName())
                        || !sameType(args, m.getParameterTypes())) {
                    return super.visitMethod(access, name, desc, signature,
                            exceptions);
                }
                MethodVisitor v = cv.visitMethod(access, name, desc, signature,
                        exceptions);
                return new MethodVisitor(Opcodes.ASM4, v) {
                    @Override
                    public void visitLocalVariable(String name, String desc,
                            String signature, Label start, Label end, int index) {
                        int i = index - 1;
                        // 如果是静态方法，则第一就是参数
                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                        if(Modifier.isStatic(m.getModifiers())) {
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
    public static void main(String[] args) throws Exception {
    	ClassVisitor cv = new ClassVisitor(Opcodes.ASM5) {
    		public FieldVisitor visitField(int access, String name, String desc,String sig, Object value) {         
    			//如果字段加 final ,则可以有默认值value,否则为null         
    			System.out.println(access+"\t"+name+"\t"+desc+"\t"+sig+"\t"+value);        
    			return super.visitField(access, name, desc, sig, value);     
    		}
		};
    	InputStream is = new FileInputStream("E:/apache-tomcat-8.0.26/webapps/AccountMS/WEB-INF/classes/com/dc/module/system/controller/LoginController.class");
    	 ClassReader creader = new ClassReader(is);
    	 creader.accept(cv, 0);
    }
}