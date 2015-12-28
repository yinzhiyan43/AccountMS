package org.dc.jdbc.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dc.jdbc.utils.DBHelper;

public class ConnectionManager {
	private static Log log = LogFactory.getLog(DBHelper.class);

	public static ThreadLocal<Map<Integer,Connection>> connLocal = new ThreadLocal<Map<Integer,Connection>>();

	public static Connection getConnection(DataSource dataSource) throws Exception{
		Map<Integer,Connection> connMap = connLocal.get();
		if(connMap!=null){
			int hashid = dataSource.hashCode();
			if(connMap.containsKey(hashid)){
				return connMap.get(hashid);
			}
			//如果上面for循环还没返回conn对象，则当前线程中还没有获得该conn对象
			Connection conn = dataSource.getConnection();
			connMap.put(dataSource.hashCode(), conn);
			return conn;
		}else{
			Map<Integer,Connection> map = new HashMap<Integer, Connection>();
			Connection conn = dataSource.getConnection();
			map.put(dataSource.hashCode(), conn);
			connLocal.set(map);
			return conn;
		}
	}


	public static void closeConnection(){
		try{
			Map<Integer,Connection> connMap = connLocal.get();
			for (Connection conn : connMap.values()) {
				try{
					conn.close();
				}catch (Exception e) {
					log.error("",e);
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}finally{
			connLocal.remove();
		}
	}
	public static void rollback() {
		try{
			Map<Integer,Connection> connMap = connLocal.get();
			for (Connection conn : connMap.values()) {
				try{
					conn.rollback();
				}catch (Exception e) {
					log.error("",e);
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}
	}
	public static void commit(){
		try{
			Map<Integer,Connection> connMap = connLocal.get();
			for (Connection conn : connMap.values()) {
				try{
					if(conn.getAutoCommit()==false){
						conn.commit();
					}
				}catch (Exception e) {
					log.error("",e);
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}
	}
}
