package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class QuizTest {
	
	private FillInTheBlank question; 
	private Set<String> firstAnswer = new HashSet<String>();
	private ArrayList<Set<String>> answers = new ArrayList<Set<String>>(); 
	private ArrayList<Question> questions = new ArrayList<Question>();
	private Quiz quiz; 
	
	//public Quiz(int creatorID, double maxScore, String description, String title, ArrayList<Question> questions, 
	//		boolean hasPracticeMode, boolean hasRandomMode, boolean hasTimedMode, boolean immediateCorrection,
	//			boolean multiplePages)
	
	private void setup(){
		ServerConnection.open();
		firstAnswer.add("George Washington");
		answers.add(firstAnswer);
		question = new FillInTheBlank("_________ is the first President of United States", answers, 1);
		questions.add(question);
		quiz = new Quiz(1, 2, "a quiz for testing", "testQuiz", questions, true, true, true, true, true); 
	}
	@Test
	public void testQuiz() {
		setup();
	}

	@Test
	public void testGetQuestion() throws Exception {		
		setup();
		int quizID = ServerConnection.addQuiz(quiz);
		Question question = ServerConnection.getQuiz(quizID).getQuestion(0);
		System.out.println("Testing getQuestion: " + question.getQuestion());
	}

	@Test
	public void testGetQuestions() {
		ArrayList<Question> questions = quiz.getQuestions(); 
		System.out.print("Testing getQuestions: " + questions.size());
	}

	@Test
	public void testGetNumQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuizID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCreatorID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMaxScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasPracticeMode() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasRandomMode() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasTimedMode() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasImmediateCorrection() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasMultiplePages() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumOfTimesPlayed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetID() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDateLastPlayed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMaxScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPracticeMode() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTimedMode() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRandom() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMultiplePages() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetImmediateCorrection() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDescription() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncrementNumOfTimesPlayed() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuiz() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDateLastPlayed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHighScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTopQuizzes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentlyPlayedQuizzes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentlyCreatedQuizzes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentlyCreatedQuizzesByUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPerformanceByDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPerformanceByScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPerformancyByTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTopPerformersIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTopPerformersIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRecentTestTakers() {
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

	@Test
	public void testRemoveQuiz() {
		fail("Not yet implemented");
	}

	@Test
	public void testToQuizzes() {
		fail("Not yet implemented");
	}

	@Test
	public void testToQuizTries() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatistics() {
		fail("Not yet implemented");
	}

	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

}
