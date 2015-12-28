package com.dc.module.system.service;

import java.util.Map;

import org.dc.jdbc.utils.DBHelper;

import com.dc.utils.ObjectFactory;

public class LoginService {
	private DBHelper accountDBHelper = ObjectFactory.getAccDBHelper(DBHelper.class);
	private DBHelper testDBHelper = ObjectFactory.getTestDBHelper(DBHelper.class);
	public Map<String,Object> login(Map<String,String[]> paramMap) throws Exception{
		Map<String,Object> userMap = accountDBHelper.selectOne("select * from sys_userinfo where username=1");
		testDBHelper.selectOne("select * from student where id=1");
		return userMap;
	}
}
