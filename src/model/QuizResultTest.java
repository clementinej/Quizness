package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class QuizResultTest {

	@Test
	public void testGetUserPerformance() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizResult.getUserPerformance(17, 49, 5); 
	}

	@Test
	public void testGetAllPerformance() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizResult.getAllPerformance(49, 5);
	}

	@Test
	public void testGetFriendPerformance() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizResult.getFriendPerformance(17, 49, 5);
	}

	@Test
	public void testGetUserAverageScore() throws Exception {
		ServerConnection.open();
		double test = QuizResult.getUserAverageScore(17, 49); 
	}

	@Test
	public void testGetAllAverageScore() throws Exception {
		ServerConnection.open();
		double test = QuizResult.getAllAverageScore(49); 
	}

	@Test
	public void testGetFriendsAverageScore() throws Exception {
		ServerConnection.open();
		double test = QuizResult.getFriendsAverageScore(17, 49);
	}

}
