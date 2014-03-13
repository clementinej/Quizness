package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberConverter {

	public static String toString(double num){
		String result = null;
		
		BigDecimal bd = new BigDecimal(num);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		double rounded = bd.doubleValue(); 
		if(rounded == 0.00) return "0 points"; 
		if(rounded == 1.00){
			result = "1 point"; 
		} else {
			result = rounded + " points"; 
		}
		return result; 
	}
	
	public static double roundToTwoPlaces(double num){
		BigDecimal bd = new BigDecimal(num);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue(); 
	}
}
