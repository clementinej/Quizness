package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;

public class Login {

	public static void notLoggedIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		if(currentUser == null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("/LoginServlet"); 
			dispatch.forward(request, response); 
		}
	}

}