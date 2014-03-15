package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Announcements {
	public Announcements(){
		
	}
	
	public static ArrayList<String> getSubject() throws Exception{
		ArrayList<String> announcements = new ArrayList<String>();
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		
		ps = con.prepareStatement("SELECT subject FROM announcements ORDER BY date");
		ResultSet rs = ps.executeQuery();
		rs.beforeFirst();
		while (rs.next()){
			announcements.add(rs.getString(1));
			System.out.println(announcements);
		}
		return announcements;
	}
	
	public static ArrayList<String> getBody() throws Exception{
		ArrayList<String> announcements = new ArrayList<String>();
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps;
		
		ps = con.prepareStatement("SELECT body FROM announcements ORDER BY date");
		ResultSet rs = ps.executeQuery();
		rs.beforeFirst();
		while (rs.next()){
			announcements.add(rs.getString(1));
			System.out.println(announcements);
		}
		return announcements;
	}
	

}
