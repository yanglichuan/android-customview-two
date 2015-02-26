
package com.example.bittt3;

import com.ylc.view.MyBrightControl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
public class Main2 extends Activity {

    MyBrightControl myvifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main2);
        //myvifi = (MyBrightControl) this.findViewById(R.id.mybrightcontrol);
    }

    PopupWindow mPopWin = null;
    public void ccc(View view) {
        // 获取LayoutInflater实例
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        // 获取弹出菜单的布局
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.bbbb, null);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWin.isShowing()) {
                    mPopWin.dismiss();
                }
            }
        });

        // 获得状态栏的高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 设置显示在状态栏下面
        LinearLayout.LayoutParams pp = (android.widget.LinearLayout.LayoutParams) layout
                .getChildAt(0).getLayoutParams();
        pp.topMargin = statusBarHeight;
        layout.getChildAt(0).setLayoutParams(pp);

        //设置显示输入法
        RelativeLayout rr = (RelativeLayout) layout.getChildAt(0);
        EditText ett = (EditText) rr.getChildAt(1);
        ShowKeyboard(ett);

        if (mPopWin == null) {
            mPopWin = new PopupWindow(layout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        mPopWin.setBackgroundDrawable(new ColorDrawable(0x66666666));
        mPopWin.setOutsideTouchable(true);
        mPopWin.setTouchable(true);
        mPopWin.setFocusable(true);
        // mPopWin.showAsDropDown(view,10,10);
        mPopWin.showAtLocation(view, Gravity.TOP, 0, 0);// 设置在屏幕中的显示位置
        mPopWin.update();
    }

    public void ddd(View view) {
        
    }

    // 显示虚拟键盘
    public void ShowKeyboard(View v)
    {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        // 显示或者隐藏输入法
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
