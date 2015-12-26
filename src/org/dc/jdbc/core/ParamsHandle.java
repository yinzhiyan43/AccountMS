package org.dc.jdbc.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * @author DC
 */
public class ParamsHandle {
	public final static  String SQLKey = "sql";
	public final static  String PARAMSKey = "params";
	public static Map<String,Object> paramParse(String sql,Object params) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>(2,1);
		if(params!=null){
			Object[] objs =  null;
			if(Map.class.isAssignableFrom(params.getClass())){
				Map<?,?> p = (Map<?,?>)params;
				objs = new Object[p.size()];
				int index = 0;
				for (int i = 0,len=sql.length(); i <len; i++) {
					if(sql.charAt(i)=='#'){
						int indexEnd = sql.indexOf("}");
						String key = sql.substring(i+2,indexEnd);
						sql = sql.replace(sql.substring(i,indexEnd+1), "?");
						len= sql.length();
						Object value= p.get(key);
						if(value instanceof Object[]){
							objs[index] = ((Object[])value)[0];
						}else{
							objs[index] = value;
						}
						index++;
					}
				}
			}else if(Collection.class.isAssignableFrom(params.getClass())){
				objs = ((Collection<?>) params).toArray();
			}else if(Object[].class.isAssignableFrom(params.getClass())){
				map.put(SQLKey, sql);
				map.put(PARAMSKey, params);
				return map;
			}else{
				throw new Exception("未匹配到Map,Collection");
			}
			map.put(SQLKey, sql);
			map.put(PARAMSKey, objs);
			return map;
		}else{
			map.put(SQLKey, sql);
			return map;
		}
	}
}
