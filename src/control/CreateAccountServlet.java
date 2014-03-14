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

import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ignore
	}

	/*
	 * Gets a login, password, and email from the user, and creates a new account on success.  On failure, is redirected to
	 * to a try again page.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		QuiznessAccountManager manager = (QuiznessAccountManager) context.getAttribute("manager");
		String userName = request.getParameter("name");		
		String pw = request.getParameter("password");		
		String email = request.getParameter("email");
		
		String location = request.getParameter("location");
		String aboutMe = request.getParameter("aboutMe"); 
		String picture = request.getParameter("picture");
		
		System.out.println("In Create Servlet");
		try {
			if(manager.createNewAccount(userName, email, pw, location, aboutMe, false, picture)) {
			//if(manager.createNewAccount(userName, email, pw, false)) {//go to "user welcome" page
				//redirectToPage("site/home.html", request, response);	
				System.out.println("CreateAccountServlet: setting current user");
				User newUser = (User) session.getAttribute("current user");
				newUser = setCurrentUser(newUser, email);
				session.setAttribute("current user", newUser);
				redirectToPage("home.jsp", request, response);	
			} else {		
				redirectToPage("try-again.jsp", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * sets the user for this HTTP session 
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
	 * redirects to the page provided 
	 */
	private void redirectToPage(String pageName, HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(pageName); 
		dispatch.forward(request, response); 		
	}
	
	
}
