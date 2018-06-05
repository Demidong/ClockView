package com.xd.demi.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xd.demi.R;
import com.xd.demi.bean.Samples;

import java.util.List;

/**
 * Created by demi on 2017/4/25.
 */

public class SamplesAdapter extends BaseQuickAdapter<Samples, BaseViewHolder> {
    private Context mContext;
    public SamplesAdapter(List<Samples> data, Context context) {
        super(R.layout.rev_item, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Samples info) {
        helper.setText(R.id.text_content,info.getDescription()).addOnClickListener(R.id.text_content);
    }

}
