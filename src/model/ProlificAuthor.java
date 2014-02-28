package model;

public class ProlificAuthor extends Achievement{

	@Override
	public String getAchievement() {
		return "Prolific Author";
	}

	@Override
	public String getDescription() {
		return "This user created five quizzes.";
	}

	@Override
	public int getKey() {
		return 2;
	}

}
