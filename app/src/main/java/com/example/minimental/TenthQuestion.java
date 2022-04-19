package com.example.minimental;

import android.graphics.Bitmap;

public class TenthQuestion {

    private Bitmap picToCopy;

    public TenthQuestion() {}

    public TenthQuestion(Bitmap picToCopy) {
        this.picToCopy = picToCopy;
    }

    public Bitmap getPicToCopy() {
        return picToCopy;
    }

    public void setPicToCopy(Bitmap picToCopy) {
        this.picToCopy = picToCopy;
    }
}
