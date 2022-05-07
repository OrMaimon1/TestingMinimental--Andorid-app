package com.example.minimental.repository;
import static com.squareup.okhttp.internal.Internal.instance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.minimental.ViewModels.IResultHandler;
import com.example.minimental.fragments.InformationFragment;
import com.example.minimental.informationQuestion;
import com.example.minimental.secoundQuestion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.Repo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.Internal;

import java.util.ArrayList;


public class AppRepository {

    private static AppRepository instance;
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<informationQuestion> infoLiveData = new MutableLiveData<>();
    private MutableLiveData<secoundQuestion> objectLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> mathWordLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> spellWordLiveData = new MutableLiveData<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository();
        }
        return instance;
    }

    //informationQuestion get and set
    public MutableLiveData<informationQuestion>getInformationData(){

        //loadInformationData();
        MutableLiveData<informationQuestion> data = new MutableLiveData<>();
        return data;
    }
    public void setinfo(MutableLiveData<informationQuestion> info){
        infoLiveData.setValue(info.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test");
        database.push().setValue(infoLiveData);
    }

    private void loadInformationData(){

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("test");
        database = FirebaseDatabase.getInstance().getReference().child("Test");
        database.push().setValue(infoLiveData);
    }

    //object get and set for 2 ,4 ,6 question
    public MutableLiveData<secoundQuestion>get3ObjectData(){

        //loadInformationData();
        MutableLiveData<secoundQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setObject(MutableLiveData<secoundQuestion> info){
        objectLiveData.setValue(info.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test");
        database.push().setValue(objectLiveData);
    }

    private void loadObjectData(){

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("test");
        database = FirebaseDatabase.getInstance().getReference().child("Test");
        database.push().setValue(objectLiveData);
    }

    public MutableLiveData<ArrayList<String>>getSpellAnswer(){

        //loadInformationData();
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        return data;
    }


    public void setSpellAnswer(MutableLiveData<ArrayList<String>> Math){
        mathWordLiveData.setValue(Math.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test");
        database.push().setValue(mathWordLiveData);
    }

    private void loadMathData(){

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("test");
        database = FirebaseDatabase.getInstance().getReference().child("Test");
        database.push().setValue(mathWordLiveData);
    }

    public MutableLiveData<ArrayList<String>>getMathAnswer(){

        //loadInformationData();
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        return data;
    }


    public void setMathAnswer(MutableLiveData<ArrayList<String>> Word){
        spellWordLiveData.setValue(Word.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test");
        database.push().setValue(spellWordLiveData);
    }
}

