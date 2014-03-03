package model;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends Question {

	private String url;
	private String question;
	private Answers answer; 
	private double pointValue;
	private String questionType = "PictureResponse"; 
	
	//Picture response supports one picture as the question and one answer
	public PictureResponse(String question, String url, ArrayList<Set<String>> answer, double pointValue){
		this.question = question; 
		this.url = url;
		this.answer = new Answers(answer);
		this.pointValue = pointValue;
	}
	
	//grabs the values of the answers. can be used to print all possible answers
	@Override
	public ArrayList<Set<String>> getAnswer() {
		return answer.getValue();
	}

	//divides the number of correct answers by the number of total entries
	//the returned point value is a fraction of the user's given point value.
	//the max is the point value
	@Override
	public double getPoints(String[] response) {
		return pointValue * answer.getNumCorrect(response);
	}
	
	@Override
	public double getMaxPoints() {
		return pointValue;
	}

	//Return the question
	@Override
	public String getQuestion() {
		return question;
	}
	
	// Return the url that points to the picture
	public String getUrl(){
		return url;
	}
}
