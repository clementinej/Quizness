package model;

import java.util.ArrayList;
import java.util.Set;

public class MultipleChoice extends Question {

	private String question;
	private String[] choices;
	private int pointValue;
	private Answers answer; 
	
	
	public MultipleChoice(String question, String[] choices, ArrayList<Set<String>> answer, int pointValue){
		this.question = question; 
		this.answer = new Answers(answer);
		this.pointValue = pointValue;
	}
	
	@Override
	public ArrayList<Set<String>> getAnswer(){
		return answer.getValue();
	}

	@Override
	public double getPoints(String[] response) {
		return pointValue * answer.getNumCorrect(response);
	}

	@Override
	public String getQuestion() {
		return question;
	}
	
	public String[] getChoices(){
		return choices;
	}

}
