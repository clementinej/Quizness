package control;

import java.io.IOException;
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
	public static final int MULTIANSWER = 5;
	public static final int MULTIPLE_CHOICE_MULTIPLE_ANSWERS = 6;
	public static final int MATCHING = 7;
	

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
		session.setAttribute("question list", questionList);
		
		User currUser = (User) session.getAttribute("current user");
		System.out.println("In CreateServlet, user = " + currUser.getUserName());
		int creatorID = getUserID(currUser);	

		if(creatorID == -1) return; //should redirect to a "You are not logged in" page
		
		if(getUserIntent(session, request).equals("add question")) {
			Question newQuestion = constructQuestion(session, request);
			questionList.add(newQuestion);			
			forwardToPage("create-quiz.jsp", request, response);
		} else if(getUserIntent(session, request).equals("add to existing quiz")) {
			Question newQuestion = constructQuestion(session, request);
			Quiz quiz = getQuiz(request);
			try {
				quiz.addQuestion(currUser.getUserID(), newQuestion);
			} catch (Exception e) { e.printStackTrace(); }	
			
			forwardToPage("quiz-edit.jsp", request, response);
		} else if(getUserIntent(session, request).equals("create quiz")) {
			makeQuizAndAddToDB(questionList, request, currUser);	
			clearQuestionList(questionList);
			forwardToPage("success.html", request, response);		
		}	
	}
	
	
private Quiz getQuiz(HttpServletRequest request) {
	int quizID = Integer.parseInt(request.getParameter("quiz_id"));
	Quiz quiz = null;
	try {
		quiz = Quiz.getQuiz(quizID);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return quiz;
}

	//--------Helper Functions---------//
	/*
	 * Takes in a current user and reads the ID from the database
	 */
	private int getUserID(User currUser) {
		int creatorID = -1;
		try {
			creatorID = User.getUserID(currUser.getEmail());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return creatorID;
	}
	
	
	/*
	 * Reads what user intends to do from HttpServletRequest request
	 */
	private String getUserIntent(HttpSession session, HttpServletRequest request) {
		String userIntent = request.getParameter("intent");
		session.setAttribute("intent", userIntent);
		System.out.println(userIntent);
		return userIntent;
	}
	

	/*
	 * makes a quiz and adds it to the database.
	 */
	private void makeQuizAndAddToDB(ArrayList<Question> questionList, HttpServletRequest request, User currUser) {
		int creatorID = getUserID(currUser);	
		double maxScore = getMaxScore(questionList);
		String description = request.getParameter("description"), title = request.getParameter("title");
		System.out.println("Title: "+ title);
		System.out.println("Description: "+ description);
		System.out.println("Max Score: "+ maxScore);
		boolean isPracticeMode = false, hasTimedMode = false; //extensions add later
		boolean multiplePages = false;
		boolean hasRandomMode = false;
		boolean immediateCorrection = false;
		if(request.getParameterValues("selected") != null) {
			String [] selected = request.getParameterValues("selected");
			for(int i = 0; i < selected.length; i++) {
				if(selected[i].equals("multiple_pages"))
					multiplePages = true;
				if(selected[i].equals("random_questions"))
					hasRandomMode= true;
				if(selected[i].equals("immediate_correction"))
					immediateCorrection = true;
			}
		}

		
		Quiz quiz = new Quiz(creatorID, maxScore, description, title, 
				questionList, isPracticeMode, hasRandomMode, hasTimedMode, immediateCorrection, multiplePages);
		try {
			ServerConnection.addQuiz(quiz);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/*
	 * After a quiz is created, the temporary question list is destroyed in preparation for another quiz to be made.
	 * TODO: discard changes button
	 * TODO: remove from list button
	 */
	private void clearQuestionList(ArrayList<Question> questionList) {
		for(int i = 0; i < questionList.size(); i++) //restart question list
			questionList.remove(i);	//may want to store questionLists in a map depending on potential quizID
		System.out.println("Question List has been reset.");
	}


	/*
	 * Gets the maximum possible score that can be achieved from the quiz.
	 */
	private double getMaxScore(ArrayList<Question> questionList) {
		double pointCount = 0;
		for(Question question: questionList) {
			pointCount += question.getMaxPoints();
		}
		return pointCount;
	}


	
	/*
	 * Makes a new question given the information passed in by the user
	 */
	private Question constructQuestion(HttpSession session, HttpServletRequest request) {		
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
	
	/*
	 * Constructs and returns a Question object.
	 */
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
		case MULTIANSWER:
			//extension
		case MULTIPLE_CHOICE_MULTIPLE_ANSWERS:
			//extension
		case MATCHING:
			//extension
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
	 * Forwards to specified page.  I might have to hard code it again here.
	 */
	private void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatch = request.getRequestDispatcher(page); 
		dispatch.forward(request, response); 
	}
	
	

}
