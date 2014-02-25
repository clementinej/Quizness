package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//import com.sun.tools.javac.util.List;

public class Answers {
	
	private ArrayList<Set<String>> answers;
	
	/*
	 * Questions can have multiple entries to be answered. This "Answers" class
	 * stores the answer to each entry into an index into an array list. A question that only has
	 * one entry has an Answer with an array list size of 1.
	 * Each entry can have multiple answers, which will be stored in a set. An entry with only one possible
	 * answer will have a set size of 1.
	 */
	public Answers(ArrayList<Set<String>> answers){
		this.answers = new ArrayList<Set<String>>();
		for (Set<String> possibilities : answers){
			this.answers.add(possibilities);
		}
	}
	
	//returns the arraylist of answers
	public ArrayList<Set<String>> getValue(){
		return answers;
	}
	
	//receives the user's responses and compares it to the correct answers.
	//returns the number 
	public int getNumCorrect(String[] responses){
		int correct = 0;
		for (int i = 0; i < responses.length; i++){
			Set<String> thisAnswer = answers.get(i);
			if (thisAnswer.contains(responses[i]))
				correct++;
		}
		return correct;
	}
	
	//returns the number of fillable entries. this is primarily used to determine the max score
	public int numEntries(){
		return answers.size();
	}

}
