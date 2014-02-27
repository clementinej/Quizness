package model;

public class QuizMachine extends Achievement {

	@Override
	public String getAchievement() {
		return "Quiz Machine";
	}

	@Override
	public String getDescription() {
		return "The user took ten quizzes.";
	}

	@Override
	public int getKey() {
		return 4;
	}

}
