package com.example.minimental.repository;


import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.example.minimental.EightQuestion;
import com.example.minimental.FifthQuestion;
import com.example.minimental.SevnthQuestion;
import com.example.minimental.SixthQuestion;
import com.example.minimental.TenthQuestion;
import com.example.minimental.fragments.NinthQuestion;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.okhttp.internal.Internal;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class AppRepository {


    private static AppRepository instance;
    private DatabaseReference database;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private MutableLiveData<informationQuestion> infoLiveData = new MutableLiveData<>();
    private MutableLiveData<secoundQuestion> objectLiveData = new MutableLiveData<>();
    private MutableLiveData<secoundQuestion> objectForthLiveData = new MutableLiveData<>();
    private MutableLiveData<FifthQuestion> FifthLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> mathWordLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> spellWordLiveData = new MutableLiveData<>();
    private MutableLiveData<SixthQuestion> sentenceLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> currectOrderSeventh = new MutableLiveData<>();
    private MutableLiveData<Boolean> currectOrderEight = new MutableLiveData<>();
    private MutableLiveData<SevnthQuestion> PicForSeventhQuestion = new MutableLiveData<>();
    private MutableLiveData<EightQuestion> eightQuestionMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SixthQuestion> ninthQuestionMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<TenthQuestion> tenthQuestionMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> userId = new MutableLiveData<>();

    //private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void setUserName(MutableLiveData<String> userName) {
        username.setValue(userName.getValue());
    }

    public void setUserId(MutableLiveData<String> userId1) {
        userId.setValue(userId1.getValue());
    }


    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository();
        }
        return instance;
    }

    //informationQuestion get and set
    public MutableLiveData<informationQuestion> getInformationData() {

        //loadInformationData();
        MutableLiveData<informationQuestion> data = new MutableLiveData<>();
        return data;
    }

    public void setinfo(MutableLiveData<informationQuestion> info) {
        infoLiveData.setValue(info.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("FirstQuestion");
        database.setValue(infoLiveData);
    }

    private void loadInformationData() {

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("test");
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("FirstQuestion");
        database.setValue(infoLiveData);
    }

    //object get and set for 2 ,4 ,6 question
    public MutableLiveData<secoundQuestion> get3ObjectData() {

        //loadInformationData();
        MutableLiveData<secoundQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setObject(MutableLiveData<secoundQuestion> info) {
        objectLiveData.setValue(info.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("SecondQuestion");
        database.setValue(objectLiveData);
    }

    private void loadObjectData() {

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("test");
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("SecondQuestion");
        database.setValue(objectLiveData);
    }

    public MutableLiveData<ArrayList<String>> getSpellAnswer() {

        //loadInformationData();
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        return data;
    }


    public void setSpellAnswer(MutableLiveData<ArrayList<String>> Spell) {
        spellWordLiveData.setValue(Spell.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("SpellQuestion");
        database.setValue(spellWordLiveData);
    }

    private void loadMathData() {

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("test");
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("MathQuestion");
        database.setValue(mathWordLiveData);
    }

    public MutableLiveData<ArrayList<String>> getMathAnswer() {

        //loadInformationData();
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        return data;
    }


    public void setMathAnswer(MutableLiveData<ArrayList<String>> Math) {
        mathWordLiveData.setValue(Math.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("MathQuestion");
        database.setValue(mathWordLiveData);
    }


    public MutableLiveData<secoundQuestion> getObjectForthLiveData() {

        //loadInformationData();
        MutableLiveData<secoundQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setObjectForthLiveData(MutableLiveData<secoundQuestion> info) {
        objectForthLiveData.setValue(info.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("ForthQuestion");
        database.setValue(objectForthLiveData);
    }

    public MutableLiveData<FifthQuestion> getPicDescription() {

        //loadInformationData();
        MutableLiveData<FifthQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setPicDescription(MutableLiveData<FifthQuestion> info) {
        FifthLiveData.setValue(info.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("FifthQuestion");
        database.setValue(FifthLiveData);
    }

    public MutableLiveData<SixthQuestion> getSentence() {

        //loadInformationData();
        MutableLiveData<SixthQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setSentence(MutableLiveData<SixthQuestion> info) {
        sentenceLiveData.setValue(info.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("SixthQuestion");
        database.setValue(sentenceLiveData);
    }

    public MutableLiveData<SevnthQuestion> getPicForSeventhQuestion() {

        //loadInformationData();
        MutableLiveData<SevnthQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setCurrectPicOrder(MutableLiveData<Boolean> currectPicOrder) {
        currectOrderSeventh.setValue(currectPicOrder.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("SeventhQuestion");
        database.setValue(currectOrderSeventh);
    }

    public MutableLiveData<EightQuestion> getPicForEighthQuestion() {

        //loadInformationData();
        MutableLiveData<EightQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setCurrectPicOrderEighth(MutableLiveData<Boolean> currectPicOrder) {
        currectOrderEight.setValue(currectPicOrder.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("EigthQuestion");
        database.setValue(currectOrderEight);
    }

    public MutableLiveData<SixthQuestion> getSentenceForNinth() {

        //loadInformationData();
        SixthQuestion buffer = new SixthQuestion();
        MutableLiveData<SixthQuestion> data = new MutableLiveData<>();
        buffer.setSentence(FirebaseDatabase.getInstance().getReference().child("Patients").child(userId.getValue()).child("next test").child("sixthQuestion").getKey());
        data.setValue(buffer);

        return data;
    }

    public void setSentenceForNinth(MutableLiveData<SixthQuestion> sentence) {
        ninthQuestionMutableLiveData.setValue(sentence.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("NineQuestion");
        database.setValue(ninthQuestionMutableLiveData);
    }

    public MutableLiveData<TenthQuestion> getpicForTenth() {

        //loadInformationData();
        MutableLiveData<TenthQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setpicForTenth(MutableLiveData<TenthQuestion> pic) {
        tenthQuestionMutableLiveData.setValue(pic.getValue());
        StorageReference TenthRef = storage.getReference().child("TenthQuestion Pic").child(userId.getValue()).child("test").child("drawImage.jpg");
        // Get the data from an ImageView as bytes
        Bitmap bitmap = pic.getValue().getPicToCopy();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = TenthRef.putBytes(data);
        /*uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });*/
        //database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child("TenthQuestion");
        //database.setValue(tenthQuestionMutableLiveData);
    }

}


