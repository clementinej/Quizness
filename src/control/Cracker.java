package control;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	
	private static CountDownLatch latch;
	private int maxLength;
	private String hashValue;
	
	public Cracker(String hashValue, int maxLength, int numThreads) {
		this.maxLength = maxLength;
		this.hashValue = hashValue;
		latch = new CountDownLatch(numThreads);
	}
	
	class Worker extends Thread {
		private int startIndex;
		private int endIndex;
		
		public Worker(int start, int end) {
			this.startIndex = start;
			this.endIndex = end;
		}
		
		@Override
		public void run() {
			//recursive search from the indecies you were assigned
			for(int i = startIndex; i < endIndex; i++) {//for each index that this thread should check start
				String potential_pw = "" + CHARS[i];
				tryStuff(potential_pw);
			}
			latch.countDown();
		}
		
	}
	
	private void tryStuff(String potential_pw) {
		if(potential_pw.length() == maxLength + 1) //if more than the required length, don't try
			return;
		else {
			for(int i = 0; i < CHARS.length; i++) {
				String potential_hash = generateHash(potential_pw);
				if(potential_hash.equals(hashValue)) {
					System.out.println(potential_pw);
					return;
				}
				String newPotential_pw = potential_pw + CHARS[i];
				tryStuff(newPotential_pw);
			}
		}
	}
	
	public static void main(String[] args) {
		//one thread for a specific index
		if(args.length == 1) 
		//1) Generation Mode -> String to hash
		//only main thread is used
			System.out.println(generateHash(args[0]));
		
		else {
			String hash = args[0];
			int maxLen = Integer.parseInt(args[1]);
			int nThreads = Integer.parseInt(args[2]);
			Cracker cracker = new Cracker(hash, maxLen, nThreads);
			latch = new CountDownLatch(nThreads);
			
			int portion = round((double)(CHARS.length / nThreads));
					
			for(int i = 0; i < nThreads; i++) {//divides almost evenly
				int endIndex;
				
				if(CHARS.length - (i * portion) <= portion) endIndex = CHARS.length;
				else endIndex = ((i) * portion) + portion;
				int startIndex = i * portion;
				Worker worker = cracker.new Worker(startIndex, endIndex);
				worker.start();
			}
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("all done");
		}
		
	}
	
	//******NEED THIS*********//
	private static String generateHash(String word) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] mdbytes = word.getBytes();
        mdbytes = md.digest(mdbytes);
  //      byte[] hexWord = hexToArray(word);
        String hashedText = hexToString(mdbytes);
		return hashedText;
	}
	
	private static int round(double num) {
		int cutOffDecimal = (int)num;
		double decimal = num - (double)cutOffDecimal;
		
		if(decimal > 0.5)
			return cutOffDecimal +1;
		return cutOffDecimal;
	}
	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
	// molly 4181eecdb7a755d19fdf73887c54837cbecf63fd

}
