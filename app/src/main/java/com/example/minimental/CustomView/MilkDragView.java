package com.example.minimental.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.BitmapKt;

import com.example.minimental.R;

public class MilkDragView extends View {

    private ImageView tableImage;
    //private Drawable tablePicture;
    private ImageView milkImage;
    private Bitmap tableBitMap;
    @SuppressLint("ResourceType")
    public MilkDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tableBitMap = BitmapFactory.decodeResource(getResources() , R.drawable.ic_dresser);
        //tableImage.setImageResource(R.drawable.ic_dresser);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
