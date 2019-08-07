package com.example.testbaserecyclerview.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by AndroidXJ on 2019/8/7.
 */

public class MutipleData implements MultiItemEntity {

    private String content;
    private Drawable mBitmap;
    private int itemType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Drawable getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Drawable bitmap) {
        mBitmap = bitmap;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
