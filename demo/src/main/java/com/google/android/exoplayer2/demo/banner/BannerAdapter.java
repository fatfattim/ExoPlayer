package com.google.android.exoplayer2.demo.banner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.demo.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;


public class BannerAdapter extends InfiniteViewPagerAdapter {

    public BannerAdapter(ArrayList<String> banners, boolean loopEnabled) {
        super((ArrayList) banners, loopEnabled);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.banner_player, container, false);
        TextView textView = (TextView) view.findViewById(R.id.banner_text);
        SimpleExoPlayerView playerView = (SimpleExoPlayerView) view.findViewById(R.id.player_view);
        playerView.setTag(position);
        textView.setText((String) getItem(position));
        container.addView(view);

        Log.d("dogtim", "instantiateItem: " + position);
        return view;
    }

}
