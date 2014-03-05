package com.touchmenotapps.keyring.fragments;

import com.touchmenotapps.keyring.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class OpenLockFragment extends Fragment implements OnDragListener {

	private View mViewHolder;
	
	private ImageView myKey;
	private TextView mStatusText;
	private RelativeLayout mParentContainer;
	private LinearLayout mLockContainer;
	private boolean isLocked = false;
	private static final String IMAGEVIEW_TAG = "The Android Logo";
	private Vibrator mVibrator;
	private OnLockStateChangedListener mCallback;
	
	public interface OnLockStateChangedListener {
		public void onLockToggle(String lockID, byte[] data, boolean isLocked);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		
		mViewHolder = inflater.inflate(R.layout.fragment_open_lock, null);
		myKey = (ImageView) mViewHolder.findViewById(R.id.open_lock_key_image);
		mStatusText = (TextView) mViewHolder.findViewById(R.id.open_lock_status_message);
		mParentContainer = (RelativeLayout) mViewHolder.findViewById(R.id.open_lock_parent_container);
		mLockContainer = (LinearLayout) mViewHolder.findViewById(R.id.open_lock_key_drop_container);
		// Sets the tag
		myKey.setTag(IMAGEVIEW_TAG);

		myKey.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View view) {
				// create it from the object's tag
				ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
				String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
				ClipData data = new ClipData(view.getTag().toString(), mimeTypes,
						item);
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

				view.startDrag(data, // data to be dragged
						shadowBuilder, // drag shadow
						view, // local data about the drag and drop operation
						0 // no needed flags
				);
				mVibrator.vibrate(50);
				view.setVisibility(View.INVISIBLE);
				return false;
			}
		});

		mParentContainer.setOnDragListener(this);
		mLockContainer.setOnDragListener(this);
		
		return mViewHolder;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnLockStateChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

	@Override
	public boolean onDrag(View v, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:
			break;
		
		case DragEvent.ACTION_DRAG_ENTERED:
			mLockContainer.setBackgroundResource(R.drawable.shape_lock_hover); 
			break;

		case DragEvent.ACTION_DRAG_EXITED:
			mLockContainer.setBackgroundResource(R.drawable.shape_lock_normal); 
			break;

		case DragEvent.ACTION_DROP:
			if(isLocked) {
				if (v == mParentContainer) {
					View view = (View) event.getLocalState();
					((ViewGroup) view.getParent()).removeView(view);
					// change the text
					mStatusText.setText("Lock opened!");
	
					RelativeLayout containView = (RelativeLayout) v;
					LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.CENTER_HORIZONTAL);
					params.setMargins(5, 5, 5, 5);
					containView.addView(view, params);
					view.setVisibility(View.VISIBLE);
				} else {
					View view = (View) event.getLocalState();
					view.setVisibility(View.VISIBLE);
					break;
				}
			} else {
				if (v == mLockContainer) {
					View view = (View) event.getLocalState();
					((ViewGroup) view.getParent()).removeView(view);
					// change the text
					mStatusText.setText("Lock closed!");
	
					LinearLayout containView = (LinearLayout) v;
					containView.addView(view);
					view.setVisibility(View.VISIBLE);
				} else {
					View view = (View) event.getLocalState();
					view.setVisibility(View.VISIBLE);
					break;
				}
			}
			break;

		case DragEvent.ACTION_DRAG_ENDED:
			mVibrator.vibrate(50);
			mLockContainer.setBackgroundResource(R.drawable.shape_lock_normal); 
			if(isLocked) {
				isLocked = false;
				mStatusText.setText(R.string.msg_drag_lock);
				mCallback.onLockToggle("default", new byte[] {0x27, 0x01, 0}, isLocked);
			} else {
				isLocked = true;
				mStatusText.setText(R.string.msg_drag_unlock);
				mCallback.onLockToggle("default", new byte[] {0x27, 0x02, 0}, isLocked);
			}
		default:
			break;
		}
		return true;
	}
}
