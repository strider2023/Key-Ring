package com.touchmenotapps.keyring.adapter;

import java.util.ArrayList;

import com.touchmenotapps.keyring.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterMenuList extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<String> mData = new ArrayList<String>();
	
	/**
	 * 
	 * @param context
	 */
	public AdapterMenuList(Context context) {
		mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * 
	 * @param data
	 */
	public void updateData(ArrayList<String> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_options_view, null);
			holder.mSettingsText = (TextView) convertView.findViewById(R.id.adapter_options_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}		
		holder.mSettingsText.setText(mData.get(position));
		return convertView;
	}
	
	static class ViewHolder {
		TextView mSettingsText;
	}
}
