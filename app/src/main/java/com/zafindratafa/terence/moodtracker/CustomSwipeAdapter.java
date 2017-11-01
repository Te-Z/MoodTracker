package com.zafindratafa.terence.moodtracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by maverick on 31/10/17.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.sad, R.drawable.disappointed, R.drawable.normal, R.drawable.happy, R.drawable.superhappy};
    private String[] background_color = {"#ffde3c50", "#ff9b9b9b", "#a5468ad9", "#ffb8e986", "#fff9ec4f"};
    private Context ctx;
    private LayoutInflater mLayoutInflater;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view  == (RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = mLayoutInflater.inflate(R.layout.swipe_layout, container, false);
        item_view.setBackgroundColor(Color.parseColor(background_color[position]));
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        imageView.setImageResource(image_resources[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
