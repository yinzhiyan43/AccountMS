package org.dc.penguinMVC.core.handle;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.dc.penguinMVC.core.util.ClassesUtils;

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
						paramObjs[i] = ClassesUtils.getTypeValue(paramType,objs);
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
								fd.set(obj_newInsten,ClassesUtils.getTypeValue(classType, key_value));
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
