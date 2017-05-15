package com.dx.demi.activity;

import android.content.Intent;
import android.content.res.ObbInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dx.demi.R;
import com.dx.demi.adapter.DividerItemDecoration;
import com.dx.demi.adapter.SamplesAdapter;
import com.dx.demi.bean.Samples;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.support.v7.widget.RecyclerView.*;


public class MainActivity extends AppCompatActivity {
    RecyclerView rcv ;
    Class[] clazz = {PieChatActivity.class, ClockActivity.class,CoordinateActivity.class,EventActivity.class,
            ListActivity.class,NotifyCationActivity.class,ShopCarActivity.class,CameraActivity.class,
            EToastActivity.class,TimeConutDownActivity.class,AnimationActivity.class,ViewPagerActivity.class,
            DownloadActivity.class,RetrofitOKHttpActivity.class,PercentActivity.class,TinkerActivity.class};
    String[] describle = {"水果大拼盘","时钟表盘","坐标系","事件监听",
            "多布局RecycleView列表","通知栏事件监听","购物车动画","照相机",
    "自定义Toast","倒计时","动画","ViewPager","下载apk","Retrofit",
    "圆环百分比","Tinker"};
    ArrayList<Samples> sampleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i <describle.length ; i++) {
            Samples mSample =new Samples();
            mSample.setDescription(describle[i]);
            mSample.setMClass(clazz[i]);
            sampleList.add(mSample);
        }
        SamplesAdapter sampleAdapter= new SamplesAdapter(sampleList,this);
        sampleAdapter.openLoadAnimation();
        rcv =(RecyclerView)findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(sampleAdapter);
        rcv.addItemDecoration(new DividerItemDecoration(this));
        sampleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Samples sample = (Samples) adapter.getItem(position);
                startActivity(new Intent(MainActivity.this,sample.getMClass()));
                return true;
            }
        });
    }


}