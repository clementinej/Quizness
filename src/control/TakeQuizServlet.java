package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

import java.util.*;

/**
 * Servlet implementation class TakeQuizServlet
 */
@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int QUESTION_RESPONSE = 1;
	public static final int FILL_IN_THE_BLANK = 2;
	public static final int MULTIPLE_CHOICE = 3;
	public static final int PICTURE_RESPONSE = 4;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ignore
	}

	/**
	 * The one page implementation of taking a quiz.  Similar to the shopping cart page of the shopping cart assignment.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
//		HttpSession session = request.getSession();
//		User currUser = (User) session.getAttribute("current user");
//		int currQuizID = Integer.parseInt(request.getParameter("quiz_id"));
//		Quiz currQuiz = null;
//		try {
//			currQuiz = Quiz.getQuiz(currQuizID); } catch (Exception e) { e.printStackTrace();
//		}
//		
//		try {
//			if(currUser == null) {
//				redirectToPage("LoginServlet", request, response);
//				return;
//			}		
//		if(currQuiz.getCreatorID() == currUser.getUserID()) {
//				//"Cannot Take Your Own Quiz!" page
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Cannot Take Your Own Quiz!</title>");
				out.println("</head>");
				out.println("<body>");
		//		out.println("<jsp:include page=\"question-answer.jsp\" />");
				out.println("<h1>Sorry you can't take your own quiz.</h1>");
				out.println("</body>");
				out.println("</html>");	
//				return;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		String title = currQuiz.getTitle();
//		String description = currQuiz.getDescription();
//		//boolean timed = currQuiz.getTimed();//extension to worry about later
//
//		ArrayList<Question> questions = currQuiz.getQuestions();
//		boolean multiPage = currQuiz.hasMultiplePages();
//		if(!multiPage) {
//			//show whole quiz on one page
//			out.println("<!DOCTYPE html>"); 
//			out.println("<head>"); 
//			out.println("<head>");
//			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style_login.css\" />");
//			out.println("<title>" + title + "</title>"); 
//			out.println("</head>"); 
//			out.println("<body>"); 
//			out.println("<p>"+ description +"<p>");
//			out.println("<div class=\"container\">");
//			out.println("<form method=\"post\" action=\"\" id=\"signup\">");
//			out.println("<div class=\"header\">");
//			out.println("<h3>Let's Get Started!</h3>");
//			out.println("<p>Best of luck</p>");
//			out.println("</div>");
//			out.println("<div class=\"sep\"></div>");
//			out.println("<div class=\"inputs\">"); 
//			out.println("<ul>");
//			out.println("<form action=\"results.jsp\" method=\"get\">");
//		
//		
//			for(int qIndex = 0; qIndex < questions.size(); qIndex++) {
//				//display question
//				
//				//get question materials
//				Question q = questions.get(qIndex);
//				int nextQuestionType = q.getQuestionType();
//				String questionText = q.getQuestion(); 
//				ArrayList<Set<String>> answers = q.getAnswer();
//				double maxPoints = q.getMaxPoints();
//				
//				//Print out question number
//				int num = qIndex + 1;
//				out.println("<p>" + num +"</p>");
//				
//				switch(nextQuestionType) {
//				case QUESTION_RESPONSE: 
//					out.println("<p>"+ questionText +"</p>");
//					out.println("<p><input type=\"text\" name=\"answer"+ qIndex +"\" /></p>");
//					//show question text
//					//give a text box to submit answer
//				
//				case FILL_IN_THE_BLANK:
//					int blankIndex = questionText.indexOf('_');
//					int lastBlankIndex = questionText.lastIndexOf('_');
//					String beforeBlank = questionText.substring(0, blankIndex);
//					String afterBlank = questionText.substring(lastBlankIndex);
//					
//					out.println("<p>"+ beforeBlank);
//					out.println("<input type=\"text\" name=\"answer"+ qIndex +"\" />");
//					out.println(afterBlank + "</p>");
//					
//					//show sentence with blank in it
//					//find the "_" in the text
//					//replace that with a text box
//				
//				case MULTIPLE_CHOICE: 
//					out.println("<p>"+ questionText +"</p>");
//			        out.println("<div class=\"checkboxy\">");
//		            out.println("<input name=\"checky\" type=\"checkbox\" name=\"multiple_pages\" value=\"1\"> </div>");
//					
//					//show question
//					//mix up the correct answer with the other choices in a set
//				
//				case PICTURE_RESPONSE:
//					//FIRST OF ALL, NEED TO WRITE THIS .jsp
//					//show picture
//					//give a text box to submit answer
//				}
//			}		
//			out.println("</div>");
//			out.println("</form>");
//			out.println("</div>");
//			out.println("</body>");
//		} else {
//			//show question one at a time, utilize switch table above
//			//forward back to TakeQuizServlet for next question.
//			//maybe have a current quiz attribute.
//		}
	}

	
	private void redirectToPage(String pageName, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher dispatch = request.getRequestDispatcher(pageName); 
			dispatch.forward(request, response); 		
		}
	
}
