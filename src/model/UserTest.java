package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UserTest {

	@Test
	public void test() {
		User u = new User(false, "Gene Oetomo", "gene", "goetomo@stanford.edu", "my name is gene", "stanford");
		System.out.println("User Name: " + u.getUserName());
		System.out.println("Password: " + u.getPassword());
		System.out.println("Email: " + u.getEmail());
		
		u.addAchievement(new AmateurAuthor());
		u.addAchievement(new QuizMachine());
		u.addAchievement(new QuizMachine());
		u.addFriend(new User(false, "Tony Wang", "tony", "tony@stanford.edu", "my name is tony", "stanford"));
		u.addFriend(new User(false, "Lloyd Lucin", "lloyd", "lglucin@stanford.edu", "my name is lloyd", "stanford"));
		u.addFriend(new User(false, "Clementine Jacoby", "clement", "cjacoby@stanford.edu", "my name is clementine", "stanford"));
		
		ArrayList<Achievement> achievements = u.getAchievements();
		ArrayList<User> friends = u.getFriends();
		
//		System.out.println("Friends:");
//		for (User f : friends){
//			System.out.println(f.getUserName());
//		}
//		System.out.println();
//		System.out.println("Achievements: ");
//		for (Achievement a : achievements){
//			System.out.print(a.getAchievement() + ": ");
//			System.out.println(a.getDescription());
//		}
		
		assertTrue(friends.size() == 3);
	}
	
	@Test
	public void getUserTest() throws Exception{
		User u = User.getUser(1);
		System.out.println(u.getUserName());
	}

}
