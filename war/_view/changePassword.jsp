<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<title>Change Password</title>
		<style type = "text/css">
          body{
           font-family: Verdan;
          
          background-color: lightblue
          }
          h1{
          font-size: 40px;
          }
          h3{
          font-size: 15px;
          }
        </style>
<body>

<form action="${pageContext.servletContext.contextPath}/changePassword" method="post">

Old Password: <input type="password" name="oldPassword">   <br>
New Password: <input type="password" name="password">
<br>Re-Enter <br>
New Password: <input type="password" name="password2">
				<td><input type = "Submit" name = "submit" value = "Submit" /> </td>
				<td><input type = "Submit" name = "Index" value = "Index" /> </td>
				
</form>

<p><b>Note:</b> The characters in a password field are masked (shown as asterisks or circles).</p>

</body>
</html>