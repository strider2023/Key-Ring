package com.touchmenotapps.keyring.fragments;

import java.util.ArrayList;

import com.touchmenotapps.keyring.R;
import com.touchmenotapps.keyring.SettingsListItem;
import com.touchmenotapps.keyring.adapter.SettingsListAdapter;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

public class SettingsFragment extends ListFragment {
	
	private ArrayList<SettingsListItem> mSettingsData = new ArrayList<SettingsListItem>();
	private SettingsListAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new SettingsListAdapter(getActivity());
		mSettingsData.add(new SettingsListItem("Visitor Log", "Keep a track of your shared keys.", R.drawable.ic_action_visitor));
		mSettingsData.add(new SettingsListItem("Change Password", "Change your application password.", R.drawable.ic_password));
		mSettingsData.add(new SettingsListItem("Help", "A walkthrough on how to use the application.", R.drawable.ic_action_help));
		mAdapter.setListData(mSettingsData);
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getListView().setPadding((int) getResources().getDimension(R.dimen.activity_horizontal_margin), 0, 
				(int) getResources().getDimension(R.dimen.activity_horizontal_margin), 0);
	}
}
