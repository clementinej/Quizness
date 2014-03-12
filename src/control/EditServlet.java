package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int SAVE_AS_SESSION = -2;
	public static final int QUESTION_RESPONSE = 1;
	public static final int FILL_IN_THE_BLANK = 2;
	public static final int MULTIPLE_CHOICE = 3;
	public static final int PICTURE_RESPONSE = 4;
	


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ignore
		}


	/*
	 * This servlet question based on user edits and replaces it in the database.  Also performs delete and
	 * saving title and description functionalities.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz quiz = QuestionHandler.getQuiz(request);
		String saveTitleAndDescription = request.getParameter("quiz_info");
		System.out.println("In EditServlet:");	
		HttpSession session = request.getSession();	
		String redirectTo;
		if(saveTitleAndDescription != null) {
			saveTitleAndDescription(request, quiz, response);
			return;
		} else if(request.getParameter("delete") != null) {
			redirectTo = deleteQuestion(request, quiz, session);
		} else {	
			redirectTo = updateQuestion(request, quiz, session);
		}	
		System.out.println("From CreateServlet redirecting to:" + redirectTo);
		QuestionHandler.forwardToPage(redirectTo, request, response);
	}


	/*
	 * Saves title and description in object and within database.
	 */
	private void saveTitleAndDescription(HttpServletRequest request, Quiz quiz, HttpServletResponse response) throws ServletException, IOException {
		String newTitle = request.getParameter("title");
		String newDescription = request.getParameter("description");
		try {
			quiz.setTitle(newTitle);
			quiz.setDescription(newDescription);
		} catch (Exception e) {	e.printStackTrace();}
		System.out.println("editing title");	
		System.out.println("editing description");
		QuestionHandler.forwardToPage("success.html", request, response);
	}

	
	/*
	 * Deletes a question from quiz and stores result in database.
	 */
	private String deleteQuestion(HttpServletRequest request, Quiz quiz, HttpSession session) {
		int qIndex = Integer.parseInt(request.getParameter("question_index"));
		if(Integer.parseInt(request.getParameter("quiz_id")) == SAVE_AS_SESSION) {
			ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute("question list");
			questionList.remove(qIndex);
			session.setAttribute("question list", questionList);
			return "create-quiz.jsp";
		} else {
			try {quiz.removeQuestionAt(qIndex);} catch (Exception e) {e.printStackTrace();}
		}
		return "quiz-edit.jsp?e=e&quiz_id="+quiz.getQuizID();
	}
	

	/*
	 * Updates question with the given question index and stores result in database
	 */
	private String updateQuestion(HttpServletRequest request, Quiz quiz, HttpSession session) throws ServletException, IOException {
		int qIndex = Integer.parseInt(request.getParameter("question_index"));
		Question newQuestion = QuestionHandler.constructQuestion(request);
		int questionType = Integer.parseInt(request.getParameter("question type"));
		if(Integer.parseInt(request.getParameter("quiz_id")) == SAVE_AS_SESSION) {
			if(newQuestion == null) return QuestionHandler.getCreateEditErrorRedirection(questionType);
			ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute("question list");
			questionList.set(qIndex, newQuestion);
			session.setAttribute("question list", questionList);
			return "create-quiz.jsp";
		} else {
			if(newQuestion == null) return QuestionHandler.getEditErrorRedirection(questionType, quiz.getQuizID(), qIndex);
			try {
				quiz.updateQuestion(qIndex, newQuestion);} 
			catch (Exception e) { e.printStackTrace();}
		}
		return "quiz-edit.jsp?e=e&quiz_id="+Integer.parseInt(request.getParameter("quiz_id"));
	}
	
}
