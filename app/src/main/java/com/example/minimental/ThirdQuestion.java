package com.example.minimental;

public class ThirdQuestion {

    private int number;
    private int divder;
    private String objectforspelling;

    public ThirdQuestion() {}

    public ThirdQuestion(int number, int divder, String objectforspelling) {
        this.number = number;
        this.divder = divder;
        this.objectforspelling = objectforspelling;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDivder() {
        return divder;
    }

    public void setDivder(int divder) {
        this.divder = divder;
    }

    public String getObjectforspelling() {
        return objectforspelling;
    }

    public void setObjectforspelling(String objectforspelling) {
        this.objectforspelling = objectforspelling;
    }
}
