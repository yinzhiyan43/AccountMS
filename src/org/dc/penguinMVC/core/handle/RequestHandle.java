package org.dc.penguinMVC.core.handle;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

public class RequestHandle {
	/*private Object obj;
	private Method methodInstance;
	private Class<?>[] paramClass;//
	 */	
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
				}else if(paramType.getClassLoader()==null){//基本数据类型
					Object[] obj = (Object[])map.get(paramName[i]);
					if(Object[].class.isAssignableFrom(paramType)){
						if(obj!=null){
							for (int j = 0; j < obj.length; j++) {
								obj[j] =getTypeValue(paramType,obj[j]);
							}
						}
						paramObjs[i] = obj;
					}else{
						if(obj==null){
							paramObjs[i] =getTypeValue(paramType,null);
						}else{
							paramObjs[i] =getTypeValue(paramType,obj[0]);
						}
					}
				}else if(ServletRequest.class.isAssignableFrom(paramType)){
					paramObjs[i] = request;
				}else if(ServletResponse.class.isAssignableFrom(paramType)){
					paramObjs[i] = response;
				}else if(HttpSession.class.isAssignableFrom(paramType)){
					paramObjs[i] = session;
				}
			}
		}
		Object obj = clazz.newInstance();
		return obj.getClass().getMethod(methodName, parameterTypes).invoke(obj, paramObjs);
	}


	public Object getTypeValue(Class<?> paramTpye,Object value){
		if(Object.class.isAssignableFrom(paramTpye)){
			if(String.class.isAssignableFrom(paramTpye)){
				if(value!=null){
					return value.toString();
				}else{
					return new String();
				}
			}
			if(Integer.class.isAssignableFrom(paramTpye)){
				if(value!=null && !value.toString().equals("")){
					return Integer.parseInt(value.toString());
				}else{
					return 0;
				}
			}
			if(Float.class.isAssignableFrom(paramTpye)){
				if(value!=null && !value.toString().equals("")){
					return Float.parseFloat(value.toString());
				}else{
					return 0F;
				}
			}
			if(Double.class.isAssignableFrom(paramTpye)){
				if(value!=null && !value.equals("")){
					return Double.parseDouble(value.toString());
				}else{
					return 0D;
				}
			}
			if(Boolean.class.isAssignableFrom(paramTpye)){
				if((value!=null && !value.toString().equals("") && value.toString().equals("true")) || value.toString().equals("1")){
					return true;
				}else{
					return false;
				}
			}
			if(Character.class.isAssignableFrom(paramTpye)){
				if(value!=null && !value.toString().equals("")){
					return value.toString().charAt(0);
				}else{
					return ' ';
				}
			}
			if(Byte.class.isAssignableFrom(paramTpye)){
				if(value!=null && !value.toString().equals("")){
					return Byte.parseByte(value.toString());
				}else{
					return (byte)0;
				}
			}
			if(Short.class.isAssignableFrom(paramTpye)){
				if(value!=null && !value.toString().equals("")){
					return Short.parseShort(value.toString());
				}else{
					return (byte)0;
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		char c = ' ';
		System.out.println(c);
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
