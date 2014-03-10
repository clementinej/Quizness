package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Search;
import model.User;

/**
 * Servlet implementation class AdminUserSearchServlet
 */
@WebServlet("/AdminUserSearchServlet")
public class AdminUserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserSearchServlet() {
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
		String userName = (String) request.getParameter("user_search");
		HttpSession session = request.getSession();

		ArrayList<User> userList = null;
		try {
			userList = Search.searchUsers(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("userResults", userList);
		RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp"); 
		dispatch.forward(request, response);	}

}
