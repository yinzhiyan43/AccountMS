package com.dc.module.system.service;

import java.util.HashMap;
import java.util.Map;

import org.dc.jdbc.utils.DBHelper;

import com.dc.commons.Common;
import com.dc.commons.ParamKey;
import com.dc.utils.EncryptUtils;
import com.dc.utils.ObjectFactory;

public class AccountService {
	private DBHelper accountDBHelper = ObjectFactory.getAccDBHelper();
	public Map<String,Object> login(Map<String,String[]> paramMap) throws Exception{
		Map<String,Object> userMap = accountDBHelper.selectOne("select * from sys_userinfo where username=1");
		return userMap;
	}
	public int addAccount(Map<String, String[]> paramMap) throws Exception {
		String sql = "insert into account(accountNo,password,title) values(#{accountNo},#{password},#{title})";
		String password = paramMap.get(ParamKey.password)[0];
		password = new String(EncryptUtils.encrypt(password, Common.secretKey));
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put(ParamKey.accountNo, paramMap.get(ParamKey.accountNo));
		map.put(ParamKey.password, password);
		map.put(ParamKey.title, paramMap.get(ParamKey.title));
		return accountDBHelper.insert(sql, map);
	}
}
