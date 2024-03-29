package model;

import java.io.Serializable;

public abstract class Achievement implements Serializable {
	
	abstract public String getAchievement();
	
	abstract public String getDescription();
	
	abstract public int getKey();
	
	/*
	 * KEY
	 * 1 - Amateur Author
	 * 2 - Prolific Author
	 * 3 - Prodigious Author
	 * 4 - QuizMachine
	 * 5 - I am the Greatest
	 * 6 - Practice Makes Perfect
	 */

}
