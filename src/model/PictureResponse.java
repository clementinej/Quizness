package model;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends Question {

	private String url;
	private Answers answer; 
	private double pointValue;
	
	//Picture response supports one picture as the question and one answer
	public PictureResponse(String url, ArrayList<Set<String>> answer, double pointValue){
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

	//Return the url that points to the picture
	@Override
	public String getQuestion() {
		return url;
	}
}
