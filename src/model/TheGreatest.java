package model;

public class TheGreatest extends Achievement{

	@Override
	public String getAchievement() {
		return "I am the Greatest";
	}

	@Override
	public String getDescription() {
		return "The user had the highest score on a quiz.";
	}

	@Override
	public int getKey() {
		return 5;
	}

}
