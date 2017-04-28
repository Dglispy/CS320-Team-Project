<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<body>

<form action="${pageContext.servletContext.contextPath}/MainPage" method="post">
<div>


First Name: ${user.firstName}<br>
Last Name: ${user.lastName}<br>
E-Mail: ${user.emailAddress}<br>

</div>
<<<<<<< HEAD
<div>
/--This is where we need to list History --/
<div>
<tr>
			<input name="changePW" type="submit" value="Change Password" />
			</tr>
=======
>>>>>>> refs/remotes/Devin-remote/master

</form>
<p>
</p>
</body>
</html>
