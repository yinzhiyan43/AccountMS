package com.dc.module.system.controller;

import java.util.Map;

import org.dc.penguinMVC.core.annotation.Controller;
import org.dc.penguinMVC.core.annotation.RequestMapping;

import com.dc.module.system.service.AccountService;
import com.dc.utils.ObjectFactory;

@Controller
@RequestMapping("/account")
public class AccountController {
	private AccountService accountService = ObjectFactory.getPrototypeByProxy(AccountService.class);
	@RequestMapping("/addAccount")
	public String addAccount(Map<String,String[]> paramMap){
		try {
			 accountService.addAccount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/WEB-INF/view/system/search.jsp";
	}
	public String accountList(Map<String,String[]> paramMap){
		try {
			int i = accountService.addAccount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/WEB-INF/view/system/search.jsp";
	}
}
