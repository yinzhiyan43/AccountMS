package org.dc.penguinMVC.core.util;

public class ClassTypeUtils {
	public static Object[] getNewInstances(Class<?> type,int len){
		if(Byte.TYPE.isAssignableFrom(type)){
			return new Byte[len];
		}
		if(Character.TYPE.isAssignableFrom(type)){
			return new Character[len];
		}
		if(Integer.TYPE.isAssignableFrom(type)){
			return new Integer[len];
		}
		if(Short.TYPE.isAssignableFrom(type)){
			return new Short[len];
		}
		if(Float.TYPE.isAssignableFrom(type)){
			return new Float[len];
		}
		if(Long.TYPE.isAssignableFrom(type)){
			return new Long[len];
		}
		if(Double.TYPE.isAssignableFrom(type)){
			return new Double[len];
		}
		if(Boolean.TYPE.isAssignableFrom(type)){
			return new Boolean[len];
		}
		return null;
	}
}
