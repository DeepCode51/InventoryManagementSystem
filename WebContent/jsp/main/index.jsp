<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.cxstock.biz.power.dto.UserDTO"%>
<%
  UserDTO userInfo=(UserDTO)session.getAttribute("userInfo");
%> 
<html>
  <head>
  	<title>库存管理系统</title>
    <script type="text/javascript">
	     window.log_id="<%=userInfo.getUserid()%>";
	     window.log_name="<%=userInfo.getUsername()%>";
	</script>
    <link rel="stylesheet" type="text/css" href="../../ext/resources/css/ext-all.css">
    <link rel="stylesheet" type="text/css" href="../../css/ext-icon.css">
    <script type="text/javascript" src="../../ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="../../ext/ext-all.js"></script>
    <script type="text/javascript" src="../../ext/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../../js/Clock.js"></script>
    <script type="text/javascript" src="index.js"></script>
    <script type="text/javascript" src="App.js"></script>
  </head>
  
  <body>
  </body>
</html>