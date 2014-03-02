package model;

import java.util.ArrayList;
import java.util.Set;

public class FillInTheBlank extends Question {

	private String question;
	private Answers answers;
	private double pointValue;
	
	/*
	 * FillInTheBlank will always only have one question. The number of blanks is determined
	 * from the number of answers that the user provides
	 */
	public FillInTheBlank(String question, ArrayList<Set<String>> answers, double pointValue){
		this.question = question;
		this.answers = new Answers(answers);
		this.pointValue = pointValue;
	}
	
	//grabs the values of the answers. can be used to print all possible answers
	@Override
	public ArrayList<Set<String>> getAnswer() {
		return answers.getValue();
	}

	//divides the number of correct answers by the number of total entries
	//the returned point value is a fraction of the user's given point value.
	//the max is the point value
	@Override
	public double getPoints(String[] responses) {
		double fractionCorrect = (double)answers.getNumCorrect(responses) / (double)answers.numEntries();
		return fractionCorrect * pointValue;
	}
	
	@Override
	public double getMaxPoints() {
		return pointValue;
	}

	//simply returns the question
	@Override
	public String getQuestion() {
		return question;
	}

}
