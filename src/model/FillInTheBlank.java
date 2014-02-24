package model;

import java.util.Set;

public class FillInTheBlank extends Question {

	private String question;
	private Set<String> answers;
	private int numBlanks;
	
	public FillInTheBlank(String question, Set<String> answers, int numBlanks){
		this.question = question;
		this.answers = answers;
		this.numBlanks = numBlanks;
	}
	
	@Override
	String[] getAnswer() {
		String[] answers = new String[this.answers.size()];
		int index = 0;
		for (String answer : answers){
			answers[index] = answer;
		}
		return answers;
	}

	@Override
	int getPoints(String[] answers) {
		
		return 0;
	}

}
