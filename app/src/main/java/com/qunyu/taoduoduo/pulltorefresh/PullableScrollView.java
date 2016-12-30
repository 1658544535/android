package com.qunyu.taoduoduo.pulltorefresh;

//import com.ruiyu.taozhuma.widget.BottomScrollView.OnScrollToBottomListener;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable {
	// private OnScrollToBottomListener onScrollToBottom;
	PointF curP = new PointF();
	private int lastScrollY;

	public PullableScrollView(Context context) {
		super(context);
	}

	public PullableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (getScrollY() == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean canPullUp() {
		if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
			return true;
		else
			return false;
	}

	/**
	 * 方法名: setOnScrollToBottomLintener
	 * 
	 * 功能描述:设置监听
	 * 
	 * @param listener
	 * @param layout
	 *            ScrollView包含的Layout
	 * @return void
	 * 
	 */
	public void setOnScrollToBottomLintener(OnScrollToBottomListener listener,
			LinearLayout layout) {
		this.layout = layout;
		// onScrollToBottom = listener;
	}

	public boolean isBottom() {

		if (getScrollY() + getHeight() >= computeVerticalScrollRange()) {
			return true;
		} else {
			return false;
		}
	}

	public interface OnScrollToBottomListener {

		// 手指离开了屏幕
		public void FingerUpLinstener(boolean moveDistance);
	}

	private LinearLayout layout;
	private int moveDistance = 0;// 移动的距离
	private OnScrollListener onScrollListener;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:// 手指按下
			curP.y = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:// 手指移动
			break;
		case MotionEvent.ACTION_UP:// 手指离开

			float lastY = ev.getY();
			if (curP.y - lastY > 100 && isBottom()) {
				// onScrollToBottom.FingerUpLinstener(true);
			} else {
				// onScrollToBottom.FingerUpLinstener(false);
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 设置滚动接口
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	/**
	 * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
	 */
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int scrollY = PullableScrollView.this.getScrollY();

			// 此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
			if (lastScrollY != scrollY) {
				lastScrollY = scrollY;
				handler.sendMessageDelayed(handler.obtainMessage(), 5);
			}
			if (onScrollListener != null) {
				onScrollListener.onScroll(scrollY);
			}

		};

	};

	/**
	 * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
	 * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
	 * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
	 * MyScrollView滑动的距离
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (onScrollListener != null) {
			onScrollListener.onScroll(lastScrollY = this.getScrollY());
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			handler.sendMessageDelayed(handler.obtainMessage(), 20);
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 滚动的回调接口
	 */
	public interface OnScrollListener {
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 */
		public void onScroll(int scrollY);
	}

	// @Override
	// protected void onFinishInflate() {
	// }
	//
	// /**
	// * 监听touch
	// */
	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// commOnTouchEvent(ev);
	//
	// return super.dispatchTouchEvent(ev);
	// }
	//
	// /**
	// * 触摸事件
	// *
	// * @param ev
	// */
	// public void commOnTouchEvent(MotionEvent ev) {
	// }
	//
	// /**
	// * 回缩动画
	// */
	// public void animation() {
	// // 开启移动动画
	//
	// }
	//
	// // 是否需要开启动画
	// public boolean isNeedAnimation() {
	// return false;
	// }
	//
	// /**
	// * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的总高度
	// * <p/>
	// * getHeight()：获取的是屏幕的高度
	// */
	// public void isNeedMove() {
	// }
}
