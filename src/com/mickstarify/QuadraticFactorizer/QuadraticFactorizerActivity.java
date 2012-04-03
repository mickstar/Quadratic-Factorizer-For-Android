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

public class QuadraticFactorizerActivity extends Activity {
	/** Called when the activity is first created. */

	Button Calculate;
	Button firstSign, secondSign;

	EditText xSquaredQuantity, xQuantity, cAmount;

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

	private void calculate() {
		xSquaredQuantity = (EditText) findViewById(R.id.valxSquaredQuantity);
		xQuantity = (EditText) findViewById(R.id.valXQuantity);
		cAmount = (EditText) findViewById(R.id.valCAmount);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// Hides the Soft Keyboard
		imm.hideSoftInputFromWindow(xSquaredQuantity.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(xQuantity.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(cAmount.getWindowToken(), 0);

		int A, B, C;

		try {
			A = Integer.parseInt(xSquaredQuantity.getText().toString());
		} catch (Exception e) {
			showError("Error could not read the A Term");
			return;
		}
		try {
			B = Integer.parseInt(xQuantity.getText().toString());
			if (!isPositive(firstSign)) {
				B *= -1;
			}
		} catch (Exception e) {
			showError("Error could not read the B term");
			return;
		}
		try {
			C = Integer.parseInt(cAmount.getText().toString());
			if (!isPositive(secondSign)) {
				C *= -1;
			}
		} catch (Exception e) {
			C = 0;
		}

		factorizer myFactorizer = new factorizer();
		String factorised = myFactorizer.factorize(A, B, C);

		TextView factorisedOutput = (TextView) findViewById(R.id.txtResults);
		TextView root0ForN = (TextView) findViewById(R.id.txtRoot0ForN);
		TextView root1ForN = (TextView) findViewById(R.id.txtRoot1ForN);
		factorisedOutput.setText(factorised);

		LinearLayout llRoots = (LinearLayout) findViewById(R.id.llRoots);

		double[] roots = myFactorizer.rootsOf(A, B, C);

		llRoots.setVisibility(View.VISIBLE);
		if (roots[0] == myFactorizer.NaN) {
			root0ForN.setText("NaN");
		} else {
			root0ForN.setText(String.format("%f", roots[0]));
		}
		if (roots[1] == myFactorizer.NaN) {
			root1ForN.setText("NaN");
		} else {
			root1ForN.setText(String.format("%f", roots[1]));
		}

		updateEquation(A, B, C);

		if (factorised == "NaN") {
			showError("Sorry i could not factor the equation, perhaps it is unfactorable");
		}
	}

	private void init() {
		Calculate = (Button) findViewById(R.id.btnCalculate);
		firstSign = (Button) findViewById(R.id.btnFirstSign);
		secondSign = (Button) findViewById(R.id.btnSecondSign);

		Button clear = (Button) findViewById (R.id.btnClear);
		
		firstSign.setText("+");
		secondSign.setText("+");

		Calculate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				calculate();
			}
		});
		firstSign.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isPositive(firstSign)) {
					firstSign.setText("-");
				} else {
					firstSign.setText("+");
				}
			}
		});
		secondSign.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (isPositive(secondSign)) {
					secondSign.setText("-");
				} else {
					secondSign.setText("+");
				}
			}
		});
		
		clear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				clearScreen();
			}
		});

		final EditText ATerm = (EditText) findViewById(R.id.valxSquaredQuantity);
		final EditText BTerm = (EditText) findViewById(R.id.valXQuantity);
		final EditText CTerm = (EditText) findViewById(R.id.valCAmount);

		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		ATerm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ATerm.setText ("");
				imm.showSoftInput(ATerm, 0);
			}
		});
		
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

	char signOfN(int n) {
		if (n < 0) {
			return '-';
		}
		return '+';
	}

	int absValOf (int n) {
		if (n < 0){
			return n * -1;
		}
		return n;
	}
	
	private void updateEquation(int a, int b, int c) {
		TextView equationView = (TextView) findViewById(R.id.txtEquationExample);

		String fTerm, sTerm, equation; // First term...secondT...thirdTerm

		if (a == 1) {
			fTerm = "x²";
		} else {
			fTerm = String.format("%dx²", a);
		}

		if (b == 1) {
			sTerm = "x";
		} else {
			sTerm = String.format("%dx", absValOf (b));
		}

		if (c == 0) {
			equation = String.format("%s %c %s", fTerm, signOfN(b), sTerm);
		} else {
			equation = String.format("%s %c %s %c %d", fTerm, signOfN(b),
					sTerm, signOfN(c), absValOf (c));
		}

		equationView.setText(equation);

	}

	private void clearScreen() {
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		TextView equationExample = (TextView) findViewById(R.id.txtEquationExample);
		TextView fOutput = (TextView) findViewById(R.id.txtResults);

		TextView txtRoot0 = (TextView) findViewById(R.id.txtRoot0ForN);
		TextView txtRoot1 = (TextView) findViewById(R.id.txtRoot1ForN);

		
		final EditText ATerm = (EditText) findViewById(R.id.valxSquaredQuantity);
		final EditText BTerm = (EditText) findViewById(R.id.valXQuantity);
		final EditText CTerm = (EditText) findViewById(R.id.valCAmount);

		firstSign.setText("+");
		secondSign.setText("+");

		equationExample.setText("Ax² + Bx + C");

		ATerm.setText("1");
		
		BTerm.setText("");
		BTerm.requestFocus();
		imm.showSoftInput(BTerm, 0);
		
		CTerm.setText("0");

		fOutput.setText("Fill in the values for A,B,C respectively");

		txtRoot0.setText("");
		txtRoot1.setText("");

	}

	private void showError(String errorMsg) {
		//Old Message box style error, quite intrusive
		/*
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(errorMsg).setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						return;
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
		 */
		//Will replace with a more subtle error message
		TextView equationOutput = (TextView) findViewById (R.id.txtResults);
		equationOutput.setText ("Unable to factor");	
	}
}