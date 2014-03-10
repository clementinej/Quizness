<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<%@ page errorPage="../site/404.jsp" %>
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
         <h3>Create a Question</h3>
         <p>When you're done, click "Add Question" to return to creating your quiz.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
             <div id="img_url" class="terms">
                  <input type="name" name="question_text" placeholder="Image url" autofocus/>
         </div>
         <h3>Solutions</h3>
               <table id="contents" class="inputs">
                  <th width="500">Solution Text</th>
                  <th>Score</th>
                  <tbody>
                     <tr>
                        <td><input type="name" name="correct_answer_text" style="width:100%" placeholder="Solution"></td>
                        <td><input type="name" name="correct_answer_score" style="width:100%"/></td>
                     </tr>
                  </tbody>
                  <tfoot>
                     <tr>
                        <td align="center" class="terms"><a href="#" id="new_synonym">Add A Synonym For Your Solution</a></td>
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
               <input name="question type" type="hidden" value="4"/>
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
