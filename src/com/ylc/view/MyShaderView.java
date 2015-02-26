
package com.ylc.view;

import com.example.bittt3.R;
import com.example.bittt3.R.drawable;

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

class MyShaderView extends View {

    private Bitmap bm;
    private Shader bitmapShader;
    private Shader linearGradient;
    private Shader radialGradient;
    private Shader sweepGradient;
    private Shader composeShader;
    private int[] colors;
    private Paint paint;
    private boolean isFirst = false;

    public int bowenX = 200;
    public int bowenY = 200;
    
    public int oldX = 200;
    public int oldY = 200;
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = (int) event.getX();
                oldY = (int) event.getY();
                bowenX = oldX;
                bowenY = oldY;
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int dx = x - oldX;
                int dy = y - oldY;
                bowenX += dx;
                bowenY += dy;
                oldX = x;
                oldY = y;
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public MyShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public MyShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        paint = new Paint();
        paint.setAntiAlias(true);
        colors = new int[] {
                Color.WHITE, Color.CYAN,Color.WHITE, Color.CYAN,Color.WHITE, Color.CYAN
        };
        bitmapShader = new BitmapShader(bm, TileMode.REPEAT, TileMode.MIRROR);
        linearGradient = new LinearGradient(0, 0, 100, 100, colors, null, TileMode.REPEAT);
        radialGradient = new RadialGradient(bowenX, bowenY, 80, colors, null, TileMode.REPEAT);
        sweepGradient = new SweepGradient(200, 200, colors, null);
        composeShader = new ComposeShader(linearGradient, radialGradient, PorterDuff.Mode.DARKEN);

        setFocusable(true);
    }




    public MyShaderView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            String content = "press up/down/left/right/middle to draw rectangle";
            paint.setColor(Color.BLUE);
            canvas.drawText(content, 0, content.length() - 1, 20, 20, paint);
        } else {
            linearGradient = new LinearGradient(0, 0, 100, 0, colors, null, TileMode.CLAMP);
            paint.setShader(linearGradient);
            canvas.drawRect(0, 0, getWidth(), this.getHeight(), paint);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        isFirst = false;
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            paint.setShader(bitmapShader);
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            paint.setShader(linearGradient);
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            paint.setShader(radialGradient);
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            paint.setShader(sweepGradient);
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            paint.setShader(composeShader);
        }
        postInvalidate();
        return super.onKeyDown(keyCode, event);
    }
}
