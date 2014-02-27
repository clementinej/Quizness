package control;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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
	public static final int AUTO_GENERATED = 8;
	public static final int HUMAN_GRADED = 9;
	public static final int TIMED = 10;
	

    public CreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		
		if(getUserIntent(session, request) == "add question") {
			System.out.println("Content Received");
			Question newQuestion = constructQuestion(session, request);
			questionList.add(newQuestion);
			RequestDispatcher dispatch = request.getRequestDispatcher("create-quiz.jsp"); 
			dispatch.forward(request, response); 
		} else if(getUserIntent(session, request) == "create quiz") {
			//int newQuizID = ServerConnection.getUnusedQuizID();
			int temporaryID = 1;
			Quiz quiz = new Quiz(temporaryID, questionList);
			
			//add Server --> Tony's implementation
			//ServerConnection.addQuiz(temporaryID, quiz);//might want to store as attribute	
			
			for(int i = 0; i < questionList.size(); i++) //restart question list
				questionList.remove(i);	//may want to store questionLists in a map depending on potential quizID
			//forward back to create-quiz.jsp or maybe create-quiz-success.html
			RequestDispatcher dispatch = request.getRequestDispatcher("create-quiz.jsp"); 
			dispatch.forward(request, response); 
		}	
	}
	
	private String getUserIntent(HttpSession session, HttpServletRequest request) {
		String userIntent = request.getParameter("intent");
		session.setAttribute("intent", userIntent);
		return userIntent;
	}
	
	/*
	 * 
	 */
	private Question constructQuestion(HttpSession session, HttpServletRequest request) {
		Map<String, Integer> questionTypeMap = (Map<String, Integer>) session.getAttribute("question-type map");
		String questionTypeString = request.getParameter("question type");
		int questionType = questionTypeMap.get(questionTypeString);

		String question = request.getParameter("question");
		
		//Set<String> is all synonyms for one answer (e.g. {Los Angeles, LA, L.A})
		//ArrayList holds multiple answers (e.g. 5 largest cities -> {LA, NY, Boston})
		ArrayList<Set<String>> allAnswers = null;
		
		addAnswerToAnswersList(request, allAnswers);
		
		double pointValue = 1;//default point value for each question depending on difficulty
		String pointValueStr = request.getParameter("correct_answer_score");
		if(pointValueStr.length() != 0)
			pointValue = Double.parseDouble(pointValueStr);
		return makeQuestion(questionType, question, allAnswers, pointValue, request);
	}
	
	
	private void addAnswerToAnswersList(HttpServletRequest request, ArrayList<Set<String>> allAnswers) {
		Map<String, String[]> answersMap = request.getParameterMap();
		java.util.Iterator<String> iter = answersMap.keySet().iterator();
		while(iter.hasNext()) {
			String answerName = iter.next();
			if(answerName.indexOf("correct_answer_key") == -1) {}//ignore
			else {
				Set<String> synonymsOfAnswerSet = new HashSet<String>();
				String[] synonyms = answersMap.get(answerName);
				for(int i = 0; i < synonyms.length; i++) {
					synonymsOfAnswerSet.add(synonyms[i]);
				}
				allAnswers.add(synonymsOfAnswerSet);
			}	
		}	
	}
	
	
	private Question makeQuestion(int questionType, String question, ArrayList<Set<String>> allAnswers, 
									double pointValue, HttpServletRequest request) {
		switch(questionType) {//correspond to the question subclass .java filenames
		case QUESTION_RESPONSE: 
			return new QuestionResponse(question, allAnswers, pointValue);
		case FILL_IN_THE_BLANK:
			return new FillInTheBlank(question, allAnswers, pointValue);
		case MULTIPLE_CHOICE:
			String choices[] = getWrongChoices(request);	
			return new MultipleChoice(question, choices, allAnswers, pointValue);
		case PICTURE_RESPONSE:
			return new PictureResponse(question, allAnswers, pointValue);
		case MULTIANSWER:
		//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
		case MULTIPLE_CHOICE_MULTIPLE_ANSWERS:
		//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
		case MATCHING:
		//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
		case AUTO_GENERATED:
		//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
		case HUMAN_GRADED:
		//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
		case TIMED:
		//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
		}	
		return null;
	}
	
	
	
	private String[] getWrongChoices(HttpServletRequest request) {
		Map<String, String[]> answersMap = request.getParameterMap();
		String choices[] = answersMap.get("incorrect_answer_key");
		return choices;
	}
	
	

}
