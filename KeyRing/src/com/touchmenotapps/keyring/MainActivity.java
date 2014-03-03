package com.touchmenotapps.keyring;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getFragmentManager().beginTransaction()
			.replace(R.id.main_container, new OpenLockFragment())
			.commit();

	}

}
