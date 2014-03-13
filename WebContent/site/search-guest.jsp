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
      <style>
         .link {
         color:#fffff;
         font-weight:bold;
         text-align:center;
         }
         .links {
         padding-left:40px;
         }
         .header-link {
		padding-left:50px;
		font-weight:bold;	
		}
		.right {
		color:#fffff;
		float:right;
		margin-right:25px;
		font-weight:bold;
		font-size:100%;
		}
      </style>
   </head>
   <body>
      <%
         String name = "Guest User";
         %>
     <!--top bar -->
      <div class="top">
      	<span class="header-link"><a href="../home-guest.jsp">Home</a></span>
         <span class="header-link"><a href="../index.jsp">Log In</a></span>
         <span class="header-link"><a href="../create-account.jsp">Create a New Account</a></span>
         <span class="right">Welcome to Quizness, <%=name %></span>
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
               <input name="guest" type="hidden" value="guest">
               <input id="submit" type="submit" value="Search!">
            </div>
      </form>
      </div>
      <br>
      </div>
   </body>
</html>