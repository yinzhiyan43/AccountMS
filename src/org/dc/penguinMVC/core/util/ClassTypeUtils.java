package org.dc.penguinMVC.core.util;

public class ClassTypeUtils {
	private static final String BYTE = "byte";
	private static final String CHAR = "char";
	private static final String INT = "int";
	private static final String SHORT = "short";
	private static final String FLOAT = "float";
	private static final String LONG = "long";
	private static final String DOUBLE = "double";
	private static final String BOOLEAN = "boolean";
	public static Object[] getNewInstances(String type,int len){
		byte[] o =new byte[0];
		if(BYTE.equals(type)){
			return new Byte[len];
		}
		if(CHAR.equals(type)){
			return new Character[len];
		}
		if(INT.equals(type)){
			return new Integer[len];
		}
		if(SHORT.equals(type)){
			return new Short[len];
		}
		if(FLOAT.equals(type)){
			return new Float[len];
		}
		if(LONG.equals(type)){
			return new Long[len];
		}
		if(DOUBLE.equals(type)){
			return new Double[len];
		}
		if(BOOLEAN.equals(type)){
			return new Boolean[len];
		}
		return null;
	}
}
