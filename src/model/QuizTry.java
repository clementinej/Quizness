package model;

public class QuizTry {
	private String tryID;
	private String userID;
	private String quizID;
	private Quiz quiz;
	private double timeElapsed;
	private double startTime;
	private String[][] responses;
	
	public QuizTry(String tryID, String userID, String quizID){
		this.tryID = tryID;
		this.userID = userID;
		this.quizID = quizID;
		this.quiz = Quiz.getQuiz(quizID);
		this.startTime = System.currentTimeMillis();
		this.timeElapsed = 0;
		this.responses = new String[][];
	}
	
	public Question getQuestion(int index){
		Question question =getQuestion(quiz.getQuestion(index));
		return question;
	}
	
	public void saveProgress(){
		
		timeElapsed = System.currentTimeMillis() - startTime;
	}
	
	public void restartTry(){
		startTime = System.currentTimeMillis();
	}
	
	public String getTryID(){
		return tryID;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public String getQuizID(){
		return quizID;
	}
}
