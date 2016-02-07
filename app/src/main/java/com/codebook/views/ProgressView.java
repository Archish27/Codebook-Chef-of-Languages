package com.codebook.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.codebook.R;


/**
 * Created by Narayan Acharya on 09/11/2015.
 */
public class ProgressView extends View {

    private static final String TAG = "ProgressView";

    private static final int STROKE_WIDTH = 5;
    private int progressPercent;
    private int colour;
    private Paint paint;
    private RectF canvasRect;

    public void setProgressPercent(int percent) {
        this.progressPercent = percent;
        if (progressPercent == 100) {
            colour = getResources().getColor(R.color.myGreen);
        }
        invalidate();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.progress, 0, 0);
        try {
            colour = typedArray.getColor(R.styleable.progress_progressColour, 0);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        canvasRect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasRect = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(colour);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);

        canvas.drawRoundRect(canvasRect, 40, 40, paint);

        // draw the part of the bar that's filled
        paint.setStrokeWidth(getMeasuredHeight()*2);
        canvas.drawLine(0, 0,
                (progressPercent * getMeasuredWidth()) / 100, 0, paint);

        // draw the unfilled section
        /*
        paint.setColor(getResources().getColor(R.color.myRed));
        canvas.drawLine((progressPercent * getMeasuredWidth()) / 100, getMeasuredHeight(),
                getWidth(), getMeasuredHeight(), paint);
                */

    }
}
