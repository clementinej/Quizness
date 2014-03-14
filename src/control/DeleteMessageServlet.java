package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Inbox;
import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class DeleteMessageServlet
 */
@WebServlet("/DeleteMessageServlet")
public class DeleteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMessageServlet() {
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
		String[] selectedMessages = request.getParameterValues("selected_messages");
		User user = (User) session.getAttribute("current user");
		
		try {
			Connection con = ServerConnection.getConnection();
			Inbox inbox = ServerConnection.getInboxWithUserID(user.getUserID());
			for (int i = 0; i < selectedMessages.length; i++){
				int messageID = Integer.parseInt(selectedMessages[i]);
				inbox.removeNote(messageID);
				
				PreparedStatement ps = con.prepareStatement("DELETE FROM messages WHERE id = ?");
				ps.setInt(1, messageID);
				ps.executeUpdate();
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("inbox.jsp"); 
			dispatch.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
