package control;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
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
		HttpSession session = request.getSession();
		String userIntent = request.getParameter("intent");//if a user adds to cart		
		session.setAttribute("intent", userIntent);//put it in the variable
		//String sc = (String) session.getAttribute("intent");
		
		if(userIntent == "add question") {
			List<Question> questionList = (List<Question>) session.getAttribute("question list");	
			Map<String, Integer> questionTypeMap = (Map<String, Integer>) session.getAttribute("question-type map");
			
			String question = request.getParameter("question");
			String questionTypeString = request.getParameter("question type");
			int questionType = questionTypeMap.get(questionTypeString);
			
			//Set<String> is all synonyms for one answer (e.g. {Los Angeles, LA, L.A})
			//ArrayList holds multiple answers (e.g. 5 largest cities -> {LA, NY, Boston})
			ArrayList<Set<String>> allAnswers = null;
			
			Map<String, String[]> answersMap = request.getParameterMap();
			String choices[] = null;
			if(questionType == MULTIPLE_CHOICE) choices = answersMap.get("multiple choice options");
			
			
			java.util.Iterator<String> iter = answersMap.keySet().iterator();
			while(iter.hasNext()) {
				String answerName = iter.next();
				if(answerName == "question" || answerName == "question type" || answerName == "multiple choice options") {}//ignore
				else {
					Set<String> synonymsOfAnswerSet = new HashSet<String>();
					String[] synonyms = answersMap.get(answerName);
					for(int i = 0; i < synonyms.length; i++) {
						synonymsOfAnswerSet.add(synonyms[i]);
					}
					allAnswers.add(synonymsOfAnswerSet);
				}
				
			}
			double pointValue = 1;//default point value for each question depending on difficulty
			String pointValueStr = request.getParameter("point value");
			if(pointValueStr.length() != 0) {
				pointValue = Double.parseDouble(pointValueStr);
			}
			Question newQuestion = null;
			switch(questionType) {//correspond to the question subclass .java filenames
				case QUESTION_RESPONSE: 
					newQuestion = new QuestionResponse(question, allAnswers, pointValue);
					break;
				case FILL_IN_THE_BLANK:
					newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
					break;
				case MULTIPLE_CHOICE:
					newQuestion = new MultipleChoice(question, choices, allAnswers, pointValue);
					break;
				case PICTURE_RESPONSE:
					newQuestion = new PictureResponse(question, allAnswers, pointValue);
					break;
				case MULTIANSWER:
				//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
					break;
				case MULTIPLE_CHOICE_MULTIPLE_ANSWERS:
				//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
					break;
				case MATCHING:
				//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
					break;
				case AUTO_GENERATED:
				//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
					break;
				case HUMAN_GRADED:
				//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
					break;
				case TIMED:
				//	newQuestion = new FillInTheBlank(question, allAnswers, pointValue);
					break;
			}
			questionList.add(newQuestion);
		} else if(userIntent == "create quiz") {
			//generate new quiz ID
			
			//make a new quiz
			
			//add it to data base
			
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
