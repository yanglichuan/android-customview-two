
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

public class MyBrightControl extends View {

    private Paint paint;
    private boolean isFirst = false;

    public MyBrightControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;
    Bitmap bitmap;
    Bitmap bitmap1;

    public MyBrightControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun1);
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.sun2);

        this.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                screenH = getMeasuredHeight();
                screenW = getMeasuredWidth();
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    public MyBrightControl(Context context) {
        super(context);
    }

    int oldx = 0;
    int oldy = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldx = (int) event.getX();
                oldy = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int dx = x - oldx;
                int dy = y - oldy;
                RRR += dx;
                oldx = x;
                oldy = y;
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public int RRR = 0;

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
            paint.setColor(0xaaaaaaaa);
            int leftMargin = 100;
            int rightMargin = 100;

            int r1 = screenH / 2;
            // canvas.drawCircle(r1 + leftMargin, r1, r1 / 2, paint);

            RectF dst = new RectF(leftMargin, 0, r1 + leftMargin + r1, 2 * r1);
            // canvas.drawRect(dst, paint);
            canvas.drawBitmap(bitmap1, null, dst, paint);

            dst = new RectF(screenW - r1 - rightMargin - r1, 0, screenW - r1 - rightMargin
                    + r1, 2 * r1);
            // canvas.drawRect(dst, paint);
            canvas.drawBitmap(bitmap, null, dst, paint);

            paint.setColor(Color.GRAY);
            canvas.drawLine(r1 + leftMargin + 2 * r1, 
                    r1, 
                    screenW - rightMargin - 3 * r1, 
                    r1, paint);
            
            
            
            

            paint.setStyle(Style.FILL);
            paint.setColor(Color.BLUE);

            canvas.drawCircle(r1 + leftMargin + 2 * r1 + RRR, 
                    r1, 
                    screenH/2, 
                    paint);

            canvas.drawLine(r1 + leftMargin + 2 * r1, r1 + 1, r1 + leftMargin + 2 * r1 + RRR,
                    r1 + 1, paint);

            paint.setStyle(Style.FILL);
            paint.setColor(Color.GREEN);
            paint.setTextSize(20);
            
            
            float measureText = paint.measureText(RRR + "");
            canvas.drawText(RRR + "", r1 + leftMargin + 2 * r1 + RRR - measureText/2, 
                    r1*5f/4f, paint);
        }
    }

}
