<%@ page language="java" session="false"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		
	
	    //获取客户端发来的Cookie
	    Cookie[] cookiesArray = request.getCookies();
		if (cookiesArray != null && cookiesArray.length > 0) {
			for (int i = 0; i < cookiesArray.length; i++) {
				out.println("Cookie的名字" + cookiesArray[i].getName());
				out.println("Cookie的值：" + cookiesArray[i].getValue());
			}
		} else {
			//新建一个cookie
			Cookie cookie = new Cookie("uuid", "uuidvalue");
			//设置cookie生命周期是一天
			cookie.setMaxAge(1*24*60*60);
			//让客户端建立一个cookie
			response.addCookie(cookie);
		}
		
		
		
	%>

    
</body>
</html>