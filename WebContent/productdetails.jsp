<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
      
      <%
        String pname = (String) request.getAttribute("p");
        out.println(pname);
          
      %>
      <br><br>
               拿到其他......该产品的详细参数
      
      <form action="<%=request.getContextPath()%>/addcart.pdo?pname=<%=pname %>" method="get">
            <input style="width:100px; height:30px; background:red; color:#fff;" type="submit" value="加入到购物车">
      
      </form>
</body>
</html>