package com.xd.demi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xd.demi.R;

import java.util.ArrayList;

/**
 * Created by demi on 2017/2/13.
 */

public class MyAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;

    public MyAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

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
        ViewHold viewHold;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHold.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.text.setText(list.get(position));
        return convertView;
    }

    class ViewHold {
        private TextView text;
    }
}
