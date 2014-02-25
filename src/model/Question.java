package model;

import java.util.ArrayList;
import java.util.Set;

public abstract class Question {
	
	abstract public ArrayList<Set<String>> getAnswer();
	
	abstract public double getPoints(ArrayList<String> response);
	
	abstract public String getQuestion();
}
