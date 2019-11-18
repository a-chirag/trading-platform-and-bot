<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Orders</title>
</head>
<body>
<table>
<tr><th>Instrument</th><th>Side</th><th>Price</th><th>Size</th><th>Status</th></tr>
<c:forEach items="${userdata.pendingOrders}"  var="orders">
<tr><td>${orders.value.instrument}</td><td>${orders.value.side}</td><td>${orders.value.price}</td><td>${orders.value.size}</td><td>${orders.value.orderId}</td></tr>
</c:forEach>
</table>
<a href="home.jsp">Home</a>
</body>
</html>