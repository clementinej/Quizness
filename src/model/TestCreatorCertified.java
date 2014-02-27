package model;

public class TestCreatorCertified extends Achievement{

	@Override
	public String getAchievement() {
		return "Test Creator - Certified";
	}

	@Override
	public String getDescription() {
		return "This user has created 25 quizzes";
	}

	@Override
	public int getKey() {
		return 5;
	}

}
