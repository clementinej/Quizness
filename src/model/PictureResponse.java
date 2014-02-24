package model;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends Question {

	private String url;
	private Answers answer; 
	private int pointValue;
	
	public PictureResponse(String url, ArrayList<Set<String>> answer, int pointValue){
		this.url = url;
		this.answer = new Answers(answer);
		this.pointValue = pointValue;
	}
	
	@Override
	public ArrayList<Set<String>> getAnswer() {
		return answer.getValue();
	}

	@Override
	public double getPoints(String[] response) {
		return pointValue * answer.getNumCorrect(response);
	}

	@Override
	public String getQuestion() {
		return url;
	}
}
