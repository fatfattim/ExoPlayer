package com.google.android.exoplayer2.demo.banner;


import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;


public class InfiniteViewPager extends ViewPager {

    private static final String TAG = InfiniteViewPager.class.getSimpleName();
    private final static int SCROLL_DELAY_TIME = 5000;

    private int currentPosition = 0;
    private boolean isLoopEnable = false;

    private Handler handler = new Handler();


    private OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if( motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE ) {
            } else if ( motionEvent.getAction() == MotionEvent.ACTION_UP ) {
            }
            return false;
        }
    };

    public InfiniteViewPager(Context context) {
        super(context);
        addOnPageChangeListener(onPageChangeListener);
        setScroller();
        setOnTouchListener( touchListener );
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(onPageChangeListener);
        setScroller();
        setOnTouchListener( touchListener );
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if ( visibility != VISIBLE ) {
            //handler.removeCallbacks(autoScroll);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if ( visibility == VISIBLE ) {
            //handler.post(updateScroll);
            //handler.postDelayed(autoScroll, SCROLL_DELAY_TIME);
        } else {
            //handler.removeCallbacks(autoScroll);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (isLoopEnable) {
            super.setCurrentItem(item + 1, smoothScroll);
        } else {
            super.setCurrentItem(item, smoothScroll);
        }
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, true);
    }

    @Override
    public int getCurrentItem() {
        if (isLoopEnable) {
            final int index = super.getCurrentItem();
            final int count = getAdapter().getCount();
            if (index == 0) {
                return count - 3;
            } else if (index == count - 1) {
                return 0;
            } else {
                return index - 1;
            }
        } else {
            return super.getCurrentItem();
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof InfiniteViewPagerAdapter) {
            isLoopEnable = ((InfiniteViewPagerAdapter) adapter).isLoopEnabled();
            if (isLoopEnable) {
                setCurrentItem(0);
                setOverScrollMode( OVER_SCROLL_NEVER );
            }
        }
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (isLoopEnable) {
                    final int count = getAdapter().getCount();
                    if (currentPosition == count - 1) {
                        //last to first
                        setCurrentItem(0, false);
                        currentPosition = 1;
                    } else if (currentPosition == 0) {
                        //first to last
                        setCurrentItem(count - 3, false);
                        currentPosition = count - 2;
                    }
                }
            }
        }
    };

    private void setScroller(){
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), sInterpolator);
            mScroller.set(this, scroller);
        } catch (NoSuchFieldException e) {
            Log.d(TAG, "NoSuchFieldException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "IllegalArgumentException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.d(TAG, "IllegalAccessException: " + e.getMessage());
        }
    }

    private class FixedSpeedScroller extends Scroller {

        private static final int mDuration = 250;

        FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    /**
     * UI SDK bug
     * if ViewPager has 3 items
     * when user scroll to bottom and make ViewPager do onDetachedFromWindow.
     * User scroll to ViewPager again will loss Animation effect at first time
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //But this is not good method for resolving it.
        /*try {
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, false);
            getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}