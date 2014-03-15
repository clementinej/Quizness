package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Delete;
import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class UnbanUserServlet
 */
@WebServlet("/UnbanUserServlet")
public class UnbanUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnbanUserServlet() {
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
		int unbanID = Integer.parseInt(request.getParameter("userID"));
		try {
			User user = ServerConnection.getUser(unbanID);
			user.unBan();
			ServerConnection.updateUser(user);
			if (!user.isBanned())
				System.out.println("This user has been unbanned");
			redirectToPage("profile.jsp?id=" + unbanID, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void redirectToPage(String pageName, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(pageName); 
		dispatch.forward(request, response); 
	}

}
