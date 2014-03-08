package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SearchTest {

	@Test
	public void searchUser() throws Exception {
		ArrayList<User> users = Search.searchUsers("a");
		for (int i = 0 ; i < users.size(); i++){
			User user = users.get(i);
			System.out.println(user.getUserName());
		}
	}

}
