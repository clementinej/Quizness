package model;

public class TestCreatorNoob extends Achievement{

	@Override
	public String getAchievement() {
		return "Test Creator - Noob";
	}

	@Override
	public String getDescription() {
		return "This user created a quiz";
	}

	@Override
	public int getKey() {
		return 1;
	}

}
