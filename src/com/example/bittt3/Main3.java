
package com.example.bittt3;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class Main3 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main3);
    }

    PopupWindow mPopWin = null;
    public void dddd(View view) {
        // 获取LayoutInflater实例
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        // 获取弹出菜单的布局
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.bbbb, null);
        layout.setOnClickListener(new OnClickListener() {   
            @Override
            public void onClick(View v) {   
                if(mPopWin.isShowing()){   
                    mPopWin.dismiss();   
                }   
            }   
        });   
        layout.getChildAt(0).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了", 0).show();
            }
        });
        
        if(mPopWin == null){
            mPopWin = new PopupWindow(layout, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        }

        mPopWin.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_inline_error_holo_dark_am));
        mPopWin.setOutsideTouchable(true);
        mPopWin.setTouchable(true);
        mPopWin.setFocusable(true);
        mPopWin.showAsDropDown(view,10,10);
        //mPopWin.showAtLocation(view, Gravity.BOTTOM|Gravity.RIGHT,0,0);//设置在屏幕中的显示位置
        mPopWin.update();
    }
    
}
