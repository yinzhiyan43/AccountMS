package org.dc.penguinMVC.core.handle;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

public class RequestHandle {
	private Class<?> clazz;
	private String methodName;
	private Class<?>[] parameterTypes;
	private String[] paramName;
	public Object excute(Map<?,?> map, ServletRequest request, ServletResponse response,HttpSession session) throws Exception{
		byte len = (byte) parameterTypes.length;
		Object[] paramObjs =null;
		if(len>0){
			paramObjs = new Object[len];
			for (int i = 0; i < len; i++) {
				Class<?> paramType = parameterTypes[i];
				if(Map.class.isAssignableFrom(paramType)){
					paramObjs[i] = map;
				}else if(ServletRequest.class.isAssignableFrom(paramType)){
					paramObjs[i] = request;
				}else if(ServletResponse.class.isAssignableFrom(paramType)){
					paramObjs[i] = response;
				}else if(HttpSession.class.isAssignableFrom(paramType)){
					paramObjs[i] = session;
				}else if(paramType.getClassLoader()==null){//基本数据类型
					Object[] objs = (Object[])map.get(paramName[i]);
					if(objs!=null && objs.length>0){
						paramObjs[i] = this.getTypeValue(paramType,objs);
					}
				}else{//对象
					Object obj_newInsten = paramType.newInstance();
					Field[] fields = paramType.getDeclaredFields();
					for (Map.Entry<?, ?> entry : map.entrySet()) {
						String jsp_key = entry.getKey().toString();
						Object[] key_value = (Object[])entry.getValue();
						for (int j = 0,j_len=fields.length; j <j_len; j++) {
							Field fd = fields[j];
							Class<?> classType = fd.getType();
							if((paramName[i]+"."+fd.getName()).equals(jsp_key)){
								fd.setAccessible(true); //
								fd.set(obj_newInsten,this.getTypeValue(classType, key_value));
								break;
							}
						}
					}
					paramObjs[i] = obj_newInsten;
				}
			}
		}
		Object obj = clazz.newInstance();
		return obj.getClass().getMethod(methodName, parameterTypes).invoke(obj, paramObjs);
	}


	public Object getTypeValue(Class<?> classType,Object[] value){
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


	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public String[] getParamName() {
		return paramName;
	}


	public void setParamName(String[] paramName) {
		this.paramName = paramName;
	}
}
