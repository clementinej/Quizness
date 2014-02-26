package model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ServerConnectionTest {
	
	private ArrayList<Question> questions = new ArrayList<Question>();	
	private Quiz quiz = new Quiz(1, questions);
	private ServerConnection con = new ServerConnection();
	

	@Test
	public void testOpen() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddQuiz() throws SQLException, IOException, ClassNotFoundException {
		con.open();
		con.addQuiz(quiz.getQuizID(), quiz);
		
		Quiz currQuiz = con.getQuiz(1);
		
		System.out.print(currQuiz.getQuizID());
	}

	@Test
	public void testRemoveQuiz() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuiz() {
		fail("Not yet implemented");
	}

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

}
