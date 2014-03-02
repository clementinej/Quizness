package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public abstract class Question implements Serializable{
	
	abstract public ArrayList<Set<String>> getAnswer();
	
	abstract public double getPoints(String[] response);
	
	abstract public String getQuestion();
	
	abstract public double getMaxPoints();
}
