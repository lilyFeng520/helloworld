<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<script type="text/javascript">
       //实现自动登录功能
//     function getCookie(c_name){
// 	    if (document.cookie.length>0) {
//          //document.cookie: ssid=abc;vvid=defg;ccid=hijk
//          //通过字符串截取
//          //开始截取的位置索引
//            var c_start = document.cookie.indexOf(c_name+"=");
//            c_start = c_start + c_name.length+1; 
//            var c_end = document.cookie.indexOf(";",c_start);
//            if(c_end ==-1)c_end = document.cookie.length;        
//            var va1 = document.cookie.substring(c_start,c_end);
//            return unescape(va1);
// 	      } 
//      }

//       window.onload=function(){
//           // alert();
//           var form = document.getElementById("loginform");
//           var username = document.getElementById("username");
//           if(getCookie("userKey")!=null && getCookie("userKey")!="" && getCookie("ssid")!=null && getCookie("ssid")!=""){
//         	  username.value = getCookie("userKey");
//         	  form.submit();
//           }
//       }

</script>
</head>
<body>
	<br>
	<br>
	<c:if test="${not empty requestScope.note }">
	   <span style="color: red; font-weight: bolder;">${note}</span>
	</c:if>
	

	<form id="loginform" action="${pageContext.request.contextPath }/login.udo" method="get">
		用户名：<input id="username" type="text" name="username" value=""/> <br> <br>
		密 码：<input type="text" name="pasword" /> <br> <br> 记住我一周<input
			type="radio" name="expiredays" value="7" />记住我一个月<input type="radio"
			name="expiredays" value="30" />记住我到永远<input type="radio"
			name="expiredays" value="100" /> <br> <br> <input
			type="submit" value="登录" />

	</form>


</body>
</html>