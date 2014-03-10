<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.*" %>
<%@page import="java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Read Mail</title>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />

</head>
<body>
<%
int messageID = Integer.parseInt(request.getParameter("msg_id"));
Message m = Message.getMessage(messageID);
int fromID = m.getFromID();
String subject = m.getSubject();
String body = m.getBody();
Date sent = m.getSentAt();
java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat();
%>

<%
String from = "Dummy user";
%>
<div class="container">
   <form id="signup">
      <div class="header">
         <h2><%=subject%></h2>
         <h4>From <%=from %></h4>
         <p>Sent at <%=formatter.format(sent) %></p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
 		<p><%=body %></p><br>
		<a href="inbox.jsp">Back to Inbox</a>
      </div>
      </form>
   </div>
</body>
</html>