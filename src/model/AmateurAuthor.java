package model;

public class AmateurAuthor extends Achievement{

	@Override
	public String getAchievement() {
		return "Amateur Author";
	}

	@Override
	public String getDescription() {
		return "This user created a quiz.";
	}

	@Override
	public int getKey() {
		return 1;
	}

}
