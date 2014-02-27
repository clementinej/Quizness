package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Quiz implements Serializable{
	
	private int quizID;
	private int creatorID;
	private int createTime; 
	private String description; 
	private ArrayList<Question> questions; 
	private double maxScore;
	
	private static Set<Quiz> 
	
	private QuizPerformance quizPerformance;
	
	public Quiz(int quizID, int creatorID, String description, ArrayList<Question> questions, boolean isPracticeMode){
		this.questions = questions;
		this.creatorID = creatorID; 
		this.quizPerformance = new QuizPerformance(isPracticeMode);
		this.maxScore = 0;
		this.description = description; 
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
	public void setPracticeMode(boolean isPracticeMode){
		quizPerformance.setPracticeMode(isPracticeMode);
	}
	
	// Returns true if practice mode is turned on
	public boolean isPracticeMode(){
		return quizPerformance.isPracticeMode();
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
	
	public void getTopQuizzes(){
		
	}
	
	public void getRecentlyPlayedQuizzes(){
		
	}
	
	public void getRecentlyCreatedQuizzes(){
		
	}
	
	public void getHighScore(){
		
	}
}
