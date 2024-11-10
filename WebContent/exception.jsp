<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name="exception_title"/></title>
</head>
<body style="padding:10px;background-color:#D6D3CE;">
<center><h2><s:text name="exception_title"/></h2></center>
<font color="#FF0000"><b><s:text name="exception_prompt"/></b></font><br/>
<textarea rows="22" cols="106">
	<!-- 输出异常信息内容 -->
	<s:property value="exception.message"/>
</textarea>
</body>
</html>