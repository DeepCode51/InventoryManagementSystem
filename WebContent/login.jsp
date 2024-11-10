<%@ page contentType="text/html; charset=UTF-8" %>
<%
	session.removeAttribute("userInfo");
%>
<html>
<head>
	<title>库存管理系统</title>
	<script type="text/javascript">
		function check(){
			var logincode = document.getElementById("logincode").value;
			var password = document.getElementById("password").value;
			var error = document.getElementById("error");
			error.innerHTML="";
			if(logincode==""){
				error.innerHTML="用户名不能为空！";
				return false;
			}
			if(password==""){
				error.innerHTML="密码不能为空！";
				return false;
			}
			return true;
		}
	</script>
	<style type="text/css">
		* { margin:0 auto; padding:0; border:0;font-size:12px;}
		body { background:#0462A5; font:12px "宋体"; color:#004C7E;}
		input { border:1px solid #004C7E;}
		.main { background:url(img/login/bg.jpg) repeat-x;}
		.login { background:#DDF1FE; width:468px; height:262px; border:1px solid #000;}
		.top { background:url(img/login/login_bg.jpg) repeat-x; width:464px; height:113px; border:1px solid #2376B1; margin-top:1px;}
		.logo { background:url(img/login/logo.gif) no-repeat; width:150px; height:42px; float:left; margin:29px 0 0 14px;}
		.lable { background:url(img/login/lable.gif) no-repeat; width:157px; height:32px; float:right; margin:81px 31px 0 0;}
		.submit { background:url(img/login/submit.gif) no-repeat; cursor:hand; width:71px; height:24px; border:0;} 
		.reset { background:url(img/login/reset.gif) no-repeat; cursor:hand; width:71px; height:24px; border:0;} 
	</style>
</head>

<body>
<div>
<table width="100%" height="100%" class="main" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
      <div class="login">
        <div class="top">
          <div class="logo"></div>
          <div class="lable"></div>
        </div>
        <table width="466" cellpadding="0" cellspacing="0">
          <tr>
            <td style="padding-top:30px;">
		    <form action="user_login.do" method="post">
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right" height="27">用户名:</td>
                  <td align="right" width="161">
                    <input type="text" name="logincode" style="width:150px;height:20px" />
                  </td>
                  <td align="center" height="29">
                    <input name="submit" type="submit" value="" onclick="return check()" class="submit" />
                  </td>
                </tr>
                <tr>
                  <td align="right" height="27">密&nbsp;&nbsp;码:</td>
                  <td align="right" width="161">
                    <input type="password" name="password" style="width:150px;height:20px" />
                  </td>
                  <td align="center" height="29">
                    <input name="reset" type="reset" value="" class="reset" />
                  </td>
                </tr>
              </table>
	        </form>
            </td>
          </tr>
        </table>
        <table width="100%" cellpadding="0" cellspacing="0" style="margin-top:8px;">
          <tr>
          	<td align="center" style="height:25"><font id="error" color="red">${error}&nbsp;</font></td>
          </tr>
          <tr>
            <td align="center">MyStock</td>
          </tr>
        </table>
      </div>
      <!--login -->
    </td>
  </tr>
</table>
</body>
</html>