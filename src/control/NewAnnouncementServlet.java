package control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

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
@WebServlet("/NewAnnouncementServlet")
public class NewAnnouncementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAnnouncementServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		ServerConnection.open();
		Connection con = ServerConnection.getConnection();
		String subject = request.getParameter("subject");
		String body = request.getParameter("body");
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		try {
			insertAnnouncement(con, subject, body, timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertAnnouncement(Connection con, String subject, String body, Timestamp t) throws Exception{
		String query = "INSERT INTO announcements (subject, body, date) VALUES(?,?,?)";

		PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, subject);
		ps.setString(2, body);
		ps.setTimestamp(3, t);
		ps.executeUpdate();
		int id = ServerConnection.getGeneratedKey(ps);

		query = "UPDATE announcements SET id = ? WHERE id = " + id; 
		ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
		ps.executeUpdate();
		
	}

}
