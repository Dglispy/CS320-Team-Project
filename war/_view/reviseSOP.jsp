<<<<<<< HEAD
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
	<style type = "text/css">
		.error {
			color: red;
		}
		
		</style>
</head>

<body>

<form action="${pageContext.servletContext.contextPath}/CreateAccount" method="post">
SOP ID: <input type="text" name="sop_Id"><br>
SOP Version: <input type="text" name="version"><br>
New SOP Version: <input type="text" name="newVersion"><br>
<td><input type = "Submit" name = "submit" value = "Submit" /> </td>
<td><input type = "Submit" name = "index" value = "Index" /> </td>
<input type="hidden" name="sessionid" value="sessionid.getSessionid">
</form>


</body>
</html>
=======
<html>
	<head>
      <title>Revise SOP</title>
		<style type = "text/css">
          body{
           font-family: Verdan;
          
         background: -moz-linear-gradient(0deg, rgba(0,255,255,1) 0%, rgba(255,255,255,1) 37%, rgba(0,128,128,1) 100%); /* ff3.6+ */
background: -webkit-gradient(linear, left top, right top, color-stop(0%, rgba(0,255,255,1)), color-stop(37%, rgba(255,255,255,1)), color-stop(100%, rgba(0,128,128,1))); /* safari4+,chrome */
background: -webkit-linear-gradient(0deg, rgba(0,255,255,1) 0%, rgba(255,255,255,1) 37%, rgba(0,128,128,1) 100%); /* safari5.1+,chrome10+ */
background: -o-linear-gradient(0deg, rgba(0,255,255,1) 0%, rgba(255,255,255,1) 37%, rgba(0,128,128,1) 100%); /* opera 11.10+ */
background: -ms-linear-gradient(0deg, rgba(0,255,255,1) 0%, rgba(255,255,255,1) 37%, rgba(0,128,128,1) 100%); /* ie10+ */
background: linear-gradient(90deg, rgba(0,255,255,1) 0%, rgba(255,255,255,1) 37%, rgba(0,128,128,1) 100%); /* w3c */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#00ffff', endColorstr='#008080',GradientType=1 ); /* ie6-9 */


          h1{
          font-size: 40px;
          }
          h3{
          font-size: 15px;
          }
        </style>
      </head>
<body>
<h1 style="text-align: center;"><span style="text-decoration: underline;">Revise SOP</span></h1>
  

<h3>Priority (1-10): <input name="priority" type="text" /><br /></h3>
  <h3>Revision Number : </h3>
  
<h3>Select Files Associated with SOP: <input multiple="multiple" name="files" type="file" /> </h3>
 <h3> <input type="submit" /></h3>
  </body>
>>>>>>> refs/remotes/Devin-remote/master
