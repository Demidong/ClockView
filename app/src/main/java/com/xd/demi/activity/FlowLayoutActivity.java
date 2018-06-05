package com.xd.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xd.demi.R;
import com.xd.demi.view.MyFlowLayout;
import com.xd.demi.view.ScaleImageView;

/**
 * Created by demi on 16/11/28.
 */

public class FlowLayoutActivity extends Activity {
    MyFlowLayout mMyFlowLayout ;
    public String[] texts = new String[]{"阳光正好","青春年少","青青河边草","彩虹桥","黄浦江边","高楼大厦","双宿双飞","美","漂亮"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        mMyFlowLayout = (MyFlowLayout) findViewById(R.id.flow_ll);
        ScaleImageView imageView = (ScaleImageView) findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(FlowLayoutActivity.this);
                int a = (int) (Math.random() * texts.length);
                textView.setText(texts[a]);
                textView.setPadding(10,10,10,10);
                textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_red_boder_shape));
                mMyFlowLayout.addView(textView);
            }
        });
    }
}
