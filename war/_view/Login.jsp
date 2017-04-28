<?php
// Start the session
session_start();
?>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
	<title> Login </title>
	<style type = "text/css">
	.error {
			color: red;
		}
		
	td.label{
		text-align: right;
	}
	
	button{
		margin-top: 2px;
	}
	
	#PageName {
		color: white;
		font-size: 250%;
		text-align: center;
		background-color: darkblue;
		font-variant: small-caps;
	}
	
	#logon {
    	margin: 50px auto;
		border: 1px solid darkblue;
		width: 400px;
		padding: 10px;
	}

	</style>
	
	</head>
	
	<body>			
	</php
	$_SESSION["username"];
	$_SESSION["userID"];
	$_SESSION["firstName"];
	$_SESSION["lastName"];
	$_SESSION["type"];
	$_SESSION["sessionid"];
	$_SESSION["emailAddress"];
	?>
	
		<c:if test="${! empty errorMessage }">
			<div class = "error"> ${errorMessage}</div>
		</c:if>
		<form action = "${pageContext.servletContext.contextPath}/Login" method = "post">
		<div id = "PageName"> Welcome to the Compliance Training Monitor</div>
		<div id = "logon">
		<table>
			<tr>
					<td class = "label"> Username: </td>
					<td><input type="text" name="username" size="12" value="${username}" /></td>
			</tr>
			<tr>
					<td class = "label"> Password: </td>
					<td><input type="password" name="password" size="12" value="${password}" /></td>
			</tr>
			</table>
			<td><input type = "Submit" name = "submit" value = "Login" /> </td>
			
				<input type="hidden" name="userName" value="user.getUsername">			
				<input type="hidden" name="sessionid" value="sessionid.getSessionid">
			</div>
		</form>
	</body>
</html>		
