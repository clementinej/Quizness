<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*" %>
<head>
   <link rel="stylesheet" type="text/css" href="../../css/style_login.css" />
</head>
<body>
   <div class="container">
   <form method="post" action="../../CreateServlet" id="signup">
      <div class="header">
         <h3>Create a Question</h3>
         <p>When you're done, click "Add Question" to return to creating your quiz.</p>
      </div>
      <div class="sep"></div>
      <div class="inputs">
      	 <div>
                  <input type="name" name="question_text" style="width:500px;height:50px" placeholder="Question Text">
               </div>
               <div id="img_url" class="terms">
                  <input type="name" name="question_image" placeholder="Image url" autofocus/>
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
                        <td align="center" class="terms"><a href="#" id="new_option">Add Another Solution</a></td>
                     </tr>
                     <tr>
                        <td align="center" class="terms"><a href="#" id="new_synonym">Add A Synonym</a></td>
                     </tr>
                  </tfoot>
               </table>
               <input name="intent" type="hidden" value="add question"/>
               <input name="question type" type="hidden" value="1"/>
         <br><input id="submit" type="submit" value="Add Question!"><!-- Store new quiz in database -->
      </div>	
   </form>
   </div>
    <script type="text/javascript">
         var button = document.getElementById("new_option");
         button.addEventListener("click", function() {
             var body = document.getElementById("contents");
             body.insertAdjacentHTML('beforeend', '<tr>' +
                     '<td><input type="name" name="correct_answer" style="width:100%" placeholder="Solution"></td>' +
                     '<td><input type="name" name="correct_answer_score" style="width:100%" /></td>' +
                 '</tr>');
             body.appendChild(newChild);
             });
         var syn_button = document.getElementById("new_synonym");
         syn_button.addEventListener("click", function() {
         	var body = document.getElementById("contents");
         	body.insertAdjacentHTML('beforeend',	'<tr>' +
         			'<td><input type="name" name="correct_syn" style="width:100%" placeholder="Synonym"></td>' +
         			'<td><input type="name" name="correct_syn_score" style="width:100%" /></td>' +
         			'</tr>');
         	body.appendChild(newChild);
         	});   
      </script>
</body>
