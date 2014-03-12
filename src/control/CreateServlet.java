package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;


/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final int QUESTION_RESPONSE = 1;
	public static final int FILL_IN_THE_BLANK = 2;
	public static final int MULTIPLE_CHOICE = 3;
	public static final int PICTURE_RESPONSE = 4;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ignore
	}

	/**
	 * First obtains the userIntent ("add question" or "create quiz") and the current questionList.  
	 * If the intent is to add a question, the question is added to the questionList.
	 * If the intent is to create a quiz, a quiz is saved in the database.   It is constructed by a 
	 * newly generated quizID and the current questionList.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession session = request.getSession();	
		ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute("question list");	
		User currUser = (User) session.getAttribute("current user");
		System.out.println("In CreateServlet, user = " + currUser.getUserName());
		int creatorID = QuestionHandler.getUserID(currUser);	
		if(creatorID == -1) return; //should redirect to a "You are not logged in" page	
		if(request.getParameter("save_title_and_description") != null) {
			saveTitleAndDescription(request, session, response);
			return;
		}	
		if(QuestionHandler.getUserIntent(session, request).equals("add question")) {
			handleQuestionJob(session, request, response, questionList);
			return;
		} else if(QuestionHandler.getUserIntent(session, request).equals("add to existing quiz")) {
			addToExistingQuiz(session, request, currUser, response);
		} else if(QuestionHandler.getUserIntent(session, request).equals("create quiz")) {
			createQuiz(session, request, response, questionList, currUser);
			return;
		}			
	}

	
	/*
	 * sets the title and description for users who are creating a quiz
	 */
	private void saveTitleAndDescription(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		session.setAttribute("title", title);
		session.setAttribute("description", description);
		QuestionHandler.forwardToPage("create-quiz.jsp?saved=signal", request, response);		
	}
	

	/*
	 * Makes a new question and adds it to the list of questions
	 */
	private void handleQuestionJob(HttpSession session, HttpServletRequest request, HttpServletResponse response, 
								   ArrayList<Question> questionList) throws ServletException, IOException {
		Question newQuestion = QuestionHandler.constructQuestion(request);
		if(newQuestion == null) {
			int questionType = Integer.parseInt(request.getParameter("question type"));
			QuestionHandler.forwardToPage(QuestionHandler.getErrorRedirection(questionType), request, response);
			return;
		}
		questionList.add(newQuestion);			
		QuestionHandler.forwardToPage("create-quiz.jsp", request, response);
	}


	/*
	 * Adds a question to an existing quiz
	 */
	private void addToExistingQuiz(HttpSession session, HttpServletRequest request, User currUser,
								   HttpServletResponse response) throws ServletException, IOException {
		Question newQuestion = QuestionHandler.constructQuestion(request);
		Quiz quiz = QuestionHandler.getQuiz(request);
		try {
			quiz.addQuestion(currUser.getUserID(), newQuestion);
		} catch (Exception e) { e.printStackTrace(); }	
		QuestionHandler.forwardToPage("quiz-edit.jsp", request, response);
	}
	
	
	/*
	 * creates a new quiz
	 */
	private void createQuiz(HttpSession session, HttpServletRequest request,
			   				HttpServletResponse response, ArrayList<Question> 
							questionList, User currUser) throws ServletException, IOException {
		if(!QuestionHandler.makeQuizAndAddToDB(questionList, request, currUser)) {
			QuestionHandler.forwardToPage("create-quiz.jsp?error=signal", request, response);	
			return;
		}
		QuestionHandler.clearQuestionList(session);
		QuestionHandler.forwardToPage("success.html", request, response);	
	}
}
