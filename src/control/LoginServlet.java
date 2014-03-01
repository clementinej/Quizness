package control;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ignore since we don't use

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
ServletContext context = getServletContext();
		
		//grabs the manager.  Needs a typecast because returns an object
		QuiznessAccountManager manager = (QuiznessAccountManager) context.getAttribute("manager");

		HttpSession session = request.getSession();
		
		//what the user types in to "user name" the page will be stored in "userName"
		String userName = request.getParameter("login");		
		session.setAttribute("login", userName);
		
		
		//what the user types into the "password" form will be stored in "pw"
		String pw = request.getParameter("password");
		session.setAttribute("password", pw);
		
		PrintWriter out = response.getWriter();
		
		try {
			if(manager.validLogin(userName, pw)) {//go to "user welcome" page
				RequestDispatcher dispatch = request.getRequestDispatcher("home.html"); 
				dispatch.forward(request, response); 
//			out.println("<!DOCTYPE html>");
//			out.println("<html>");
//			out.println("<head>");
//			out.println("<title>Welcome " + userName + "</title>");
//			out.println("</head>");
//			out.println("<body>");
//			out.println("<h1>Welcome " + userName + "</h1>");
//			out.println("</body>");
//			out.println("</html>");
				
			} else { //go to "Please Try Again" page
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Information Incorrect</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Please Try Again</h1>");
				out.println("<p>Either your user name or password is incorrect. Please try again.</p>");
				out.println("<form action=\"LoginServlet\" method=\"post\">");
				out.println("<p>User Name: <input type=\"text\" name=\"user name\" /></p>");
				out.println("<p>Password: <input type=\"password\" name=\"password\"/><input type=\"submit\" value=\"Login\"/ ></p>");
				out.println("</form>");
				out.println("<p><a href=\"createnewaccount.html\">Create New Account</a></p>");
				out.println("</body>");
				out.println("</html>");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
