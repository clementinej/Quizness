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
 * Servlet implementation class UnfriendServlet
 */
@WebServlet("/UnfriendServlet")
public class UnfriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnfriendServlet() {
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
		int toID = Integer.parseInt(request.getParameter("userID"));
		try {
			User toUser = User.getUser(toID);
			fromUser.removeFriend(toID);
		} catch (Exception e) {} 
		
		String target = "/profile.jsp?id="+toID; 
		RequestDispatcher dispatch = request.getRequestDispatcher(target); 
		dispatch.forward(request, response); 
	}
}
