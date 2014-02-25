<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
   <head>
      <link rel="stylesheet" href="../css/style.css" type="text/css" media="screen"/>
      <link rel="stylesheet" href="../css/component.css" type="text/css" media="screen"/>
   </head>
   <style>
      *{
      margin:0;
      padding:0;
      }
      body{
      font-family: 'Lato', Arial, sans-serif;
      font-size: 16px;
      font-style: italic;
      font-weight: normal;
      letter-spacing: normal;
      background: #f0f0f0;
      }
      #content{
      background-color:#fff;
      width:750px;
      padding:40px;
      margin:0 auto;
      border-left:30px solid #1D81B6;
      border-right:1px solid #ddd;
      -moz-box-shadow:0px 0px 16px #aaa;
      }
      #content h1{
      font-family: 'Lato', Arial, sans-serif;
      color:#1D81B6;
      font-weight:normal;
      font-style:normal;
      font-size:56px;
      text-shadow:1px 1px 1px #aaa;
      }
      #content h2{
      font-family: 'Lato', Arial, sans-serif;
      font-size:34px;
      font-style:normal;
      background-color:#f0f0f0;
      margin:40px 0px 30px -40px;
      padding:0px 40px;
      clear:both;
      float:left;
      width:100%;
      color:#aaa;
      text-shadow:1px 1px 1px #fff;
      }
   </style>
   <body>
      <div id="content">
         <a class="back" href=""></a>
         <span class="scroll"></span>
         <h2>Question</h2>
         <form method="post" action="/CreateServlet">
            <p>Enter either a question in text or a picture in the image url field. <br> If you leave the Image URL
               field blank, no image will be displayed.
            </p>
            <br>
            <table class="table1">
               <tr>
                  <th>Question Text</th>
                  <td>
                     <textarea name="question_text" style="width:500px;height:80px;"></textarea>
                  </td>
               </tr>
               <tr>
                  <th>Image URL</th>
                  <td><input name="question_image" type="text" value="" style="width:500px"></td>
               </tr>
            </table>
            <h2>Solutions</h2>
            <table id="contents" class="table1">
               <tr>
                  <th width="500">Solution Text</th>
                  <th>Score</th>
               </tr>
               <tbody>
                  <tr>
                     <td><input type="text" name="correct_answer_key" style="width:100%" value=""></td>
                     <td><input type="text" name="correct_answer_score" style="width:100%" value="" /></td>
                  </tr>
               </tbody>
               <tfoot>
                  <tr>
                     <td align="center" colspan="2"><a href="#" id="new_option">Add Another Solution</a></td>
                  </tr>
               </tfoot>
            </table>
            <input type="submit" class="btn btn-4 btn-4a" value="Create Question"/>
         </form>
         </table>
      </div>
   </body>
   <script type="text/javascript">
      var button = document.getElementById("new_option");
      button.addEventListener("click", function() {
          var body = document.getElementById("contents");
          body.insertAdjacentHTML('beforeend', '<tr>' +
                  '<td><input type="text" name="correct_answer_key" style="width:100%"></td>' +
                  '<td><input type="text" name="correct_answer_score" value="0" style="width:100%" /></td>' +
              '</tr>');
          body.appendChild(newChild);
          });
      
   </script>
</html>