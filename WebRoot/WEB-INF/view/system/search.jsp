<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'search.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<form action="/${base}/account/addAccount.do" method="post">
  		检索标题<input type="text" name="title" value="${param.title}"><br/>
	  	账号<input type="text" name="accountNo" value="${param.accountNo}"><br/>
	  	密码<input type="text" name="password" value="${param.password}"><br/>
	  	<input type="submit" value="添加">
  	</form>
  	<form action="/${base}" method="post">
	  	<input type="text" name=""><br/>
	  	<input type="radio" name="">
	  	<input type="submit" value="提交">
  	</form>
  </body>
</html>
