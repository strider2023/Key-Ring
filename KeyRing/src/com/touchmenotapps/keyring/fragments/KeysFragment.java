package com.touchmenotapps.keyring.fragments;

import com.touchmenotapps.keyring.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KeysFragment extends Fragment {

	private View mHolder;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mHolder = inflater.inflate(R.layout.fragment_manage_keys, null);
		return mHolder;
	}
}
