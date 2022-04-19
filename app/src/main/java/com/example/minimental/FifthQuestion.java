package com.example.minimental;

import android.graphics.Bitmap;

public class FifthQuestion {

    private Bitmap firstpic;
    private Bitmap secoundpic;

    public FifthQuestion() {}

    public FifthQuestion(Bitmap firstpic, Bitmap secoundpic) {
        this.firstpic = firstpic;
        this.secoundpic = secoundpic;
    }

    public Bitmap getFirstpic() {
        return firstpic;
    }

    public void setFirstpic(Bitmap firstpic) {
        this.firstpic = firstpic;
    }

    public Bitmap getSecoundpic() {
        return secoundpic;
    }

    public void setSecoundpic(Bitmap secoundpic) {
        this.secoundpic = secoundpic;
    }
}
