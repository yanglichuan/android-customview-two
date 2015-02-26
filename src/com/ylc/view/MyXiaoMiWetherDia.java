
package com.ylc.view;

import java.util.ArrayList;
import java.util.Random;

import android.R.integer;
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

class MyXiaoMiWetherDia extends View {
    private Paint paint;

    public MyXiaoMiWetherDia(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;
    public int bigR = 0;
    public int value;

    public int centerx = 0;
    public int centery = 0;

    public MyXiaoMiWetherDia(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        this.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                // 触发动画
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new Object(), "vvv", 0, 100);
                objectAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        value = (Integer) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                objectAnimator.setDuration(2000);
                objectAnimator.start();
                return false;
            }
        });
    }

    public MyXiaoMiWetherDia(Context context) {
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

    public int r3 = 0;
    public int r2 = 0;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        screenH = getMeasuredHeight();
        screenW = getMeasuredWidth();
        centerx = screenW / 2;
        centery = screenH / 2;
        bigR = screenH / 2 - 30;
        r3 = bigR;
        r2 = bigR - 20;

        paint.setColor(Color.CYAN);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(2);
        RectF oval = new RectF(centerx - bigR, centery - bigR, centerx + bigR, centery + bigR);
        canvas.drawArc(oval, 135, 270, false, paint);

        double deg0 = Math.toRadians(0);
        double deg45 = Math.toRadians(45);
        double deg90 = Math.toRadians(90);
        double deg135 = Math.toRadians(135);
        double deg180 = Math.toRadians(180);
        double deg225 = Math.toRadians(225);
        double deg270 = Math.toRadians(270);
        double deg315 = Math.toRadians(315);

        PointF pp0_begin = new PointF(centerx + r3, centery);
        PointF pp45_begin = new PointF((float) (centerx + r3 * Math.cos(deg45)),
                (float) (centery + r3 * Math.sin(deg45)));
        PointF pp90_begin = new PointF((float) (centerx + r3 * Math.cos(deg90)),
                (float) (centery + r3 * Math.sin(deg90)));
        PointF pp135_begin = new PointF((float) (centerx + r3 * Math.cos(deg135)),
                (float) (centery + r3 * Math.sin(deg135)));
        PointF pp180_begin = new PointF((float) (centerx + r3 * Math.cos(deg180)),
                (float) (centery + r3 * Math.sin(deg180)));
        PointF pp225_begin = new PointF((float) (centerx + r3 * Math.cos(deg225)),
                (float) (centery + r3 * Math.sin(deg225)));
        PointF pp270_begin = new PointF((float) (centerx + r3 * Math.cos(deg270)),
                (float) (centery + r3 * Math.sin(deg270)));
        PointF pp315_begin = new PointF((float) (centerx + r3 * Math.cos(deg315)),
                (float) (centery + r3 * Math.sin(deg315)));

        PointF pp0_end = new PointF(centerx + r2, centery);
        PointF pp45_end = new PointF((float) (centerx + r2 * Math.cos(deg45)),
                (float) (centery + r2 * Math.sin(deg45)));
        PointF pp90_end = new PointF((float) (centerx + r2 * Math.cos(deg90)),
                (float) (centery + r2 * Math.sin(deg90)));
        PointF pp135_end = new PointF((float) (centerx + r2 * Math.cos(deg135)),
                (float) (centery + r2 * Math.sin(deg135)));
        PointF pp180_end = new PointF((float) (centerx + r2 * Math.cos(deg180)),
                (float) (centery + r2 * Math.sin(deg180)));
        PointF pp225_end = new PointF((float) (centerx + r2 * Math.cos(deg225)),
                (float) (centery + r2 * Math.sin(deg225)));
        PointF pp270_end = new PointF((float) (centerx + r2 * Math.cos(deg270)),
                (float) (centery + r2 * Math.sin(deg270)));
        PointF pp315_end = new PointF((float) (centerx + r2 * Math.cos(deg315)),
                (float) (centery + r2 * Math.sin(deg315)));

        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawLine(pp0_begin.x, pp0_begin.y, pp0_end.x, pp0_end.y, paint);
        canvas.drawLine(pp45_begin.x, pp45_begin.y, pp45_end.x, pp45_end.y, paint);
        canvas.drawLine(pp135_begin.x, pp135_begin.y, pp135_end.x, pp135_end.y, paint);
        canvas.drawLine(pp180_begin.x, pp180_begin.y, pp180_end.x, pp180_end.y, paint);
        canvas.drawLine(pp225_begin.x, pp225_begin.y, pp225_end.x, pp225_end.y, paint);
        canvas.drawLine(pp270_begin.x, pp270_begin.y, pp270_end.x, pp270_end.y, paint);
        canvas.drawLine(pp315_begin.x, pp315_begin.y, pp315_end.x, pp315_end.y, paint);
        paint.setTextSize(20);
        paint.setColor(Color.MAGENTA);
        float measureText = paint.measureText("000");
        canvas.drawText("300", pp0_end.x - measureText, pp0_end.y, paint);
        canvas.drawText("500", pp45_end.x - measureText, pp45_end.y, paint);
        canvas.drawText("0", pp135_end.x, pp135_end.y, paint);
        canvas.drawText("50", pp180_end.x, pp180_end.y, paint);
        canvas.drawText("100", pp225_end.x, pp225_end.y, paint);
        canvas.drawText("150", pp270_end.x - measureText / 2, pp270_end.y + 20, paint);
        canvas.drawText("200", pp315_end.x - measureText, pp315_end.y, paint);

        Bitmap bitmap = Bitmap.createBitmap(screenW, screenH, Config.ARGB_8888);
        Canvas cc = new Canvas(bitmap);
        paint.setStyle(Style.FILL);
        RectF newoval = new RectF(centerx - bigR - 1, centery - bigR - 1, centerx + bigR + 1,
                centery + bigR + 1);
        RectF newoval1 = new RectF(centerx - bigR + 20, centery - bigR + 20,
                centerx + bigR - 20,
                centery + bigR - 20);
        cc.drawArc(newoval, 135, degree * ((float) value / 100f), true, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        cc.drawArc(newoval1, 135, degree * ((float) value / 100f), true, paint);

        paint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        super.onDraw(canvas);
    }

    // 默认是90度
    public int degree = 90;
}
