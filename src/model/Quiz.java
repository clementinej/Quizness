package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private boolean immediateCorrection;
	private boolean multiplePages;
	
	
	private String description; 
	private String title; 
	
	private ArrayList<Question> questions; 
	
	private double maxScore;
	
	//private ArrayList<User> topScorers;

	
	// Frequently updated informations
	private String dateLastPlayed;
	private int numOfTimesPlayed;

	public Quiz(int creatorID, double maxScore, String description, String title, ArrayList<Question> questions, 
			boolean hasPracticeMode, boolean hasRandomMode, boolean hasTimedMode, boolean immediateCorrection,
				boolean multiplePages){
		this.questions = questions;
		this.creatorID = creatorID; 
		this.maxScore = maxScore;
		
		this.hasPracticeMode = hasPracticeMode;
		this.hasRandomMode = hasRandomMode;
		this.hasTimedMode = hasTimedMode; 
		
		//TODO: add getters and setters
		this.immediateCorrection = immediateCorrection;
		this.multiplePages = multiplePages;
		
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
	
	//Returns true if immediateCorrection setting is on
	public boolean immediateCorrection() {
		return immediateCorrection();
	}
	
	//Returns true if multiple pages setting is on
	public boolean multiplePages() {
		return multiplePages;
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
			score += questions.get(i).getPoints(responses.get(i));
		}
		return score;
	}
	
	// Return the quiz given the ID
	public static Quiz getQuiz(int quizID) throws Exception{
		return ServerConnection.getQuiz(quizID);
	}
	

	public static double getHighScore(int userID, int quizID) throws Exception {
		double result = 0; 
		String query = "SELECT MAX(score) FROM quizTries "
				+ "WHERE userID = " + userID
				+ "AND WHERE quizID = " + quizID;
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		ResultSet rs = ps.getResultSet();
		while(rs.next()){
			result = rs.getDouble(1);
		}
		return result; 
	}
	
	// Return the top x number of quizzes
	public static ArrayList<Integer> getTopQuizzes(int num) throws Exception{
		String query = "SELECT quizID FROM quizzes ORDER BY numTimesTaken DESC LIMIT" + num;
		return executeQuery(query); 
	}
	
	// Return x number of recently played quizzes

	public static ArrayList<Integer> getRecentlyPlayedQuizzes(int num) throws Exception{
		String query = "SELECT quizID FROM quizzes ORDER BY dateLastPlayed DESC LIMIT" + num;
		return executeQuery(query);
	}
	
	// Return x number of recently created quizzes
	public static ArrayList<Integer> getRecentlyCreatedQuizzes(int num) throws Exception{
		String query = "SELECT quizID FROM quizzes ORDER BY dateCreated DESC LIMIT" + num;
		return executeQuery(query); 
	}
	
	// Return x number of performances on this specific quiz, order by date 
	public static ArrayList<Integer> getPerformanceByDate(int userID, int quizID, int num) throws Exception{
		String query = "SELECT quizTryID FROM quizTries WHERE userID = ?"
				+ "AND WHERE quizID = " + quizID + " ORDER BY dateCreated DESC LIMIT" + num;
		return executeQuery(query);
	}
	
	// Return x number of performances on this specific quiz, order by score 
	public static ArrayList<Integer> getPerformanceByScore(int userId, int quizID, int num) throws Exception{
		String query = "SELECT quizTryID FROM quizTries WHERE userID = ?"
				+ "AND WHERE quizID = " + quizID + " ORDER BY score DESC LIMIT" + num;
		return executeQuery(query);
	}
	
	// Return x number of performances on this specific quiz, order by time spent 

	public static ArrayList<Integer> getPerformancyByTime(int userID, int quizID, int num) throws Exception{
		String query = "SELECT quizTryID FROM quizTries WHERE userID = ?"
				+ "AND WHERE quizID = " + quizID + " ORDER BY timeSpent DESC LIMIT" + num;
		return executeQuery(query);
	}
	

	public static ArrayList<Integer> getTopPerformers(int num, int quizID) throws Exception{
		String query = "SELECT quizTryID FROM quizTries WHERE quizID = " + quizID 
				+ " ORDER BY score DESC LIMIT" + num;
		return executeQuery(query);
	}
	

	public static ArrayList<Integer> getTopPerformers(int num, int quizID, int numOfDays) throws Exception{
		String query = "SELECT quizTryID FROM quizTries WHERE quizID = " + quizID
				+ " AND WHERE dateCreated >= NOW() - INTERVAL " + numOfDays + " DAY "
				+ " ORDER BY score DESC LIMIT" + num;
		return executeQuery(query);
	}
	

	public static ArrayList<Integer> getRecentTestTakers(int num, int quizID, int numOfDays) throws Exception{
		String query = "SELECT DISTINCT userID FROM quizTries WHERE quizID = " + quizID
				+ " AND WHERE dateCreated >= NOW() - INTERVAL " + numOfDays + " DAY "
				+ " ORDER BY score DESC LIMIT" + num;
		return executeQuery(query);
	}
	
	// Remove a quiz from the database
	public static void removeQuiz(int quizID) throws Exception {
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM quizzes WHERE quizID = ?");
		ps.setInt(1, quizID);
		ps.executeQuery();
	}
	
	// Convert IDs into Quiz object
	public static ArrayList<Quiz> iDToQuizzes(ArrayList<Integer> quizIDs) throws Exception{
		ArrayList<Quiz> results = new ArrayList<Quiz>();
		int num = quizIDs.size();
		for(int i = 0; i < num; i++){
			results.add(Quiz.getQuiz(quizIDs.get(i)));
		}
		return results; 
	}
	
	public void getStatistics(){
	// TODO Provide methods for getting summary statistics 
	}
		
	
	// Private helper methods
	
	// Return a set of IDs from a ResultSet
	private static ArrayList<Integer> resultSetToArray(ResultSet rs) throws Exception{
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(rs.next()){
			result.add(rs.getInt(1)); 
		}
		return result; 
	}
	
	// Execute the given query, throws exception=
	private static ArrayList<Integer> executeQuery(String query) throws Exception{
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		return resultSetToArray(ps.getResultSet()); 
	}
}
