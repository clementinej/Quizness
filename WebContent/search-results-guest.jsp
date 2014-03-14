<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@page import="model.*, java.util.*" %>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="css/style.css" />
      <link rel="stylesheet" type="text/css" href="css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
   </head>
   <body>
      <%
         String name = "Guest User"; 
             
             %>
      <!--top bar -->
      <div class="top">
         <span class="header-link"><a href="home-guest.jsp">Home</a></span>
         <span class="header-link"><a href="index.jsp">Log In</a></span>
         <span class="header-link"><a href="create-account.jsp">Create a New Account</a></span>
         <span class="right">Welcome to Quizness, <%=name %></span>
      </div>
      <div class="container">
         <h1>Search Results</h1>
         <h3>Click the links below to explore quizzes</h3>
         <%
            String resultType = (String) session.getAttribute("resultType");
            if(resultType.equals("user")) {
            	ArrayList<User> users = (ArrayList<User>) session.getAttribute("userResults");
            	for(int i = 0; i < users.size(); i++) {
            		User u = users.get(i);
            		%>
         <a href="profile.jsp?id=<%=u.getUserID() %>">
            <p><%=u.getUserName() %></p>
         </a>
         <br>
         <% 
            }
            } else {
            ArrayList<Quiz> quizzes = (ArrayList<Quiz>) session.getAttribute("quizResults");
            for(int i = 0; i < quizzes.size(); i++) {
            	Quiz q = quizzes.get(i);
            	%>
         <a href="../Quizness/quiz/quiz-summary-guest.jsp?quiz_id=<%=q.getQuizID()%>"><p><%=q.getTitle() %></p></a>
         <% 
            }
            }
            %>
      </div>
   </body>
</html>
