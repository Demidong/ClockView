package com.xd.demi.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xd.demi.R;
import com.xd.demi.utils.ViewSizeChangeAnimation;

/**
 * Created by demi on 2019/1/30 下午3:15.
 */
public class PercentView extends LinearLayout {
    private static int LEFT_HEAD = 0;
    private static int CENTER_HEAD = 1;
    private static int RIGHT_HEAD = 2;
    private int leftHead = 60;
    private int rightHead = 40;
    private int centerHead;
    private int flagMax = LEFT_HEAD;
    private RelativeLayout center_rl;
    private FrameLayout left_fr;
    private FrameLayout right_fr;
    private ImageView left_iv;
    private ImageView right_iv;
    private ImageView center_iv_left;
    private ImageView center_iv_right;
    private ObjectAnimator animatorL;
    private ObjectAnimator animatorR;
    private ObjectAnimator animatorCL;
    private ObjectAnimator animatorCR;

    public PercentView(Context context) {
        super(context);
        init(context);
    }

    public PercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.view_percent, this);
        left_fr = view.findViewById(R.id.left_fr);
        left_iv = view.findViewById(R.id.left_iv);
        right_fr = view.findViewById(R.id.right_fr);
        right_iv = view.findViewById(R.id.right_iv);
        center_rl = view.findViewById(R.id.center_rl);
        center_iv_left = view.findViewById(R.id.center_iv_left);
        center_iv_right = view.findViewById(R.id.center_iv_right);
        confirmFlagMax();
        setArrowAnimator();
        setPercentMoveAnimator();
    }

    public void startLeftGunDong() {
        if (animatorL != null && animatorL.isRunning()) {
            return;
        }
        PropertyValuesHolder xholder = PropertyValuesHolder.ofInt("scrollX", 0, -220);
        animatorL = ObjectAnimator.ofPropertyValuesHolder(left_iv, xholder);
        setAnimatorProperty(animatorL);
        animatorL.start();
    }

    public void startRightGunDong() {
        if (animatorR != null && animatorR.isRunning()) {
            return;
        }
        PropertyValuesHolder xholder = PropertyValuesHolder.ofInt("scrollX", 0, 220);
        animatorR = ObjectAnimator.ofPropertyValuesHolder(right_iv, xholder);
        setAnimatorProperty(animatorR);
        animatorR.start();
    }

    public void startCenterGunDong() {
        if (animatorCL != null && animatorCL.isRunning()) {
            return;
        }
        PropertyValuesHolder xLeftholder = PropertyValuesHolder.ofInt("scrollX", 0, 220);
        PropertyValuesHolder xRightholder = PropertyValuesHolder.ofInt("scrollX", 0, -220);
        animatorCL = ObjectAnimator.ofPropertyValuesHolder(center_iv_left, xLeftholder);
        animatorCR = ObjectAnimator.ofPropertyValuesHolder(center_iv_right, xRightholder);
        setAnimatorProperty(animatorCL);
        setAnimatorProperty(animatorCR);
        animatorCL.start();
        animatorCR.start();
    }

    private void setAnimatorProperty(ObjectAnimator animator) {
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
    }
    private void clearLeftGunDong(){
        if(animatorL != null){
            animatorL.cancel();
        }
    }
    private void clearRightGunDong(){
        if(animatorR != null){
            animatorR.cancel();
        }
    }
    private void clearCenterGunDong(){
        if(animatorCL != null){
            animatorCL.cancel();
        }
        if(animatorCR != null){
            animatorCR.cancel();
        }
    }
    public void setPercent(int left, int right) {
        this.leftHead = left;
        this.rightHead = right;
        this.centerHead = 100 - rightHead - leftHead;
        confirmFlagMax();
        setArrowAnimator();
        setPercentMoveAnimator();
    }

    public void confirmFlagMax() {
        int max;
        if (leftHead > rightHead) {
            max = leftHead;
            flagMax = LEFT_HEAD;
        } else {
            max = rightHead;
            flagMax = RIGHT_HEAD;
        }
        if (max < centerHead) {
            flagMax = CENTER_HEAD;
        }
    }

    public void setArrowAnimator() {
        if (flagMax == LEFT_HEAD) {
            startLeftGunDong();
            clearRightGunDong();
            clearCenterGunDong();
            left_iv.setVisibility(View.VISIBLE);
            right_iv.setVisibility(View.GONE);
            center_iv_left.setVisibility(View.GONE);
            center_iv_right.setVisibility(View.GONE);
        } else if (flagMax == RIGHT_HEAD) {
            startRightGunDong();
            clearLeftGunDong();
            clearCenterGunDong();
            left_iv.setVisibility(View.GONE);
            right_iv.setVisibility(View.VISIBLE);
            center_iv_left.setVisibility(View.GONE);
            center_iv_right.setVisibility(View.GONE);
        } else {
            startCenterGunDong();
            clearLeftGunDong();
            clearRightGunDong();
            right_iv.setVisibility(View.GONE);
            left_iv.setVisibility(View.GONE);
            center_iv_left.setVisibility(View.VISIBLE);
            center_iv_right.setVisibility(View.VISIBLE);
        }
    }


    public void setPercentMoveAnimator() {
        LinearLayout.LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, leftHead);
        LinearLayout.LayoutParams cp = new LayoutParams(0, LayoutParams.MATCH_PARENT, centerHead);
        LinearLayout.LayoutParams rp = new LayoutParams(0, LayoutParams.MATCH_PARENT, rightHead);
        ViewSizeChangeAnimation animationL = new ViewSizeChangeAnimation(left_fr, (int) (lp.weight / 100 * getWidth()));
        animationL.setDuration(500);
        left_fr.startAnimation(animationL);
        ViewSizeChangeAnimation animationC = new ViewSizeChangeAnimation(center_rl, (int) (cp.weight / 100 * getWidth()));
        animationC.setDuration(500);
        center_rl.startAnimation(animationC);
        ViewSizeChangeAnimation animationR = new ViewSizeChangeAnimation(right_fr, (int) (rp.weight / 100 * getWidth()));
        animationR.setDuration(500);
        right_fr.startAnimation(animationR);
    }


}
