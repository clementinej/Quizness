package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
	
	private int quizID;
	private int creatorID;
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
	
	static Quiz getQuiz(int quizID) throws ClassNotFoundException, SQLException, IOException{
		return ServerConnection.getQuiz(quizID);
	}
	
	static void addQuiz(Quiz quiz){
		ServerConnection.addQuiz(quiz.getQuizID(), quiz);
	}

	
}
