package com.leon.assistivetouch.main.ui;


import com.leon.assistivetouch.main.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


/** 
 * 类名      TouchDotView.java
 * 说明   description of the class
*/
public class TouchDotView extends LinearLayout{

	private static final String TAG = "TouchDotView";

	private GestureDetector mGestureDetector;
	private ImageView mTopViewIconImg;
	private Context mContext;
	private OnTouchDotViewListener mOnTouchDotViewListener;

	private int upx = 0;
	private int upy = 0;
	private int downx =0;
	private int downy =0;
	private int x =0;
	private int y =0;
	
	public TouchDotView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	public TouchDotView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}



	private void init () {
		mGestureDetector = new GestureDetector(mContext, mOnGestureListener);
		inflate(mContext, R.layout.touch_dot_view, this);
		initView ();
	}
	
	private void initView () {
		mTopViewIconImg = (ImageView) findViewById(R.id.top_view_icon);
	}
	
	public ImageView getTouchDotImageView () {
		return mTopViewIconImg;
	}

	GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener (){


		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}

		//短按ACTION_DOWN、ACTION_UP
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}


		//ACTION_DOWN 、快滑动、 ACTION_UP
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			if (mOnTouchDotViewListener != null) {
				int w = getWidth() / 2;
				int h = getHeight() / 2;
				upx = (int) (e2.getRawX() - w);
				upy = (int) (e2.getRawY() - h);
				Log.d("DQ","distanceX="+distanceX);
				Log.d("DQ","distanceY="+distanceY);

				mOnTouchDotViewListener.onScrollTo(TouchDotView.this, upx, upy);
				return true;
			}
			return  super.onScroll(e1,e2,distanceX,distanceY);
		}


		//ACTION_DOWN 、长按不滑动
		@Override
		public void onLongPress(MotionEvent e) {
			if (mOnTouchDotViewListener != null) {
				mOnTouchDotViewListener.onLongPress();
			}
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mOnTouchDotViewListener != null) {
				return mOnTouchDotViewListener.onDoubleTap();
			}
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return super.onDoubleTapEvent(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			mTopViewIconImg.setImageResource(R.drawable.ic_launcher_pressed);
			if (mOnTouchDotViewListener != null) {
				mOnTouchDotViewListener.onSingleTap(TouchDotView.this);
				return true;
			} else {
				return false;
			}
		}


		//ACTION_DOWN 、快滑动、 ACTION_UP
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
	};


	//拖动完成后
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = mGestureDetector.onTouchEvent(event);
		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();
		int height = windowManager.getDefaultDisplay().getHeight();
		int w = getWidth()/2;
		int h = getHeight()/2;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			downx = (int) (event.getRawX()-w);
			downy = (int) (event.getRawY()-h);
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			upx = (int) (event.getRawX()-w);
			upy = (int) (event.getRawY()-h);
			x = upx;
			y = upy;
			mTopViewIconImg.setImageResource(R.drawable.ic_launcher);
			if (mOnTouchDotViewListener != null) {
				if(upx <= width/2 && upy <= height/2){
					if(x <= y){
						x = 0;
					}else{
						y = 0;
					}
				}else if(upx > width/2 && upy <= height/2){
					if((width-upx) <= upy){
						x = width;
					}else{
						y = 0;
					}
				}else if(upx <= width/2 && upy > height/2){
					if(upy < (height-(height/4))){
						x = 0;
					}else{
						y = height;
					}
				}else if(upx > width/2 && upy > height/2) {
					if (upy < (height - (height / 4))) {
						x = width;
					} else {
						y = height;
					}
				}
			}
			if (Math.abs(upx-downx)<5 && Math.abs(upy-downy)<5) {
				mOnTouchDotViewListener.onTouchUp(this,upx,upy);
			} else {
				mOnTouchDotViewListener.onScrollTo(TouchDotView.this, x, y);
			}
		}


		return result;
	}


	public void setOnTouchDotViewListener (OnTouchDotViewListener listener) {
		this.mOnTouchDotViewListener = listener;
	}

	public abstract interface OnTouchDotViewListener {
		public abstract void onScrollTo (View view, int x, int y);
		public abstract void onTouchUp (View view, int x, int y);
		public abstract void onSingleTap (View view);
		public abstract void onLongPress ();
		public abstract boolean onDoubleTap ();
	}
	
	public abstract interface OnTouchViewDoubleClickListener {
		public abstract void onDoubleClick ();
	}
	
	public abstract interface OnTouchViewLongPressListener {
		public abstract void onLongPress ();
	}
}
