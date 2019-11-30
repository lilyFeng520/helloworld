<%@ page language="java" session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
     
     
       /* HttpSession session2 =  request.getSession();
        
        session2.setAttribute("users", "admin");
        
        out.println("取出session2里的users这个Key的value：" + session2.getAttribute("users"));*/
        
        
        <a href="<%=response.encodeURL("/testServlet.udo") %>"></a>
         
     
   
     
     
</body>
</html>