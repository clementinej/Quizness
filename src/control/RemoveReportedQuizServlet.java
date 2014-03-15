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

/**
 * Servlet implementation class RemoveReportedQuizServlet
 */
@WebServlet("/RemoveReportedQuizServlet")
public class RemoveReportedQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveReportedQuizServlet() {
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
		String[] quizIDList = request.getParameterValues("check"); 
		String removeQuizzes = request.getParameter("remove");
		
		if(quizIDList != null){
			for(int i = 0; i < quizIDList.length; i++){
				int quizID = Integer.parseInt(quizIDList[i]); 
				try {
					Quiz.removeFromReported(quizID);
					if(removeQuizzes != null) Delete.deleteQuiz(quizID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
		String target = "reported-quizzes.jsp";
		RequestDispatcher dispatch = request.getRequestDispatcher(target); 
		dispatch.forward(request, response); 
	}

}
