
package com.ylc.view;

import java.util.Random;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

public class MyVifiView extends View {

    private Paint paint;
    private boolean isFirst = false;

    public MyVifiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;
    public ObjectAnimator animator1;

    public MyVifiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);

        this.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                screenH = getMeasuredHeight();
                screenW = getMeasuredWidth();

                startAnim();

                animator1 = ObjectAnimator.ofInt(new Object(), "w", -40, 40);
                animator1.setDuration(2000);
                animator1.setRepeatCount(ValueAnimator.INFINITE);
                animator1.setRepeatMode(ValueAnimator.REVERSE);
                animator1.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (Integer) animation.getAnimatedValue();
                        // invalidate();
                    }
                });
                animator1.start();

                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    public MyVifiView(Context context) {
        super(context);
    }

    public int num = 0;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            num++;
            if (num == 4) {
                num = 0;
            }
            color = Color.GREEN;
            handler.sendEmptyMessageDelayed(100, 500);
            invalidate();
        };
    };

    public void startAnim() {
        handler.sendEmptyMessageDelayed(100, 500);
    }

    public void stopAnim1(int c) {
        handler.removeMessages(100);
        num = 3;
        color = c;
        invalidate();
    }

    public void stopAnim2(int c) {
        handler.removeMessages(100);
        num = 3;
        color = c;
        invalidate();
    }

    public void drawArc(Canvas canvas, RectF oval1, RectF oval2, RectF oval3, Paint paint, int i) {
        if (i == 1) {
            canvas.drawArc(oval1, 255, 30, false, paint);
        } else if (i == 2) {
            canvas.drawArc(oval1, 255, 30, false, paint);
            canvas.drawArc(oval2, 240, 60, false, paint);
        } else if (i == 3) {
            canvas.drawArc(oval1, 255, 30, false, paint);
            canvas.drawArc(oval2, 240, 60, false, paint);
            canvas.drawArc(oval3, 220, 100, false, paint);
        } else {

        }
    }
    public int color = Color.GREEN;
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            String content = "press up/down/left/right/middle to draw rectangle";
            paint.setColor(Color.BLUE);
            canvas.drawText(content, 0, content.length() - 1, 30, 30, paint);
        } else {
            // canvas.drawColor(Color.YELLOW);
            int x = screenW / 2;
            int y = screenH - 10;
            RectF oval1 = new RectF(0, (3f / 4f) * y, screenW, (3f / 4f) * y + screenW * 3);
            RectF oval2 = new RectF(0, (2f / 4f) * y, screenW, (2f / 4f) * y + screenW * 1.5f);
            RectF oval3 = new RectF(0, (1f / 4f) * y, screenW, (1f / 4f) * y + screenW);

            paint.setColor(color);
            paint.setStrokeCap(Cap.ROUND);
            paint.setStrokeJoin(Join.ROUND);
            paint.setStrokeWidth(5);
            canvas.drawPoint(x, y, paint);
            paint.setStrokeWidth(5);
            paint.setStrokeCap(Cap.SQUARE);
            paint.setStrokeJoin(Join.BEVEL);
            drawArc(canvas, oval1, oval2, oval3, paint, num);
            // adb
        }
    }

}
