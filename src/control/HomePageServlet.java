package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Achievement;
import model.Inbox;
import model.Quiz;
import model.QuizTry;
import model.User;

/**
 * Servlet implementation class HomePageServlet
 */
@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int maxNumQuizzes = 10;  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
		int userID = (Integer) session.getAttribute("userID"); 
		
		getAnnouncement(session);
		try {
			getPopularQuizzes(session);
			getRecentlyCreatedQuizzes(session);
			getRecentlyTakenQuizzes(session, userID);
			getRecentlyCreatedQuizzesByUser(session, userID);
			getAchievements(session, username);
			getMessages(session, userID);
			getFriendActivity(session, userID, 20);
			getFriendAchievement(session,  userID, username);
		} catch (Exception e) {
		}

		
		RequestDispatcher dispatch = request.getRequestDispatcher("/home.html"); 
		dispatch.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void getAnnouncement(HttpSession session){
		ArrayList<String> result = new ArrayList<String>(Arrays.asList("Unimplemented")); 
		session.setAttribute("announcements", result);
		// TODO Decide where to store the announcements
	}
	
	private void getPopularQuizzes(HttpSession session) throws Exception{
		ArrayList<Quiz> result = Quiz.toQuizzes(Quiz.getTopQuizzes(maxNumQuizzes)); 
		session.setAttribute("popular", result);
	}
	
	private void getRecentlyCreatedQuizzes(HttpSession session) throws Exception{
		ArrayList<Quiz> result = Quiz.toQuizzes(Quiz.getRecentlyCreatedQuizzes(maxNumQuizzes)); 
		session.setAttribute("recentlyCreatedQuizzes", result);
	}
	
	private void getRecentlyTakenQuizzes(HttpSession session, int userID) throws Exception{
		ArrayList<Quiz> result = Quiz.toQuizzes(Quiz.getRecentlyPlayedQuizzes(maxNumQuizzes)); 
		session.setAttribute("recentlyTakenQuizzes", result);
	}
	
	private void getRecentlyCreatedQuizzesByUser(HttpSession session, int userID) throws Exception{
		ArrayList<Quiz> result = Quiz.toQuizzes(Quiz.getRecentlyCreatedQuizzesByUser(maxNumQuizzes, userID)); 
		session.setAttribute("recentlyCreatedQuizzesByUser", result);
	}
	
	private void getAchievements(HttpSession session, String username) throws Exception{
		User user = User.getUser(username); 
		ArrayList<Achievement> result = user.getAchievements(); 
		session.setAttribute("achievements", result);
	}
	
	private void getMessages(HttpSession session, int userID) throws Exception{
		Inbox inbox = Inbox.getInbox(userID);
		session.setAttribute("inbox", inbox);
	}
	
	private void getFriendActivity(HttpSession session, int userID, int num) throws Exception{
		ArrayList<QuizTry> result = Quiz.toQuizTries(Quiz.getFriendActivities(num, userID)); 
		session.setAttribute("friend activities", result);
	}

	private void getFriendAchievement(HttpSession session, int userID, String username) throws Exception{
		User user = User.getUser(username); 
		HashSet<ArrayList<Achievement>> result = new HashSet<ArrayList<Achievement>>();
		ArrayList<User> friends = user.getFriends();
		for(int i = 0; i < friends.size(); i++){
			ArrayList<Achievement> currAchievements = friends.get(i).getAchievements();
			result.add(currAchievements);
			
		}
		session.setAttribute("friend achievement", result);
	}
}
