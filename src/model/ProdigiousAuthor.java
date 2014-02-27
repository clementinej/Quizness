package model;

public class ProdigiousAuthor extends Achievement{

	@Override
	public String getAchievement() {
		return "Pridigious Author";
	}

	@Override
	public String getDescription() {
		return "The user created ten quizzes.";
	}

	@Override
	public int getKey() {
		return 3;
	}

}
