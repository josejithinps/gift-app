package com.Catalina.wrapper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Custom horizontal scroll view for  
 * determining  full scroll.
 * 
 * @author Jose
 *
 */

public class CatalinaHorizontalScrollView extends HorizontalScrollView {

	public interface OnScrollEndListener{
		public void onScrollEnd(CatalinaHorizontalScrollView catalinaHorizontalScrollView, int l, int t, int oldl, int oldt);
	}

	private boolean isFling = false;
	private OnScrollEndListener onScrollEndListener;
	private boolean isScrolling = false;
	private boolean canScroll = true;

	public CatalinaHorizontalScrollView(Context context) {
		super(context);
	}

	public CatalinaHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}

	public CatalinaHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (canScroll) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				isScrolling = true;
				break;

			case MotionEvent.ACTION_UP:
				isScrolling = false;
				break;

			default:
				break;
			}
			return super.onTouchEvent(ev);
		}
		else {
			return false;
		}
	}

	@Override
	public void fling(int velocityX) {	
		super.fling(velocityX);
		isFling = true;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {	
		super.onScrollChanged(l, t, oldl, oldt);

		if(isFling || isScrolling){
			if(Math.abs(l - oldl) < 2){

				isFling = false;
				isScrolling = false;

				if(onScrollEndListener != null){
					onScrollEndListener.onScrollEnd(this, l, t, oldl, oldt);
				}
			}
		}			
	}

	public OnScrollEndListener getOnScrollEndListener(){
		return onScrollEndListener;
	}

	public void setOnScrollEndListener(OnScrollEndListener onScrollEndListener){
		this.onScrollEndListener = onScrollEndListener;
	}	

	public void enableScrolling(){
		canScroll = true;
	}

	public void disableScrolling(){
		canScroll = false;
	}

}
