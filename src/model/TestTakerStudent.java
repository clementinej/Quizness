package model;

public class TestTakerStudent extends Achievement {

	@Override
	public String getAchievement() {
		return "Test Taker - Student";
	}

	@Override
	public String getDescription() {
		return "This user has taken over 100 quizzes. Probably a student";
	}

	@Override
	public int getKey() {
		return 8;
	}

}
