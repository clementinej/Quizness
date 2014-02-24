package model;

import java.util.ArrayList;
import java.util.Set;

public class FillInTheBlank extends Question {

	private String question;
	private Answers answers;
	private double pointValue;
	
	public FillInTheBlank(String question, ArrayList<Set<String>> answers, double pointValue){
		this.question = question;
		this.answers = new Answers(answers);
		this.pointValue = pointValue;
	}
	
	@Override
	public ArrayList<Set<String>> getAnswer() {
		return answers.getValue();
	}

	@Override
	public double getPoints(String[] responses) {
		double fractionCorrect = answers.getNumCorrect(responses) / answers.numEntries();
		return fractionCorrect * pointValue;
	}

	@Override
	public String getQuestion() {
		return question;
	}

}
