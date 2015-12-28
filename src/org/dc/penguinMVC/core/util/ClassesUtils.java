package org.dc.penguinMVC.core.util;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public final class ClassesUtils {
	public static Object getTypeValue(Class<?> classType,Object[] value){
		int len = value.length;
		if(String.class.isAssignableFrom(classType)){
			String[] byte_arr = new String[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null){
					byte_arr[i] =value[i].toString();
				}else{
					byte_arr[i] = null;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Byte.TYPE.isAssignableFrom(classType)){
			byte[] byte_arr = new byte[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Byte.parseByte(value[i].toString());
				}else{
					byte_arr[i] = 0;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Byte.class.isAssignableFrom(classType)){
			Byte[] byte_arr = new Byte[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Byte.parseByte(value[i].toString());
				}else{
					byte_arr[i] = 0;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Character.class.isAssignableFrom(classType)){
			Character[] byte_arr = new Character[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =value[i].toString().charAt(0);
				}else{
					byte_arr[i] = '\u0000';
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Character.TYPE.isAssignableFrom(classType)){
			char[] byte_arr = new char[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =value[i].toString().charAt(0);
				}else{
					byte_arr[i] = '\u0000';
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Integer.TYPE.isAssignableFrom(classType)){
			int[] byte_arr = new int[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Integer.parseInt(value[i].toString());
				}else{
					byte_arr[i] = 0;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Integer.class.isAssignableFrom(classType)){
			Integer[] byte_arr = new Integer[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Integer.parseInt(value[i].toString());
				}else{
					byte_arr[i] = 0;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Short.class.isAssignableFrom(classType)){
			Short[] byte_arr = new Short[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Short.parseShort(value[i].toString());
				}else{
					byte_arr[i] = 0;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Short.TYPE.isAssignableFrom(classType)){
			short[] byte_arr = new short[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Short.parseShort(value[i].toString());
				}else{
					byte_arr[i] = 0;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Float.TYPE.isAssignableFrom(classType)){
			float[] byte_arr = new float[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Float.parseFloat(value[i].toString());
				}else{
					byte_arr[i] = 0;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Float.class.isAssignableFrom(classType)){
			Float[] byte_arr = new Float[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Float.parseFloat(value[i].toString());
				}else{
					byte_arr[i] = 0f;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Long.class.isAssignableFrom(classType)){
			Long[] byte_arr = new Long[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Long.parseLong(value[i].toString());
				}else{
					byte_arr[i] = 0L;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Long.TYPE.isAssignableFrom(classType)){
			long[] byte_arr = new long[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Long.parseLong(value[i].toString());
				}else{
					byte_arr[i] = 0l;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Double.TYPE.isAssignableFrom(classType)){
			double[] byte_arr = new double[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Double.parseDouble(value[i].toString());
				}else{
					byte_arr[i] = 0d;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Double.class.isAssignableFrom(classType)){
			Double[] byte_arr = new Double[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("")){
					byte_arr[i] =Double.parseDouble(value[i].toString());
				}else{
					byte_arr[i] = 0D;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Boolean.class.isAssignableFrom(classType)){
			Boolean[] byte_arr = new Boolean[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("") && value.toString().equals("true") || value.toString().equals("1")){
					byte_arr[i] = true;
				}else{
					byte_arr[i] = false;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		if(Boolean.TYPE.isAssignableFrom(classType)){
			boolean[] byte_arr = new boolean[len];
			for (int i = 0; i < len; i++) {
				if(value[i]!=null && !value[i].toString().equals("") && value.toString().equals("true") || value.toString().equals("1")){
					byte_arr[i] = true;
				}else{
					byte_arr[i] = false;
				}
			}
			if(classType.isArray()){
				return byte_arr;
			}else{
				return byte_arr[0];
			}
		}
		return null;
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
				if (!name.equals(m.getName()) || !sameType(args, m.getParameterTypes())) {
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
}