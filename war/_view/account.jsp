<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- Priority #3 -->

<html>
	<head>
		<title>Your Account</title>
		<style type = "text/css">
		.error {
			background-color: red;
			font-color:black;
		}
		body{
           font-family: Verdan;
          
          background-color: lightblue
          }"src/ycp/cs320/teamProject/servlets/AccountServlet.java"
          h1{
          font-size: 40px;
          }
          h3{
          font-size: 15px;
          }
		
		</style>
	</head>
	
	<body>
	<h3> Name: <%=request.getAttribute("LastName")%>, <%=request.getAttribute("FirstName")%></h3>
	<h3> Employee ID: <%=request.getAttribute("UserID")%></h3>
	<h3> Active Username: <%=request.getAttribute("User.username")%></h3>
	<h3> Email: <%=request.getAttribute("Email")%></h3>
	
	
	</body>
</html>