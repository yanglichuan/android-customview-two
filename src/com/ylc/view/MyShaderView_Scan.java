
package com.ylc.view;

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
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

class MyShaderView_Scan extends View {

    private Bitmap bm;
    private Shader linearGradient;
    private Shader linearGradient2;
    private int[] colors;
    private int[] colors2;
    private Paint paint;
    private boolean isFirst = false;

    public MyShaderView_Scan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;

    public ObjectAnimator animator;
    public int current_value = 0;

    public MyShaderView_Scan(Context context, AttributeSet attrs) {
        super(context, attrs);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        paint = new Paint();
        paint.setAntiAlias(true);
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
                animator = ObjectAnimator.ofInt(new Object(), "t", 0, screenW);
                animator.setDuration(2000);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (Integer) animation.getAnimatedValue();
                        current_value = value;
                        linearGradient = new LinearGradient(value, 0, value + 30, 0, colors, null,
                                TileMode.CLAMP);
                        invalidate();
                    }
                });
                animator.start();

                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    public MyShaderView_Scan(Context context) {
        super(context);
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
            paint.setShader(linearGradient2);
            canvas.drawRect(0, 0, getWidth(), this.getHeight(), paint);

            paint.setShader(linearGradient);
            canvas.drawRect(current_value, 0, current_value + 30, this.getHeight(), paint);
        }
    }
}
