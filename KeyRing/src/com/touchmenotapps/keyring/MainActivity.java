package com.touchmenotapps.keyring;

import java.util.ArrayList;

import com.touchmenotapps.keyring.adapter.AdapterMenuList;
import com.touchmenotapps.keyring.fragments.AboutFragment;
import com.touchmenotapps.keyring.fragments.KeysFragment;
import com.touchmenotapps.keyring.fragments.OpenLockFragment;
import com.touchmenotapps.keyring.fragments.OpenLockFragment.OnLockStateChangedListener;
import com.touchmenotapps.keyring.fragments.SettingsFragment;
import com.touchmenotapps.keyring.threads.OpenBluetoothPort;
import com.touchmenotapps.keyring.threads.OpenBluetoothPort.OnBluetoothPortOpened;

import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class MainActivity extends Activity 
	implements OnBluetoothPortOpened, OnLockStateChangedListener {
	
	private SlidingPaneLayout mDrawerLayout;
	private ListView mDrawerList;
	private AdapterMenuList mNavListAdapter;
	private ArrayList<String> data = new ArrayList<String>();
	private int currentListSelection = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setHomeButtonEnabled(true);
		
		mNavListAdapter = new AdapterMenuList(this);
		mDrawerLayout = (SlidingPaneLayout) findViewById(R.id.main_drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navigation_list);
		mDrawerLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
		mDrawerList.setAdapter(mNavListAdapter);
		mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		// Set the adapter for the list view
		data.add("Home");
		data.add("Manage Keys");
		data.add("Settings");
		data.add("About");
		mNavListAdapter.updateData(data);
		getActionBar().setSubtitle("Home");
		
		//Fetch last selected state instance
		if(savedInstanceState == null)
			mDrawerList.setItemChecked(currentListSelection, true);
		else {
			mDrawerList.setItemChecked(
					savedInstanceState.getInt("LIST_SELECTION", 0), true);
			if(savedInstanceState.getBoolean("LIST_SHOWN"))
				mDrawerLayout.openPane();
			else
				mDrawerLayout.closePane();
		}
		switchFragment(currentListSelection);

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				getActionBar().setSubtitle(data.get(pos));
				mDrawerList.setItemChecked(pos, true);
				switchFragment(pos);
				currentListSelection = pos;
				mDrawerLayout.closePane();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//Prompt to start buletooth if switched off
		if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) 
			startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(mDrawerLayout.isOpen())
				mDrawerLayout.closePane();
			else
				mDrawerLayout.openPane();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("LIST_SELECTION", currentListSelection);
		outState.putBoolean("LIST_SHOWN", mDrawerLayout.isOpen());
	}

	private void switchFragment(int pos) {
		switch(pos) {
		case 0:
			getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new OpenLockFragment())
				.commit();
			break;
		case 1:
			getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new KeysFragment())
				.commit();
			break;
		case 2:
			getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new SettingsFragment())
				.commit();
			break;
		case 3:
			getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new AboutFragment())
				.commit();
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	       if(!mDrawerLayout.isOpen())
	    	   mDrawerLayout.openPane();
	       else
	    	   finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBluetoothCommandSent() {
		
	}

	@Override
	public void onLockToggle(String lockID, byte[] data, boolean isLocked) {
		new OpenBluetoothPort(this, this, data).execute(new String[] {"keyring"});	
	}
}
