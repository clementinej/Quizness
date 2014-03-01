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
	//private int createTime;
	private boolean hasPracticeMode;
	private boolean hasRandomMode; 
	private boolean hasTimedMode; 
	
	
	private String description; 
	private String title; 
	
	private ArrayList<Question> questions; 
	
	private double maxScore;
	
	//private ArrayList<User> topScorers;

	
	// Frequently updated informations
	private String dateLastPlayed;
	private int numOfTimesPlayed;


	
	//NOTE: CONSTRUCTOR SHOULD NOT STORE QUIZ ID.
	public Quiz(int quizID, int creatorID, int maxScore, String description, String title, ArrayList<Question> questions, 
			boolean hasPracticeMode, boolean hasRandomMode, boolean hasTimedMode){
		this.questions = questions;
		this.creatorID = creatorID; 
		this.maxScore = maxScore;
		
		this.hasPracticeMode = hasPracticeMode;
		this.hasRandomMode = hasRandomMode;
		this.hasTimedMode = hasTimedMode; 
		
		this.numOfTimesPlayed = 0;
		
		//this.topScorers = new ArrayList<User>();
		this.description = description; 
		this.title = title; 

	}
	
	// Return a question specified by the index
	public Question getQuestion(int index){
		return questions.get(index);
	}
	
	// Return the number of questions
	public int getNumQuestions(){
		return questions.size(); 
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
	
	// Return the ID of the quiz
	public int getQuizID(){
		return quizID;
	}
	
	// Return the ID of the creator
	public int getCreatorID(){
		return creatorID;
	}
	
	// Return the maximum score achievable on this quiz
	public double getMaxScore(){
		return maxScore; 
	}
	
	// Returns true if practice mode is available
	public boolean hasPracticeMode(){
		return hasPracticeMode;
	}
	
	// Returns true if random mode is available
	public boolean hasRandomMode(){
		return hasRandomMode;
	}
	
	// Returns true if timed mode is available
	public boolean hasTimedMode(){
		return hasTimedMode;
	}
	
	// Return the description of the quiz
	public String getDescription(){
		return description;
	}
	
	// Return the title of the quiz
	public String getTitle(){
		return title;
	}
	
	// Return the number of times this quiz was played 
	public int getNumOfTimesPlayed(){
		return numOfTimesPlayed; 
	}
	
	// Set the maximum score achievable on this quiz
	public void setMaxScore(int maxScore){
		this.maxScore = maxScore; 
	}
	
	// Set if the practice mode is available
	public void setPracticeMode(int userID, boolean hasPracticeMode){
		if(userID == creatorID){	
			this.hasPracticeMode = hasPracticeMode;
		}
	}
	
	// Set if the timed mode is available	
	public void setTimedMode(int userID, boolean hasTimedMode){
		if(userID == creatorID){	
			this.hasTimedMode = hasTimedMode;
		}
	}
	
	// Set if the random mode is available
	public void setRandom(int userID, boolean hasRandomMode){
		if(userID == creatorID){
			this.hasRandomMode = hasRandomMode;
		}
	}
	
	// Set the description of the quiz
	public void setDescription(int userID, String description){
		if(userID == creatorID){
			this.description = description;
		}
	}
	
	// Set the title of the quiz
	public void setTitle(int userID, String title){
		if(userID == creatorID){ 
			this.title = title;
		}	
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
	
	// Return the top x number of quizzes
	public void getTopQuizzes(int num){
		
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
