<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="/Quizness/css/style.css" />
      <link rel="stylesheet" type="text/css" href="/Quizness/css/style_login.css" />
      <link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300|Montserrat' rel='stylesheet' type='text/css' />
   </head>
   <body>
      <div class="container-main">
         <section class="main">
            <h2 class="cs-text" id="cs-text">Correct!</h2>
         </section>
      </div>
      <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
      <script type="text/javascript" src="/Quizness/js/jquery.lettering.js"></script>
      <script>
         $(document).ready(function() {
         	$("#cs-text").lettering().children('span').wrap('<span />');
         });
      </script>
      <div style="text-align:center" id="error-box">
      <h2>You got it </h2>
       <form class="form-1" action="" method="post">
       <%String quizID = request.getParameter("quiz_id");
         String quizTryID = request.getParameter("quiz try id");%>
       <a id="submit" href="show-quiz.jsp?quiz_id=<%=quizID %>&quiz try id=<%=quizTryID%>" value="Back">Back to Quiz</a>
      </form>
      </div>
   </body>
</html>