package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuestionHandler {

	public static final int QUESTION_RESPONSE = 1;
	public static final int FILL_IN_THE_BLANK = 2;
	public static final int MULTIPLE_CHOICE = 3;
	public static final int PICTURE_RESPONSE = 4;
		
	public static Quiz getQuiz(HttpServletRequest request) {
		int quizID = Integer.parseInt(request.getParameter("quiz_id"));
		Quiz quiz = null;
		try {
			quiz = Quiz.getQuiz(quizID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quiz;
	}

	/*
	 * Takes in a current user and reads the ID from the database
	 */
	public static int getUserID(User currUser) {
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
	public static String getUserIntent(HttpSession session, HttpServletRequest request) {
		String userIntent = request.getParameter("intent");
		session.setAttribute("intent", userIntent);
		System.out.println(userIntent);
		return userIntent;
	}
	

	/*
	 * makes a quiz and adds it to the database.
	 */
	public static boolean makeQuizAndAddToDB(ArrayList<Question> questionList, HttpServletRequest request, User currUser) {
		if(questionList.size() == 0) return false;
		int creatorID = getUserID(currUser);	
		double maxScore = getMaxScore(questionList);
		String description = request.getParameter("description"), title = request.getParameter("title");
		if(title.length() == 0) return false;
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
			checkAchievements(creatorID);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
		return true;
	}

	
	/*
	 * Checks achievements, and adds if any are fulfilled 
	 */
	private static void checkAchievements(int creatorID) throws Exception{
		System.out.println("Checking achievements");
		Connection con = ServerConnection.getConnection();
		User user = ServerConnection.getUser(creatorID);
		PreparedStatement ps = con.prepareStatement("SELECT id FROM quizzes WHERE creatorID = ?");
		ps.setInt(1, creatorID);
		ResultSet rs = ps.executeQuery();
		int numQuizzes = 0;
		while (rs.next()){
			numQuizzes++;
		}
		System.out.println(numQuizzes);
		if (numQuizzes == 1){
			System.out.println("Adding amateur author");
			user.addAchievement(new AmateurAuthor());
		}
		if (numQuizzes == 3){
			System.out.println("Adding prolific author");
			user.addAchievement(new ProlificAuthor());
		}
		if (numQuizzes == 5){
			System.out.println("Adding prodigious author");
			user.addAchievement(new ProdigiousAuthor());
		}
		ServerConnection.updateUser(user);
	}
	
	
	/*
	 * After a quiz is created, the temporary question list is replaced with an empty list
	 * in preparation for another quiz to be made.
	 * TODO: discard changes button
	 * TODO: remove from list button
	 */
	public static void clearQuestionList(HttpSession session) {
		ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute("question list");	
		questionList = new ArrayList<Question>();
		session.setAttribute("question list", questionList);
        String quizTitle = "";
        String description = "";
        session.setAttribute("title", quizTitle);
        session.setAttribute("description", description);

		System.out.println("Question List has been reset.");
	}


	/*
	 * Gets the maximum possible score that can be achieved from the quiz.
	 */
	private static double getMaxScore(ArrayList<Question> questionList) {
		double pointCount = 0;
		for(Question question: questionList) {
			pointCount += question.getMaxPoints();
		}
		return pointCount;
	}


	
	/*
	 * Makes a new question given the information passed in by the user
	 */
	public static Question constructQuestion(HttpServletRequest request) {		
		int questionType = Integer.parseInt(request.getParameter("question type"));
		String question = request.getParameter("question_text");
		if(question.isEmpty()) return null;
		System.out.println("Question:" + question);
		ArrayList<Set<String>> allAnswers = new ArrayList<Set<String>>();
		makeAnswersList(request, allAnswers);
		if(allAnswers.isEmpty()) return null;
		double pointValue = -1;//default point value for each question depending on difficulty
		String pointValueStr = request.getParameter("correct_answer_score");
		if(pointValueStr.length() != 0) 
			try { pointValue = Double.parseDouble(pointValueStr); }
			catch (Exception e) {return null;}
		else return null;
		return makeQuestion(questionType, question, allAnswers, pointValue, request);
	}
	
	/*
	 * Moves all answers to the allAnswers ArrayList.
	 */
	private static void makeAnswersList(HttpServletRequest request, ArrayList<Set<String>> allAnswers) {
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
					if(synonyms[i].isEmpty()) System.out.println("empty space ignored");
					else System.out.print(synonyms[i]);
					if(i != synonyms.length - 1)
						System.out.print(", ");
					if(!synonyms[i].isEmpty())
						synonymsOfAnswerSet.add(synonyms[i]);
				}
				System.out.println();
				if(!synonymsOfAnswerSet.isEmpty())
					allAnswers.add(synonymsOfAnswerSet);
			}	
		}	
	}
	
	/*
	 * Constructs and returns a Question object.
	 */
	private static Question makeQuestion(int questionType, String question, ArrayList<Set<String>> allAnswers, 
									double pointValue, HttpServletRequest request) {
		switch(questionType) {//correspond to the question subclass
		case QUESTION_RESPONSE: 
			Question newQuestion;
			System.out.println("QuestionResponse Question made.");
			newQuestion = new QuestionResponse(question, allAnswers, pointValue);		
			return newQuestion;
		case FILL_IN_THE_BLANK:
			if(!question.contains("_")) return null;
			System.out.println("FillInTheBlank Question made.");
			return new FillInTheBlank(question, allAnswers, pointValue);
		case MULTIPLE_CHOICE:
			String choices[] = getWrongChoices(request);	
			if(choices.length == 0) return null;
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
	private static String[] getWrongChoices(HttpServletRequest request) {
		Map<String, String[]> answersMap = request.getParameterMap();
		String choices[] = answersMap.get("incorrect_answer");
		ArrayList<String> filterEmpty = new ArrayList<String>();
		System.out.print("Incorrect Answers: ");
		for(int i = 0; i < choices.length; i++) {
			System.out.print(choices[i]);
			if(i != choices.length - 1) System.out.print(", ");
			if(!choices[i].isEmpty()) filterEmpty.add(choices[i]);
		}
		System.out.println();
		String filteredChoices[] = new String[filterEmpty.size()];
		filterEmpty.toArray(filteredChoices);
		return filteredChoices;
	}
	
	/*
	 * Forwards to specified page.  I might have to hard code it again here.
	 */
	public static void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatch = request.getRequestDispatcher(page); 
		dispatch.forward(request, response); 
	}
	
	
	/*
	 * gives url of where to go from error
	 */
	public static String getErrorRedirection(int questionType) {
		switch(questionType) {//correspond to the question subclass
		case QUESTION_RESPONSE: 
			return "quiz/questionCreation/question-answer.jsp?error=signal";
		case FILL_IN_THE_BLANK:
			return "quiz/questionCreation/fill-in-blanks.jsp?error=signal";
		case MULTIPLE_CHOICE:
			return "quiz/questionCreation/multiple-choice.jsp?error=signal";
		case PICTURE_RESPONSE:
			return "quiz/questionCreation/picture-response.jsp?error=signal";
		}
		return null;
	}
	
	/*
	 * gives url of where to go from error
	 */
	public static String getCreateEditErrorRedirection(int questionType) {
		switch(questionType) {//correspond to the question subclass
		case QUESTION_RESPONSE: 
			return "quiz/questionEditing/edit-question-answer.jsp?error=signal";
		case FILL_IN_THE_BLANK:
			return "quiz/questionEditing/edit-fill-in-blanks.jsp?error=signal";
		case MULTIPLE_CHOICE:
			return "quiz/questionEditing/edit-multiple-choice.jsp?error=signal";
		case PICTURE_RESPONSE:
			return "quiz/questionEditing/edit-picture-response.jsp?error=signal";
		}
		return null;
	}
	
	
}
