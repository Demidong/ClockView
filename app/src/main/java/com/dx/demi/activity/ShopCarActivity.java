package com.dx.demi.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dx.demi.R;
import com.dx.demi.view.ShopCarLayout;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by demi on 16/12/16.
 */

public class ShopCarActivity extends Activity {
    TextView tv_myDr;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;
    private ShopCarLayout shopCarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);
        shopCarLayout = new ShopCarLayout(ShopCarActivity.this);
        iv1 = (ImageView) findViewById(R.id.iv_one);
        iv2 = (ImageView) findViewById(R.id.iv_two);
        iv3 = (ImageView) findViewById(R.id.iv_three);
        iv4 = (ImageView) findViewById(R.id.iv_four);
        tv_myDr = (TextView) findViewById(R.id.tv_myDr);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] start_location = new int[2];
                iv1.getLocationInWindow(start_location);//获取点击商品图片的位置
                shopCarLayout.doAnim(iv1.getDrawable(),start_location,tv_myDr);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] start_location = new int[2];
                iv2.getLocationInWindow(start_location);//获取点击商品图片的位置

                shopCarLayout.doAnim(iv2.getDrawable(),start_location,tv_myDr);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] start_location = new int[2];
                iv3.getLocationInWindow(start_location);//获取点击商品图片的位置

                shopCarLayout.doAnim(iv3.getDrawable(),start_location,tv_myDr);
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] start_location = new int[2];
                iv4.getLocationInWindow(start_location);//获取点击商品图片的位置
                shopCarLayout.doAnim(iv4.getDrawable(),start_location,tv_myDr);
            }
        });
    }

}
