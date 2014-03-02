package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ServerConnectionTest {
	
	private ArrayList<Question> questions = new ArrayList<Question>();	
	//private Quiz quiz = new Quiz(1, 1, "About Presidents", questions, true);
	private User user = new User(true, "Patrick", "[imagineThisButHashed]", "pYoung@stanford.edu");
	private ServerConnection con = new ServerConnection();
	

	@Test
	public void testOpen() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUser() throws Exception {
		con.open();
		//con.addUser(user.getUserID(), user);
		User currUser = con.getUser(8);
		assertEquals(currUser.getUserID(), 8);
		con.close();
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
	public void testAddQuiz() throws Exception {
		con.open();
	//	con.addQuiz(quiz.getQuizID(), quiz);
		Quiz currQuiz = con.getQuiz(8);
		assertEquals(currQuiz.getQuizID(), 8);
		con.close();
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
