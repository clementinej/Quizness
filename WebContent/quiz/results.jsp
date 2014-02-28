<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link href="../css/default.css" rel="stylesheet" type="text/css"  />
      <link href="../css/hexaflip.css" rel="stylesheet" type="text/css">
      <link href="../css/results.css" rel="stylesheet" type="text/css">
   </head>
   <%@page import="model.*", import="java.util.ArrayList"%>
   <%
      //Get the user
      User user = (User)session.getAttribute("currUser");
      //Get the quiz try by id
      QuizTry quizTry = QuizTry.getQuizTry(Integer.parseInt(request.getParameter("quizTryID")));
      //Only let users see their own quiz results
      if(quizTry.getUserID() != user.getUserID() && !user.isAdmin()) {
      	return;
      }
      //Only let users see completed quizes
      if(quizTry.isInProgress()) {
      	return;
      }
      %>
   <body>
      <div class="container">
         <header class="clearfix">
         </header>
         <div class="main">
            <div id="cubes" class="demo"></div>
         </div>
         <form method="post" action="/SaveResults">
            <%
               double score = quizTry.getScore();
               double time = quizTry.getTime();
               ArrayList<String[]> responses = quizTry.getResponses();
               %>
            <!-- Potentially do a switch statement on score and give them different messages 
               depending on how well they did.  -->
            <div id="score">
               <p>Your score is [Score]</p>
               <p>It took you [Insert elapsed time] seconds to complete this quiz</p>
            </div>
         </form>
      </div>
      <script src="../js/hexaflip.js"></script>
      <script>
         var hexa,
             text1 = 'SCORE'.split(''),
             settings = {
                 size: 150,
                 margin: 12,
                 fontSize: 100,
                 perspective: 450
             },
             makeObject = function(a){
                 var o = {};
                 for(var i = 0, l = a.length; i < l; i++){
                     o['letter' + i] = a;
                 }
                 return o;
             },
             getSequence = function(a, reverse, random){
                 var o = {}, p;
                 for(var i = 0, l = a.length; i < l; i++){
                     if(reverse){
                         p = l - i - 1;
                     }else if(random){
                         p = Math.floor(Math.random() * l);
                     }else{
                         p = i;
                     }
                     o['letter' + i] = a[p];
                 }
                 return o;
             };
         
         document.addEventListener('DOMContentLoaded', function(){
             hexa = new HexaFlip(document.getElementById('cubes'), makeObject(text1), settings);
         
             setTimeout(function(){
                 hexa.setValue(getSequence(text1, true));
             }, 0);
         
             setTimeout(function(){
                 hexa.setValue(getSequence(text1));
             }, 1000);
         
             setTimeout(function(){
                 setInterval(function(){
                     hexa.setValue(getSequence(text1, false, true));
                 }, 3000);
             }, 5000);
         }, false);
      </script>
   </body>
</html>