package com.example.minimental;

import android.graphics.Bitmap;

public class SevnthQuestion {

    private Bitmap object1;
    private Bitmap object2;
    private Bitmap object3;
    private String action;

    public SevnthQuestion() {}

    public SevnthQuestion(Bitmap object1, Bitmap object2, Bitmap object3, String action) {
        this.object1 = object1;
        this.object2 = object2;
        this.object3 = object3;
        this.action = action;
    }

    public Bitmap getObject1() {
        return object1;
    }

    public void setObject1(Bitmap object1) {
        this.object1 = object1;
    }

    public Bitmap getObject2() {
        return object2;
    }

    public void setObject2(Bitmap object2) {
        this.object2 = object2;
    }

    public Bitmap getObject3() {
        return object3;
    }

    public void setObject3(Bitmap object3) {
        this.object3 = object3;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
