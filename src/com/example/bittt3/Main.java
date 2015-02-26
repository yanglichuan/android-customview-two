
package com.example.bittt3;
import java.util.ArrayList;
import java.util.Collections;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ylc.view.MyCharSelectView;
import com.ylc.view.MyCharSelectView.OnSelectCharListner;

public class Main extends Activity {
    private MyCharSelectView charView;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        charView = (MyCharSelectView) this.findViewById(R.id.char_view);
        listView = (ListView) this.findViewById(R.id.mylist);
        String[] ssss = new String[] {
                "赵1", "赵2", "赵3", "赵4", "赵5", "赵6", "赵7", "赵8", "赵9", "赵10", "赵11", "赵12", "赵13",
                "赵14", "赵15",
                "王1", "王2", "王3", "王4", "王5", "王6", "王7", "王8", "王9", "王10", "王11", "王12", "王13",
                "王14", "王15",
                "李1", "李2", "李3", "李4", "李5", "李6", "李7", "李8", "李9", "李10", "李11", "李12", "李13",
                "李14", "李15",
                "阿1", "开2", "拍3", "放4", "就5", "地6", "老7", "提8", "送9", "曹10", "赵11", "赵12", "赵13",
                "赵14", "赵15"
        };
        for (String string : ssss) {
            names.add(string);
        }
        Collections.sort(names, Util.getComparator());
        listView.setAdapter(new MyAdapter());

        charView.setOnSelectCharListner(new OnSelectCharListner() {
            @Override
            public void onSelectChar(String str) {
                for (int i = 0; i < names.size(); i++) {
                    Log.i("bibi", ">>>>>" + Util.getPingYin(names.get(i)).toUpperCase() + "   "
                            + str);

                    if (Util.getPingYin(names.get(i)).toUpperCase().startsWith(str)) {
                        listView.setSelection(i);
                        break;
                    }
                }
            }
        });
    }
    private ArrayList<String> names = new ArrayList<String>();
    public void dddd(View view) {
    }
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.size();
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
            TextView tv = null;
            if (convertView != null) {
                tv = (TextView) convertView;
            } else {
                tv = new TextView(getApplicationContext());
            }
            tv.setBackgroundColor(Color.BLUE);
            tv.setText(names.get(position));
            tv.setLayoutParams(new AbsListView.LayoutParams(-1, 200));
            return tv;
        }
    }
}
