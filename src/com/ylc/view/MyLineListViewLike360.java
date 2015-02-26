
package com.ylc.view;

import com.example.bittt3.R;
import com.example.bittt3.R.drawable;
import com.example.bittt3.R.id;
import com.example.bittt3.R.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyLineListViewLike360 extends ListView {

    public MyLineListViewLike360(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public View bar = null;
    public void setBar(View bar){
        this.bar = bar;
    }
    public View touxiang = null;
    public void settouxiang(View touxiang){
        this.touxiang = touxiang;
        
        RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) touxiang.getLayoutParams();
        layoutParams.topMargin  = 250;
        touxiang.setLayoutParams(layoutParams);
    }
    
    
    
    public MyAdapter adapter;
    public Activity context;
    public MyLineListViewLike360(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        adapter = new MyAdapter();
        setAdapter(adapter);
        addHeader();

        setDividerHeight(0);
        setDivider(null);
        
        setListener();
    }

    public View header;

    public void addHeader() {
        header = View.inflate(getContext(), R.layout.line_listlike360_title, null);
        if (header.getLayoutParams() == null) {
            AbsListView.LayoutParams pp = new LayoutParams(-1, 400);
            header.setLayoutParams(pp);
        }
        
        Log.i("alp", header.getAlpha()+"sdfsfsdf");
        header.getBackground().setAlpha(255);
        Log.i("alp", header.getAlpha()+"sdfsfsdf");
        addHeaderView(header);
    }
    
    

    
    
    

    int oldx = 0;
    int oldy = 0;

    public int header_h = 400;
    @SuppressLint("NewApi")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
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
                
                
                int[] location = new  int[2] ;
                header.getLocationOnScreen(location);
                Log.i("nimi", location[0]+"  "+location[1]);
                
                int  ddddy = 60 - location[1];
                header.getBackground().setAlpha((int)(255f - 255f* ddddy/460f));
                Log.i("mini", header.getBackground().getAlpha()+" up" +(int)(255f* (ddddy/460f)));
                
                
                
                RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) touxiang.getLayoutParams();
                layoutParams.topMargin += dy;
                if( layoutParams.topMargin >= 250){
                    layoutParams.topMargin = 250;
                }
                
                if( layoutParams.topMargin <= 50){
                    layoutParams.topMargin = 50;
                }
                touxiang.setLayoutParams(layoutParams);
                
                if(bar != null){
                    bar.getBackground().setAlpha((int)(255f* (ddddy/460f)));
                    Log.i("mini", bar.getBackground().getAlpha()+"");
                }
                
                oldx = x;
                oldy = y;
                //
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    
    
    public void setListener(){
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        int[] location = new  int[2] ;
                        header.getLocationOnScreen(location);
                        Log.i("mini", location[0]+" ---------- "+location[1]);
                        if(location[1] <= -399){
                            bar.getBackground().setAlpha(255);
                        }
                        
                        
                        break;

                    default:
                        break;
                }
                
                
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                mfirstVisibleItem  = firstVisibleItem;

                int[] location = new  int[2] ;
                header.getLocationOnScreen(location);
                Log.i("nimi", location[0]+"  "+location[1]);
                
                int  ddddy = 60 - location[1];
                header.getBackground().setAlpha((int)(255f - 255f* ddddy/460));
                if(bar != null){
                    if(mfirstVisibleItem >=1){
                        bar.getBackground().setAlpha(255);
                    }else{
                        bar.getBackground().setAlpha((int)(255f* (ddddy/460f)));
                    }
                }
                
                Log.i("nimi", location[0]+" :::: "+location[1]);
                
            }
        });
        
    }
    
    public int mfirstVisibleItem  =0;
    
    
    

    public MyLineListViewLike360(Context context) {
        super(context);

    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(getContext(), R.layout.line_listview_360, null);
                TextView show1 = (TextView) view.findViewById(R.id.show1);
                TextView show2 = (TextView) view.findViewById(R.id.show2);
                TextView show3 = (TextView) view.findViewById(R.id.show3);
                ViewHoder hoder = new ViewHoder();
                hoder.show1 = show1;
                hoder.show2 = show2;
                hoder.show3 = show3;
                view.setTag(hoder);
            }
            ViewHoder hh = (ViewHoder) view.getTag();
            if (position == 2) {
                hh.show2.setVisibility(View.INVISIBLE);
            } else {
                hh.show2.setVisibility(View.VISIBLE);
            }

            if (position == 10) {
                hh.show2.setBackgroundResource(R.drawable.sun1);
            } else {
                hh.show2.setBackgroundResource(R.drawable.ic_launcher);
            }
            return view;
        }
    }

    public static class ViewHoder {
        public TextView show1;
        public TextView show2;
        public TextView show3;
    }

}
