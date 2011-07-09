package com.mickstarify.QuadraticFactorizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.*;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;

public class QuadraticFactoriserActivity extends Activity {
    /** Called when the activity is first created. */
    
    Button Calculate;
    Button firstSign, secondSign;
    
    EditText xSquaredQuantity, xQuantity, cAmount;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        init();
    }
    
    private boolean isPositive (Button sign){
    	if (sign.getText() == "+") {
    		return true;    		
    	}
    	return false;
    }
    
    private void calculate (){
    	xSquaredQuantity = (EditText) findViewById (R.id.valxSquaredQuantity);
    	xQuantity = (EditText) findViewById (R.id.valXQuantity);
    	cAmount = (EditText) findViewById (R.id.valCAmount);
    	
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	//Hides the Soft Keyboard
    	imm.hideSoftInputFromWindow(xSquaredQuantity.getWindowToken(), 0);
    	imm.hideSoftInputFromWindow(xQuantity.getWindowToken(), 0);
    	imm.hideSoftInputFromWindow(cAmount.getWindowToken(), 0);
    	
    	int A, B, C;
    	
    	try {
    		A = Integer.parseInt(xSquaredQuantity.getText ().toString());
    	}
    	catch (Exception e){
    		showError ();
    		return;
    	}
    	try {
    		B = Integer.parseInt(xQuantity.getText ().toString());
    		if (!isPositive (firstSign)){
    			B *= -1;
    		}
    	}
    	catch (Exception e){
    		showError ();
    		return;
    	}
    	try {
    		C = Integer.parseInt(cAmount.getText ().toString());
    		if (!isPositive (secondSign)){
    			C *= -1;
    		}
    	}
    	catch (Exception e){
    		C = 0;
    	}
    	
    	factorizer myFactorizer = new factorizer ();
    	String factorised = myFactorizer.factorize (A,B,C);
    	
    	TextView factorisedOutput = (TextView)findViewById (R.id.txtResults);
    	TextView root0ForN = (TextView)findViewById (R.id.txtRoot0ForN);
    	TextView root1ForN = (TextView)findViewById (R.id.txtRoot1ForN);
    	factorisedOutput.setText (factorised);
    	
    	LinearLayout llRoots = (LinearLayout)findViewById (R.id.llRoots);
    	
    	double[] roots = myFactorizer.rootsOf(A, B, C);
    	
    	llRoots.setVisibility(View.VISIBLE);
    	if (roots[0] == myFactorizer.NaN){
    		root0ForN.setText ("NaN");
    	}else{
    		root0ForN.setText(String.format ("%f", roots[0]));
    	}
    	if (roots[1] == myFactorizer.NaN){
    	root1ForN.setText("NaN");  
    	}
    	else {
    		root1ForN.setText(String.format ("%f", roots[1]));
    	}
    	
    	
    	if (factorised == "NaN"){
    		showError();
    	}
    }
    
    
    private void init (){
    	Calculate = (Button) findViewById (R.id.btnCalculate);
    	firstSign = (Button) findViewById (R.id.btnFirstSign);
    	secondSign = (Button) findViewById (R.id.btnSecondSign);
    	
    	firstSign.setText("+");
    	secondSign.setText ("+");
    	
    	Calculate.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
				calculate ();
			}
		});
    	firstSign.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isPositive(firstSign)){
					firstSign.setText("-");
				}
				else {
					firstSign.setText ("+");					
				}
			}
		});
    	secondSign.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isPositive(secondSign)){
					secondSign.setText("-");
				}
				else {
					secondSign.setText ("+");					
				}
			}
		});    	
    }
    
    private void showError (){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Sorry, i couldnt interpret the equation, please check it")
    	       .setCancelable(false)
    	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   return;
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
    	
    }
}