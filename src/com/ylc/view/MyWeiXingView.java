
package com.ylc.view;

import java.util.ArrayList;

import com.example.bittt3.R;
import com.example.bittt3.R.id;
import com.example.bittt3.R.layout;
import com.example.bittt3.R.style;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

class MyWeiXingView extends View {

    public MyWeiXingView(Context context) {
        super(context);
    }

    public MyWeiXingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Context context;
    public PopupWindow mPopWin1;
    public PopupWindow mPopWin2;
    public PopupWindow mPopWin3;
    public TextView tv_char;

    PopupWindow[] pops = new PopupWindow[3];

    private int globalWH = 100;

    public void createpopup(final int i, PopupWindow bb) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // 获取弹出菜单的布局
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.show_char, null);
        tv_char = (TextView) layout.findViewById(R.id.show_char);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWin1.isShowing()) {
                    mPopWin1.dismiss();
                }
            }
        });
        if (mPopWin1 == null) {
            mPopWin1 = new PopupWindow(layout, globalWH, globalWH);
        }
        mPopWin1.setBackgroundDrawable(new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setStyle(Style.FILL);
                paint.setColor(Color.BLUE);
                canvas.drawCircle(mPopWin2.getWidth() / 2, mPopWin2.getHeight() / 2,
                        mPopWin2.getHeight() / 2, paint);
            }
        }));

        // 获取弹出菜单的布局
        layout = (RelativeLayout) inflater.inflate(R.layout.show_char, null);
        tv_char = (TextView) layout.findViewById(R.id.show_char);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWin2.isShowing()) {
                    mPopWin2.dismiss();
                }
            }
        });
        if (mPopWin2 == null) {
            mPopWin2 = new PopupWindow(layout, globalWH, globalWH);
        }
        mPopWin2.setBackgroundDrawable(new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setStyle(Style.FILL);
                paint.setColor(Color.BLUE);
                canvas.drawCircle(mPopWin2.getWidth() / 2, mPopWin2.getHeight() / 2,
                        mPopWin2.getHeight() / 2, paint);
            }
        }));

        // 获取弹出菜单的布局
        layout = (RelativeLayout) inflater.inflate(R.layout.show_char, null);
        tv_char = (TextView) layout.findViewById(R.id.show_char);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWin3.isShowing()) {
                    mPopWin3.dismiss();
                }
            }
        });
        if (mPopWin3 == null) {
            mPopWin3 = new PopupWindow(layout, globalWH, globalWH);
        }
        mPopWin3.setBackgroundDrawable(new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setStyle(Style.FILL);
                paint.setColor(Color.BLUE);
                canvas.drawCircle(mPopWin2.getWidth() / 2, mPopWin2.getHeight() / 2,
                        mPopWin2.getHeight() / 2, paint);
            }
        }));

        // mPopWin.setOutsideTouchable(true);
        // mPopWin.setTouchable(true);
        // mPopWin.setFocusable(true);
        // mPopWin.showAsDropDown(view,10,10);
    }

    public Paint paint;
    public MyWeiXingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        paint.setTextSize(39);

        createpopup(0, mPopWin1);
        createpopup(1, mPopWin2);
        createpopup(2, mPopWin3);
    }

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
            result = (int) defaultW + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        screenW = result;
        return result;
    }

    public int screenH = 0;
    public int screenW = 0;
    public int defaultH = 100;
    public int defaultW = 100;
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
            Log.i("bbb", "sdfsdf::" + result);
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        screenH = result;
        return result;
    }
    
    private int cc = Color.MAGENTA;
    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawColor(Color.GRAY);
        paint.setColor(cc);
        paint.setStyle(Style.FILL);
        RectF rect = new RectF(0, 0, screenW, screenH);
        canvas.drawCircle(screenW / 2, screenW / 2, screenW / 2, paint);
    }

    public int oldX = 0;
    public int oldY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = (int) event.getX();
                oldY = (int) event.getY();
                cc = Color.CYAN;
                Log.i("bibi", "开始调用sdfsdfs");
                if (mPopWin3 != null) {
                    Log.i("bibi", "开始调用ontouche");
                    if (mPopWin1.isShowing()) {
                        mPopWin1.setAnimationStyle(R.style.popwin_anim_style);
                        mPopWin2.setAnimationStyle(R.style.popwin_anim_style);
                        mPopWin3.setAnimationStyle(R.style.popwin_anim_style);
                        mPopWin1.dismiss();
                        mPopWin2.dismiss();
                        mPopWin3.dismiss();
                    } else {
                        mPopWin1.setAnimationStyle(R.style.popwin_anim_style);
                        mPopWin2.setAnimationStyle(R.style.popwin_anim_style);
                        mPopWin3.setAnimationStyle(R.style.popwin_anim_style);
                        mPopWin1.showAsDropDown(this, 30, 30);
                        mPopWin2.showAsDropDown(this, 200, -150);
                        mPopWin3.showAsDropDown(this, 160, -30);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int dx = x - oldX;
                int dy = y - oldY;

                oldX = x;
                oldY = y;
                break;
            case MotionEvent.ACTION_UP:
                cc = Color.MAGENTA;
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

}
