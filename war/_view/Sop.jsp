<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
      <title>Create SOP</title>
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
      </head>
<body>

<form action="${pageContext.servletContext.contextPath}/Sop.jsp" method="post">
SOP ID Number: ${model.sopIdNumber} <br>
SOP Title: ${model.sopName}<br>
Author of SOP: ${model.sopAuthor}<br>
Author ID Number: ${model.authorIDnumber}<br>
Priority (1-10): ${model.priority}<br>
Revision Number: ${model.revision}
</form>
<form action="/action_page.php">
  Select Files Associated with SOP: <input type="file" name="files" multiple>
  <input type="submit">
</form>




</body>
</html>