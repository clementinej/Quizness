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
		System.out.println("In EditServlet:" + saveTitleAndDescription);	
		if(saveTitleAndDescription != null) {
			saveTitleAndDescription(request, quiz, response);
			return;
		} else if(request.getParameter("delete") != null) {
			deleteQuestion(request, quiz);
		} else {	
			updateQuestion(request, quiz);
		}	
		QuestionHandler.forwardToPage("quiz-edit.jsp", request, response);
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
	private void deleteQuestion(HttpServletRequest request, Quiz quiz) {
		int qIndex = Integer.parseInt(request.getParameter("question_index"));
		try {quiz.removeQuestionAt(qIndex);} catch (Exception e) {e.printStackTrace();}		
	}
	

	/*
	 * Updates question with the given question index and stores result in database
	 */
	private void updateQuestion(HttpServletRequest request, Quiz quiz) {
		int qIndex = Integer.parseInt(request.getParameter("question_index"));
		Question newQuestion = QuestionHandler.constructQuestion(request);				
		try {
			quiz.updateQuestion(qIndex, newQuestion);} 
		catch (Exception e) { e.printStackTrace();}
	}
	
}
