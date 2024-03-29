package control;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ignore since we don't use
	}
	
	/*
	 * If a user provides a valid login, then log him in and set him as the current user.  
	 * Otherwise, redirect him to the try again page.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();

		QuiznessAccountManager manager = (QuiznessAccountManager) context.getAttribute("manager");
		String email = request.getParameter("login");		
		String pw = request.getParameter("password");
		String remember = request.getParameter("remember"); 
		
		try {
			if(manager.validLogin(email, pw)) {
				User newUser = (User) session.getAttribute("current user");
				newUser = setCurrentUser(newUser, email);
				session.setAttribute("current user", newUser);
				System.out.println("redirected to home.jsp");
				
				if(remember != null){
					String uniqueID = session.getId();
					CookieManager.add(uniqueID, newUser.getUserID());
					Cookie cookie = new Cookie("id", uniqueID);
					cookie.setMaxAge(1200);
					response.addCookie(cookie);
				}
				
				redirectToPage("home.jsp", request, response);
				return;
			} else { 
				redirectToPage("index.jsp?invalid_login=signal", request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/*
	 * sets current user
	 */
	private User setCurrentUser(User newUser, String email) {
		System.out.println("In setCurrentUser");
		try {
			newUser = ServerConnection.getUser(email);
			System.out.println("username = " + newUser.getUserName());
		} catch (Exception e) {
			System.out.println("skipped try");
			e.printStackTrace();
		}
		return newUser;
	}
	
	
	/*
	 * redirects to given page
	 */
	private void redirectToPage(String pageName, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(pageName); 
		dispatch.forward(request, response); 
	}	
	
	/*
	 * redirects to try again page
	 */
	private void redirectToTryAgain(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Information Incorrect</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Please Try Again</h1>");
		out.println("<p>Either your user name or password is incorrect. Please try again.</p>");
		out.println("<form action=\"LoginServlet\" method=\"post\">");
		out.println("<p>Email: <input type=\"text\" name=\"login\" /></p>");
		out.println("<p>Password: <input type=\"password\" name=\"password\"/><input type=\"submit\" value=\"Login\"/ ></p>");
		out.println("</form>");
		out.println("<p><a href=\"create-account.jsp\">Create New Account</a></p>");
		out.println("</body>");
		out.println("</html>");	
	}
}
