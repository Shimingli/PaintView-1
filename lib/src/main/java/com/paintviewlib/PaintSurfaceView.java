package com.paintviewlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by huanglongfei on 2017/7/27.
 */

public class PaintSurfaceView extends SurfaceView implements
    SurfaceHolder.Callback, Runnable {
  // SurfaceHolder实例
  private SurfaceHolder mSurfaceHolder;
  // Canvas对象
  private Canvas mCanvas;
  // 控制子线程是否运行
  private boolean startDraw;
  // Path实例
  private Path mPath = new Path();
  // Paint实例
  private Paint mPaint = new Paint();

  private int mPreviousX = 0;
  private int mPreviousY = 0;

  public PaintSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(); // 初始化
    init();
  }

  private void init() {
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(dip2px(getContext(), 5));
    mPaint.setColor(Color.BLACK);

  }

  private void initView() {
    mSurfaceHolder = getHolder();
    mSurfaceHolder.addCallback(this);

    // 设置可获得焦点
    setFocusable(true);
    setFocusableInTouchMode(true);
    // 设置常亮
    this.setKeepScreenOn(true);

  }

  @Override
  public void run() {
    // 如果不停止就一直绘制
    while (startDraw) {
      // 绘制
      draw();
    }
  }

  /*
   * 创建
   */
  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    startDraw = true;
    new Thread(this).start();
  }

  /*
   * 改变
   */
  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
                             int height) {
  }

  /*
   * 销毁
   */
  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    startDraw = false;
  }

  private void draw() {
    try {
      mCanvas = mSurfaceHolder.lockCanvas();
      mCanvas.drawPath(mPath, mPaint);

    } catch (Exception e) {

    } finally {
      // 对画布内容进行提交
      if (mCanvas != null) {
        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
      }
    }
  }

  private float dip2px(Context context, int dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int x = (int) event.getX();    //获取手指移动的x坐标
    int y = (int) event.getY();    //获取手指移动的y坐标

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mPath.reset();
        mPreviousX = x;
        mPreviousY = y;
        mPath.moveTo(x, y);
        break;

      case MotionEvent.ACTION_MOVE:
        float cX = (x + mPreviousX) / 2;
        float cY = (y + mPreviousY) / 2;

        //这里终点设为两点的中心点的目的在于使绘制的曲线更平滑，如果终点直接设置为x,y，效果和lineto是一样的,实际是折线效果
        mPath.quadTo(mPreviousX, mPreviousY, cX, cY);

        //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
        mPreviousX = x;
        mPreviousY = y;

        break;

      case MotionEvent.ACTION_UP:
        break;
    }
    return true;
  }

  // 重置画布
  public void reset() {
    mPath.reset();
  }
}
