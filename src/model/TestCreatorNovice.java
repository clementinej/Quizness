package model;

public class TestCreatorNovice extends Achievement{

	@Override
	public String getAchievement() {
		return "Test Creator - Novice";
	}

	@Override
	public String getDescription() {
		return "This user has created 10 quizzes";
	}

	@Override
	public int getKey() {
		return 3;
	}

}
