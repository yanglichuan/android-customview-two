
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
import android.graphics.Xfermode;
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

class MyBatteryView extends View {

    private Paint paint;
    private boolean isFirst = false;

    public MyBatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;
    public ObjectAnimator animator1;

    public MyBatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);

        this.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                screenH = getMeasuredHeight();
                screenW = getMeasuredWidth();
                setPersent(0.6f);
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

    public MyBatteryView(Context context) {
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
    
    public void setPersent(float ft){
        persent  = ft;
        invalidate();
    }

    public float  persent = 0.4f;
    public int color = Color.GREEN;
    public int df = 1;
    public int rxy = 10;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            String content = "press up/down/left/right/middle to draw rectangle";
            paint.setColor(Color.BLUE);
            canvas.drawText(content, 0, content.length() - 1, 30, 30, paint);
        } else {
            paint.setStyle(Style.STROKE);
            paint.setColor(Color.GRAY);
            float bottom = screenH - df - 1;
            float right = screenW - df;
            float top = df;
            float left = df;
            RectF rect = new RectF(left, top, right, bottom);

            rxy = (int) ((bottom - 2 * df) / 2f);
            canvas.drawRoundRect(rect, rxy, rxy, paint);

            paint.setColor(Color.GREEN);
            paint.setStyle(Style.FILL);

            float bottom1 = bottom - 3;
            float right1 = right - 3;
            float top1 = top + 3;
            float left1 = left + 3;
            RectF ddd = new RectF(left1, top1, right1, bottom1);
            rxy = (int) ((bottom1 - 3) / 2f);

            //=========================
            Bitmap bb = Bitmap.createBitmap((int) (right1 - left1 + 6), (int) (bottom1 - top1 + 6),
                    Config.ARGB_8888);
            Canvas ccc = new Canvas(bb);

            Paint paint1 = new Paint();
            paint1.setAntiAlias(true);
            paint1.setStyle(Style.FILL);
            paint1.setColor(Color.GREEN);
            ccc.drawRoundRect(ddd, rxy, rxy, paint1);
            paint1.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
            ccc.drawRect(left1 - 3, top1 - 3, (left1 - 3)+(right1 - left1 + 6)*(1 - persent), (int) (bottom1 + 3), paint1);
            //============================
            
            canvas.drawBitmap(bb, left1 - 3, top1 - 4, paint);

            // canvas.drawRoundRect(ddd, rxy, rxy, paint);
        }
    }

}
