package org.dc.jdbc.utils;

import java.sql.Connection;
import java.util.Map;

import org.dc.jdbc.core.ConnectionManager;
import org.dc.jdbc.core.ParamsHandle;
import org.dc.jdbc.core.operate.InsertOneOper;
import org.dc.jdbc.core.operate.SelectOneOper;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * 数据持久化操作类
 * @author DC
 * @time 2015-12-09
 */
public class DBHelper {
	private DruidDataSource dataSource;
	
	public <T> T selectOne(String sql,Class<?> cls,Object params) throws Exception{
		Connection conn = ConnectionManager.getConnection(dataSource);
		
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
		Connection conn = ConnectionManager.getConnection(dataSource);
		
		Map<String,Object> pms_Map = ParamsHandle.paramParse(sql,params);
		String sql_handle = pms_Map.get(ParamsHandle.SQLKey).toString();
		Object[] params_obj = (Object[])pms_Map.get(ParamsHandle.PARAMSKey);

		return InsertOneOper.insert(conn, sql_handle, params_obj);
	}
	
	public DruidDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}
}
