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
	
		HttpSession session = request.getSession(); 
		String msg_id = request.getParameter("msg_id");
		int messageID = Integer.parseInt(msg_id); 
		String messageType = request.getParameter("messageType");
		String send_accept = request.getParameter("send_accept"); 
		String target = null; 
				
		try {
			if(messageType.equals("friendRequest")){
				FriendRequest friendRequest = (FriendRequest) Message.getMessage(messageID);
				friendRequest.accept();
				target = "social/success-friend.html";
			} else if (messageType.equals("challenge")){
				Challenge challenge = (Challenge) Message.getMessage(messageID);
				challenge.accept();
				target = "../quiz/quiz-summary?quiz_id=" + challenge.getQuizID(); 
			}
		} catch (Exception e) { }
		
		RequestDispatcher dispatch = request.getRequestDispatcher(target); 
		dispatch.forward(request, response); 

	}

}
