<%@ page errorPage="../site/404.jsp" %>
<!DOCTYPE html>
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="css/style_login.css" />
      <title>Try Again</title>
   </head>
   <body>
      <div class="container">
         <form action="CreateAccountServlet" method="post" id="signup">
            <div class="header">
               <h3>Account Already In Use</h3>
               <p>Please think of more creative identifying info.</p>
            </div>
            <div class="inputs">
               <div class="sep"></div>
               <input type="email" name="email" placeholder="email"/>
               <input type="name" name="password" placeholder="password"/>
               <input type="name" name="name" placeholder="name"/>
               <input type="name" name="aboutMe" placeholder="about me" />
               <input type="name" name="location" placeholder="location" />
               <input id="submit" type="submit" value="Create Account" />
            </div>
         </form>
      </div>
   </body>
</html>