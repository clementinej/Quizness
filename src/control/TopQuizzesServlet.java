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

import model.Quiz;

/**
 * Servlet implementation class TopQuizzesServlet
 */
@WebServlet("/TopQuizzesServlet")
public class TopQuizzesServlet extends HttpServlet {
	
	private static final int DEFAULT_NUM_OF_QUIZZES = 20; 
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopQuizzesServlet() {
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
		HttpSession session = request.getSession();
		int numOfQuizzes = (Integer) session.getAttribute("numOfQuizzes");
		if(numOfQuizzes == 0) numOfQuizzes = DEFAULT_NUM_OF_QUIZZES; 
		try {
			ArrayList<Quiz> topQuizzes = Quiz.iDToQuizzes(Quiz.getTopQuizzes(numOfQuizzes));
			session.setAttribute("topQuizzes", topQuizzes);
		} catch (Exception e) {}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(page); 
		dispatch.forward(request, response); 
	}

}
