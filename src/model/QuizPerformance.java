package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuizPerformance {

	private boolean isPracticeMode;
	private Set<User> sortedByTime;
	private Set<User> sortedByScore;
	private Map<Integer, List<QuizTry>> pastPerformance;
	
	public QuizPerformance(boolean isPracticeMode){
		this.isPracticeMode = isPracticeMode;
	}
	
	public void addQuizTry(int userID, QuizTry quizTry){
		
	}
	
	public List<QuizTry> getPerformanceByDate(int userID){
		return null;
	}
	
	public List<QuizTry> getPerformanceByScore(int userId){
		return null;
	}
	
	public List<QuizTry> getPerformancyByTime(int userID){
		return null;
	}
	
	public List<Integer> getAllTimeTopPerformers(int numOfUsers){
		return null;
	}
	
	public List<Integer> getLastDayTopPerformers(int numOfUsers){
		return null;
	}
	
	public List<Integer> getLastDayTopPerformers(){
		return null;
	}
	
	public List<Integer> getRecentTestTakers(int numOfUsers){
		return null;
	}
	
	public Summary getSummaryStatistics(){
		return null; 
	}
	
	private class Summary {
		
	}
	
}
