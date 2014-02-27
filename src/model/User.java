package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private int userID;
	private boolean isAdmin;
	private String userName;
	private String pw;
	private ArrayList<Quiz> quizzesMade;
	private ArrayList<QuizTry> quizzesTried;
	private ArrayList<User> friendsList;
	private ArrayList<Achievement> achievements;
	private ArrayList<Integer> achievementKeys;
	
	public User(boolean isAdmin, String userName, String pw){
		this.isAdmin = isAdmin;
		this.pw = pw;
		this.userName = userName;
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTried = new ArrayList<QuizTry>();
		this.friendsList = new ArrayList<User>();
		this.achievements = new ArrayList<Achievement>();
		this.achievementKeys = new ArrayList<Integer>();
	}
	
	public void addAchievement(Achievement achievement){
		int key = achievement.getKey();
		if (!achievementKeys.contains(key)){
			achievementKeys.add(key);
			achievements.add(achievement);
		}
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	public User getUser(int userID) throws Exception{
		return ServerConnection.getUser(userID);
	}
	
	public void addTry(QuizTry quizTry){
		int index = quizzesTried.indexOf(quizTry);
		if (index == -1){
			quizzesTried.add(quizTry);
		} else {
			quizzesTried.set(index, quizTry);
		}
	}
	
	public int numQuizzesTaken(){
		return quizzesTried.size();
	}
	
	public int numQuizzesMade(){
		return quizzesMade.size();
	}
	
	public void addFriend(User friend){
		friendsList.add(friend);
		checkFriendAchievements();
	}
	
	//gives achievements for having friends. needs to be touched up on after we implement the friend adding system
	private void checkFriendAchievements(){
		if (friendsList.size() == 1){
			
		}
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public void makeQuiz(Quiz quiz){
		quizzesMade.add(quiz);
	}
	
	public void deleteQuiz(Quiz quiz){
		quizzesMade.remove(quiz);
	}
}
