package com.google.android.exoplayer2.demo.banner;

import android.view.View;
import android.view.ViewGroup;

public class Decorator {

    public static void setRelativeWidth(View viewRef, View viewDeco, double ratio) {
        viewRef.measure(0,0);
        ViewGroup.LayoutParams params = viewDeco.getLayoutParams();
        params.width = (int) (viewRef.getMeasuredWidth() * ratio);
        viewDeco.setLayoutParams(params);
    }

    // ratio is width / height
    public static void setRatioByWidth(View view, int width, double ratio) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = (int)(width / ratio);
        view.setLayoutParams( params );
    }

    public static void setRatioByHeight(View view, int height, double ratio) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        params.width = (int)(height * ratio);
        view.setLayoutParams( params );
    }

}
