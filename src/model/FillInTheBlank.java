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
<<<<<<< HEAD
	Set<String>[] getAnswer() {
		// TODO Auto-generated method stub
		return null;
=======
	String[] getAnswer() {
		String[] answers = new String[this.answers.size()];
		int index = 0;
		for (String answer : answers){
			answers[index] = answer;
		}
		return answers;
>>>>>>> 16de69a008f01d2925feb5c65f9aa58104a55c99
	}

	@Override
	int getPoints(String[] answers) {
		
		return 0;
	}

}
