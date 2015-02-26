
package com.ylc.view;

import java.util.Random;

import com.example.bittt3.R;
import com.example.bittt3.R.drawable;

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
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
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

class MyRoundLiuLiangView extends View {

    private Bitmap bm;
    private Shader linearGradient;
    private Shader linearGradient2;
    private int[] colors;
    private int[] colors2;
    private Paint paint;
    private boolean isFirst = false;

    public MyRoundLiuLiangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;
    public ObjectAnimator animator;
    public ObjectAnimator animator1;
    public int current_value1 = 0;

    public MyRoundLiuLiangView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0x3300ff00);
        paint.setStyle(Style.FILL);
        colors = new int[] {
                Color.CYAN, Color.BLUE, Color.CYAN, Color.BLUE, Color.CYAN, Color.BLUE, Color.CYAN,
                Color.BLUE
        };
        colors2 = new int[] {
                Color.CYAN, Color.BLUE
        };
        setFocusable(true);

        this.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                screenH = getMeasuredHeight();
                screenW = getMeasuredWidth();
                linearGradient = new LinearGradient(0, 0, 30, 0, colors, null, TileMode.CLAMP);
                linearGradient2 = new LinearGradient(0, 0, 0, screenH, colors2, null,
                        TileMode.REPEAT);

                animator1 = ObjectAnimator.ofInt(new Object(), "w", -40, 40);
                animator1.setDuration(2000);
                animator1.setRepeatCount(ValueAnimator.INFINITE);
                animator1.setRepeatMode(ValueAnimator.REVERSE);
                animator1.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (Integer) animation.getAnimatedValue();
                        current_value1 = value;
                        invalidate();
                    }
                });
                animator1.start();

                getViewTreeObserver().removeOnPreDrawListener(this);
               
                //设置百分比
                setPersant(0.23f);
                return false;
            }
        });
    }

    public MyRoundLiuLiangView(Context context) {
        super(context);
    }

    public int dr = 20;
    public Path ppath;

    public int height = 200;

    public void setPersant(final double d) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int r = screenW / 2 - dr;
                height = (int) ((float) 2 * r * d);
                invalidate();
            }
        }, 0);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            String content = "press up/down/left/right/middle to draw rectangle";
            paint.setColor(Color.BLUE);
            canvas.drawText(content, 0, content.length() - 1, 30, 30, paint);
        } else {

            Bitmap bb = Bitmap.createBitmap(screenH, screenH, Config.ARGB_8888);
            Canvas ccc = new Canvas(bb);

            Paint paint1 = new Paint();
            paint1.setAntiAlias(true);
            paint1.setStyle(Style.FILL);
            paint1.setColor(0xff00ff00);
            ccc.drawCircle(screenW / 2, screenW / 2, screenW / 2 - dr, paint1);

            // 画滚动的矩形（顶部是贝赛尔曲线）
            paint1.setColor(0xff0000ff);
            paint1.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));

            Point p1 = new Point();
            p1.x = dr;
            p1.y = dr + (screenH - 2 * dr) - height;

            Point p2 = new Point();
            p2.x = screenW - dr;
            p2.y = dr + (screenH - 2 * dr) - height;

            Point middle1 = new Point();
            middle1.x = p1.x + (p2.x - p1.x) / 8;
            middle1.y = dr + (screenH - 2 * dr) - height + current_value1 / 2;

            Point middle2 = new Point();
            middle2.x = p1.x + (p2.x - p1.x) * 3 / 8;
            middle2.y = dr + (screenH - 2 * dr) - height - current_value1 / 3;

            Point middle3 = new Point();
            middle3.x = p1.x + (p2.x - p1.x) * 5 / 8;
            middle3.y = dr + (screenH - 2 * dr) - height + current_value1 / 2;

            Point middle4 = new Point();
            middle4.x = p1.x + (p2.x - p1.x) * 7 / 8;
            middle4.y = dr + (screenH - 2 * dr) - height - current_value1 / 3;

            Point mmdd1 = new Point();
            mmdd1.x = p1.x + (p2.x - p1.x) / 4;
            mmdd1.y = dr + (screenH - 2 * dr) - height;

            Point mmdd2 = new Point();
            mmdd2.x = p1.x + (p2.x - p1.x) / 2;
            mmdd2.y = dr + (screenH - 2 * dr) - height;

            Point mmdd3 = new Point();
            mmdd3.x = p1.x + (p2.x - p1.x) * 3 / 4;
            mmdd3.y = dr + (screenH - 2 * dr) - height;

            Point p3 = new Point();
            p3.x = screenW - dr;
            ;
            p3.y = screenH - dr;

            Point p4 = new Point();
            p4.x = dr;
            p4.y = screenH - dr;
            paint.setStyle(Style.FILL);
            ppath = new Path();
            ppath.reset();// 重置path
            // 贝赛尔曲线的起始点
            ppath.moveTo(p1.x, p2.y);
            // 设置贝赛尔曲线的操作点以及终止点
            ppath.quadTo(middle1.x, middle1.y, mmdd1.x, mmdd1.y);
            ppath.quadTo(middle2.x, middle2.y, mmdd2.x, mmdd2.y);
            ppath.quadTo(middle3.x, middle3.y, mmdd3.x, mmdd3.y);
            ppath.quadTo(middle4.x, middle4.y, p2.x, p2.y);

            ppath.lineTo(p3.x, p3.y);
            ppath.lineTo(p4.x, p4.y);
            ppath.close();
            // 绘制贝赛尔曲线（Path）
            ccc.drawPath(ppath, paint1);

            // 外围的绿框
            paint1.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
            paint1.setColor(0x3300ff00);
            ccc.drawCircle(screenW / 2, screenW / 2, screenW / 2, paint1);
            canvas.drawBitmap(bb, 0f, 0f, paint);
        }
    }
}
