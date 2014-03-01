package model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Quiz implements Serializable{
	
	// Basic quiz information
	private int quizID;
	private int creatorID;
	private int createTime;
	private boolean isPracticeMode;
	private String description; 
	private String title; 
	private ArrayList<Question> questions; 
	private double maxScore;
	private ArrayList<User> topScorers;

	
	// Frequently updated informations
	private String lastPlayTimeStamp;
	private int numOfTimesPlayed;

	
	// Provide basic performance information
	private Set<User> sortedByTime;
	private Set<User> sortedByScore;
	private Map<Integer, List<QuizTry>> pastPerformance;
	
	//NOTE: CONSTRUCTOR SHOULD NOT STORE QUIZ ID.
	public Quiz(int quizID, int creatorID, String description, ArrayList<Question> questions, boolean isPracticeMode){
		this.questions = questions;
		this.creatorID = creatorID; 
		this.isPracticeMode = isPracticeMode;
		this.maxScore = 0;
		this.numOfTimesPlayed = 0;
		this.topScorers = new ArrayList<User>();
		this.description = description; 
		
		// TODO store in the database the quiz creation time
	}
	
	// Return the ID of the quiz
	public int getQuizID(){
		return quizID;
	}
	
	// Return a question specified by the index
	public Question getQuestion(int index){
		return questions.get(index);
	}
	
	// Add question to the quiz
	public void addQuestion(int userID, Question question){
		if(userID == creatorID){
			questions.add(question);
		}
	}
	
	// Remove a question from the quiz
	public void removeQuestion(int userID, Question question){
		if(userID == creatorID){
			questions.remove(question);
		}
	}
	
	// Remove all of the questions from the quiz
	public void clearQuestions(int userID){
		if(userID == creatorID){
			questions.clear();
		}
	}
	
	// Return the description of the quiz
	public String getDescription(){
		return description;
	}
	
	// Set the description of the quiz
	public void setDescription(String description){
		this.description = description; 
	}
	
	// Set if the practice mode is available
	public void setPracticeMode(int userID, boolean isPracticeMode){
		if(userID == creatorID){
			this.isPracticeMode = isPracticeMode;
		}
	}
	
	// Returns true if practice mode is turned on
	public boolean isPracticeMode(){
		return isPracticeMode();
	}
	
	// Return the total score for this question
	public double calculateScore(ArrayList<String[]> responses){
		double score = 0;
		for (int i = 0; i < questions.size(); i++){
			Question question = questions.get(i);
			String[] response = responses.get(i);
			score += question.getPoints(response);
		}
		return score;
	}
	
	public int getNumOfTimesPlayed(){
		return numOfTimesPlayed; 
	}
	
	public void getTopQuizzes(){
		
	}
	
	public void getRecentlyPlayedQuizzes(){
		
	}
	
	public void getRecentlyCreatedQuizzes(){
		
	}
	
	public double getHighScore(){
		return 0;
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
	
	// Remove a quiz from the database
	public static void removeQuiz(int quizID) throws Exception{
		PreparedStatement ps = con.prepareStatement("DELETE FROM quizzes WHERE quizID = ?");
		ps.setInt(1, quizID);
		ps.executeQuery();
	}
	
	public void getStatistics(){
	// TODO Provide methods for getting summary statistics 
}
