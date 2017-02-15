package com.dx.demi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dx.demi.R;
import com.dx.demi.View.JazzyViewPager;
import com.dx.demi.utils.OutlineContainer;

import java.util.ArrayList;

/**
 * Created by demi on 2017/2/13.
 */

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> pageList;
    private Context context;
    int a[] = {R.mipmap.comeon, R.mipmap.nice, R.mipmap.single, R.mipmap.ok, R.mipmap.sky};
    private JazzyViewPager jazzyViewPager ;
    public MyPagerAdapter(ArrayList<View> pageList, Context context,JazzyViewPager jazzyViewPager) {
        this.pageList = pageList;
        this.context = context;
        this.jazzyViewPager = jazzyViewPager;
    }

    @Override
    public int getCount() {
        return pageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (view instanceof OutlineContainer)
        {
            return ((OutlineContainer) view).getChildAt(0) == object;
        } else
        {
            return view == object;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) pageList.get(position);
        BitmapFactory.Options opts =new BitmapFactory.Options();
        opts.inSampleSize =2;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), a[position],opts);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(bitmap);
        container.addView(imageView,android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.MATCH_PARENT);
        jazzyViewPager.setObjectForPosition(imageView, position);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(jazzyViewPager.findViewFromObject(position));
    }
}
