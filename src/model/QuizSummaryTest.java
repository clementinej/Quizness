package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class QuizSummaryTest {

	@Test
	public void testGetPerformanceByDate() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizSummary.getPerformanceByDate(17, 49, 5);
	}

	@Test
	public void testGetPerformanceByScore() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizSummary.getPerformanceByScore(17, 49, 5);
	}

	@Test
	public void testGetPerformancyByTime() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizSummary.getPerformancyByTime(17, 49, 5);
	}

	@Test
	public void testGetTopPerformersIntInt() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizSummary.getTopPerformers(49, 5);
	}

	@Test
	public void testGetTopPerformersIntIntInt() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizSummary.getTopPerformers(49, 5, 1);
	}

	@Test
	public void testGetRecentPerformance() throws Exception {
		ServerConnection.open();
		ArrayList<Integer> test = QuizSummary.getRecentPerformance(49, 5);
	}

	@Test
	public void testGetStatistics() {
	
	}

}
