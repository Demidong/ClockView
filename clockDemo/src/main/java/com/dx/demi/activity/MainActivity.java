package com.dx.demi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dx.demi.R;

import static android.support.v7.widget.RecyclerView.*;


public class MainActivity extends AppCompatActivity {

    RecyclerView rcv ;
    Class[] clazz = {PieChatActivity.class, ClockActivity.class};
    String[] describle = {"水果大拼盘","时钟表盘"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv =(RecyclerView)findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(new MyRecyclerAdapter());
       // rcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST)));
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder =
                    new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(
                            R.layout.rev_item,parent,false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.text_content.setText(describle[position]);
            holder.text_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this,clazz[position]));
                }
            });
        }

        @Override
        public int getItemCount() {
            System.out.println(describle.length);
            return clazz.length;
        }
        /**
         *印证上面所说的，这边定义的内部类MyViewHolder需要继承RecyclerView.ViewHolder，
         *这样才能为系统所重用。
         **/
        class MyViewHolder extends ViewHolder{
            public TextView text_content;
            public MyViewHolder(View itemView) {
                super(itemView);
                /**
                 *初始化item里面的控件
                 */
                text_content = (TextView) itemView.findViewById(R.id.text_content);
            }
        }
    }
}
