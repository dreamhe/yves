<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.com.yves.bean.UserBean"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'testForward.jsp' starting page</title>

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
	<input type="text" value="<%=basePath%>"> This is
	testRedirect.jsp
	<%
		UserBean userBean = (UserBean) request.getAttribute("userBean");
		String yves = "何宇yves";
		int result  = 1;
		if(userBean == null){
			result = -1;
		}
	%>


	<input type="button" value="点击我" onclick="alert(1);alerMessage();">
	<br>
	<script type="text/javascript">
		function alerMessage() {
			var message ="<%=yves%>";
			var num = "<%=result%>";
			alert(num);
		}
	</script>

</body>
</html>
