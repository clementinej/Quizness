<%@page contentType="text/html" pageEncoding="UTF-8"%>
     <head>
     <link rel="stylesheet" type="text/css" href="css/style_login.css" />
     </head>

<body>
<div class="container">
	<% if(request.getParameter("bad_email") != null) { %>
	<div class="error_message">
		<strong>Account Already Active</strong> That email address already has and account associated with it.
		Try another email address.
	</div>
	<% } %>
	<% if(request.getParameter("missing") != null) { %>
	<div class="error_message">
		<strong>Required Fields Left Blank</strong> You're missing something! 
		Try again by entering all the info we asked for.
	</div>
	<% } %>
	<form method="post" action="CreateAccountServlet" id="signup">
	 <div class="header">       
            <h3>Sign Up</h3>
            <p>Become part of the end-all, be-all online quiz community</p>    
        </div>
         <div class="sep"></div>
         <div class="inputs">
            <input type="email" name="email" placeholder="e-mail" autofocus />
            <input type="password" name="password" placeholder="Password" />
            <input type="name" name="name" placeholder="name"/>
            <div class="checkboxy">
                <input name="cecky" id="checky" value="1" type="checkbox" /><label class="terms">I understand what I'm getting myself into</label>
            </div>
            <%--Option 1: Have a less pretty button that logs in automatically--%>
            <input type="submit" value="LET'S DO THIS THING" />
            <%--Option 2: Find a way for this line below to be sent to the CreateAccountServlet 
            <a id="submit" href="index.html" value="Create Account">LET'S DO THIS THING!</a>
            --%>
        </div>
	</form>
	</div>
</body>
