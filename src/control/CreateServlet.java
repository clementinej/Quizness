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
		session.setAttribute("question list", questionList);
			
		User currUser = (User) session.getAttribute("current user");
		System.out.println("In CreateServlet, user = " + currUser.getUserName());
		int creatorID = getUserID(currUser);	
		if(creatorID == -1) return; //should redirect to a "You are not logged in" page
		
		if(getUserIntent(session, request).equals("add question")) {
			Question newQuestion = constructQuestion(session, request);
			questionList.add(newQuestion);			
			forwardToPage("quiz/create-quiz.jsp", request, response);

		} else if(getUserIntent(session, request).equals("create quiz")) {
			makeQuizAndAddToDB(questionList, request, currUser);	
			clearQuestionList(questionList);
			forwardToPage("quiz/create-quiz.jsp", request, response);		
		}	
	}
	
	
//--------Helper Functions---------//
	/*
	 * Takes in a current user and reads the ID from the database
	 * @param currUser
	 * @return id of user
	 */
	private int getUserID(User currUser) {
		int creatorID = -1;
		try {
			System.out.println("currUser.getUserName =" + currUser.getUserName());
			creatorID = User.getUserID(currUser.getUserName());
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
	


	private void makeQuizAndAddToDB(ArrayList<Question> questionList, HttpServletRequest request, User currUser) {
		int creatorID = getUserID(currUser);	
		double maxScore = getMaxScore(questionList);
		String description = request.getParameter("description"), title = request.getParameter("name");
		boolean isPracticeMode = false, hasTimedMode = false; //extensions add later
		boolean multiplePages = Boolean.parseBoolean(request.getParameter("multiple_pages"));
		boolean hasRandomMode = Boolean.parseBoolean(request.getParameter("random_questions"));
		boolean immediateCorrection = Boolean.parseBoolean(request.getParameter("immediate_correction"));
		Quiz quiz = new Quiz(creatorID, maxScore, description, title, 
				questionList, isPracticeMode, hasRandomMode, hasTimedMode, immediateCorrection, multiplePages);
		try {
			ServerConnection.addQuiz(quiz);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void clearQuestionList(ArrayList<Question> questionList) {
		for(int i = 0; i < questionList.size(); i++) //restart question list
			questionList.remove(i);	//may want to store questionLists in a map depending on potential quizID
	}



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
		ArrayList<Set<String>> allAnswers = new ArrayList<Set<String>>();
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
		int solNum = 0;
		while(iter.hasNext()) {	
			String answerName = iter.next();
			if(answerName.indexOf("correct_answer_text") == -1) {}//ignore
			else {
				Set<String> synonymsOfAnswerSet = new HashSet<String>();
				String[] synonyms = answersMap.get(answerName);
				for(int i = 0; i < synonyms.length; i++) {
					System.out.println("Solution: " + solNum + " = " + synonyms[i]);
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
			Question newQuestion;
			System.out.println("Making a question response");
			newQuestion = new QuestionResponse(question, allAnswers, pointValue);
			System.out.println("Question: " + newQuestion.getQuestion());
			System.out.println("Solution: " + newQuestion.getAnswer());			
			return newQuestion;
		case FILL_IN_THE_BLANK:
			return new FillInTheBlank(question, allAnswers, pointValue);
		case MULTIPLE_CHOICE:
			String choices[] = getWrongChoices(request);	
			return new MultipleChoice(question, choices, allAnswers, pointValue);
		case PICTURE_RESPONSE:
			String url = "";//implement
			return new PictureResponse(question, url, allAnswers, pointValue);
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
	
	/*
	 * Forwards to specified page.  I might have to hard code it again here.
	 */
	private void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatch = request.getRequestDispatcher(page); 
		dispatch.forward(request, response); 
	}
	
	

}
