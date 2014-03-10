package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.FriendRequest;
import model.Inbox;
import model.Message;
import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class FriendRequestServlet
 */
@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestServlet() {
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
		User fromUser = (User)session.getAttribute("current user");
		int fromID = fromUser.getUserID(); 
		int toID = Integer.parseInt(request.getParameter("toID"));
		String subject = fromID + " would like to be friends with you"; 
		String body = "Hi " + toID +", I hope we can be friends on Quizness."; 
		Inbox inbox;
		try {
			inbox = Inbox.getInbox(toID);
			FriendRequest friendRequest = new FriendRequest(fromID, toID, subject, body);
			int messageID = Message.addMessage(friendRequest); 
			inbox.addFriendRequest(messageID);
			ServerConnection.updateInbox(inbox);
		} catch (Exception e) {} 
		
		String target = "/social/profile.jsp?id="+toID; 
		RequestDispatcher dispatch = request.getRequestDispatcher(target); 
		dispatch.forward(request, response); 
	}

}
