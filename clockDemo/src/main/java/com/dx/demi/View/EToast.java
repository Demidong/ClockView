package com.dx.demi.View;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dx.demi.R;


public class EToast {
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    private static EToast result;
    //动画时间
    private final int ANIMATION_DURATION = 600;
    private static TextView mTextView;
    private ViewGroup container;
    private View v;
    //默认展示时间
    private int HIDE_DELAY = 2000;
    private LinearLayout mContainer;
    private AlphaAnimation mFadeOutAnimation;
    private AlphaAnimation mFadeInAnimation;
    private boolean isShow = false;
    private static Context mContext;
    private Handler mHandler = new Handler();

    private EToast(Context context) {
        mContext = context;
        container = (ViewGroup) ((Activity) context)
                .findViewById(android.R.id.content);
        v = ((Activity) context).getLayoutInflater().inflate(
                R.layout.etoast, container);
        mContainer = (LinearLayout) v.findViewById(R.id.mbContainer);
        mContainer.setVisibility(View.GONE);
        mTextView = (TextView) v.findViewById(R.id.mbMessage);
    }

    public static EToast makeText(Context context, String message, int HIDE_DELAY) {
        if(result == null){
            result = new EToast(context);
        }else{
            //这边主要是当切换Activity后我们应该更新当前持有的context，不然无法显示的
            if(!mContext.getClass().getName().equals(context.getClass().getName())){
                result = new EToast(context);
            }
        }
        if(HIDE_DELAY == LENGTH_LONG){
            result.HIDE_DELAY = 2500;
        }else{
            result.HIDE_DELAY = 1500;
        }
        mTextView.setText(message);
        return result;
    };
    public static EToast makeText(Context context, int resId, int HIDE_DELAY) {
        String mes = "";
        try{
            mes = context.getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return makeText(context,mes,HIDE_DELAY);
    }
    public void show() {
        if(isShow){
            //如果已经显示，则再次显示不生效
            return;
        }
        isShow = true;
        //显示动画
        mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        //消失动画
        mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFadeOutAnimation.setDuration(ANIMATION_DURATION);
        mFadeOutAnimation
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        //消失动画消失后记得刷新状态
                        isShow = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //隐藏布局，没有remove主要是为了防止一个页面创建多次布局
                        mContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
        mContainer.setVisibility(View.VISIBLE);

        mFadeInAnimation.setDuration(ANIMATION_DURATION);

        mContainer.startAnimation(mFadeInAnimation);
        mHandler.postDelayed(mHideRunnable, HIDE_DELAY);
    }

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mContainer.startAnimation(mFadeOutAnimation);
        }
    };
    public void cancel(){
        if(isShow) {
            isShow = false;
            mContainer.setVisibility(View.GONE);
            mHandler.removeCallbacks(mHideRunnable);
        }
    }
    //这个方法主要是为了解决用户在重启页面后单例还会持有上一个context，
    //并且上面的mContext.getClass().getName()其实是一样的
    //所以使用上还需在你们的BaseActivity的onDestroy()方法中调用该方法
    public static void reset(){
        result = null;
    }
    public void setText(CharSequence s){
        if(result == null) return;
        TextView mTextView = (TextView) v.findViewById(R.id.mbMessage);
        if(mTextView == null) throw new RuntimeException("This Toast was not created with Toast.makeText()");
        mTextView.setText(s);
    }
    public void setText(int resId) {
        setText(mContext.getText(resId));
    }
}