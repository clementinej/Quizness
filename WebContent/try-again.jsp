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
                        <div>
            <%
			for(int i = 1; i < 9; i++) {
				if(i == 5) {%>
					<br>
				<%} %>
			<img src="/Quizness/img/<%=i %>.png" width="150" height="150">
			<input id="pic-<%=i %>" name="pic<%=i %>" value="<%=i%>.png" type="radio">
			<%}%>
            </div>
               <input id="submit" type="submit" value="Create Account" />
            </div>
         </form>
      </div>
   </body>
</html>