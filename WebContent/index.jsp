<%@ page errorPage="../site/404.jsp"  %>
<%@ page import="java.util.*, control.*, model.*" %>
<%@ page errorPage="../site/404.jsp"  %>
<!DOCTYPE html>
<html lang="en">
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
   		boolean isCorrectUserID = true;
   		Cookie[] cookies = request.getCookies();  		
   		if(cookies != null){
   			for(Cookie cookie : cookies){
   				if(cookie.getName().equals("id")){
   					int userID = CookieManager.getUserID(cookie.getValue());
   					if(userID != -1){
   						User user = User.getUser(userID); 
   						session.setAttribute("current user", user); 
   						session.setAttribute("current_session_id", cookie.getValue());
   						RequestDispatcher dispatch = request.getRequestDispatcher("home.jsp"); 
   						dispatch.forward(request, response);
   					}
   				}
   			}
   		}
   
        if(request.getParameter("invalid_login") != null) {
        	System.out.println("Invalid Login");%>
        <div id="invalid"><h1>Invalid Login.  Please Try Again.</h1></div>
        <%}%>
      <div class="container_main">
         <section class="main">
            <h2 class="cs-text" id="cs-text">Quizness</h2>
         </section>
      </div>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
      <script type="text/javascript" src="js/jquery.lettering.js"></script>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>
      <h2 id="create-account"><a href="create-account.jsp">Create New Account</a></h2>
      <h2 id="create-account"><a href="home-guest.jsp">Continue As a Guest</a></h2>
      <form class="form-1" action="LoginServlet" method="post">
         <p class="field">
            <input type="text" name="login" placeholder="Username or email">
            <i class="icon-user icon-large"></i>
         </p>
         <p class="field">
            <input type="password" name="password" placeholder="Password">
            <i class="icon-lock icon-large"></i>
         </p>
         <input type="checkbox" name="remember" value="remember" ><span style="color:grey; font-weight:600;"> Remember Me</span><br>
         <p class="login">
            <button type="submit" name="submit"><i class="icon-arrow-right icon-large"></i></button>
         </p>
      </form>
   </body>
</html>