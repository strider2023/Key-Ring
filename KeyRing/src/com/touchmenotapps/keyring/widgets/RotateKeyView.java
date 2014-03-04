package com.touchmenotapps.keyring.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

public class RotateKeyView extends View {

	private static final int SIZE = 60;

	private SparseArray<PointF> mActivePointers;
	private Paint mPaint;

	private Paint textPaint;

	public RotateKeyView(Context context) {
		super(context);
		initView();
	}

	public RotateKeyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		mActivePointers = new SparseArray<PointF>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// set painter color to a color you like
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5.0f);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// draw all pointers
		if(mActivePointers.size() == 2) {
			canvas.drawCircle(mActivePointers.valueAt(0).x, mActivePointers.valueAt(0).y, SIZE, mPaint);
			canvas.drawCircle(mActivePointers.valueAt(1).x, mActivePointers.valueAt(1).y, SIZE, mPaint);
			canvas.drawLine(mActivePointers.valueAt(0).x, mActivePointers.valueAt(0).y, 
					mActivePointers.valueAt(1).x, mActivePointers.valueAt(1).y, mPaint);
		}
		canvas.drawText("Total pointers: " + mActivePointers.size(), 10, 40,
				textPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();
		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);
		// get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();
		switch (maskedAction) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			// We have a new pointer. Lets add it to the list of pointers
			PointF f = new PointF();
			f.x = event.getX(pointerIndex);
			f.y = event.getY(pointerIndex);
			mActivePointers.put(pointerId, f);
			break;
		case MotionEvent.ACTION_MOVE: // a pointer was moved
			for (int size = event.getPointerCount(), i = 0; i < size; i++) {
				PointF point = mActivePointers.get(event.getPointerId(i));
				if (point != null) {
					point.x = event.getX(i);
					point.y = event.getY(i);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL:
			mActivePointers.remove(pointerId);
			break;
		}
		invalidate();
		return true;
	}
}
