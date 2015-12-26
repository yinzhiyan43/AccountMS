package org.dc.jdbc.core.operate;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SelectOneOper {
	private static Log jdbclog = LogFactory.getLog(SelectOneOper.class);
	@SuppressWarnings("unchecked")
	public static <T> T selectOne(Connection conn,String sql,Class<?> cls,Object[] params) throws Exception{
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
			if(rs.last() && rs.getRow()>1){
				throw new Exception("列太多");
			}

			rs.beforeFirst();
			ResultSetMetaData metaData  = rs.getMetaData();
			int cols_len = metaData.getColumnCount();
			if(cls==null){
				Map<String, Object> map = null;
				while(rs.next()){
					map = new HashMap<String, Object>(cols_len,1);
					for(int i=0; i<cols_len; i++){  
						String cols_name = metaData.getColumnLabel(i+1);  
						Object cols_value = rs.getObject(cols_name);  
						map.put(cols_name, cols_value);
					}
					return (T) map;
				}
			}else{
				if(cls.getClassLoader()==null){//java基本类型
					if(cols_len>1){
						throw new Exception("列太多");
					}
					while(rs.next()){
						Object cols_value = rs.getObject(1);
						return (T) cols_value;
					}
				}else{//java对象
					while(rs.next()){
						Object obj_newInsten = cls.newInstance();
						Field[] fields = cls.getDeclaredFields();
						for(int i = 0; i<cols_len; i++){
							String cols_name = metaData.getColumnName(i+1);  
							Object cols_value = rs.getObject(cols_name);
							for (int j = 0,j_len=fields.length; j < j_len; j++) {
								Field fd = fields[j];
								if(fd.getName().equals(cols_name)){
									fd.setAccessible(true);
									fd.set(obj_newInsten, cols_value);
									break;
								}
							}
						}
						return (T) obj_newInsten;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(rs!=null){
				try{
					rs.close();
					rs = null;
				}catch (Exception e) {
					jdbclog.error("",e);
				}
			}
			if(ps!=null){
				try{
					ps.close();
					ps = null;
				}catch (Exception e) {
					jdbclog.error("",e);
				}
			}
		}
		return null;
	}
}
