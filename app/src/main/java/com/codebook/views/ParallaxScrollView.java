package com.codebook.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Narayan Acharya on 07/01/2016.
 */
public class ParallaxScrollView extends ScrollView {
    public interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public ParallaxScrollView(Context context) {
        super(context);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
    }


    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }
}
