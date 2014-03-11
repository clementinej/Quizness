package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class MakeAdminServlet
 */
@WebServlet("/MakeAdminServlet")
public class MakeAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeAdminServlet() {
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
		int userID = Integer.parseInt(request.getParameter("user_id"));
		try {
			setAdmin(userID, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setAdmin(int userID, HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = ServerConnection.getUser(userID);
		user.setAdmin();
		ServerConnection.updateUser(user);
		RequestDispatcher dispatch = request.getRequestDispatcher("profile.jsp?id=" + user.getUserID()); 
		dispatch.forward(request, response);
	}

}
