package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Delete;
import model.Quiz;
import model.ServerConnection;

/**
 * Servlet implementation class ClearQuizHistoryServlet
 */
@WebServlet("/ClearQuizHistoryServlet")
public class ClearQuizHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearQuizHistoryServlet() {
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
		int quizID = Integer.parseInt(request.getParameter("quiz_id"));
		try {
			Quiz quiz = ServerConnection.getQuiz(quizID);
			Delete.clearQuizTryHistory(quiz);
			System.out.println("Cleared quiz history");
			redirectToPage("home.jsp", request, response);
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
