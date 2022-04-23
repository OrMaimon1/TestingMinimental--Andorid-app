package com.example.minimental.repository;
import static com.squareup.okhttp.internal.Internal.instance;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.minimental.ViewModels.IResultHandler;
import com.example.minimental.fragments.InformationFragment;
import com.example.minimental.informationQuestion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.Repo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.Internal;


public class AppRepository {

    private Application application;
    private static AppRepository instance;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<informationQuestion> infoLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    private static IResultHandler iResultHandler;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository();
        }
        iResultHandler = (IResultHandler) context;
        return instance;
    }

    public MutableLiveData<InformationFragment>getInformationData(){

        loadInformationData();

        MutableLiveData<InformationFragment> data = new MutableLiveData<>();
        return data;
    }

    private void loadInformationData(){

    }

}
//    public AppRepository getFirestoreLiveData() {
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        this.infoLiveData = new MutableLiveData<>();
//
//    }
