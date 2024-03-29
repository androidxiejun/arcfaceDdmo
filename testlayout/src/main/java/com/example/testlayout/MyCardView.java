package com.example.testlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by AndroidXJ on 2019/7/26.
 */

public class MyCardView extends View {

    private Bitmap[] mCards = new Bitmap[3];

    private int[] mImgId = new int[]{R.drawable.door, R.drawable.door, R.drawable.door};

    public MyCardView(Context context) {
        super(context);
        Bitmap bm = null;
        for (int i = 0; i < mCards.length; i++) {
            bm = BitmapFactory.decodeResource(getResources(), mImgId[i]);
            mCards[i] = Bitmap.createScaledBitmap(bm, 400, 600, false);
        }

        setBackgroundColor(0xff00ff00);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(20, 120);
//        for (Bitmap bitmap : mCards) {
//            canvas.translate(120, 0);
//            canvas.drawBitmap(bitmap, 0, 0, null);
//        }

        for (int i = 0; i < mCards.length; i++) {
            canvas.translate(120, 0);
            canvas.save();
            if (i < mCards.length - 1) {
                canvas.clipRect(0, 0, 120, mCards[i].getHeight());
            }
            canvas.drawBitmap(mCards[i], 0, 0, null);
            canvas.restore();
        }
        canvas.restore();

    }
}
