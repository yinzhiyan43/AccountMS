package org.dc.jdbc.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dc.jdbc.utils.DBHelper;

public class ConnThreadShare {
	private static Log log = LogFactory.getLog(DBHelper.class);
	private static Map<Long ,LinkedList<Connection>> threadConnMap = new HashMap<Long, LinkedList<Connection>>();
	public static void putConnect(long threadId,Connection conn){
		LinkedList<Connection> connList = threadConnMap.get(threadId);
		if(connList==null){
			connList = new LinkedList<Connection>();
		}
		connList.addFirst(conn);
		threadConnMap.put(threadId, connList);
	}
	public static void removeConnect(long threadId){
		threadConnMap.remove(threadId);
	}
	public static LinkedList<Connection> getConnList(long threadId){
		return threadConnMap.get(threadId);
	}
	public static void closeConnection(long threadId){
		try{
			LinkedList<Connection> connList = threadConnMap.get(threadId);
			if(connList!=null){
				for (int i = 0,i_len=connList.size(); i < i_len; i++) {
					try{
						connList.get(i).close();
					}catch (Exception e) {
						log.error("",e);
					}
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}finally{
			ConnThreadShare.removeConnect(threadId);
		}
	}
	public static void rollback(long threadId) {
		try{
			LinkedList<Connection> connList = threadConnMap.get(threadId);
			if(connList!=null){
				for (int i = 0,i_len=connList.size(); i < i_len; i++) {
					try{
						connList.get(i).rollback();
					}catch (Exception e) {
						log.error("",e);
					}
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}
	}
	public static void commit(long threadId){
		try{
			LinkedList<Connection> connList = threadConnMap.get(threadId);
			if(connList!=null){
				for (int i = 0,i_len=connList.size(); i < i_len; i++) {
					try{
						Connection conn = connList.get(i);
						if(conn.getAutoCommit()==false){
							conn.commit();
						}
					}catch (Exception e) {
						log.error("",e);
					}
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}
	}
}
