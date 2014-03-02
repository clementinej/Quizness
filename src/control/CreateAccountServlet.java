package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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
		HttpSession session = request.getSession();
		QuiznessAccountManager manager = (QuiznessAccountManager) context.getAttribute("manager");
		String userName = request.getParameter("login");		
		String pw = request.getParameter("password");		
		String email = request.getParameter("email");
		
		try {
			if(manager.createNewAccount(userName, email, pw, false)) {//go to "user welcome" page
				redirectToPage("site/home.html", request, response);	
			} else {		
				redirectToAccountIsInUse(response, userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void redirectToPage(String pageName, HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(pageName); 
		dispatch.forward(request, response); 		
	}

	private void redirectToAccountIsInUse(HttpServletResponse response, String userName) throws IOException {
		PrintWriter out = response.getWriter();
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
