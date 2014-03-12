package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import model.ServerConnection;

public class CookieManager {
			
	public static void add(String uniqueID, int userID) throws Exception{
		Connection con = ServerConnection.getConnection();
		String query = "INSERT INTO uniqueIDs (userID, uniqueID) VALUES (?,?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, userID);
		ps.setString(2, uniqueID);
		ps.executeUpdate();
	}
	
	public static int getUserID(String key) throws Exception{
		Connection con = ServerConnection.getConnection();
		String query = "SELECT userID FROM uniqueIDs WHERE uniqueID = \"" + key + "\""; 
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}
		return -1;
	}
	
	public static void removeSessionID(String key) throws Exception{
		Connection con = ServerConnection.getConnection();
		String query = "DELETE FROM uniqueIDs WHERE uniqueID = \"" + key + "\""; 
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
	}
	
}
