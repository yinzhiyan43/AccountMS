package org.dc.jdbc.core.operate;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InsertOneOper {
	private static Log jdbclog = LogFactory.getLog(SelectListOper.class);
	public static int insert(Connection conn,String sql,Object[] params) throws Exception{
		PreparedStatement ps = null;
		try {
			if(conn.getAutoCommit()==true){
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql);
			if(params!=null && params.length>0){
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);
				}
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		}finally{
			if(ps!=null){
				try{
					ps.close();
				}catch (Exception e) {
					jdbclog.error("",e);
				}
			}
		}
	}
}
