<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, model.*" %>
<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="../css/style.css" />
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
   </head>
   <body>
      <%
         User u = (User) session.getAttribute("current user");
         int userID = u.getUserID();
         String name = u.getUserName();
         %>
      <!--top bar -->
      <div class="top">
         <span class="header-link"><a href="../home.jsp">Home</a></span>
         <span class="header-link"><a href="../create-quiz.jsp">Create Quiz</a></span>
         <span class="header-link"><a href="../social/compose-mail.jsp">Compose </a></span>
         <span class="header-link"><a href="../profile.jsp">Profile</a></span>
         <span class="header-link"><a href="../site/admin.jsp">Admin</a></span>
         <span class="header-link"><a href="../inbox.jsp">Inbox</a></span>
         <span class="right">Welcome to Quizness, <%=name %></span>
      </div>
      <div class="container">
         <div>
            <form action="../QuizSearchServlet" method="post" id="signup">
               <div class="header">
                  <h3>Quiz Search</h3>
               </div>
               <div class="sep"></div>
               <div class="inputs">
                  <div>
                     <input type="name" name="search" placeholder="Search for quizzes, friends, etc."></input>
                     <div>
                        <p>Order by:</p>
                        <br>
                        <label><input type="radio" name="order" value="popular"/> Popular</label>
                        <label><input type="radio" name="order" value="recent"/> Recent</label>
                     </div>
                     <input id="submit" type="submit" value="Search!">
                  </div>
               </div>
            </form>
         </div>
         <br>
         <div>
            <form action="../UserSearchServlet" method="post" id="signup">
               <div class="header">
                  <h3>User Search</h3>
               </div>
               <div class="sep"></div>
               <div class="inputs">
                  <div>
                     <input type="name" name="search" placeholder="Search for quizzes, friends, etc."></input>
                     <input id="submit" type="submit" value="Search!">
                  </div>
               </div>
            </form>
         </div>
      </div>
   </body>
</html>