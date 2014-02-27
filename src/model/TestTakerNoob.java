package model;

public class TestTakerNoob extends Achievement{

	@Override
	public String getAchievement() {
		return "Test Taker - Noob";
	}

	@Override
	public String getDescription() {
		return "This user has taken a quiz.";
	}

	@Override
	public int getKey() {
		return 2;
	}

}
