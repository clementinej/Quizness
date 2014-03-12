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
import model.FriendRequest;
import model.Inbox;
import model.Message;

/**
 * Servlet implementation class AcceptMessageServlet
 */
@WebServlet("/AcceptMessageServlet")
public class AcceptMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptMessageServlet() {
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
		String target = null;
		try {
			HttpSession session = request.getSession(); 
			String msg_id = request.getParameter("msg_id");
			int messageID = Integer.parseInt(msg_id); 
			String messageType = request.getParameter("messageType");
			String isAccepted = request.getParameter("send_accept"); 
			String toIDString = request.getParameter("to_id"); 
			int toID = Integer.parseInt(toIDString);
			
			Inbox inbox = Inbox.getInbox(toID);
					
			if(messageType.equals("friendRequest")){
				FriendRequest friendRequest = (FriendRequest) Message.getMessage(messageID);
				if(isAccepted != null){
					friendRequest.accept();
					target = "success-friend.html";
				} else {
					target = "social/reject-friend.html";
				}
				// If not accepted simply remove the request
				if(inbox.removeFriendRequest(messageID) == false) throw new Exception();
				

			} else if (messageType.equals("challenge")){
				Challenge challenge = (Challenge) Message.getMessage(messageID);
				if(isAccepted != null){
					challenge.accept();
					target = "quiz/quiz-summary.jsp?quiz_id=" + challenge.getQuizID()+"&challenge_id=" + msg_id; 
				} else {
					target = "inbox.jsp"; 
				}
				if(inbox.removeChallenge(messageID) == false) throw new Exception();
				
			}
		} catch (Exception e) { 
			target = "social/message-fail.html";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(target); 
		dispatch.forward(request, response); 

	}

}
