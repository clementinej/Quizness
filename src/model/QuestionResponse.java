package model;

import java.util.ArrayList;
import java.util.Set;

public class QuestionResponse extends Question {

	private String question;
	private Answers answers;
	private double pointValue;
	
	/*
	 * QuestionResponse is much like Fill In the Blank - there is only one question. There can be multiple 
	 * answers required by the creator of the test, but there can also only be one
	 */
	public QuestionResponse(String question, ArrayList<Set<String>> answers, double pointValue){
		this.question = question;
		this.answers = new Answers(answers);
		this.pointValue = pointValue;
	}
	
	//returns the set of answers
	@Override
	public
	ArrayList<Set<String>> getAnswer() {
		return answers.getValue();
	}

	//divides the number of correct answers by the number of total answers to grab the total score
	@Override
	public
	double getPoints(String[] responses) {
		double fractionCorrect = (double)answers.getNumCorrect(responses) / (double)answers.numEntries();
		return fractionCorrect * pointValue;
	}
	
	@Override
	public double getMaxPoints() {
		return pointValue;
	}

	//returns the question
	@Override
	public String getQuestion() {
		return question;
	}

}
