package listener;

import java.util.*;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import model.Question;

/**
 * Application Lifecycle Listener implementation class CreateListener
 *
 */
@WebListener
public class CreateListener implements HttpSessionListener {

	/**
	 * 
	 */
    public void sessionCreated(HttpSessionEvent event) {
     	HttpSession session = event.getSession(); 
      	List<Question> questionList = new ArrayList<Question>();
        session.setAttribute("questionList", questionList);

        Map<String, Integer> questionTypeMap = new HashMap<String, Integer>();
        questionTypeMap.put("question reponse", 1);
        questionTypeMap.put("fill in the blank", 2);
        questionTypeMap.put("multiple choice", 3);
        questionTypeMap.put("picture reponse", 4);
        questionTypeMap.put("multi-answer", 5);
        questionTypeMap.put("multiple choice with multiple answers", 6);
        questionTypeMap.put("matching", 7);
        questionTypeMap.put("auto-generated", 8);
        questionTypeMap.put("human graded", 9);
        questionTypeMap.put("timed", 10);	
        session.setAttribute("question-type map", questionTypeMap);
        //add questions later        
    }
    
	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        
    }
    
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
