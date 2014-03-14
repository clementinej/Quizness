package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

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
		Connection con = ServerConnection.getConnection();
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		int reviewerID = Integer.parseInt(request.getParameter("reviewerID"));
//		int ranking = Integer.parseInt(request.getParameter("rating-input"));
		String review = request.getParameter("review");
		int ranking = 5;
		//add a review to the database		
		String query = "INSERT INTO reviews (quizID, reviewerID, ranking, review, dateCreated) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			ps.setInt(1, quizID);
			ps.setInt(2, reviewerID);
			ps.setInt(3, ranking);
			ps.setString(4, review);
			ps.setTimestamp(5, timestamp);
			ps.executeUpdate();

			RequestDispatcher dispatch = request.getRequestDispatcher("quiz/quiz-summary.jsp?quiz_id=" + quizID); 
			dispatch.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	
	}

}
