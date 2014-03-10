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
import model.User;

/**
 * Servlet implementation class QuizSearchServlet
 */
@WebServlet("/QuizSearchServlet")
public class QuizSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String quizName = (String) request.getParameter("search");
		boolean popular = Boolean.parseBoolean(request.getParameter("popular"));
		System.out.println("Seaching for: " + quizName);
				
//		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE html>");
//		out.println("<head>");
//		out.println("<meta charset=\"UTF-8\" />");
//		out.println("<title>Student Store</title>");
//		out.println("</head>");
//		out.println("<body>");
//		out.println("<h1>Student Store</h1>");
//		out.println("<p>Items available:</p>");
//		out.println("<ul>");
		

		ArrayList<Quiz> quizList = null;
		try {
			quizList = Search.searchQuizzes(popular, quizName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("quizResults", quizList);
		session.setAttribute("resultType", "quiz");
		RequestDispatcher dispatch = request.getRequestDispatcher("search-results.jsp"); 
		dispatch.forward(request, response);
//		for (int i = 0; i < quizList.size(); i++){
//			Quiz quiz = quizList.get(i);
//			out.println("<li>");
//			out.println("<a href=\"show-quiz.jsp?id=" + quiz.getQuizID() + "\">" + quiz.getTitle() + "</a>");
//			out.println("</li>");
//		}
//		out.println("</ul>");
//		out.println("</body>");
//		out.println("</html>");	
	}
}
