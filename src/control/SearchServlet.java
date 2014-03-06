package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Quiz;
import model.ServerConnection;
import model.User;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
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
		ServletContext context = request.getServletContext();
		int numElem = (Integer) context.getAttribute("number of elements");
		int searchFor = (Integer) context.getAttribute("search for");
		String searchElem = (String) context.getAttribute("search name");
		int searchType = (Integer) context.getAttribute("search type");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title>Student Store</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Student Store</h1>");
		out.println("<p>Items available:</p>");
		out.println("<ul>");
		
		switch(searchFor){
		case SEARCH_QUIZZES:
			ArrayList<Quiz> quizList;
			if (searchType == SEARCH_RECENT){
				quizList = HomePageServlet.getRecentlyTakenQuizzes(searchElem);
			} else {
				quizList = ServerConnection.getPopularQuizzes(searchElem);
			}
			if (quizList.size() < numElem)
				numElem = quizList.size();
			for (int i = 0; i < numElem; i++){
				Quiz quiz = quizList.get(i);
				out.println("<li>");
				out.println("<a href=\"show-quiz.jsp?id=" + quiz.getQuizID() + "\">" + quiz.getTitle() + "</a>");
				out.println("</li>");
			}
			
		//should we have a way of ordering users when we search them?
		case SEARCH_USERS:
			ArrayList<User> userList = ServerConnection.getUserList(searchElem);
			if (userList.size() < numElem)
				numElem = userList.size();
			for (int i = 0; i < numElem; i++){
				User user = userList.get(i);
				out.println("<li>");
				out.println("<a href=\"user-profile.jsp?id=" + user.getUserID() + "\">" + user.getUserName() + "</a>");
				out.println("</li>");
			}
			
		}
		out.println("</ul>");
		out.println("</body>");
		out.println("</html>");		
	}
	
	private static final int SEARCH_QUIZZES = 1;
	private static final int SEARCH_USERS = 2;
	private static final int SEARCH_RECENT = 3;
	private static final int SEARCH_POPULAR = 4;
}
