package model;

public class PracticeMakesPerfect extends Achievement {

	@Override
	public String getAchievement() {
		return "Practice Makes Perfect";
	}

	@Override
	public String getDescription() {
		return "The user took a quiz in practice mode";
	}

	@Override
	public int getKey() {
		return 6;
	}

}
