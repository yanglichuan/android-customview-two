
package com.ylc.activity;

import com.example.bittt3.R;
import com.example.bittt3.R.id;
import com.example.bittt3.R.layout;
import com.ylc.view.MyJDMoneyRing;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Activity_Five extends Activity {
    public MyJDMoneyRing jdring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_five);
        jdring = (MyJDMoneyRing) this.findViewById(R.id.mybrightcontrol);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            
            @Override
            public void run() {
                jdring.setSweepAngle(90);
            }
        }, 2000);
        
    }
}
