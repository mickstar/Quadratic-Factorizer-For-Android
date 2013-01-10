package com.mickstarify.QuadraticFactorizer;

import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.*;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;

public class QuadraticFactorizerActivity extends Activity {
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		init();
	}

	private boolean isPositive(Button sign) {
		if (sign.getText() == "+") {
			return true;
		}
		return false;
	}

	private int getATerm (){
		TextView ATerm = (TextView) findViewById (R.id.txtATerm);
		try{
			return Integer.parseInt( String.valueOf( ATerm.getText() ) );
		}
		catch (Exception e){
			System.out.printf ("[QuadraticFactorizer]Error retrieving ATerm Value! %s", e);
			//Will add an error prompt later
		}
		return 0;
	}
	private int getBTerm (){
		TextView BTerm = (TextView) findViewById (R.id.txtBTerm);
		Button BSign = (Button) findViewById (R.id.btnFirstSign);
		
		int multiplier = 1;
		
		if (!isPositive ( BSign )){
			//B Is Negative!
			multiplier = -1;
		}
		
		try{
			return Integer.parseInt( String.valueOf( BTerm.getText() ) ) * multiplier;
		}
		catch (Exception e){
			System.out.printf ("Error retrieving BTerm Value! %s", e);
			//Will add an error prompt later
		}
		return 0;
	}
	private int getCTerm (){
		TextView CTerm = (TextView) findViewById (R.id.txtCTerm);
		Button CSign = (Button) findViewById (R.id.btnSecondSign);
		
		int multiplier = 1;
		
		if (!isPositive ( CSign )){
			//B Is Negative!
			multiplier = -1;
		}
		try{
			return Integer.parseInt( String.valueOf( CTerm.getText() ) ) * multiplier;
		}
		catch (Exception e){
			System.out.printf ("Error retrieving CTerm Value! %s", e);
			//Will add an error prompt later
		}
		return 0;
	}
	
	private void init (){
		//First we will declare all our buttons
		
		final Button btnCalculate = (Button) findViewById ( R.id.btnCalculate );
		final Button btnFirstSign = (Button) findViewById ( R.id.btnFirstSign );
		final Button btnSecondSign = (Button) findViewById ( R.id.btnSecondSign );
		final Button btnClear = (Button) findViewById ( R.id.btnClear );
		
		
		btnFirstSign.setText( "+" );
		btnSecondSign.setText( "+" );
		
		btnCalculate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				calculate();
			}
		});
		
		btnClear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clearScreen();
			}
		});
		
		btnFirstSign.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isPositive(btnFirstSign)) {
					btnFirstSign.setText("-");
				} else {
					btnFirstSign.setText("+");
				}
			}
		});
		btnSecondSign.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isPositive( btnSecondSign )) {
					btnSecondSign.setText("-");
				} else {
					btnSecondSign.setText("+");
				}
			}
		});
		
		//Now we need to set all the EditText actions
		
		final TextView ATerm = (TextView) findViewById (R.id.txtATerm);
		final TextView BTerm = (TextView) findViewById (R.id.txtBTerm);
		final TextView CTerm = (TextView) findViewById (R.id.txtCTerm);
		
		//We will use this to open/close the virtual keyboard
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		ATerm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					ATerm.setText("");
					imm.showSoftInput(ATerm, 0);
				}
			}
		});
		BTerm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					BTerm.setText("");
					imm.showSoftInput(BTerm, 0);
				}
			}
		});
		BTerm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				BTerm.setText ("");
				imm.showSoftInput(BTerm, 0);
			}
		});
		CTerm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					CTerm.setText("");
					imm.showSoftInput(CTerm, 0);
				}
			}
		});
		CTerm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CTerm.setText ("");
				imm.showSoftInput(CTerm, 0);
			}
		});
	}
	
	private void calculate() {
		int a = getATerm();
		int b = getBTerm();
		int c = getCTerm();
		
		updateEquationDisplay (a, b, c);
		hideSoftKeyboard ();
		
		factorizer aFactorizer = new factorizer();
		
		String factorised = aFactorizer.factorize(a, b, c);
		
		TextView factorisedOutput = (TextView) findViewById (R.id.txtResults);
		TextView txtRoot0 = (TextView) findViewById ( R.id.txtRoot0ForN);
		TextView txtRoot1 = (TextView) findViewById ( R.id.txtRoot1ForN);
		
		if (factorised == "NaN"){
			factorisedOutput.setText ("Unable to Factor");
		}
		else{
			factorisedOutput.setText (factorised);
		}
		
		LinearLayout llRoots = (LinearLayout) findViewById(R.id.llRoots);
		
		llRoots.setVisibility(View.VISIBLE);
		
		double[] roots = aFactorizer.rootsOf(a, b, c);
		
		if (roots[0] == aFactorizer.NaN){
			txtRoot0.setText("NaN");
		}
		else{
			txtRoot0.setText (String.valueOf(roots[0]));
		}
		if (roots[1] == aFactorizer.NaN){
			txtRoot1.setText("NaN");
		}
		else{
			txtRoot1.setText (String.valueOf(roots[0]));
		}
		
	}
	
	private void clearScreen() {
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		TextView equationExample = (TextView) findViewById(R.id.txtEquationExample);
		TextView fOutput = (TextView) findViewById(R.id.txtResults);

		TextView txtRoot0 = (TextView) findViewById(R.id.txtRoot0ForN);
		TextView txtRoot1 = (TextView) findViewById(R.id.txtRoot1ForN);
		
		Button firstSign = (Button) findViewById (R.id.btnFirstSign);
		Button secondSign = (Button) findViewById (R.id.btnSecondSign);

		final EditText ATerm = (EditText) findViewById(R.id.txtATerm);
		final EditText BTerm = (EditText) findViewById(R.id.txtBTerm);
		final EditText CTerm = (EditText) findViewById(R.id.txtCTerm);

		firstSign.setText("+");
		secondSign.setText("+");

		equationExample.setText("Ax² + Bx + C");

		ATerm.setText("1");

		BTerm.setText("");
		BTerm.requestFocus();
		imm.showSoftInput(BTerm, 0);

		CTerm.setText("0");

		fOutput.setText(R.string.txtInstructions);

		txtRoot0.setText("");
		txtRoot1.setText("");

		}
	
	private void updateEquationDisplay ( int a, int b, int c){
		String equation = makeEquation (a, b, c);
		
		TextView equationDisplay = (TextView) findViewById (R.id.txtEquationExample);
		
		equationDisplay.setText ( equation );		
	}
	
	private String makeEquation ( int a, int b, int c){
		String equation;
		//We will produce each term and then combine them later
		//Note that there are spaces after each term, this is so it can all seamlessly join together
		//So that if b was empty, there wouldnt be a double space inbetween a & c
		String firstTerm, secondTerm, thirdTerm;
		
		if (a == 0){
			firstTerm = ""; //Empty, because 0x^2 will look stupid
		}
		else if (a == 1){
			//Looks better than 1x^2
			firstTerm = "x²";
		}
		else{
			firstTerm = String.format("%dx² ", a );;
		}
		if (b == 0){
			secondTerm = "";
		}
		//We have absolute values in b & c because we also have a sign infront which is either +/-
		//This is so we can have a space between and don't get that ugly "+ -2" instead of "- 2"
		else if (b == 1){
			//- x instead of - 1x
			secondTerm = String.format ("%c x ", getSign (b), Math.abs(b) );
		}
		else{
			secondTerm = String.format ("%c %dx ", getSign (b), Math.abs(b) );
		}
		if (c == 0){
			thirdTerm = "";
		}
		else{
			thirdTerm = String.format("%c %d ", getSign (c), Math.abs(c) );
		}
		
		//We will now put it all together!
		equation = String.format ("%s%s%s", firstTerm, secondTerm, thirdTerm);
		
		return equation;
	}

	private char getSign(int n) {
		if (n >= 0){
			return '+';
		}
		return '-';
	}
	
	private void hideSoftKeyboard() {
		final TextView ATerm = (TextView) findViewById (R.id.txtATerm);
		final TextView BTerm = (TextView) findViewById (R.id.txtBTerm);
		final TextView CTerm = (TextView) findViewById (R.id.txtCTerm);
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		//Force close the keyboard for all textboxs, even though only one will have it open
		imm.hideSoftInputFromWindow(ATerm.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(BTerm.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(CTerm.getWindowToken(), 0);
	}
}