package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Quiz;
import model.Search;
import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet() {
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
		String userName = (String) request.getParameter("search");
		HttpSession session = request.getSession();

		ArrayList<User> userList = null;
		try {
			userList = Search.searchUsers(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("resultType", "user");
		session.setAttribute("userResults", userList);
		RequestDispatcher dispatch = request.getRequestDispatcher("search-results.jsp"); 
		dispatch.forward(request, response);	
	}

}
