<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
</head>
<%--
   User currUser = (User) session.getAttribute("current user");
   int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
   Quiz currQuiz = Quiz.getQuiz(currQuizID);
   if(!currUser.isAdmin() && currQuiz.getCreatorID() != currUser.getUserID()) return;
   --%>
<body>
   <div class="container">
      <form method="post" action="../../CreateServlet" id="signup">
         <div class="header">
            <h3>Create a Question</h3>
            <p>Click "Add Question!" to return to creating your quiz.</p>
         </div>
         <div class="sep"></div>
         <div class="inputs">
            <div>
               <input type="name" name="question_text" style="width:500px;height:50px" placeholder="Question Text">
            </div>
            <h3>Solutions</h3>
            <table id="contents" class="inputs">
                  <th style="width:400px">Solution Text</th>
                  <th>Score</th>
                 
                  <tbody>
                     <tr>
                        <td><input type="name" name="correct_answer_text" style="width:100%" placeholder="Solution"></td>
                        <td><input type="name" name="correct_answer_score" style="width:100%" /></td>
                     </tr>
                    </tbody>
                                      <tfoot>
                     <tr>
                        <td align="center" class="terms"><a href="#" id="new_synonym">Add A Synonym For Your Solution</a></td>
                     </tr>
                  </tfoot>
             
               </table>
               <input name="intent" type="hidden" value="add question"/>
			<input name="question type" type="hidden" value="2"/>
<br><input id="submit" type="submit" value="Add Question!"><!-- Store new quiz in database -->
         </div>
      </form>
   </div>
<script type="text/javascript">
         var syn_button = document.getElementById("new_synonym");
         syn_button.addEventListener("click", function() {
         	var body = document.getElementById("contents");
         	body.insertAdjacentHTML('beforeend',	'<tr>' +
         			'<td><input type="name" name="correct_answer_text" style="width:100%" placeholder="Synonym"></td>' +
         			'</tr>');
         	body.appendChild(newChild);
         	});   
      </script>
</body>