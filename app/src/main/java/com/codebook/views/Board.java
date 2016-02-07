package com.codebook.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.codebook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Narayan Acharya on 31/10/2015.
 */
public class Board extends View {
    private static final String TAG = "Board";

    private static final int POINT_COUNT = 40;
    private static final int SPEED_X_BARRIER = 1;
    private static final int SPEED_Y_BARRIER = 1;
    private static final int DISTANCE_BARRIER = 100;

    Random random;
    private List<Point> points;

    private int radius;
    private int colour;

    private Paint paint;

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        points = new ArrayList<>(20);
        for (int i = 0; i < POINT_COUNT; i++) {
            points.add(new Point());
        }
        random = new Random();
        paint = new Paint();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.points, 0, 0);
        try {
            radius = typedArray.getInteger(R.styleable.points_pointRadius, 6);
            colour = typedArray.getInteger(R.styleable.points_pointColor, 0);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        for (int i = 0; i < POINT_COUNT; i++) {
            points.get(i).setCenterX(random.nextInt(viewWidth));
            points.get(i).setCenterY(random.nextInt(viewHeight));
            int speedX = random.nextInt(2 * SPEED_X_BARRIER) - SPEED_X_BARRIER;
            int speedY = random.nextInt(2 * SPEED_Y_BARRIER) - SPEED_Y_BARRIER;
            points.get(i).setSpeedX(speedX);
            points.get(i).setSpeedY(speedY);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(colour);
        for (int i = 0; i < POINT_COUNT; i++) {
            canvas.drawCircle(points.get(i).getCenterX(), points.get(i).getCenterY(), radius, paint);
            int newCenterX = points.get(i).getCenterX() + points.get(i).getSpeedX();
            int newCenterY = points.get(i).getCenterY() + points.get(i).getSpeedY();
            if (newCenterX < 0) {
                points.get(i).setCenterX(getMeasuredWidth() + newCenterX);
            } else if (newCenterX > getMeasuredWidth()) {
                points.get(i).setCenterX(getMeasuredWidth() - newCenterX);
            } else {
                points.get(i).setCenterX(newCenterX);
            }
            if (newCenterY < 0) {
                points.get(i).setCenterY(getMeasuredHeight() + newCenterY);
            } else if (newCenterY > getMeasuredHeight()) {
                points.get(i).setCenterY(getMeasuredHeight() - newCenterY);
            } else {
                points.get(i).setCenterY(newCenterY);
            }
            for (int j = 0; j < POINT_COUNT; j++) {
                if (distance(points.get(i), points.get(j)) < DISTANCE_BARRIER) {
                    canvas.drawLine(points.get(i).getCenterX(), points.get(i).getCenterY(),
                            points.get(j).getCenterX(), points.get(j).getCenterY(),
                            paint);
                }
            }
        }
        invalidate();
    }

    private int distance(Point p1, Point p2) {
        int dist = (int) Math.sqrt(Math.pow(p1.getCenterX() - p2.getCenterX(), 2) + Math.pow(p1.getCenterY() - p2.getCenterY(), 2));
        return dist;
    }

    public void parallax(int startX, int startY, int endX, int endY) {
        Log.d(TAG, "Parallax");
        for (int i = 0; i < POINT_COUNT; i++) {
            points.get(i).setCenterX(points.get(i).getCenterX() + (endX - startX) / 40);
            points.get(i).setCenterY(points.get(i).getCenterY() + (endY - startY) / 40);
        }
    }

    public void parallaxReverse(int startX, int startY, int endX, int endY) {
        Log.d(TAG, "Parallax");
        for (int i = 0; i < POINT_COUNT; i++) {
            points.get(i).setCenterX(points.get(i).getCenterX() + (startX - endX) / 40);
            points.get(i).setCenterY(points.get(i).getCenterY() + (startY - endY) / 40);
        }
    }

    public void resetPointSpeeds() {
        for (int i = 0; i < POINT_COUNT; i++) {
            int speedX = random.nextInt(2 * SPEED_X_BARRIER) - SPEED_X_BARRIER;
            int speedY = random.nextInt(2 * SPEED_Y_BARRIER) - SPEED_Y_BARRIER;
            points.get(i).setSpeedX(speedX);
            points.get(i).setSpeedY(speedY);
        }
    }
}