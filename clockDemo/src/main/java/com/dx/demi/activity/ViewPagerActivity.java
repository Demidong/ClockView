package com.dx.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.dx.demi.R;
import com.dx.demi.adapter.MyAdapter;
import com.dx.demi.adapter.MyPagerAdapter;
import com.dx.demi.View.MyViewPager;
import com.dx.demi.utils.T;

import java.util.ArrayList;

/**
 * Created by demi on 2017/2/13.
 */

public class ViewPagerActivity extends Activity {
    private ListView pagerlist ;
    private MyAdapter myAdapter;
    private ArrayList<String> list ;
    private MyViewPager viewpager ;
    private MyPagerAdapter myPagerAdapter;
    private ArrayList<View> pagerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        View header =  getLayoutInflater().inflate(R.layout.header_viewpager,null);
        viewpager= (MyViewPager) header.findViewById(R.id.pager);
        pagerlist = (ListView) findViewById(R.id.pagerlist);
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(""+i);
        }
        pagerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ImageView image = new ImageView(this);
            pagerList.add(image);
        }
        myPagerAdapter = new MyPagerAdapter(pagerList,this);
        viewpager.setAdapter(myPagerAdapter);
        pagerlist.addHeaderView(header);
        myAdapter = new MyAdapter(list,this);
        pagerlist.setAdapter(myAdapter);
        pagerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                T.showLong(ViewPagerActivity.this,list.get(position));
            }
        });
    }
}
