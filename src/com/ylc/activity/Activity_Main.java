
package com.ylc.activity;

import com.example.bittt3.R;
import com.example.bittt3.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class Activity_Main extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);
    }
    
    public void bt_function1(View view){
        startActivity(new Intent(this, Activity_One.class));
    }
    
    public void bt_function2(View view){
        startActivity(new Intent(this, Activity_Two.class));
    }
    
    public void bt_function3(View view){
        startActivity(new Intent(this, Activity_Three.class));
    }
    
    public void bt_function4(View view){
        startActivity(new Intent(this, Activity_Four.class));
    }
    public void bt_function5(View view){
        startActivity(new Intent(this, Activity_Five.class));
    }
    
    public void bt_function6(View view){
        startActivity(new Intent(this, Activity_six.class));
    }
    
    public void bt_function7(View view){
        startActivity(new Intent(this, Activity_7.class));
    }
    
    
    public void bt_function8(View view){
        startActivity(new Intent(this, Activity_8.class));
    }
    
    public void bt_function9(View view){
        startActivity(new Intent(this, Activity_9.class));
    }
    
    public void bt_function10(View view){
        startActivity(new Intent(this, Activity_10.class));
    }
    
    public void bt_function11(View view){
        startActivity(new Intent(this, Activity_11.class));
    }
    
    public void bt_function12(View view){
        startActivity(new Intent(this, Activity_12.class));
    }
    
    
}
