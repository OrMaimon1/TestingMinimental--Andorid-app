package com.example.minimental.repository;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.minimental.EightQuestion;
import com.example.minimental.FifthQuestion;
import com.example.minimental.MissingDetail;
import com.example.minimental.SevnthQuestion;
import com.example.minimental.SixthQuestion;
import com.example.minimental.TenthQuestion;
import com.example.minimental.ThirdQuestion;
import com.example.minimental.informationQuestion;
import com.example.minimental.secoundQuestion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class AppRepository {


    private static AppRepository instance;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference() ;
    private DatabaseReference databasePatients , databaseTest;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private MutableLiveData<informationQuestion> infoLiveData = new MutableLiveData<>();
    private MutableLiveData<MissingDetail> missingDetailLiveData = new MutableLiveData<>();
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
    private MutableLiveData<Boolean> permission = new MutableLiveData<>();
    private MutableLiveData<Integer> version = new MutableLiveData<>();
    private MutableLiveData<String> userId = new MutableLiveData<>();
    private MutableLiveData<String> dateTimeFirst = new MutableLiveData<>();
    private MutableLiveData<String> dateTimeLast = new MutableLiveData<>();
    private MutableLiveData<secoundQuestion> objectLiveDataLoad = new MutableLiveData<>();
    private MutableLiveData<SixthQuestion> LoadSentence = new MutableLiveData<>();
    private MutableLiveData<ThirdQuestion> LoadThirdQuestion = new MutableLiveData<>();
    private MutableLiveData<FifthQuestion> LoadFifthQuestion = new MutableLiveData<>();
    private String id;
    private String test = "test";




    public AppRepository() {
        databasePatients = FirebaseDatabase.getInstance().getReference();
        databaseTest = FirebaseDatabase.getInstance().getReference();
    }


    public void setUserName(MutableLiveData<String> userName) {
        username.setValue(userName.getValue());
    }
    public void setUserId(MutableLiveData<String> userId1) {
        userId.setValue(userId1.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue());
        database.setValue(userId);
        databasePatients = FirebaseDatabase.getInstance().getReference();
        databaseTest = FirebaseDatabase.getInstance().getReference();
        databasePatients = databasePatients.child("Patients").child(userId.getValue());
        databaseTest = databaseTest.child("Test").child(userId.getValue());
    }

    public void setPermission(MutableLiveData<Boolean> permission1) { // need to check
        permission.setValue(permission1.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Patients").child(userId.getValue()).child("patient details").child("has permission");
        database.setValue(permission.getValue());
    }
    public MutableLiveData<Integer> getVersion() {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        data.setValue(version.getValue());
        return data;
    }


    public void setDataTimeFirst(MutableLiveData<String> time) {
        dateTimeFirst.setValue(time.getValue());
    }
    public void setDataTimeLast(MutableLiveData<String> time) {
        dateTimeLast.setValue(time.getValue());
        database = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("userId");
        database.child("id").setValue(id);
        database.child("Start").setValue(dateTimeFirst);
        database.child("End").setValue(dateTimeLast);
    }


    //informationQuestion get and set
    public MutableLiveData<informationQuestion> getInformationData() {

        //loadInformationData();
        MutableLiveData<informationQuestion> data = new MutableLiveData<>();
        return data;
    }

    public void setinfo(MutableLiveData<informationQuestion> info) {
        infoLiveData.setValue(info.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("FirstQuestion");
        databaseTest.setValue(infoLiveData);
    }

    //MissingDetails get and set
    public MutableLiveData<MissingDetail> getMissingDetail() {
        MutableLiveData<MissingDetail> data = new MutableLiveData<>();
        data.setValue(missingDetailLiveData.getValue());
        return data;
    }

    public void load() {
        databasePatients = FirebaseDatabase.getInstance().getReference().child("Patients").child(userId.getValue());
        databasePatients.child("next test").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                secoundQuestion threeObject = new secoundQuestion();
                ThirdQuestion thirdQuestion = new ThirdQuestion();
                FifthQuestion fifthQuestion = new FifthQuestion();
                SixthQuestion sentence = new SixthQuestion();
                if (task.isSuccessful()) {
                    threeObject.setObject1(task.getResult().child("secondQuestion").child("object1").getValue(String.class));
                    threeObject.setObject2(task.getResult().child("secondQuestion").child("object2").getValue(String.class));
                    threeObject.setObject3(task.getResult().child("secondQuestion").child("object3").getValue(String.class));
                    sentence.setSentence(task.getResult().child("sixthQuestion").child("sentence").getValue(String.class));
                    thirdQuestion.setNumber(task.getResult().child("thirdQuestion").child("number").getValue(String.class));
                    thirdQuestion.setObjectforspelling(task.getResult().child("thirdQuestion").child("word").getValue(String.class));
                    fifthQuestion.setFirstpic(task.getResult().child("fifthQuestion").child("pic1").getValue(String.class));
                    fifthQuestion.setSecoundpic(task.getResult().child("fifthQuestion").child("pic2").getValue(String.class));
                    sentence.setSentence(task.getResult().child("sixthQuestion").child("sentence").getValue(String.class));
                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());

                }
                objectLiveDataLoad.setValue(threeObject);
                LoadThirdQuestion.setValue(thirdQuestion);
                LoadFifthQuestion.setValue(fifthQuestion);
                LoadSentence.setValue(sentence);

            }

        });

    }
    public void loadId(){
        database = FirebaseDatabase.getInstance().getReference().child("Patients").child(userId.getValue());
        database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String id1= null;
                if (task.isComplete()){
                    id1 = task.getResult().child("id").getValue(String.class);
                }
                else {
                    Log.e("firebase LoadId", "Error getting data", task.getException());
                }
                id = id1;
            }
        });
        database.child("patient details").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Integer version_int1 = 1;
                if (task.isComplete()){
                  version_int1 = task.getResult().child("test_number").getValue(Integer.class);
              }
              else{
                  Log.e("firebase LoadId", "Error getting data", task.getException());
              }
              version.setValue(version_int1);
              if (version_int1 ==null){
                  version_int1 =1;
              }
              test = test + version_int1;
            }
        });
    }

    public void loadMissingDetail(FireBaseCallBack callBack) {
        databasePatients = FirebaseDatabase.getInstance().getReference().child("Patients").child(userId.getValue());
        databasePatients.child("patient details").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                MissingDetail missingDetail = new MissingDetail();
                if (task.isSuccessful()) {
                    missingDetail.setCountry(task.getResult().child("country").getValue(String.class));
                    missingDetail.setCity(task.getResult().child("city").getValue(String.class));
                    missingDetail.setAddress(task.getResult().child("street").getValue(String.class));
                    missingDetail.setFloor(task.getResult().child("floor").getValue(String.class));
                    missingDetail.setArea(task.getResult().child("area").getValue(String.class));
                    try {
                        missingDetail.setHas_permission(task.getResult().child("has permission").getValue(Boolean.class));
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    try {
                        missingDetail.setHas_permission(task.getResult().child("is_in_hospital").getValue(Boolean.class));
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                    Log.d("firebase", String.valueOf(task.getResult().child("country").getValue()));
                    Log.d("firebase", String.valueOf(task.getResult().child("has permission").getValue()));
                }
                else {


                    Log.e("firebase", "Error getting data", task.getException());

                }
                callBack.onCallback(missingDetail);
                missingDetailLiveData.setValue(missingDetail);
            }


        });


    }

    public void setMissingDetail(MutableLiveData<MissingDetail> info) {
        missingDetailLiveData.setValue(info.getValue());

        databasePatients = FirebaseDatabase.getInstance().getReference().child("Patients").child(userId.getValue());
        databasePatients.child("patient details").child("country").setValue(missingDetailLiveData.getValue().getCountry());
        databasePatients.child("patient details").child("city").setValue(missingDetailLiveData.getValue().getCity());
        databasePatients.child("patient details").child("street").setValue(missingDetailLiveData.getValue().getAddress());
        databasePatients.child("patient details").child("floor").setValue(missingDetailLiveData.getValue().getFloor());
        databasePatients.child("patient details").child("area").setValue(missingDetailLiveData.getValue().getArea());
        databasePatients.child("patient details").child("has permission").setValue(missingDetailLiveData.getValue().isHas_permission());
        //databasePatients.child("patient details").child("has permission").setValue(missingDetailLiveData.getValue().isHas_permission());
    }


    //object get and set for 2
    public MutableLiveData<secoundQuestion> get3ObjectData() {

        MutableLiveData<secoundQuestion> data = new MutableLiveData<>();
        return objectLiveDataLoad ;
    }

    public MutableLiveData<secoundQuestion> getObjectLoad(){
        MutableLiveData<secoundQuestion> data = new MutableLiveData<>();
        data.setValue(objectLiveDataLoad.getValue());
        return data;
    }


    public void setObject(MutableLiveData<secoundQuestion> info) {
        objectLiveData.setValue(info.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("SecondQuestion");
        databaseTest.setValue(objectLiveData);
    }

    private void loadObjectData() {

        databaseTest.child("Test").child(userId.getValue()).child(test).child("SecondQuestion");
        databaseTest.setValue(objectLiveData);
    }

    public MutableLiveData<ThirdQuestion> getSpellAnswer() {

        //loadInformationData();
        MutableLiveData<ThirdQuestion> data = new MutableLiveData<>();
        data.setValue(LoadThirdQuestion.getValue());
        return data;
    }


    public void setSpellAnswer(MutableLiveData<ArrayList<String>> Spell) {
        spellWordLiveData.setValue(Spell.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("SpellQuestion");
        databaseTest.setValue(spellWordLiveData);
    }


    public MutableLiveData<ThirdQuestion> getMathAnswer() {

        MutableLiveData<ThirdQuestion> data = new MutableLiveData<>();
        data.setValue(LoadThirdQuestion.getValue());
        return data;
    }


    public void setMathAnswer(MutableLiveData<ArrayList<String>> Math) {
        mathWordLiveData.setValue(Math.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("MathQuestion");
        databaseTest.setValue(mathWordLiveData);
    }


    public MutableLiveData<secoundQuestion> getObjectForthLiveData() {

        //loadInformationData();
        MutableLiveData<secoundQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setObjectForthLiveData(MutableLiveData<secoundQuestion> info) {
        objectForthLiveData.setValue(info.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("ForthQuestion");
        databaseTest.setValue(objectForthLiveData);
    }

    public MutableLiveData<FifthQuestion> getPicDescription() {

        MutableLiveData<FifthQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setPicDescription(MutableLiveData<FifthQuestion> info) {
        FifthLiveData.setValue(info.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("FifthQuestion");
        databaseTest.setValue(FifthLiveData);
    }

    public MutableLiveData<SixthQuestion> getSentence() {

        MutableLiveData<SixthQuestion> data = new MutableLiveData<>();
        data.setValue(LoadSentence.getValue());
        return data;
    }


    public void setSentence(MutableLiveData<SixthQuestion> info) {
        sentenceLiveData.setValue(info.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("SixthQuestion");
        databaseTest.setValue(sentenceLiveData);
    }

    public MutableLiveData<SevnthQuestion> getPicForSeventhQuestion() {

        MutableLiveData<SevnthQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setCurrectPicOrder(MutableLiveData<Boolean> currectPicOrder) {
        currectOrderSeventh.setValue(currectPicOrder.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("SeventhQuestion");
        databaseTest.setValue(currectOrderSeventh);
    }

    public MutableLiveData<EightQuestion> getPicForEighthQuestion() {

        //loadInformationData();
        MutableLiveData<EightQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setCurrectPicOrderEighth(MutableLiveData<Boolean> currectPicOrder) {
        currectOrderEight.setValue(currectPicOrder.getValue());
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("EigthQuestion");
        databaseTest.setValue(currectOrderEight);
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
        databaseTest = FirebaseDatabase.getInstance().getReference().child("Test").child(userId.getValue()).child(test).child("NineQuestion");
        databaseTest.setValue(ninthQuestionMutableLiveData);
    }

    public MutableLiveData<TenthQuestion> getpicForTenth() {

        //loadInformationData();
        MutableLiveData<TenthQuestion> data = new MutableLiveData<>();
        return data;
    }


    public void setpicForTenth(MutableLiveData<TenthQuestion> pic) {
        tenthQuestionMutableLiveData.setValue(pic.getValue());
        StorageReference TenthRef = storage.getReference().child("TenthQuestion Pic").child(userId.getValue()).child(test).child("drawImage.jpg");
        // Get the data from an ImageView as bytes
        Bitmap bitmap = pic.getValue().getPicToCopy();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = TenthRef.putBytes(data);

    }

}


