package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Review;
import model.ServerConnection;

/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
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
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		int reviewerID = Integer.parseInt(request.getParameter("reviewerID"));
		int ranking = Integer.parseInt(request.getParameter("ranking"));
		String title = request.getParameter("title");
		String subject = request.getParameter("subject");
		
		//for testing
//		int quizID = 1;
//		int reviewerID = 9;
//		int ranking = 5;
//		String title = "this rocks";
//		String subject = "this is the best quiz i've ever taken hands down 5/5";
		
		Review review = new Review(quizID, reviewerID, ranking, title, subject);
		
		try {
			ServerConnection.addReview(review);
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz/quiz-summary.jsp?quiz_id=" + quizID); 
			dispatch.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
