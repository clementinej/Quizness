<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
</head>
<body>
   <div class="container">
      <form method="post" action="../../CreateServlet" id="signup">
      
      <%
      String intent = request.getParameter("intent");
      %>
         <div class="header">
            <h3>Create a Question - Multiple Choice</h3>
            <p>When you're done, click "Add Question" to return to creating your quiz.</p>
         </div>
         <div class="sep"></div>
         <div class="inputs">
            <div>
               <input type="name" name="question_text" style="width:500px;height:50px" placeholder="Question Text">
            </div>
            <br>
            <h3>Correct Solution</h3>
            <table id="correct_contents" class="inputs">
               <th width="500">Solution Text</th>
               <th>Score</th>
               <tbody>
                  <tr>
                     <td><input type="name" name="correct_answer_text" style="width:100%" placeholder="Solution"></td>
                     <td><input type="name" name="correct_answer_score" style="width:100%"/></td>
                  </tr>
               </tbody>
            </table><br>	
            <h3>Incorrect Solutions</h3>
            <table id="incorrect_contents" class="inputs">
               <th width="500">Incorrect Solution Text</th>
               <tbody>
                  <tr>
                     <td><input type="name" name="incorrect_answer" style="width:100%" placeholder="Incorrect Solution"></td>
                  </tr>
               </tbody>
               <tfoot>
                  <tr>
                     <td align="center" class="terms"><a href="#" id="new_wrong_answer">Add Another Incorrect Solution</a></td>
                  </tr>
               </tfoot>
            </table>
            
    <%  if(intent.equals("add to existing quiz")) {
    		int quizID = Integer.parseInt(request.getParameter("quizID"));
    		System.out.println("In question-answer: Quiz = " + quizID);
    %>
    		<input type="hidden" name="quiz_id" value="<%=quizID%>"/>
    <%
      }
      %>
            <input name="intent" type="hidden" value="<%=intent%>"/>
            <input name="question type" type="hidden" value="3"/>
            <br><input id="submit" type="submit" value="Add Question!"><!-- Store new quiz in database -->
         </div>
   </form>
   </div>
   <script type="text/javascript">
      var button = document.getElementById("new_wrong_answer");
      button.addEventListener("click", function() {
      	var wrong = document.getElementById("incorrect_contents");
      	wrong.insertAdjacentHTML('beforeend','<tr>' +
      			'<td><input type="name" name="incorrect_answer" style="width:100%" placeholder="Solution"></td>' +
      			'</tr>');
      	});
   </script>
</body>