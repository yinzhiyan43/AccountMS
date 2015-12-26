package org.dc.jdbc.utils;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.dc.jdbc.core.ConnThreadShare;

public final class JDBCProxy implements MethodInterceptor {
	private static class JDBCProxyHolder {    
		private static final JDBCProxy INSTANCE = new JDBCProxy();
	}
	public static final JDBCProxy getInstance() {
		return JDBCProxyHolder.INSTANCE;
	}
	public JDBCProxy(){
	}
	public JDBCProxy(Object obj){
		this.target = obj;
	}
	private Object target;
	public Object intercept(Object obj, Method arg1, Object[] arg2, MethodProxy proxy) throws Throwable {
		Object invokeObj = null;
		long threadId = Thread.currentThread().getId();
		try{
			invokeObj = proxy.invokeSuper(obj, arg2);
		}catch(Throwable e){
			ConnThreadShare.rollback(threadId);
			throw e;
		}finally{
			ConnThreadShare.commit(threadId);
			ConnThreadShare.closeConnection(threadId);
		}
		return invokeObj;
	}
	public Object getTarget(Object target) {
		Enhancer enhancer = new Enhancer();
		if(target == null){
			enhancer.setSuperclass(this.target.getClass());  
		}else{
			enhancer.setSuperclass(target.getClass());  
		}
		// 回调方法  
		enhancer.setCallback(this);
		// 创建代理对象  
		return enhancer.create();
	}
	public Object getTarget() {
		return getTarget(null);
	}
	public void setTarget(Object target) {
		this.target = target;
	}
}
