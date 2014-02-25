package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class QuestionResponseTest {

	@Test
	public void test() {
		String q = "Who was the 16th president? Who was the first?";
		
		ArrayList<Set<String>> answer = new ArrayList<Set<String>>();
		Set<String> a = new HashSet<String>();
		a.add("Abe");
		a.add("Abraham");
		answer.add(a);
		Set<String> b = new HashSet<String>();
		b.add("Washington");
		b.add("George");
		answer.add(b);
		
		double pointValue = 3;
		FillInTheBlank question = new FillInTheBlank(q, answer, pointValue);
		
		String[] response = {"Abe", "George"};
		
		System.out.println(question.getAnswer());
		System.out.println(question.getPoints(response));
		assertTrue(3 == question.getPoints(response));
	}

}
