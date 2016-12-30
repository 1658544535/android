package com.qunyu.taoduoduo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ViewpagerHorizontalScrollView extends HorizontalScrollView {
    public ViewpagerHorizontalScrollView(Context p_context, AttributeSet p_attrs) {
        super(p_context, p_attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent p_event) {
        return true;
    }

        @Override
//    public boolean onTouchEvent(MotionEvent p_event)
//    {
//        if (p_event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null)
//        {
////            Log.d("DEBUG", "intercept move event");
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }
//
//        return super.onTouchEvent(p_event);
//    }
    public boolean pageScroll(int direction) {
        return false;
    }

}
