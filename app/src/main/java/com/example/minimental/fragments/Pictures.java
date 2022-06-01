package com.example.minimental.fragments;

import android.net.Uri;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;

import java.io.Serializable;

public class Pictures implements Serializable {

     String name;
     byte[] photoPath;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(byte[] photoPath) {
        this.photoPath = photoPath;
    }
}
