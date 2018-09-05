package com.msc.someweather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.msc.someweather.R;

public class HourlyAqiView extends FrameLayout {

    public HourlyAqiView(Context context) {
        super(context);
    }

    public HourlyAqiView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HourlyAqiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.hourly_aqi_layout, this);
    }



}
