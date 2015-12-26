package org.dc.jdbc.utils;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dc.jdbc.core.ConnThreadShare;
import org.dc.jdbc.core.ParamsHandle;
import org.dc.jdbc.core.operate.InsertOneOper;
import org.dc.jdbc.core.operate.SelectOneOper;
/**
 * 数据持久化操作类
 * @author DC
 * @time 2015-12-09
 */
public class DBHelper {
	private static Log log = LogFactory.getLog(DBHelper.class);
	private DataSource dataSource;
	private Connection conn;
	private long threadId = Thread.currentThread().getId();

	
	public Connection getConnection() throws Exception{
		if(conn==null){
			conn = dataSource.getConnection();
			ConnThreadShare.putConnect(threadId,conn);
		}
		return conn;
	}
	public void closeConnection(){
		try{
			LinkedList<Connection> connList = ConnThreadShare.getConnList(threadId);
			for (int i = 0,i_len=connList.size(); i < i_len; i++) {
				try{
					connList.get(i).close();
				}catch (Exception e) {
					log.error("",e);
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}finally{
			ConnThreadShare.removeConnect(threadId);
		}
	}
	public void rollback(){
		LinkedList<Connection> connList = ConnThreadShare.getConnList(threadId);
		for (int i = 0,i_len=connList.size(); i < i_len; i++) {
			try{
				connList.get(i).rollback();
			}catch (Exception e) {
				log.error("",e);
			}
		}
	}
	public void commit(){
		LinkedList<Connection> connList = ConnThreadShare.getConnList(threadId);
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
	public <T> T selectOne(String sql,Class<?> cls,Object params) throws Exception{
		Connection conn = getConnection();
		
		Map<String,Object> pms_Map = ParamsHandle.paramParse(sql,params);
		String sql_handle = pms_Map.get(ParamsHandle.SQLKey).toString();
		Object[] params_obj = (Object[])pms_Map.get(ParamsHandle.PARAMSKey);

		return SelectOneOper.selectOne(conn,sql_handle,cls,params_obj);
	}
	public <T> T selectOne(String sql,Object params) throws Exception{
		return this.selectOne(sql, null,params);
	}
	public <T> T selectOne(String sql,Class<?> cls) throws Exception{
		return this.selectOne(sql, cls,null);
	}
	public <T> T selectOne(String sql) throws Exception{
		return this.selectOne(sql,null,null);
	}
	
	public int insert(String sql,Object params) throws Exception{
		Connection conn = getConnection();
		
		Map<String,Object> pms_Map = ParamsHandle.paramParse(sql,params);
		String sql_handle = pms_Map.get(ParamsHandle.SQLKey).toString();
		Object[] params_obj = (Object[])pms_Map.get(ParamsHandle.PARAMSKey);

		return InsertOneOper.insert(conn, sql_handle, params_obj);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
