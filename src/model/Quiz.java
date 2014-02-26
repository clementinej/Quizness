package model;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Quiz implements Serializable{
	
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
=======
	private List<Question> questions; 
	
	public Quiz(int quizID, ArrayList<Question> questions){
		this.quizID = quizID;
		this.questions = questions;
>>>>>>> 791a7f6e649f9d6fe8f63719ef62b9ce92123b75
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
	
	static void addQuiz(Quiz quiz) throws SQLException, IOException{
		ServerConnection.addQuiz(quiz.getQuizID(), quiz);
	}

	
}
