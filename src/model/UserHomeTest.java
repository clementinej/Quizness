package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UserHomeTest {

	@Test
	public void testGetTopQuizzes() {
		ArrayList<Integer> test = UserHome.getTopQuizzes(5);
	}

	@Test
	public void testGetRecentlyCreatedQuizzes() throws Exception {
		ArrayList<Integer> test = UserHome.getRecentlyCreatedQuizzes(5);
	}

	@Test
	public void testGetRecentlyPlayedQuizzes() throws Exception {
		ArrayList<Integer> test = UserHome.getRecentlyPlayedQuizzes(5, 17);
	}

	@Test
	public void testGetRecentlyCreatedQuizzesByUser() throws Exception {
		ArrayList<Integer> test = UserHome.getRecentlyCreatedQuizzesByUser(5, 27); 
	}

	@Test
	public void testGetRecentlyCreatedByFriends() throws Exception {
		ArrayList<Integer> test = UserHome.getRecentlyCreatedByFriends(5, 27);
	}

	@Test
	public void testGetRecentlyTakenByFriends() throws Exception {
		ArrayList<Integer> test = UserHome.getRecentlyTakenByFriends(5, 27);
	}

}
