package com.dx.demi.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dx.demi.R;

import java.util.ArrayList;

/**
 * Created by demi on 2018/1/28.
 */

public class EmojiAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;
    public EmojiAdapter(ArrayList<String> list, Context context) {
        this.list =list;
        this.context =context;
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
        ViewHold viewHold ;
        if(convertView == null){
            viewHold = new ViewHold();
           TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(25);
            convertView = textView;
            viewHold.text = textView;
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.text.setText(list.get(position));
        return convertView;
    }
    class ViewHold{
        private TextView text;
    }
}
