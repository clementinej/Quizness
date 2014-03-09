package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SearchTest {

	@Test
	public void searchUser() throws Exception {
		ArrayList<User> users = Search.searchUsers("t");
		for (int i = 0 ; i < users.size(); i++){
			User user = users.get(i);
			System.out.println(user.getUserName());
		}
	}
	
	@Test
	public void searchQuizzes() throws Exception {
		ArrayList<Quiz> quizzes = Search.searchQuizzes(true, "");

		for (int i = 0; i < quizzes.size(); i++){
			Quiz quiz = quizzes.get(i);
			System.out.println(quiz.getTitle());
		}
	}

}
