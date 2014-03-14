package listener;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
	private static final boolean debugging = true;
	private HttpSession session;
	private ArrayList<String[]> readyResponses;
	private ArrayList<String[]> currentMultiAnswers;
	
	/**
	 * When a browser is opened, a questionList is created, and
	 */
    public void sessionCreated(HttpSessionEvent event) {
    	System.out.println("Session Created");
    	session = event.getSession(); 
      	List<Question> questionList = new ArrayList<Question>();
      	   
		//addOneToQuestionList(questionList);//testing
		//System.out.println(questionList.get(0));//testing
        session.setAttribute("question list", questionList);
        
        currUser = null;
        
        if(debugging) {
        	setAutomaticAccount();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a");
        session.setAttribute("time formatter", formatter);
        session.setAttribute("current user", currUser);
        
        String quizTitle = "";
        String description = "";
        session.setAttribute("title", quizTitle);
        session.setAttribute("description", description);
        
        readyResponses = new ArrayList<String[]>();
        session.setAttribute("ready responses", readyResponses);
        
        currentMultiAnswers = new ArrayList<String[]>();
        session.setAttribute("answers", currentMultiAnswers);
    }


	/**
     * user is signed out if web browser exits
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        currUser = null;
        session.setAttribute("current user", null);
        readyResponses = new ArrayList<String[]>();
        session.setAttribute("ready responses", readyResponses);
        String quizTitle = "";
        String description = "";
        session.setAttribute("title", quizTitle);
        session.setAttribute("description", description);
    }
	
    private void setAutomaticAccount() {
    	try {
			currUser = User.getUser(User.getUserID("lloyd@lloyd.com"));
			System.out.println("Automatic user \""+currUser.getUserName() +"\" in use.");
		} catch (SQLException e) {
			System.out.println("Automatic user debug failed.");
			e.printStackTrace();	
		} catch (Exception e) {
			System.out.println("Automatic user debug failed.");
			e.printStackTrace();
		}
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
