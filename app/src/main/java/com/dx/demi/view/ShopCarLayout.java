package com.dx.demi.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;


import com.dx.demi.utils.DensityUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by demi on 16/12/19.
 */

public class ShopCarLayout extends FrameLayout {
    //动画时间
    private int animationDuration = 1000;
    //正在执行的动画数量
    private int number = 0;
    //是否完成清理
    private boolean isClean = false;
    private Context context ;
    public ShopCarLayout(Context context) {
        super(context);
        this.context =context ;
        ViewGroup rootView = (ViewGroup) ((Activity)context).getWindow().getDecorView();
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
        setBackgroundResource(android.R.color.transparent);
        rootView.addView(this);
    }
    /**
     * 动画效果设置
     * @param drawable
     *       将要加入购物车的商品
     * @param start_location
     *        起始位置
     */
    private void setAnim(Drawable drawable, int[] start_location,View finish_view){


        Animation mScaleAnimation = new ScaleAnimation(1.5f,0.0f,1.5f,0.0f,Animation.RELATIVE_TO_SELF,0.1f,Animation.RELATIVE_TO_SELF,0.1f);
        mScaleAnimation.setDuration(animationDuration);
        mScaleAnimation.setFillAfter(true);


        final CircleImageView iview = new CircleImageView(context);
        iview.setImageDrawable(drawable);
        // final View view = addViewToAnimLayout(this,iview,start_location);
        //view.setAlpha(0.6f);

        int[] end_location = new int[2];
        finish_view.getLocationInWindow(end_location);
        int endX = end_location[0]-start_location[0]+finish_view.getWidth()/2;
        int endY = end_location[1]-start_location[1]+finish_view.getHeight()/2;

        Animation mTranslateAnimation = new TranslateAnimation(0,endX,0,endY);
        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(animationDuration);
        mTranslateAnimation.setDuration(animationDuration);
        AnimationSet mAnimationSet = new AnimationSet(true);

        mAnimationSet.setFillAfter(true);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimation);

        mAnimationSet.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub

                number--;
                if(number==0){
                    isClean = true;
                    removeAllViews();
                    isClean =false;
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

        });
        iview.startAnimation(mAnimationSet);

    }


    public void doAnim(Drawable drawable, int[] start_location,View finish_view){
        if(!isClean){
            setAnim(drawable,start_location,finish_view);
        }else{
            try{
                removeAllViews();
                isClean = false;
                setAnim(drawable,start_location,finish_view);
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                isClean = true;
            }
        }
    }
    /**
     * @deprecated 将要执行动画的view 添加到动画层
     * @param vg
     *        动画运行的层 这里是frameLayout
     * @param view
     *        要运行动画的View
     * @param location
     *        动画的起始位置
     * @return
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location){
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        LayoutParams lp = new LayoutParams(
                DensityUtils.dp2px(context,30), DensityUtils.dp2px(context,30));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }
}
