package com.paintview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by longfei.huang on 2017/7/26.
 */

public class PaintView extends View {

    enum PaintMode {
        PAINT_MODE, ERASE_MODE
    }

    enum PaintSource {
        PAINT_SOURCE_FINGER, PAINT_SOURCE_PEN
    }

    private final int DEFAULT_BG_COLOR = Color.TRANSPARENT;//默认背景颜色
    private final int DEFAULT_PAINT_COLOR = Color.BLACK;//默认笔触颜色
    private final int DEFAULT_PAINT_WIDTH = 1;//默认笔触宽度
    private final int DEFAULT_MAX_POINTS = 1000 * 7;//默认最大笔点数
    private final int DEFAULT_ERASER_RADIUS = 10;//橡皮擦半径


    //默认背景颜色
    private int mBackgroundColor = DEFAULT_BG_COLOR;
    //默认笔触颜色
    private int mPaintColor = DEFAULT_PAINT_COLOR;
    //默认笔触宽度
    private float mPaintWidth = DEFAULT_PAINT_WIDTH;
    //默认最大笔点数
    private int mMaxPoint = DEFAULT_MAX_POINTS;

    //当前模式
    private PaintMode mMode = PaintMode.PAINT_MODE;
    //源
    private PaintSource mPaintSource = PaintSource.PAINT_SOURCE_PEN;

    private float currentX, currentY;

    private Paint mPaint;

    private Path mPath;

    private float mEraserRadius;


    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);

    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.paint_view);
        this.mPaintColor = ta.getColor(R.styleable.paint_view_paint_color, DEFAULT_PAINT_COLOR);
        this.mPaintWidth = ta.getDimension(R.styleable.paint_view_paint_width, DEFAULT_PAINT_WIDTH);
        this.mMaxPoint = ta.getInteger(R.styleable.paint_view_max_points, DEFAULT_MAX_POINTS);
        this.mEraserRadius = ta.getDimension(R.styleable.paint_view_eraser_radius, DEFAULT_ERASER_RADIUS);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentX = x;
                currentY = y;
                mPath.moveTo(currentX, currentY);
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = x;
                currentY = y;
                mPath.quadTo(currentX, currentY, x, y); // 画线
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);
                break;
        }

        invalidate();
        return true;
    }
}