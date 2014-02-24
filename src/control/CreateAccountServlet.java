package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ignore
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		
		//grabs the manager.  Needs a typecast because returns an object
		QuiznessAccountManager manager = (QuiznessAccountManager) context.getAttribute("manager");

		HttpSession session = request.getSession();
		
		//what the user types in to "user name" the page will be stored in "userName"
		String userName = request.getParameter("user name");		
		session.setAttribute("user name", userName);

		//what the user types into the "password" form will be stored in "pw"
		String pw = request.getParameter("password");
		session.setAttribute("password", pw);

		PrintWriter out = response.getWriter();
		
		if(manager.createNewAccount(userName, pw)) {//go to "user welcome" page
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Welcome " + userName + "</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Welcome " + userName + "</h1>");
			out.println("</body>");
			out.println("</html>");
			
		} else {//go to "Account Name is in Use" page		
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Create Account</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>The Name " + userName + " is Already In Use</h1>");
			out.println("<p>Please enter another user name and password.</p>");
			out.println("<form action=\"CreateAccountServlet\" method=\"post\">");
			out.println("<p>User Name: <input type=\"text\" name=\"user name\" /></p>");
			out.println("<p>Password: <input type=\"text\" name=\"password\"/><input type=\"submit\" value=\"Login\"/></p>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");	
		}
	}
}
