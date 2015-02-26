
package com.ylc.activity;

import com.example.bittt3.R;
import com.example.bittt3.R.id;
import com.example.bittt3.R.layout;
import com.ylc.view.MyVifiView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class Activity_11 extends Activity {
    MyVifiView myvifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_11);
        
        myvifi= (MyVifiView) this.findViewById(R.id.myvifi);
    }
    
    public void ccc(View view){
        myvifi.stopAnim2(Color.GRAY);
    }
    
    public void ddd(View view){
        myvifi.startAnim();
    }
}
