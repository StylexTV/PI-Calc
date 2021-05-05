package de.stylextv.pi.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

public class Main {
	
	private static final int DIGITS = 20000;
	
	public static void main(String[] args) throws IOException {
		System.out.print("init... |               |\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
		
		long k1 = 545140134;
		long k2 = 13591409;
		long k3 = 640320;
		long k4 = 100100025;
		long k5 = 327843840;
		long k6 = 53360;
		
		MathContext context = new MathContext(DIGITS);
		
		BigDecimal k = new BigDecimal(k6);
		
		System.out.print("█");
		
		BigDecimal x1 = new BigDecimal(k3).sqrt(context);
		BigDecimal x2 = new BigDecimal(8 * k4 * k5);
		
		System.out.print("██████████");
		
		k = k.multiply(x1);
		
		System.out.print("███");
		
		BigDecimal sum = new BigDecimal(0);
		
		BigDecimal lastResult = new BigDecimal(3);
		
		System.out.println("█");
		
		long i = 0;
		
		long lastDebugSent = 0;
		
		long startTime = System.currentTimeMillis();
		
		while(true) {
			
			BigDecimal d1 = factorial(6 * i).multiply(new BigDecimal(k2 + i * k1));
			BigDecimal d2 = factorial(i).pow(3).multiply(factorial(3 * i)).multiply(x2.pow((int) i));
			
			BigDecimal j = d1.divide(d2, context);
			
			if(i % 2 != 0) j = j.multiply(new BigDecimal(-1));
			
			sum = sum.add(j);
			
			BigDecimal result = k.divide(sum, context);
			
			int digits = countValidDigits(lastResult, result);
			
			long now = System.currentTimeMillis();
			
			if(digits == DIGITS || now - lastDebugSent > 3000) {
				long time = now - startTime;
				
				int dps = time == 0 ? 0 : (int) (digits * 1000 / time);
				
				System.out.println("digits " + digits + " dps " + dps + " time " + (time / 1000) + "s");
				
				lastDebugSent = now;
			}
			
			lastResult = result;
			
			if(digits == DIGITS) break;
			
			i++;
		}
		
		String pi = lastResult.toPlainString().substring(0, DIGITS + 1);
		
		if(pi.length() > 50) {
			writeTextFile(pi, new File("pi.txt"));
			
			System.out.println("saved to 'pi.txt'");
		} else {
			System.out.println(pi);
		}
	}
	
	private static int countValidDigits(BigDecimal oldDigits, BigDecimal newDigits) {
		int n = 0;
		
		String s1 = oldDigits.toString();
		String s2 = newDigits.toString();
		
		int l = s1.length();
		
		for(int i = 0; i < l; i++) {
			char ch = s1.charAt(i);
			
			if(ch == s2.charAt(i)) {
				n++;
			} else {
				break;
			}
		}
		
		if(n > 1) return n - 1;
		
		return n;
	}
	
	private static BigDecimal factorial(long l) {
		BigDecimal d = new BigDecimal(1);
		
		for(int i = 2; i <= l; i++) {
			d = d.multiply(new BigDecimal(i));
		}
		
		return d;
	}
	
	private static void writeTextFile(String s, File f) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		
		while(!s.isEmpty()) {
			int l = Math.min(50, s.length());
			
			writer.write(s.substring(0, l) + "\n");
			
			s = s.substring(l);
		}
		
		writer.close();
	}
	
}
