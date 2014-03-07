package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Challenge;
import model.Inbox;
import model.Note;
import model.User;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 

		try {
			// The challenger
			User fromUser = (User) session.getAttribute("currUser"); 
			int fromID = fromUser.getUserID();
			
			// The challengee
			int toID = Integer.parseInt(request.getParameter("id_field"));
			Inbox inbox = Inbox.getInbox(toID);
			
			//String email = request.getParameter("email_field");
			String subject = request.getParameter("subject"); 
			String body = request.getParameter("body");
			String challengeRequest = request.getParameter("send_challenge");
			
			if(challengeRequest != null){
				int quizID = Integer.parseInt(request.getParameter("quiz_id"));
				Challenge challenge = new Challenge(fromID, toID, quizID, subject, body); 
				inbox.addMessage(challenge);
			} else {
				Note note = new Note(toID, toID, subject, body);
				inbox.addMessage(note);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		// Need to know which page to forward to
		RequestDispatcher dispatch = request.getRequestDispatcher("TODO: ADD url"); 
		dispatch.forward(request, response); 
	}

}
