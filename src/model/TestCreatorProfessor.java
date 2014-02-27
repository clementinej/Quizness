package model;

public class TestCreatorProfessor extends Achievement{

	@Override
	public String getAchievement() {
		return "Test Taker - Professor";
	}

	@Override
	public String getDescription() {
		return "This user has created over 50 quizzes. Probably a professor";
	}

	@Override
	public int getKey() {
		return 7;
	}

}
