package model;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends Question {

	private String url;
	private Answer answer; 
	
	public PictureResponse(String url, ArrayList<Set<String>> answer){
		this.url = url;
		this.answer = new Answer(answer);
	}
	
	@Override
	public ArrayList<Set<String>> getAnswer() {
		return answer.getValue();
	}

	@Override
	public int getPoints(String[] response) {
		return pointValue * answer.getNumCorrect(response);
	}

	@Override
	public String getQuestion() {
		return url;
	}
}
