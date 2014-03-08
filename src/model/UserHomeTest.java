package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UserHomeTest {

	@Test
	public void testGetTopQuizzes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentlyCreatedQuizzes() throws Exception {
		ArrayList<Integer> recentlyCreatedQuizzes = UserHome.getRecentlyCreatedQuizzes(5);
	}

	@Test
	public void testGetRecentlyPlayedQuizzes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentlyCreatedQuizzesByUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentlyCreatedByFriends() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentlyTakenByFriends() {
		fail("Not yet implemented");
	}

}
