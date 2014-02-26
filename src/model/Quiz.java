package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable{
	
	private int quizID;
	private int creatorID;
	private int createTime; 
	private ArrayList<Question> questions; 
	private ArrayList<User> topScorers;
	private double maxScore;
	
	public Quiz(int quizID, ArrayList<Question> questions){
		this.questions = questions;
		this.topScorers = new ArrayList<User>();
		this.maxScore = 0;
	}
	
	public void addQuestion(Question question){
		questions.add(question);
	}
	
	public double calculateScore(ArrayList<String[]> responses){
		double score = 0;
		for (int i = 0; i < questions.size(); i++){
			Question question = questions.get(i);
			String[] response = responses.get(i);
			score += question.getPoints(response);
		}
		return score;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public Question getQuestion(int index){
		return questions.get(index);
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
