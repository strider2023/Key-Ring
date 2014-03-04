package com.touchmenotapps.keyring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		getActionBar().hide();
		
		findViewById(R.id.sign_up_proceed_sign_in_btn)
			.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
				finish();
			}
		});
		
		findViewById(R.id.sign_up_procced_btn)
			.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SignUpActivity.this, MainActivity.class));
				finish();
			}
		});
	}
}
