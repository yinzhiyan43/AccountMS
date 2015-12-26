package org.dc.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dc.jdbc.core.operate.SelectListOper;
import org.dc.jdbc.core.operate.SelectOneOper;
import org.dc.jdbc.entity.Student;
import org.junit.Before;
import org.junit.Test;

public class JDBCTest {
	private Connection conn;
	@Before
	public void init(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8" ;    
			String username = "root" ;   
			String password = "123456" ; 
			conn =DriverManager.getConnection(url , username , password ) ;   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void selectOne() {
		try {
			Student obj=SelectOneOper.selectOne(conn, "select id,maxNum,pid from student where id = 1",Student.class,null);
			System.out.println(obj.getPid());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Test
	public void selectList() {
		try {
			/*List<Long> list=SelectListOper.selectList(conn, "select maxNum from student",Long.class);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
			List<Student> stulist=SelectListOper.selectList(conn, "select id,blobtest from student",Student.class);
			for (int i = 0; i < stulist.size(); i++) {
				System.out.println(stulist.get(i).getId() +" = "+stulist.get(i).getBlobtest());
			}*/
			List<Map<String,Object>> maplist=SelectListOper.selectList(conn, "select id,blobtest from student",HashMap.class);
			for (int i = 0; i < maplist.size(); i++) {
				System.out.println(maplist.get(i).get("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
