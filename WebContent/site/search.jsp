<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>Search</title>
   </head>
   <body>
   
      <div class="container">
      
      <div>
      <form action="/QuizSearchServlet" method="post" id="signup">
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
                  <label><input type="radio" name="order" id="popular"/> Popular</label>
                  <label><input type="radio" name="order" id="recent"/> Recent</label>
               </div>
               <input id="submit" type="submit" value="Search!">
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
      </form>
      </div>
      
      </div>
   </body>
</html>