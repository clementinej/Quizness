package model;

public abstract class Question {
	
	abstract String[] getAnswer();
	
	abstract int getPoints(String[] answers);

}
