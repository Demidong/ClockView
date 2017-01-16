package com.dx.demi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dx.demi.R;
import com.dx.demi.View.PieChatView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * Created by demi on 16/12/9.
 */

public class ListActivity extends Activity {
    RecyclerView list_rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list_rcv = (RecyclerView) findViewById(R.id.list_rcv);
        list_rcv.setLayoutManager(new LinearLayoutManager(this));
        list_rcv.setAdapter(new ListActivity.TypeRecyclerAdapter());
    }

    class TypeRecyclerAdapter extends RecyclerView.Adapter<TypeRecyclerAdapter.MyViewHolder> {
        public static final int title_type = 1;
        public static final int content_type = 2;


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = null;

            switch (viewType) {
                case title_type:
                    viewHolder = new TypeRecyclerAdapter.MyViewHolder(LayoutInflater.from(ListActivity.this).inflate(
                            R.layout.recycle_title, parent, false), title_type);
                    break;
                case content_type:
                    viewHolder = new TypeRecyclerAdapter.MyViewHolder(LayoutInflater.from(ListActivity.this).inflate(
                            R.layout.recycle_content, parent, false), content_type);
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            switch (holder.type){
                case  title_type:
                    holder.name.setText("我的达人");
                    holder.more.setText("更多");
                    break;
                case  content_type:
                setPieChatView(holder);
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return 20;
        }


        @Override
        public int getItemViewType(int position) {

            return position % 3 == 0 ? title_type : content_type;
        }

        /**
         * 印证上面所说的，这边定义的内部类MyViewHolder需要继承RecyclerView.ViewHolder，
         * 这样才能为系统所重用。
         **/
        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public TextView more;
            public PieChatView pieChatView;
            public int type;

            public MyViewHolder(View itemView, int viewType) {
                super(itemView);
                switch (viewType) {
                    case title_type:
                        /**
                         *初始化item里面的控件
                         */
                        name = (TextView) itemView.findViewById(R.id.name);
                        more = (TextView) itemView.findViewById(R.id.more);
                        type = viewType;
                        break;
                    case content_type:
                        pieChatView = (PieChatView) itemView.findViewById(R.id.piechat);
                        type = content_type;
                        break;
                }

            }
        }
      public void  setPieChatView(MyViewHolder holder){
           LinkedHashMap kindsMap = new LinkedHashMap<String, Integer>();
           ArrayList<Integer> colors = new ArrayList<>();
          holder.pieChatView.setCenterTitle("水果大拼盘");
          kindsMap.put("苹果", 10);
          kindsMap.put("梨子", 30);
          kindsMap.put("香蕉", 10);
          kindsMap.put("葡萄", 30);
          kindsMap.put("哈密瓜", 10);
          kindsMap.put("猕猴桃", 30);
          kindsMap.put("草莓", 10);
          kindsMap.put("橙子", 30);
          kindsMap.put("火龙果", 10);
          kindsMap.put("椰子", 20);
          for (int i = 1; i <= 40; i++) {
              int r = (new Random().nextInt(100) + 10) * i;
              int g = (new Random().nextInt(100) + 10) * 3 * i;
              int b = (new Random().nextInt(100) + 10) * 2 * i;
              int color = Color.rgb(r, g, b);
              if (Math.abs(r - g) > 10 && Math.abs(r - b) > 10 && Math.abs(b - g) > 10) {
                  colors.add(color);
              }
          }
          holder.pieChatView.setCenterTitle("水果大拼盘");
          holder.pieChatView.setDataMap(kindsMap);
          holder.pieChatView.setColors(colors);
          holder.pieChatView.setMinAngle(50);
          holder.pieChatView.startDraw();
        }
    }
}
