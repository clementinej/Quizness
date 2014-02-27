<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../css/style_login.css" />
</head>
<body>
   <div class="container">
      <form method="post" action="../CreateServlet" id="signup">
         <div class="header">
            <h3>Compose Message</h3>
            <p>Brag about how good your are at quizzes.</p>
         </div>
         <div class="sep"></div>
         <div class="inputs">
            <input type="name" name="recipient" placeholder="Recipient"></input>
            <input type="name" name="description" style="width:500px;height:400px"></input>
            <input type="name" name="challenge" placeholder="Recipient"></input>
            <input id="submit" type="submit" value="Send">
         </div>
      </form>
   </div>
</body>
<script></script>