
package com.ylc.view;

import java.util.ArrayList;

import com.example.bittt3.R;
import com.example.bittt3.R.drawable;
import com.example.bittt3.R.id;
import com.example.bittt3.R.layout;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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

public class MyCharSelectView extends View {

    public int bowenX = 200;
    public int bowenY = 200;

    public int oldX = 200;
    public int oldY = 200;

    public int defaultW = 50;
    public int defaultH = 200;

    public MyCharSelectView(Context context) {
        super(context);
    }

    public MyCharSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public Context context;
    public PopupWindow mPopWin;
    public TextView tv_char;

    public void createpopup() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // 获取弹出菜单的布局
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.show_char, null);
        tv_char = (TextView) layout.findViewById(R.id.show_char);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWin.isShowing()) {
                    mPopWin.dismiss();
                }
            }
        });
        if (mPopWin == null) {
            mPopWin = new PopupWindow(layout, 200, 200);
        }

        mPopWin.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.back));
        // mPopWin.setOutsideTouchable(true);
        // mPopWin.setTouchable(true);
        // mPopWin.setFocusable(true);
        // mPopWin.showAsDropDown(view,10,10);
    }

    public Paint paint;

    public MyCharSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        paint.setTextSize(39);

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*";
        char[] array = str.toCharArray();
        for (char c : array) {
            mChars.add(new String(new char[] {
                    c
            }));
        }
        createpopup();
    }

    public ArrayList<String> mChars = new ArrayList<String>();

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

        perW = result;
        screenW = result;
        return result;
    }

    public int perH = 0;
    public int perW = 0;
    public int screenH = 0;
    public int screenW = 0;

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
        
        perH = result / mChars.size();
        screenH = result;
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawColor(Color.GRAY);
        paint.setColor(Color.GRAY);
        paint.setStyle(Style.FILL);
        RectF rect = new RectF(0, 0, screenW, screenH);
        canvas.drawRoundRect(rect, 20, 20, paint);
        // canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),
        // R.drawable.back),
        // null, rect, paint);

        paint.setStyle(Style.FILL);
        paint.setColor(Color.BLUE);
        for (int i = 0; i < mChars.size(); i++) {
            int y = (i + 1) * perH;
            canvas.drawText(mChars.get(i), perW / 4, y, paint);
        }
    }

    public void showCharByxy(int x, int y) {
        if (tv_char == null) {
            return;
        }
        int index = y / perH;
        Log.i("bibi", index + "");
        tv_char.setText(mChars.get(index));
        mPopWin.showAtLocation(this, Gravity.CENTER, 0, 0);// 设置在屏幕中的显示位置
        mPopWin.update();
        
        //todo 添加监听
        if(listner != null){
            listner.onSelectChar(mChars.get(index));
        }
    }
    
    private OnSelectCharListner listner;
    public interface OnSelectCharListner{
        public void onSelectChar(String str);
    }
    public void setOnSelectCharListner(OnSelectCharListner l){
        this.listner = l;
    }
    
    
    
    
    

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mPopWin != null) {
                mPopWin.dismiss();
            }
        };
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = (int) event.getX();
                oldY = (int) event.getY();
                showCharByxy(oldX, oldY);
                handler.removeMessages(100);
                break;
            case MotionEvent.ACTION_MOVE:
                handler.removeMessages(100);
                int x = (int) event.getX();
                int y = (int) event.getY();
                int dx = x - oldX;
                int dy = y - oldY;
                showCharByxy(x, y);
                oldX = x;
                oldY = y;
                break;
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(100, 2000);
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

}
