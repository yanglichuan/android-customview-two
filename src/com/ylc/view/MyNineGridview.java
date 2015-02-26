
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

public class MyNineGridview extends View {

    private Paint paint;
    private boolean isFirst = false;

    public MyNineGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int screenH = 0;
    public int screenW = 0;
    public float rr = 0;

    public ArrayList<Ring> rings = new ArrayList<MyNineGridview.Ring>();

    public MyNineGridview(Context context, AttributeSet attrs) {
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

                getViewTreeObserver().removeOnPreDrawListener(this);
                initRing();
                return false;
            }
        });

        for (int i = 0; i < 9; i++) {
            Ring r = new Ring();
            rings.add(r);
        }

    }

    public void initRing() {
        rr = (int) ((float) screenW / 11f);
        for (int i = 0; i < rings.size(); i++) {
            rings.get(i).r = rr;
            rings.get(i).sub_r = rr / 3;
        }
        rings.get(0).x = (int) (2 * rr);
        rings.get(0).y = (int) (2 * rr);

        rings.get(1).x = (int) (5.5f * rr);
        rings.get(1).y = (int) (2 * rr);

        rings.get(2).x = (int) (9f * rr);
        rings.get(2).y = (int) (2 * rr);

        rings.get(3).x = (int) (2 * rr);
        rings.get(3).y = (int) (5.5 * rr);

        rings.get(4).x = (int) (5.5f * rr);
        rings.get(4).y = (int) (5.5 * rr);

        rings.get(5).x = (int) (9f * rr);
        rings.get(5).y = (int) (5.5 * rr);

        rings.get(6).x = (int) (2 * rr);
        rings.get(6).y = (int) (9 * rr);

        rings.get(7).x = (int) (5.5f * rr);
        rings.get(7).y = (int) (9 * rr);

        rings.get(8).x = (int) (9f * rr);
        rings.get(8).y = (int) (9 * rr);

    }

    public MyNineGridview(Context context) {
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

    public MyPath path = new MyPath();
    Line line = new Line();
    public boolean bPressed = false;
    public boolean bOk = false; // 默认就是ok的

    public SharedPreferences sp;

    public void saveData() {
        if (sp == null) {
            sp = getContext().getSharedPreferences("ninegrid", Context.MODE_PRIVATE);
        }
        Editor editor = sp.edit();
        for (int i = 0; i < 9; i++) {
            editor.putInt("num" + i, -1);
        }
        for (int j = 0; j < record.size(); j++) {
            editor.putInt("num" + j, record.get(j));
            Log.i("data", record.get(j) + "  <><><>  ");
        }
        boolean commit = editor.commit();
        if (commit) {
            if (listener != null) {
                listener.onOver(record);
            }
        }
    }

    public ArrayList<Integer> getData() {
        if (sp == null) {
            sp = getContext().getSharedPreferences("ninegrid", Context.MODE_PRIVATE);
        }
        ArrayList<Integer> t = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            int nmmm = sp.getInt("num" + i, -1);
            if (nmmm != -1) {
                t.add(nmmm);
            }
        }
        return t;
    }

    public ArrayList<Integer> record = new ArrayList<Integer>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                line.point1.x = event.getX();
                line.point1.y = event.getY();
                path.clear();
                drawReset();
                record.clear();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < 9; i++) {
                    if (bPointInRect(event.getX(), event.getY(), rings.get(i))) {
                        rings.get(i).color = Color.BLUE;
                        rings.get(i).bDrawSub = true;

                        if (!path.bContainPoint(new PointF(rings.get(i).x, rings.get(i).y))) {
                            bPressed = true;
                            bOk = false;
                            line.point1.x = rings.get(i).x;
                            line.point1.y = rings.get(i).y;
                        }

                        if (path.points.size() == 0) {
                            path.moveToto(rings.get(i).x, rings.get(i).y, i);
                        } else {
                            path.lineToto(rings.get(i).x, rings.get(i).y, i);
                        }
                        Log.i("test", path.points.size() + "   ::");
                        break;
                    }
                }
                line.point2.x = event.getX();
                line.point2.y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                bPressed = false;
                // 判断path中是否有4个点（至少4个点）
                if (path.points.size() >= 4) {
                    bOk = true;
                    saveData();
                } else {
                    // 重新绘制
                    drawReset();
                }
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public boolean bPointInRect(float x, float y, Ring ring) {
        RectF rectF = new RectF(ring.x - ring.r, ring.y - ring.r, ring.x + ring.r, ring.y + ring.r);
        if (rectF.contains(x, y)) {
            return true;
        }
        return false;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(1);
        for (int i = 0; i < 9; i++) {
            Ring ring = rings.get(i);
            drawRing(canvas, ring);
        }
        if (bPressed) {
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(4);
            drawLine(canvas);
            drawPath(canvas);
        } else {
            if (path.points.size() >= 4) {
                drawPath(canvas);
                // 画上肩头
            }
        }
        super.onDraw(canvas);
    }

    public void drawReset() {
        Log.i("test", "调用drawrest");
        for (int i = 0; i < 9; i++) {
            rings.get(i).color = Color.GRAY;
            rings.get(i).bDrawSub = false;
        }
    }

    public void drawRing(Canvas canvas, Ring ring) {
        paint.setStrokeWidth(1);
        paint.setStyle(Style.STROKE);
        paint.setColor(ring.color);
        canvas.drawCircle(ring.x, ring.y, ring.r, paint);

        if (ring.bDrawSub) {
            paint.setStyle(Style.FILL);
            paint.setColor(ring.color);
            canvas.drawCircle(ring.x, ring.y, ring.sub_r, paint);
        }
    }

    public void drawLine(Canvas canvas) {
        paint.setStrokeWidth(10);
        paint.setStyle(Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawLine(line.point1.x, line.point1.y, line.point2.x, line.point2.y, paint);
    }

    public void drawPath(Canvas canvas) {
        if (path.points.size() <= 1) {
            return;
        }
        paint.setStrokeWidth(10);
        paint.setStyle(Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setAlpha(150);
        canvas.drawPath(path, paint);
    }

    private NineGridViewListener listener;

    public interface NineGridViewListener {
        public void onOver(ArrayList<Integer> arrays);
    }

    public void setNineGridViewListener(NineGridViewListener ll) {
        this.listener = ll;
    }

    class Ring {
        public int x;
        public int y;
        public float r;
        public int color = Color.GRAY;
        public float sub_r;
        public boolean bDrawSub = false;
    }

    class Line {
        public PointF point1 = new PointF();// 表示开始
        public PointF point2 = new PointF();// 表示滑动的当前
    }

    class MyPath extends Path {
        public ArrayList<PointF> points = new ArrayList<PointF>();

        public void clear() {
            points.clear();
            this.reset();
        }

        public boolean bContainPoint(PointF ppp) {
            for (int i = 0; i < points.size(); i++) {
                int tx = (int) points.get(i).x;
                int ty = (int) points.get(i).y;
                if (tx == ppp.x && ty == ppp.y) {
                    return true;
                }
            }
            return false;
        }

        public void moveToto(float x, float y, int index) {
            this.reset();
            points.clear();
            points.add(new PointF(x, y));
            record.clear();
            record.add(index);
            super.moveTo(x, y);
        }

        public void lineToto(float x, float y, int index) {
            for (int i = 0; i < points.size(); i++) {
                int tx = (int) points.get(i).x;
                int ty = (int) points.get(i).y;
                if (tx == x && ty == y) {
                    return;
                }
            }
            points.add(new PointF(x, y));
            record.add(index);

            super.lineTo(x, y);
        }
    }
}
