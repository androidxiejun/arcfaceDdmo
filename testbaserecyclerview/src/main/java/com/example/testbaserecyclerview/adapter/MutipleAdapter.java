package com.example.testbaserecyclerview.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.testbaserecyclerview.R;
import com.example.testbaserecyclerview.bean.MutipleData;

import java.util.List;

/**
 * Created by AndroidXJ on 2019/8/7.
 */

public class MutipleAdapter extends BaseMultiItemQuickAdapter<MutipleData, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MutipleAdapter(List<MutipleData> data) {
        super(data);
        addItemType(0, R.layout.layout_img);
        addItemType(1, R.layout.layout_txt);
    }

    @Override
    protected void convert(BaseViewHolder helper, MutipleData item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setImageDrawable(R.id.img_multi, item.getBitmap());
                break;
            case 1:
                helper.setText(R.id.txt_multi, item.getContent());
                break;
        }
    }
}
