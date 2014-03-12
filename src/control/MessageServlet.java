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
import model.Message;
import model.Note;
import model.ServerConnection;
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
			User fromUser = (User) session.getAttribute("current user"); 
			int fromID = fromUser.getUserID();
			
			// The challengee
			//int toID = Integer.parseInt(request.getParameter("id"));
			String toEmail = null; 
			if(request.getParameter("user_email") != null) toEmail = request.getParameter("user_email"); 
			
			int toID = User.getUserID(toEmail);
			
			Inbox inbox = Inbox.getInbox(toID);
			
			//String email = request.getParameter("email_field");
			String subject = request.getParameter("subject"); 
			String body = request.getParameter("body");
			String challengeRequest = request.getParameter("send_challenge");
			
			if(challengeRequest != null){
				int quizID = Integer.parseInt(request.getParameter("quiz_id"));
				Challenge challenge = new Challenge(fromID, toID, quizID, subject, body); 
				int messageID = Message.addMessage(challenge);
				inbox.addChallenge(messageID);
			} else {
				Note note = new Note(fromID, toID, subject, body);
				int messageID = ServerConnection.addMessage(note);
				inbox.addNote(messageID);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		// Need to know which page to forward to
		RequestDispatcher dispatch = request.getRequestDispatcher("/social/message-success.html"); 
		dispatch.forward(request, response); 
	}

}
