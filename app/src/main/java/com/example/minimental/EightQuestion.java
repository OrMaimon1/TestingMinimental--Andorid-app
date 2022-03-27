package com.example.minimental;

import android.graphics.Bitmap;

public class EightQuestion {

    private Bitmap image1;
    private Bitmap image2;
    private Bitmap shape1;
    private Bitmap shape2;

    public EightQuestion(Bitmap image1, Bitmap image2, Bitmap shape1, Bitmap shape2) {
        this.image1 = image1;
        this.image2 = image2;
        this.shape1 = shape1;
        this.shape2 = shape2;
    }

    public Bitmap getImage1() {
        return image1;
    }

    public void setImage1(Bitmap image1) {
        this.image1 = image1;
    }

    public Bitmap getImage2() {
        return image2;
    }

    public void setImage2(Bitmap image2) {
        this.image2 = image2;
    }

    public Bitmap getShape1() {
        return shape1;
    }

    public void setShape1(Bitmap shape1) {
        this.shape1 = shape1;
    }

    public Bitmap getShape2() {
        return shape2;
    }

    public void setShape2(Bitmap shape2) {
        this.shape2 = shape2;
    }
}
