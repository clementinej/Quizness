package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Quiz;
import model.Search;
import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
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
		ServletContext context = request.getServletContext();
		String userName = (String) context.getAttribute("search");
				
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title>Student Store</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Student Store</h1>");
		out.println("<p>Items available:</p>");
		out.println("<ul>");
		

		ArrayList<User> userList = null;
		try {
			userList = Search.searchUsers(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < userList.size(); i++){
			User user = userList.get(i);
			out.println("<li>");
			try {
				out.println("<a href=\"user-profile.jsp?id=" + user.getUserID() + "\">" + user.getUserName() + "</a>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println("</li>");
		}
		out.println("</ul>");
		out.println("</body>");
		out.println("</html>");		
	}

}
