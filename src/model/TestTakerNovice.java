package model;

public class TestTakerNovice extends Achievement {

	@Override
	public String getAchievement() {
		return "Test Taker - Novice";
	}

	@Override
	public String getDescription() {
		return "This user has taken 25 quizzes";
	}

	@Override
	public int getKey() {
		return 4;
	}

}
