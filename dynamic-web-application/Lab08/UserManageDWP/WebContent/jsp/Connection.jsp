<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="database.connection.Connect" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User management Dynamic Web Appliaction</title>
</head>
<body>
<%
Connect sc =  new Connect();
sc.connect();
sc.createTables();
%>

  <label for="fname">First name:</label>
  <input type="text" id="fname" name="fname"><br><br>
  <label for="lname">Last name:</label>
  <input type="text" id="lname" name="lname"><br><br>
  <label for="lname">User number:</label>
  <input type="text" id="number" name="lname"><br><br>
  <input type="submit" value="Submit">

<p>Click the button to add data to database.</p>

<button onclick="myFunction()">Add user</button>
<script>

function myFunction() {
	
  fname=document.getElementById("fname").value;
  lname=document.getElementById("lname").value;
  number=document.getElementById("number").value;
  <%
   %>
}
</script>

</body>
</html>