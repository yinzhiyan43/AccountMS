package com.dc.module.system.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.dc.jdbc.entity.Student;
import org.dc.penguinMVC.core.annotation.Controller;
import org.dc.penguinMVC.core.annotation.RequestMapping;

import com.dc.commons.Common;
import com.dc.commons.ParamKey;
import com.dc.module.system.service.LoginService;
import com.dc.utils.ObjectFactory;

@Controller
@RequestMapping("/user")
public class LoginController {
	private LoginService loginService = ObjectFactory.getPrototypeByProxy(LoginService.class);
	@RequestMapping("/login")
	public String login(Student s,Student s1,String username,Map<String,String[]> paramMap,HttpSession session){
		try {
			Map<String, Object> userMap = loginService.login(paramMap);
			if(userMap!=null){
				session.setAttribute(Common.userKey, userMap.get(ParamKey.username));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/WEB-INF/view/system/search.jsp";
	}
	public static void main(String[] args) {
		char[] c =new char[10];
		System.out.println(c);
	}
}
