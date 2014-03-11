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
	 * This servlet gets a constructs a new question based on user edits and replaces it 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz quiz = getQuiz(Integer.parseInt(request.getParameter("quiz_id")));
		String buttonPressed = request.getParameter("quiz_info");
		System.out.println("In EditServlet:" + buttonPressed);	
		String delete = request.getParameter("delete");
		if(buttonPressed != null) {
			String newTitle = request.getParameter("title");
			String newDescription = request.getParameter("description");
			try {
				quiz.setTitle(newTitle);
				quiz.setDescription(newDescription);
			} catch (Exception e) {	e.printStackTrace();}
			System.out.println("editing title");	
			System.out.println("editing description");
			redirectToPage("success.html", request, response);
			return;
		} else if(delete != null) {
			int qIndex = Integer.parseInt(request.getParameter("question_index"));
			try {
				quiz.removeQuestionAt(qIndex);} catch (Exception e) {e.printStackTrace();}
		} else {	
			int qIndex = Integer.parseInt(request.getParameter("question_index"));
			Question newQuestion = constructQuestion(request);	
			
			try {
				quiz.updateQuestion(qIndex, newQuestion);} 
			catch (Exception e) { e.printStackTrace();}
		}
		
		redirectToPage("quiz-edit.jsp", request, response);
	}

	/*
	 * grabs the quiz from the database given an id
	 */
	private Quiz getQuiz(int quizID) {
		Quiz quiz = null;
		try {
			quiz = Quiz.getQuiz(quizID);
		} catch (Exception e) {e.printStackTrace();}		
		return quiz;
	}

	/*
	 * Makes a new question given the information passed in by the user
	 */
	private Question constructQuestion(HttpServletRequest request) {		
		int questionType = Integer.parseInt(request.getParameter("question type"));
		String question = request.getParameter("question_text");
		System.out.println("Question:" + question);
		ArrayList<Set<String>> allAnswers = new ArrayList<Set<String>>();
		makeAnswersList(request, allAnswers);
		double pointValue = 1;//default point value for each question depending on difficulty
		String pointValueStr = request.getParameter("correct_answer_score");
		if(pointValueStr.length() != 0)
			pointValue = Double.parseDouble(pointValueStr);
		return makeQuestion(questionType, question, allAnswers, pointValue, request);
	}
	
	
	/*
	 * Moves all answers to the allAnswers ArrayList.
	 */
	private void makeAnswersList(HttpServletRequest request, ArrayList<Set<String>> allAnswers) {
		Map<String, String[]> answersMap = request.getParameterMap();
		java.util.Iterator<String> iter = answersMap.keySet().iterator();
		int solNum = 0;
		while(iter.hasNext()) {	
			String answerName = iter.next();
			if(answerName.indexOf("correct_answer_text") == -1) {}//ignore
			else {
				Set<String> synonymsOfAnswerSet = new HashSet<String>();
				String[] synonyms = answersMap.get(answerName);
				int debugIndex = solNum + 1;
				System.out.print("Solution " + debugIndex + ": ");
				for(int i = 0; i < synonyms.length; i++) {
					System.out.print(synonyms[i]);
					if(i != synonyms.length - 1)
						System.out.print(", ");
					synonymsOfAnswerSet.add(synonyms[i]);
				}
				System.out.println();
				allAnswers.add(synonymsOfAnswerSet);
			}	
		}	
	}
	
	private Question makeQuestion(int questionType, String question, ArrayList<Set<String>> allAnswers, 
		double pointValue, HttpServletRequest request) {
		switch(questionType) {//correspond to the question subclass
		case QUESTION_RESPONSE: 
			Question newQuestion;
			System.out.println("QuestionResponse Question made.");
			newQuestion = new QuestionResponse(question, allAnswers, pointValue);		
			return newQuestion;
		case FILL_IN_THE_BLANK:
			System.out.println("FillInTheBlank Question made.");
			return new FillInTheBlank(question, allAnswers, pointValue);
		case MULTIPLE_CHOICE:
			String choices[] = getWrongChoices(request);	
			System.out.println("MultipleChoice Question made.");
			return new MultipleChoice(question, choices, allAnswers, pointValue);
		case PICTURE_RESPONSE:
			System.out.println("PictureResponse Question made.");
			return new PictureResponse(question, allAnswers, pointValue);
		}	
		return null;
	}
	
	/*
	 * For multiple choice.  Get's wrong answer options and returns them in a string array.
	 */
	private String[] getWrongChoices(HttpServletRequest request) {
		Map<String, String[]> answersMap = request.getParameterMap();
		String choices[] = answersMap.get("incorrect_answer");
		
		System.out.print("Incorrect Answers: ");
		for(int i = 0; i < choices.length; i++) {
			System.out.print(choices[i]);
			if(i != choices.length - 1) System.out.print(", ");
		}
		System.out.println();
		
		return choices;
	}
	
	
	/*
	 * redirects to given page
	 */
	private void redirectToPage(String pageName, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(pageName); 
		dispatch.forward(request, response); 
	}
	
}
