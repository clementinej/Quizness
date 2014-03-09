package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Inbox;
import model.Note;
import model.ServerConnection;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class AnnouncementServlet
 */
@WebServlet("/AnnouncementServlet")
public class AnnouncementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnouncementServlet() {
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
		String subject = request.getParameter("subject");
		String body = request.getParameter("body");
		
		String query = "INSERT INTO announcements (subject, body) VALUES(?,?)";
		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, subject);
		ps.setString(2, body);
		ps.executeUpdate();
		
		int id = ServerConnection.getGeneratedKey(ps);
		user.setUserID(userID);
		
		query = "UPDATE users SET user = ? WHERE id = " + userID; 
		ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
		ps.setBytes(1, convertToByteArray(user));
		ps.executeUpdate();
		System.out.println("User at "+ user.getEmail() +" added to database");
		
		Inbox inbox = new Inbox(userID); 
		Note note = new Note(1, 2, "Hi", "Hello World"); 
		int messageID = ServerConnection.addMessage(note);
		inbox.addNote(messageID);
		addInbox(inbox);
		return userID;
	}

}
