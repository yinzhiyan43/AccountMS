package org.dc.jdbc.core.operate;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SelectListOper{
	private static Log jdbclog = LogFactory.getLog(SelectListOper.class);
	@SuppressWarnings("unchecked")
	public static <T> List<T> selectList(Connection conn,String sql,Class<?> cls,Object[] params) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if(params!=null && params.length>0){
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);
				}
			}
			rs = ps.executeQuery();
			rs.last();
			int rowNum = rs.getRow();
			if(rowNum>0){
				rs.beforeFirst();
				List<T> list = new ArrayList<T>(rowNum);

				ResultSetMetaData metaData  = rs.getMetaData();
				int cols_len = metaData.getColumnCount();
				if(cls==null || Map.class.isAssignableFrom(cls)){
					while(rs.next()){
						Map<String, Object> map = new HashMap<String, Object>();
						for(int i=0; i<cols_len; i++){  
							String cols_name = metaData.getColumnLabel(i+1);  
							Object cols_value = rs.getObject(cols_name);  
							map.put(cols_name, cols_value);
						}
						list.add((T)map);
					}
					return list;
				}else{
					if(cls.getClassLoader()==null){//封装成基本类型
						if(cols_len>1){
							throw new Exception("列太多");
						}
						while(rs.next()){
							for(int i=0; i<cols_len; i++){  
								Object cols_value = rs.getObject(i+1);
								list.add((T)cols_value);
							}
						}
						return list;
					}else{//
						while(rs.next()){
							Object obj_newInsten = cls.newInstance();
							Field[] fields = cls.getDeclaredFields();
							for(int i = 0; i<cols_len; i++){
								String cols_name = metaData.getColumnName(i+1);  
								Object cols_value = rs.getObject(cols_name);  
								for (int j = 0,j_len=fields.length; j <j_len; j++) {
									Field fd = fields[j];
									if(fd.getName().equals(cols_name)){
										fd.setAccessible(true); //
										fd.set(obj_newInsten, cols_value);
										break;
									}
								}
							}
							list.add((T) obj_newInsten);
						}
						return list;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch (Exception e) {
					jdbclog.error("",e);
				}
			}
			if(ps!=null){
				try{
					ps.close();
				}catch (Exception e) {
					jdbclog.error("",e);
				}
			}
		}
		return null;
	}
	public static <T> List<T> selectList(Connection conn,String sql,Class<?> cls) throws Exception{
		return selectList(conn, sql, cls, null);
	}
	public static <T> List<T> selectList(Connection conn,String sql,Object[] params) throws Exception{
		return selectList(conn, sql, null, params);
	}
	public static <T> List<T> selectList(Connection conn,String sql) throws Exception{
		return selectList(conn, sql, null, null);
	}
}
