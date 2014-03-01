package listener;

import java.util.*;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import model.*;

/**
 * Application Lifecycle Listener implementation class CreateListener
 *
 */
@WebListener
public class CreateListener implements HttpSessionListener {
	private User currUser;
	
	/**
	 * 
	 */
    public void sessionCreated(HttpSessionEvent event) {
     	System.out.println("Session Created");
    	HttpSession session = event.getSession(); 
      	List<Question> questionList = new ArrayList<Question>();
      	        
		addOneToQuestionList(questionList);//testing
		System.out.println(questionList.get(0));//testing
        session.setAttribute("question list", questionList);

        Map<String, Integer> questionTypeMap = new HashMap<String, Integer>();
        makeQuestionTypeMap(questionTypeMap);
        session.setAttribute("question-type map", questionTypeMap);
        
        currUser = null;//updated for every login, set to null for every logout
        session.setAttribute("current user", currUser);
    }
    
	
	private void makeQuestionTypeMap(Map<String, Integer> questionTypeMap) {
        questionTypeMap.put("question-answer", 1);
        questionTypeMap.put("fill in the blank", 2);
        questionTypeMap.put("multiple choice", 3);
        questionTypeMap.put("picture reponse", 4);
        questionTypeMap.put("multi-answer", 5);
        questionTypeMap.put("multiple choice with multiple answers", 6);
        questionTypeMap.put("matching", 7);
        questionTypeMap.put("auto-generated", 8);
        questionTypeMap.put("human graded", 9);
        questionTypeMap.put("timed", 10);	
        //add questions later   
	}


	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        currUser = null;
    }
	
    
    private void addOneToQuestionList(List<Question> questionList) {
    	String q = "Who was the 16th president? Who was the first?";
		
		ArrayList<Set<String>> answer = new ArrayList<Set<String>>();
		Set<String> a = new HashSet<String>();
		a.add("Abe");
		a.add("Abraham");
		answer.add(a);
		Set<String> b = new HashSet<String>();
		b.add("Washington");
		b.add("George");
		answer.add(b);
	
		
		double pointValue = 3;
		QuestionResponse question = new QuestionResponse(q, answer, pointValue);
		questionList.add(question);
    }
}