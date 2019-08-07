package com.example.testbaserecyclerview.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.testbaserecyclerview.R;
import com.example.testbaserecyclerview.bean.WorkInfo;

import java.util.List;

/**
 * Created by AndroidXJ on 2019/8/7.
 */

public class ReadIndexAdapter extends BaseQuickAdapter<WorkInfo, BaseViewHolder> {

    public ReadIndexAdapter(@Nullable List<WorkInfo> data) {
        super(R.layout.layout_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkInfo item) {
        helper.setText(R.id.txt_title, item.getTitle())
                .setText(R.id.txt_description, item.getDescription());

    }
}
