
package com.ylc.activity;

import java.util.ArrayList;

import com.example.bittt3.R;
import com.example.bittt3.R.id;
import com.example.bittt3.R.layout;
import com.ylc.view.MyNineGridview;
import com.ylc.view.MyNineGridview.NineGridViewListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

public class Activity_six extends Activity {
    public MyNineGridview nineview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.activity_six);

        nineview = (MyNineGridview) this.findViewById(R.id.mybrightcontrol);
        nineview.setNineGridViewListener(new NineGridViewListener() {
            @Override
            public void onOver(ArrayList<Integer> arrays) {
                Log.i("nine", arrays.toString());
                Log.i("nine", nineview.getData().toString());
            }
        });
    }
}
