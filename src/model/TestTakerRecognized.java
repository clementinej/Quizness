package model;

public class TestTakerRecognized extends Achievement{

	@Override
	public String getAchievement() {
		return "Test Taker - Recognized";
	}

	@Override
	public String getDescription() {
		return "This user is known to like taking quizzes. At least 50 of them";
	}

	@Override
	public int getKey() {
		return 6;
	}

}
