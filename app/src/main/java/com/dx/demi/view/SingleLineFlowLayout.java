package com.dx.demi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dx.demi.R;
import com.dx.demi.activity.FlowLayoutActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by demi on 17/4/24.
 */
public class SingleLineFlowLayout extends LinearLayout {
    private int horizontalSpacing = 10;
    private boolean isFull;

    public SingleLineFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void addList(List<String> list) {
        isFull = false;
        removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            if (isFull) return;
            addView(initItemView(list.get(i)));
        }
    }

    private TextView initItemView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(10,10,10,10);
        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_red_boder_shape));
        return textView;
    }

    public void addStrings(String... text) {
        addList(Arrays.asList(text));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int widthUsed = paddingLeft + paddingRight;  //已经占用的宽度
        int heightUsed = paddingTop + paddingBottom; //已经占用的高度

        int childMaxHeightOfThisLine = 0;       //孩子中最高的那位，其实都一样高
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int childUsedWidth = 0;   //某个孩子的宽度
                int childUsedHeight = 0;  //某个孩子的高度
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                childUsedWidth += child.getMeasuredWidth();
                childUsedHeight += child.getMeasuredHeight();

                LayoutParams childLayoutParams = (LayoutParams) child.getLayoutParams();

                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childLayoutParams;

                childUsedWidth += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + horizontalSpacing;
                childUsedHeight += marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;

                if (widthUsed + childUsedWidth < widthSpecSize) {
                    widthUsed += childUsedWidth;
                    if (childUsedHeight > childMaxHeightOfThisLine) {
                        childMaxHeightOfThisLine = childUsedHeight;
                    }
                }
            }

        }

        heightUsed += childMaxHeightOfThisLine;
        setMeasuredDimension(widthSpecSize, heightUsed);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int childStartLayoutX = paddingLeft;
        int childStartLayoutY = paddingTop;

        int widthUsed = paddingLeft + paddingRight;

        int childMaxHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int childNeededWidth, childNeedHeight;
                int left = 0, top = 0, right = 0, bottom = 0;

                int childMeasuredWidth = child.getMeasuredWidth();
                int childMeasuredHeight = child.getMeasuredHeight();

                LayoutParams childLayoutParams = (LayoutParams) child.getLayoutParams();
                MarginLayoutParams marginLayoutParams = childLayoutParams;
                int childLeftMargin = marginLayoutParams.leftMargin;
                int childTopMargin = marginLayoutParams.topMargin;
                int childRightMargin = marginLayoutParams.rightMargin;
                int childBottomMargin = marginLayoutParams.bottomMargin;
                childNeededWidth = childLeftMargin + childRightMargin + childMeasuredWidth + horizontalSpacing;
                childNeedHeight = childTopMargin + childBottomMargin + childMeasuredHeight;

                if (widthUsed + childNeededWidth <= r - l) {
                    if (childNeedHeight > childMaxHeight) {
                        childMaxHeight = childNeedHeight;
                    }
                    left = childStartLayoutX + childLeftMargin;
                    top = childStartLayoutY + childTopMargin;
                    right = left + childMeasuredWidth;
                    bottom = top + childMeasuredHeight;
                    widthUsed += childNeededWidth;
                    childStartLayoutX += childNeededWidth;
                } else {
                    isFull = true;
                }
                child.layout(left, top, right, bottom);
            }
        }
    }
}
