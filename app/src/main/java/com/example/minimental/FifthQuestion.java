package com.example.minimental;

import android.graphics.Bitmap;

public class FifthQuestion {

    private String firstpic;
    private String secoundpic;

    public FifthQuestion() {}

    public FifthQuestion(String firstpic, String secoundpic) {
        this.firstpic = firstpic;
        this.secoundpic = secoundpic;
    }

    public String getFirstpic() {
        return firstpic;
    }

    public void setFirstpic(String firstpic) {
        this.firstpic = firstpic;
    }

    public String getSecoundpic() {
        return secoundpic;
    }

    public void setSecoundpic(String secoundpic) {
        this.secoundpic = secoundpic;
    }
}
