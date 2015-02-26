
package com.ylc.view;

import java.util.ArrayList;
import java.util.Random;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.graphics.PointF;
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
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

public class MyJDMoneyRing extends View {

    private Paint paint;

    public MyJDMoneyRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;
    public int bigR = 0;
    public int smallR = 0;

    public MyJDMoneyRing(Context context, AttributeSet attrs) {
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
                bigR = screenH / 2;
                smallR = (int) ((bigR * 2f) / 3f);
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    public MyJDMoneyRing(Context context) {
        super(context);
    }

    public int defaultW = 200;
    public int defaultH = 200;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (defaultW + getPaddingLeft()
                    + getPaddingRight());
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * 
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultH + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public int sweepAngle = 1;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Style.FILL);
        paint.setColor(Color.CYAN);
        paint.setAlpha(100);
        canvas.drawCircle(bigR, bigR, bigR, paint);

        paint.setStyle(Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setAlpha(150);
        boolean useCenter = true;
        float startAngle = 270;
        RectF oval = new RectF(0, 0, 2 * bigR, 2 * bigR);
        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);

        paint.setAlpha(255);
        canvas.drawCircle(bigR, bigR, smallR, paint);

        paint.setColor(Color.YELLOW);
        paint.setStyle(Style.FILL);
        paint.setStrokeWidth(0);
        paint.setTextSize(50);

        
        float measureText = paint.measureText((int) ((float) sweepAngle / 360f * 100) + "%");
        canvas.drawText((int) ((float) sweepAngle / 360f * 100) + "%", bigR - measureText/2, bigR, paint);
        super.onDraw(canvas);
    }

    //
    public void setSweepAngle(int dushu) {
        sweepAngle = dushu;
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new Object(), "dushu", 0, dushu);
        objectAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        objectAnimator.start();
    }

}
