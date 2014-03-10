package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Delete;
import model.ServerConnection;

/**
 * Servlet implementation class AdminEditServlet
 */
@WebServlet("/AdminEditServlet")
public class AdminEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEditServlet() {
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
		String type = (String) request.getParameter("quiz_search");
		int userID = (Integer) request.getAttribute("user_ID");
		int quizID = (Integer) request.getAttribute("quiz_ID");
		
		if (type.equals("quiz")){
			try {
				Delete.deleteQuiz(quizID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Delete.deleteUser(userID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

}
