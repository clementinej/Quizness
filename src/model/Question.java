package model;

import java.util.ArrayList;
import java.util.Set;

public abstract class Question {
	
	abstract public ArrayList<Set<String>> getAnswer();
	
	abstract public int getPoints(String[] response);
	
	abstract public String getQuestion();
}
