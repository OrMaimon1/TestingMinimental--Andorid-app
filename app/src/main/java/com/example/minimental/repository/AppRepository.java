package com.example.minimental.repository;
import android.app.Application;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.minimental.informationQuestion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;


public class AppRepository {

    private Application application;

    private FirebaseFirestore firebaseFirestore ;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<informationQuestion> infoLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

//    public AppRepository getFirestoreLiveData() {
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        this.infoLiveData = new MutableLiveData<>();
//
//    }



    }