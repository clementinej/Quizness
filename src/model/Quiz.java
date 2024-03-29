package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Quiz implements Serializable{
	private static final long serialVersionUID = -1573586149365786232L;
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
	private ArrayList<Integer> originalToRandomMap; //index = original, value = random
	
	private ArrayList<Question> questions; 
	
	private double maxScore;
	
	//private ArrayList<User> topScorers;

	
	// Frequently updated informations
	private Date dateLastPlayed;
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
		
		this.immediateCorrection = immediateCorrection;
		this.multiplePages = multiplePages;
		
		this.numOfTimesPlayed = 0;
		
		this.description = description; 
		this.title = title; 
	}
	
	/*
	 * If random, indexes of the original map to questions of the  
	 * If not random, makes indexes line up with the values.
	 * 
	 * e.g.
	 * og|rand
	 * ------ 
	 * 1 | 2
	 * 2 | 3
	 * 3 | 1
	 */
	private ArrayList<Question> makeIndexMap() {
		ArrayList<Question> questionsInOrder = new ArrayList<Question>();
		originalToRandomMap = new ArrayList<Integer>();
		if(hasRandomMode) {
			while(questionsInOrder.size() < questions.size()) {
				Random rand = new Random();
				int randomIndex = rand.nextInt(questions.size());
				if(!originalToRandomMap.contains(randomIndex)) {//TODO:MIGHT NOT BE CLEAN AT ALL!!
					originalToRandomMap.add(randomIndex);
					questionsInOrder.add(questions.get(randomIndex));
				}
			}
		} else {
			for(int i= 0; i < questions.size(); i++) {
				originalToRandomMap.add(i);
				questionsInOrder.add(questions.get(i));
			}
		}
		return questionsInOrder;
	}

	// Return a question specified by the index
	public Question getQuestion(int index){
		return questions.get(index);
	}
	
	/*
	 * Returns a list of questions.  The order in which they are returned differs
	 * every if the quiz is set to be random.
	 */
	public ArrayList<Question> getQuestions(){
		return makeIndexMap();
	}
	
	public ArrayList<Question> getNonRandomQuestions() {
		return questions;
	}
	
	// Return the number of questions
	public int getNumQuestions(){
		return questions.size(); 
	}
	
	// Add question to the quiz
	public void addQuestion(int userID, Question question) throws Exception{
		if(userID == creatorID){
			questions.add(question);
		}
		ServerConnection.updateQuiz(this);
	}
	
	// Remove a question from the quiz
	public void removeQuestion(int userID, Question question) throws Exception{
		if(userID == creatorID){
			questions.remove(question);
		}
		ServerConnection.updateQuiz(this);
	}
	
	// Remove all of the questions from the quiz
	public void clearQuestions(int userID) throws Exception{
		if(userID == creatorID){
			questions.clear();
		}
		ServerConnection.updateQuiz(this);
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
	public boolean hasImmediateCorrection() {
		return immediateCorrection;
	}
	
	//Returns true if multiple pages setting is on
	public boolean hasMultiplePages() {
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
	
	// This is only called with the quiz is inserted into the database for the first time
	public void setID(int quizID){
		this.quizID = quizID; 
	}
	
	// Set the date this quiz was last taken
	public void setDateLastPlayed(Date date) throws Exception{
		Connection con = ServerConnection.getConnection();
		this.dateLastPlayed = date;
		Timestamp timestamp = new Timestamp(date.getTime());
		String query = "UPDATE quizzes SET dateLastPlayed = ? WHERE id = " + this.quizID;
		PreparedStatement ps = con.prepareStatement(query);
		ps.setTimestamp(1, timestamp);
		ps.executeQuery(); 
	}
	
	// Set the maximum score achievable on this quiz
	public void setMaxScore(int maxScore) throws Exception{
		this.maxScore = maxScore; 
		ServerConnection.updateQuiz(this);
	}
	
	// Set if the practice mode is available
	public void setPracticeMode(int userID, boolean hasPracticeMode) throws Exception{
		if(userID == creatorID){	
			this.hasPracticeMode = hasPracticeMode;
		}
		ServerConnection.updateQuiz(this);
	}
	
	// Set if the timed mode is available	
	public void setTimedMode(int userID, boolean hasTimedMode) throws Exception{
		if(userID == creatorID){	
			this.hasTimedMode = hasTimedMode;
		}
		ServerConnection.updateQuiz(this);
	}
	
	// Set if the random mode is available
	public void setRandom(int userID, boolean hasRandomMode) throws Exception{
		if(userID == creatorID){
			this.hasRandomMode = hasRandomMode;
		}
		ServerConnection.updateQuiz(this);
	}
	
	// Set if the multiple page mode is available
	public void setMultiplePages(int userID, boolean multiplePages) throws Exception{
		if(userID == creatorID){
			this.multiplePages = multiplePages;
		}
		ServerConnection.updateQuiz(this);
	}
	
	// Set if the immediate correction mode is available
	public void setImmediateCorrection(int userID, boolean immediateCorrection) throws Exception{
		if(userID == creatorID){
			this.immediateCorrection = immediateCorrection;
		}
		ServerConnection.updateQuiz(this);
	}
	
	// Set the description of the quiz
	public void setDescription(String description) throws Exception{
		this.description = description;
		ServerConnection.updateQuiz(this);
	}
	
	// Increment the number of times that this quiz was played
	public void incrementNumOfTimesPlayed() throws Exception{
		numOfTimesPlayed++;
		Connection con = ServerConnection.getConnection();
		String query = "UPDATE quizzes SET numTimesTaken = ? WHERE id = " + this.quizID;
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, numOfTimesPlayed);
		ps.executeUpdate();
		ServerConnection.updateQuiz(this);
		System.out.println("This quiz has been played " + numOfTimesPlayed + " times.");
	}
	
	// Set the title of the quiz
	public void setTitle(String title) throws Exception{
		this.title = title;
		ServerConnection.updateQuiz(this);
	}
	
	// Return the total score for this question
	//TODO: This code assumes that the index of "questions" corresponds to the index of "responses"
	/*TODO:
	 * Maybe have a map of responses that when responses gets passed in, the index of the response 
	 * goes to a map of random indecies to normal indecies.
	 */
	public double calculateScore(ArrayList<String[]> responses){
		double score = 0;
		for (int i = 0; i < questions.size(); i++){
			System.out.println(i +"< questions.size():"+questions.size() + "and <" +responses.size());
			double toAdd = 0;
			try {
				toAdd = questions.get(originalToRandomMap.get(i)).getPoints(responses.get(i));
			} catch (Exception e) { 
	
			}
			score += toAdd;
		}
		return score;
	}
	
	// Return the quiz given the ID
	public static Quiz getQuiz(int quizID) throws Exception{
		return ServerConnection.getQuiz(quizID);
	}
	
	// Return the date last played
	public Date getDateLastPlayed(){
		return this.dateLastPlayed;
	}
	
	// Return the date this quiz was created
	// Check for runtime index error
	public Date getDateCreated() throws Exception{
		Date date = null; 
		Connection con = ServerConnection.getConnection();
		String query = "SELECT dateCreated FROM quizzes WHERE id = " + this.quizID; 
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery(); 
		while(rs.next()){
			date = rs.getTimestamp(1);
		}
		return date;
	}
	

	public static double getHighScore(int userID, int quizID) throws Exception {
		double result = 0; 
		String query = "SELECT MAX(score) FROM quizTries "
				+ "WHERE userID = " + userID
				+ " AND quizID = " + quizID;
		Connection con = ServerConnection.getConnection();
		ResultSet rs = con.prepareStatement(query).executeQuery();
		while(rs.next()){
			result = rs.getDouble(1);
		}
		return result; 
	}	
	
	
	public void updateQuestion(int qIndex, Question question) throws Exception {
		questions.set(qIndex, question);
		ServerConnection.updateQuiz(this);
	}

	public void removeQuestionAt(int qIndex) throws Exception {
		questions.remove(qIndex);
		ServerConnection.updateQuiz(this);
	}
	
	public double getTopScore() throws SQLException{
		double result = 0;
		String query = "SELECT MAX(score) FROM quizTries WHERE quizID = " + quizID;
		Connection con = ServerConnection.getConnection();
		ResultSet rs = con.prepareStatement(query).executeQuery();
		while (rs.next()){
			result = rs.getDouble(1);
		}
		System.out.println("The top score on this quiz is: ");
		return result;
	}
	
	public static void report (int userID, int quizID) throws Exception{
		String query = "INSERT INTO reportedQuizzes (userID, quizID) VALUES (?,?)";
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, userID);
		ps.setInt(2, quizID);
		ps.executeUpdate();
	}
	
	public static void removeFromReported(int quizID) throws Exception {
		String query = "DELETE FROM reportedQuizzes WHERE quizID = " + quizID; 
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
	}
	
	public static ArrayList<Integer> getReported() throws Exception{
		String query = "SELECT quizID FROM reportedQuizzes";
		Connection con = ServerConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(rs.next()){
			result.add(rs.getInt(1));
		}
		return result;
	}
}
