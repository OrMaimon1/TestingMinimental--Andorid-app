package com.example.minimental.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.minimental.EightQuestion;
import com.example.minimental.FifthQuestion;
import com.example.minimental.SevnthQuestion;
import com.example.minimental.SixthQuestion;
import com.example.minimental.TenthQuestion;
import com.example.minimental.ThirdQuestion;
import com.example.minimental.fragments.SecondQuestion;
import com.example.minimental.informationQuestion;
import com.example.minimental.repository.AppRepository;
import com.example.minimental.secoundQuestion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    //region New View-Model For Second Fragment
    private AppRepository repository = new AppRepository();


    //informationQuestion
    private MutableLiveData<informationQuestion> infoLiveData = new MutableLiveData<>();
    public MutableLiveData<informationQuestion> getInfoLiveData(){

        infoLiveData = repository.getInformationData();
        return infoLiveData;
    }
    public void setInfoLiveData(informationQuestion info){

        infoLiveData.setValue(info);
        repository.setinfo(infoLiveData);
    }

    private MutableLiveData<secoundQuestion> secondQuestionLiveData = new MutableLiveData<>();

    public MutableLiveData<secoundQuestion> getObjectdata(){

        secondQuestionLiveData = repository.get3ObjectData();
        return secondQuestionLiveData;
    }
    public void setObjectData(secoundQuestion secoundQuestion){

        secondQuestionLiveData.setValue(secoundQuestion);
        repository.setObject(secondQuestionLiveData);
    }


    //region Third Question Math Version Data Member and spelling Version Data Member
    private MutableLiveData<ArrayList<String>> mathAnswerGiven = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> spelledWord = new MutableLiveData<>();

    //region Third Question Math Version Methods
    public void setMathAnswerGiven(ArrayList<String> mathAnswer)
    {
        mathAnswerGiven.setValue(mathAnswer);
        repository.setMathAnswer(mathAnswerGiven);
    }
    public MutableLiveData<ArrayList<String>> getMathAnswerGiven()
    {
        mathAnswerGiven = repository.getMathAnswer();
        return mathAnswerGiven;
    }

    public void setSpelledWord(ArrayList<String> Word)
    {
        spelledWord.setValue(Word);
        repository.setSpellAnswer(spelledWord);
    }

    public MutableLiveData<ArrayList<String>> getSpelledWord()
    {
        spelledWord = repository.getSpellAnswer();
        return spelledWord;
    }


    //region Fourth Question Data Members
    private MutableLiveData<secoundQuestion> fourthQuestionLiveData = new MutableLiveData<>();

    public MutableLiveData<secoundQuestion> getFourthQuestionLiveData(){

        fourthQuestionLiveData = repository.get3ObjectData();
        return fourthQuestionLiveData;
    }
    public void setFourthQuestionLiveData(secoundQuestion secoundQuestion){

        fourthQuestionLiveData.setValue(secoundQuestion);
        repository.setObject(fourthQuestionLiveData);
    }





    //region Fifth Question Data Members
    private MutableLiveData<FifthQuestion> PicItemDescription = new MutableLiveData<>();


    public MutableLiveData<FifthQuestion> getFifthQuestionLiveData(){

        PicItemDescription = repository.getPicDescription();
        return PicItemDescription;
    }
    public void setFifthQuestionLiveData(FifthQuestion itemDescription){

        PicItemDescription.setValue(itemDescription);
        repository.setPicDescription(PicItemDescription);
    }

    //region Sixth Question Data member
    private MutableLiveData<String> repeatedSentence = new MutableLiveData<>();
    //endregion

    //region Eight Question Data Member
    private MutableLiveData<Float> blackBallLocationX = new MutableLiveData<>();
    private MutableLiveData<Float> blackBallLocationY = new MutableLiveData<>();
    private MutableLiveData<Float> yellowBallLocationX = new MutableLiveData<>();
    private MutableLiveData<Float> yellowBallLocationY = new MutableLiveData<>();
    //endregion



    //region Sixth Question Methods
    public void setRepeatedSentence(String value)
    {
        repeatedSentence.setValue(value);
    }

    public MutableLiveData<String> getRepeatedSentence() {
        return repeatedSentence;
    }
    //endregion

    //region Eight Question Methods
    public void setBlackBallLocationX(Float value)
    {
        blackBallLocationX.setValue(value);
    }
    public void setBlackBallLocationY(Float value)
    {
        blackBallLocationY.setValue(value);
    }
    public void setYellowBallLocationX(Float value)
    {
        yellowBallLocationX.setValue(value);
    }
    public void setYellowBallLocationY(Float value)
    {
        yellowBallLocationX.setValue(value);
    }

    public MutableLiveData<Float> getBlackBallLocationX() {
        return blackBallLocationX;
    }

    public MutableLiveData<Float> getBlackBallLocationY() {
        return blackBallLocationY;
    }

    public MutableLiveData<Float> getYellowBallLocationX() {
        return yellowBallLocationX;
    }

    public MutableLiveData<Float> getYellowBallLocationY() {
        return yellowBallLocationY;
    }
    //endregion


    //firebase database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("users");

    //need to check with user.getIdToken()
    private void fetchData(){
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot datasnapshot : snapshot.getChildren()){
                        secoundQuestion secoundquestion = datasnapshot.getValue(secoundQuestion.class);
                        ThirdQuestion thirdquestion = datasnapshot.getValue(ThirdQuestion.class);
                        FifthQuestion fifthQuestion = datasnapshot.getValue(FifthQuestion.class);
                        SixthQuestion sixthQuestion = datasnapshot.getValue(SixthQuestion.class);
                        SevnthQuestion sevnthQuestion = datasnapshot.getValue(SevnthQuestion.class);
                        EightQuestion eightQuestion = datasnapshot.getValue(EightQuestion.class);
                        TenthQuestion tenthQuestion = datasnapshot.getValue(TenthQuestion.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
