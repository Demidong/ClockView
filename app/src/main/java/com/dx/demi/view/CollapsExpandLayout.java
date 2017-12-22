package com.dx.demi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dx.demi.R;

/**
 * Created by demi on 17/4/24.
 */
public class CollapsExpandLayout extends LinearLayout {
    private TextView number_text;
    private TextView title_name;
    private ImageView arrow;
    private RelativeLayout content;
    private RelativeLayout title_layout;
    private int parentWidthMeasureSpec;
    private int parentHeightMeasureSpec;
    private int contentWidthMeasureSpec;
    private int contentHeightMeasureSpec;
    private int duration = 1000;
    private long lastClickTime;

    public CollapsExpandLayout(Context context) {
        this(context, null);
    }

    public CollapsExpandLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsExpandLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.collaps_item, this);
        initView();
        collaps(content);
    }

    private void initView() {
        number_text = (TextView) findViewById(R.id.number);
        title_name = (TextView) findViewById(R.id.title_name);
        arrow = (ImageView) findViewById(R.id.arrow);
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        content = (RelativeLayout) findViewById(R.id.content);
        title_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateArrow(System.currentTimeMillis());
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        contentWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,0, ViewGroup.LayoutParams.MATCH_PARENT);
        contentHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, height - getChildAt(0).getMeasuredHeight());
    }

    private void expand(final View view) {
        view.setVisibility(View.VISIBLE);
        view.measure(contentWidthMeasureSpec, contentHeightMeasureSpec);
        final int height = view.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.getLayoutParams().height = height;
                } else {
                    view.getLayoutParams().height = (int) (height * interpolatedTime);
                }
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private void collaps(final View view) {
        final int measureheight = view.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(GONE);
                } else {
                    view.getLayoutParams().height = measureheight - (int) (measureheight * interpolatedTime);
                }
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private void rotateArrow(long time) {
        if (time - lastClickTime < duration) {
            return;
        }
        int degree;
        if (content.getVisibility() == View.GONE) {
            expand(content);
            degree = 90;
        } else {
            collaps(content);
            degree = 0;
        }
        arrow.animate().setDuration(duration).rotation(degree);
        lastClickTime = time;
    }

    public void setNumber(String num) {
        if (!TextUtils.isEmpty(num)) {
            number_text.setText(num);
        }
    }

    public void setName(String text) {
        if (!TextUtils.isEmpty(text)) {
            title_name.setText(text);
        }
    }

    public void setContent(ImageView imageView) {
        //View view = LayoutInflater.from(getContext()).inflate(resID,null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        content.addView(imageView);
    }
}
