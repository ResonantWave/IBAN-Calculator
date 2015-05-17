/* IBAN Calculator
Copyright (C) 2015 NiXijav Nitrozede

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package com.ionicbyte.ibancalculator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Main extends ActionBarActivity {
	EditText ibanInput;
	TextView displayIban;
	Button button1, buttonDelete;
	private InterstitialAd interstitialAd;
	String ccc, iban;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-yourcode");

		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
		}

		ibanInput = (EditText) findViewById(R.id.editText1);
		displayIban = (TextView) findViewById(R.id.textView1);

		ibanInput.addTextChangedListener(textChanged);
		displayIban.setText(Main.this.getString(R.string.number20));

		button1 = (Button) findViewById(R.id.button1);
		buttonDelete = (Button) findViewById(R.id.buttonDelete);

		button1.setVisibility(View.GONE);
		buttonDelete.setVisibility(View.GONE);

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				copyClipboard(iban);
			}
		});
		
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ibanInput.setText("");
				buttonDelete.setVisibility(View.GONE);
			}
		});

	}

	// 0012 0345 03 0000067890
	TextWatcher textChanged = new TextWatcher() {

		public void afterTextChanged(Editable s) {
			if (s.length() < 20) {
				displayIban.setText(Main.this.getString(R.string.write) + " "
						+ String.valueOf((20 - s.length())) + " "
						+ Main.this.getString(R.string.more));
				button1.setVisibility(View.GONE);
			} else {
				ccc = ibanInput.getText().toString();
				if (ccc.substring(i, i + 1).matches("[1234567890]")
						&& Ccc.validateCCC(ccc)) {

					iban = Iban.getIban("ES", ccc.substring(0, 4),
							ccc.substring(4, 8), ccc.substring(8, 10),
							ccc.substring(10, 20));

					displayIban.setText(Main.this.getString(R.string.code)
							+ "\n" + iban);
					button1.setVisibility(View.VISIBLE);
					buttonDelete.setVisibility(View.VISIBLE);
				} else {
					displayIban.setText(Main.this.getString(R.string.invalid));
				}
			}
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}
	};

	public void onBackPressed() {
		if (interstitialAd.isLoaded()) {
			interstitialAd.show();
		}
		super.onBackPressed();
	}

	@SuppressWarnings("deprecation")
	@TargetApi(11)
	public void copyClipboard(String text) {
		int sdk = android.os.Build.VERSION.SDK_INT;

		if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData
					.newPlainText("IBAN", text);
			clipboard.setPrimaryClip(clip);
		}
		Toast.makeText(getApplicationContext(),
				Main.this.getString(R.string.copy), Toast.LENGTH_LONG).show();
	}
}
