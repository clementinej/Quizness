<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/style_login.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
<%
String resultType = (String) session.getAttribute("resultType");
if(resultType.equals("user")) {
	ArrayList<User> users = (ArrayList<User>) session.getAttribute("userResults");
	for(int i = 0; i < users.size(); i++) {
		User u = users.get(i);
		%>
		<p><%=u.getUserName() %><p><br>
		<% 
	}
} else {
	ArrayList<Quiz> quizzes = (ArrayList<Quiz>) session.getAttribute("quizResults");
	for(int i = 0; i < quizzes.size(); i++) {
		Quiz q = quizzes.get(i);
		%>
		<p><%=q.getTitle() %></p>
		<% 
	}
}
%>

</body>
</html>