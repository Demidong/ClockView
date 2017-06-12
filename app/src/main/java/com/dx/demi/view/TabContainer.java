package com.dx.demi.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dx.demi.R;

/**
 * Created by demi on 17/4/24.
 */
public class TabContainer extends LinearLayout {
    private int currentPosition;
    private OnItemClickListener mOnItemClickListener;

    public TabContainer(Context context) {
        this(context,null);
    }

    public TabContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.HORIZONTAL);

        if (getTag() == null) {
            throw new RuntimeException("Please set string array!!");
        }

        String[] strArr = null;
        try {
            int strArrId = Integer.valueOf(getTag().toString());
            strArr = getResources().getStringArray(strArrId);
        } catch (Throwable e) {

        }

        if (strArr == null) {
            strArr = getTag().toString().split(",");
        }

        for (int i = 0; i < strArr.length; i++) {
            int layoutId = R.layout.tab_container_item;
            final TextView item = (TextView) inflate(getContext(),layoutId,null);
            item.setClickable(true);
            final int finalI = i;
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(finalI);
                }
            });

            item.setText(strArr[i]);
            if(i==0){
                item.setTextColor(getResources().getColor(R.color.white));
                item.setBackgroundColor(getResources().getColor(R.color.num_blue));
            }
            addView(item);
        }

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int width = getMeasuredWidth();
                int avgWidth = width / getChildCount();

                for (int i = 0; i < getChildCount(); i++) {
                    View item = getChildAt(i);
                    ViewGroup.LayoutParams params = item.getLayoutParams();
                    params.width = avgWidth;
                    item.setLayoutParams(params);
                }
                return true;
            }
        });
    }

    private void onItemClick(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            TextView item = (TextView) getChildAt(i);
            if (i == position) {
                item.setTextColor(getResources().getColor(R.color.white));
                item.setBackgroundColor(getResources().getColor(R.color.num_blue));
            }
            else {
                item.setTextColor(getResources().getColor(R.color.gray));
                item.setBackgroundColor(getResources().getColor(R.color.black));
            }
        }

        this.currentPosition = position;

        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(TabContainer.this,position);
        }
    }

    public int getPosition() {
        return this.currentPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(TabContainer tabBar,int position);
    }
}
