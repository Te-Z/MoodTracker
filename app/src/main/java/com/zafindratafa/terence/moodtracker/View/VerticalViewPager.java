package com.zafindratafa.terence.moodtracker.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by maverick on 31/10/17.
 */

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // change the ViewPager vertically
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(View view, float position) {
            if (position < -1) { // disable left swipe
                view.setAlpha(0);
            } else if (position <= 1) {
                view.setAlpha(1);

                // Counteract the default horizontal slide transition
                view.setTranslationX(view.getWidth()* -position);

                // set the vertical slide transition
                float yPosition = position * view.getHeight();;
                view.setTranslationY(yPosition);

            } else { // disable right swipe
                view.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent ev){
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX,newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }
}
