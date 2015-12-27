package com.dc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.dc.jdbc.utils.DBHelper;
import org.dc.jdbc.utils.JDBCProxy;

import com.alibaba.druid.pool.DruidDataSource;


public class ObjectFactory {
	private static Lock lock = new ReentrantLock();
	private static Map<Class<?>,Object> objMap = new HashMap<Class<?>,Object>();
	public static DruidDataSource testSource = new DruidDataSource();
	public static DruidDataSource accSource = new DruidDataSource();
	static{
		testSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8");
		testSource.setUsername("root");
		testSource.setPassword("123456");
		/*try {
			testSource.setFilters("config");
		} catch (Exception e) {
			log.error("",e);
		}
		testSource.setConnectionProperties("config.decrypt=true");*/
		testSource.setInitialSize(1);
		testSource.setMaxActive(4);
		testSource.setMinIdle(0);
		testSource.setMaxWait(60000);
		testSource.setValidationQuery("SELECT 1");
		testSource.setTestOnBorrow(false);
		testSource.setTestWhileIdle(true);
		testSource.setPoolPreparedStatements(false);
		testSource.setDriverClassName("com.mysql.jdbc.Driver");
	}
	static{
		accSource.setUrl("jdbc:mysql://localhost:3306/account_ms?useUnicode=true&characterEncoding=UTF-8");
		accSource.setUsername("root");
		accSource.setPassword("123456");
		/*try {
			testSource.setFilters("config");
		} catch (Exception e) {
			log.error("",e);
		}
		testSource.setConnectionProperties("config.decrypt=true");*/
		accSource.setInitialSize(2);
		accSource.setMaxActive(4);
		accSource.setMinIdle(0);
		accSource.setMaxWait(60000);
		accSource.setValidationQuery("SELECT 1");
		accSource.setTestOnBorrow(false);
		accSource.setTestWhileIdle(true);
		accSource.setPoolPreparedStatements(false);
		accSource.setDriverClassName("com.mysql.jdbc.Driver");
	}
	public static DBHelper getTestDBHelper(){
		DBHelper db = new DBHelper();
		db.setDataSource(testSource);
		return db;
	}
	public static DBHelper getAccDBHelper(){
		DBHelper db = new DBHelper();
		db.setDataSource(accSource);
		return db;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getSingletonByProxy(Class<?> clazz){
		Object obj = objMap.get(clazz);
		if(obj!=null){
			return (T) obj;
		}
		try {
			lock.lock();
			Object o = objMap.get(clazz);
			if(o!=null){
				return (T) o;
			}
			Object proxyInstance = JDBCProxy.getInstance().getTarget(clazz.newInstance());
			objMap.put(clazz, proxyInstance);
			return (T) proxyInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getPrototypeByProxy(Class<?> clazz){
		try {
			Object proxyInstance = JDBCProxy.getInstance().getTarget(clazz.newInstance());
			return (T) proxyInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getPrototype(Class<?> clazz){
		try {
			return (T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
