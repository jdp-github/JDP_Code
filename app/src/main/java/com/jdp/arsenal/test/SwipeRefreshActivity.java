package com.jdp.arsenal.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jdp.arsenal.R;

import java.util.ArrayList;

public class SwipeRefreshActivity extends Activity {

    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        ListView listView = (ListView) findViewById(R.id.list);

        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }

        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(SwipeRefreshActivity.this).inflate(R.layout.item_list, parent, false);

                viewHolder.tv = (TextView) convertView.findViewById(R.id.item_tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv.setText(list.get(position));

            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }
    }
}
