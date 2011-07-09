// Factorizer Class by Michael Johnston
// 9th July 2011
//Code is public domain, do with it what you will, though i'd prefer aknowledgement (not neccessary)

package com.mickstarify.QuadraticFactorizer;

import android.util.Log;

public class factorizer {
	private static final String TAG = "myFactorizer";
	
	public final double NaN = 32133420.6984; //Magic number
	
	private int[][] factors = new int [5000][2]; //Global
	private int factorsFound = 0; //Global count of the amount of factors found
	
	private void factor ( int n ){
		if (n < 0) { n *= -1; }
		int i = 1;
		
		int pivot = (int)Math.sqrt (n) + 1;

		while ( i < pivot ){
			if (n % i == 0){
				factors[factorsFound]  [0] = i;
				factors[factorsFound++][1] = n / i;
			}
			i ++;
		}
	}
	
	private int GCD (int m, int n){
		//Euclid
		int r = m % n;
		if (r == 0){
			return n;
		}
		else {
			return GCD (n, r);
		}
	}
	
	private char signOf (int n){
		if ( n >= 0){
			return '+';
		}
		return '-';
	}
	
	int absVal (int n){
		if (n < 0){
			return n *-1;
		}		
		return n;
	}
	
	public double[] rootsOf (int a,int b,int c){
		double[] rootsOf = new double[2];
		
		if (a == 0){
			rootsOf [0] = NaN;
			rootsOf [1] = NaN;
			return rootsOf;
		}
		
		if (((b * b) - (4 * a * c)) < 0 ){
			rootsOf [0] = NaN;
			rootsOf [1] = NaN;
			return rootsOf;
		}
		
		rootsOf [0] = (double)( (-b + Math.sqrt(b*b - 4 * a * c)) / (2 * a));
		rootsOf [1] = (double)( (-b - Math.sqrt(b*b - 4 * a * c)) / (2 * a));
		
		return rootsOf;
	}
	
	public String factorize (int a, int b, int c){
		//Extremely messy hack that i dont recommend using
		if (a < 0){
			a *= -1; b *= -1; c *= -1;
		}
		else if (a == 0){
			return "NaN";
		}
		
		int f0 = 0, f1 = 0;
		
		factor ( a * c);
		if (factorsFound < 1 && c != 0){
			return "NaN";
		}
		for (int i = 0; i < factorsFound; ++i){
			//Log.v (TAG, "Testing " + factors[i][0] + " "+ factors[i][1]);
			if (c > 0){
				if ((factors[i][0] + factors[i][1]) == b){
					f0 = factors[i][0];
					f1 = factors[i][1];
					//Log.v(TAG, f0 +" and " + f1 + " Satisfy a + b");
				}
				else if ((factors[i][0] * -1) + (factors[i][1] *-1 ) == b){
					f0 = factors[i][0] *-1;
					f1 = factors[i][1] *-1;				
				}
			}
			else if (c < 0){
				if ((factors[i][0] *-1) + (factors[i][1]) == b){
					f0 = factors[i][0] *-1;
					f1 = factors[i][1];
				}
				else if ((factors[i][0]) + (factors[i][1] *-1 ) == b){
					f0 = factors[i][0];
					f1 = factors[i][1] *-1;				
				}
			}
		}
		if (c == 0){
			f0 = 0;
			f1 = b;
		}
		if (f0 == 0 && f1 == 0){ return "NaN"; }
		if (a == 1){
			if (f0 == 0){
				return String.format("n(n %c %d)", signOf(f1), absVal(f1));
			}
			else{
				return String.format("(n %c %d)(n %c %d)", signOf(f0), absVal(f0), signOf(f1), absVal(f1));
			}
		}
		else {
			//A is not equal to one... so its gets slightly tricky
			
			
			int divisor = a;
			
			int gcd0 = GCD (f0, divisor); //Greatest common Factor
			int gcd1 = GCD (f1, divisor);
			
			int r0 = a, r1 = a; //remainders
			
			if (gcd0 >= gcd1){
				if (divisor == gcd0){
					f0 = f0 / gcd0; r0 = r0 / gcd0;
					return String.format("(n %c %d)(%dn %c %d)", signOf(f0), absVal(f0), r1, signOf(f1), absVal(f1));
				}
				else {
					f0 = f0 / gcd0; r1 = r1 / gcd1;
					divisor = divisor / gcd0;					
				}
			}
			// Otherwise, we'll use GCD1
			else {
				if (divisor == gcd1){
					f1 = f1 / gcd1; r0 = r0 / gcd1;
					return String.format("(%dn %c %d)(n %c %d)", a, signOf(f0), absVal(f0), signOf(f1), absVal(f1));
				}
				else {
					f1 = f1 / gcd1; r1 = r1 / gcd1;
					divisor = divisor / gcd1;					
				}
			}
			gcd0 = GCD (f0, divisor);
			gcd1 = GCD (f1, divisor);
			
			if (gcd0 >= gcd1){
				if (divisor == gcd0){
					f0 = f0 / gcd0;  r0 = r0 / gcd0;
					return String.format("(n %c %d)(%dn %c %d)", signOf(f0), absVal(f0), r1, signOf(f1), absVal(f1));
				}
				else {
					return String.format("(%dn %c %d)(%dn %c %d)/%d", r0, signOf(f0), absVal(f0), r1, signOf(f1), absVal(f1),divisor);				
				}
			}
			else {
				if (divisor == gcd1){
					f1 = f1 / gcd1; r1 = r1 / gcd1;
					return String.format("(%dn %c %d)(n %c %d)", r0, signOf(f0), absVal(f0), signOf(f1), absVal(f1));
				}
				else {
					f1 = f1 / gcd1; r1 = r1 / gcd1;
					return String.format("(%dn %c %d)(%dn %c %d)/%d", r0, signOf(f0), absVal(f0), r1, signOf(f1), absVal(f1),divisor);			
				}
			}
		}
	}	
}