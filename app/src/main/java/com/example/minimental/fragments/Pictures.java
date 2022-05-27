package com.example.minimental.fragments;

import android.net.Uri;

import java.io.Serializable;

public class Pictures implements Serializable {
    private String photoPath;

    public Pictures(){ }

    public Pictures(String photoPath){
        this.photoPath = photoPath;
    }


    public String getPhoto(){ return photoPath; }

    public void setPhoto(String photo){this.photoPath = photo;}
}
