package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class FillInTheBlankTest {

	@Test
	public void test() {
		String q = "The 16th president was _____. The 1st was _____.";
		
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
		
		String[] response = {"Lincoln", "George"};
		
		System.out.println(question.getAnswer());
		System.out.println(question.getPoints(response));
		assertTrue(1.5 == question.getPoints(response));
	}

}
