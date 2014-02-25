package model;

import java.util.ArrayList;
import java.util.Set;

public class MultipleChoice extends Question {

	private String question;
	private String[] choices;
	private double pointValue;
	private Answers answer; 
	
	//Multiple choice questions support one question and one correct answer among a string of choices
	public MultipleChoice(String question, String[] choices, ArrayList<Set<String>> answer, double pointValue){
		this.question = question; 
		this.answer = new Answers(answer);
		this.pointValue = pointValue;
	}
	
	//grabs the values of the answers. can be used to print all possible answers
	@Override
	public ArrayList<Set<String>> getAnswer(){
		return answer.getValue();
	}

	//divides the number of correct answers by the number of total entries
	//the returned point value is a fraction of the user's given point value.
	//the max is the point value
	@Override
	public double getPoints(String[] response) {
		return pointValue * answer.getNumCorrect(response);
	}

	//simply returns the question
	@Override
	public String getQuestion() {
		return question;
	}
	
	//Return the possible choices as a array of strings
	public String[] getChoices(){
		return choices;
	}

}
