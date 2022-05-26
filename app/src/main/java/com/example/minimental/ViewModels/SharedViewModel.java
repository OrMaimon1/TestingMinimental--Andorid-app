package com.example.minimental.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.minimental.EightQuestion;
import com.example.minimental.FifthQuestion;
import com.example.minimental.MissingDetail;
import com.example.minimental.SevnthQuestion;
import com.example.minimental.SixthQuestion;
import com.example.minimental.TenthQuestion;
import com.example.minimental.informationQuestion;
import com.example.minimental.repository.AppRepository;
import com.example.minimental.repository.FireBaseCallBack;
import com.example.minimental.secoundQuestion;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {

    //region New View-Model For Second Fragment
    private AppRepository repository;
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> userId = new MutableLiveData<>();
    private MutableLiveData<String> dateTimeLiveData = new MutableLiveData<>();


    public SharedViewModel() {
        repository = new AppRepository();
    }

    public void setUserId(String userId1){

        userId.setValue(userId1);
        repository.setUserId(userId);
    }

    public  void setDateFirst(String dateTime){
        dateTimeLiveData.setValue(dateTime);
        repository.setDataTimeFirst(dateTimeLiveData);
    }
    public  void setDatelast(String dateTime){
        dateTimeLiveData.setValue(dateTime);
        repository.setDataTimeLast(dateTimeLiveData);
    }

    public void setUserName(String userName){

        username.setValue(userName);
        repository.setUserName(username);

    }

    public void loadData()
    {
        repository.load();
    }

    //missing details
    private MutableLiveData<MissingDetail> missingDetailMutableLiveData = new MutableLiveData<>();

    public LiveData<MissingDetail> getMissingDetailMutableLiveData(){

        missingDetailMutableLiveData = repository.getMissingDetail();
        return missingDetailMutableLiveData;
    }

    public void getMissingDetailCallback(FireBaseCallBack callBack){

        repository.loadMissingDetail(callBack);
    }

    public void setMissingDetailMutableLiveData(MissingDetail missingDetail){

        missingDetailMutableLiveData.setValue(missingDetail);
        repository.setMissingDetail(missingDetailMutableLiveData);
    }


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
    public MutableLiveData<secoundQuestion> objectLoad(){
        return repository.getObjectLoad();
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

        fourthQuestionLiveData = repository.getObjectForthLiveData();
        return fourthQuestionLiveData;
    }
    public void setFourthQuestionLiveData(secoundQuestion secoundQuestion){

        fourthQuestionLiveData.setValue(secoundQuestion);
        repository.setObjectForthLiveData(fourthQuestionLiveData);
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
    private MutableLiveData<SixthQuestion> repeatedSentence = new MutableLiveData<>();

    public MutableLiveData<SixthQuestion> getRepeatedSentence(){

        repeatedSentence = repository.getSentence();
        return repeatedSentence;
    }

    public void setRepeatedSentence(SixthQuestion value)
    {
        repeatedSentence.setValue(value);
        repository.setSentence(repeatedSentence);
    }
    //region Sevnth Question Data Member
    private MutableLiveData<Boolean> currectOrderSeventh = new MutableLiveData<>();
    private MutableLiveData<SevnthQuestion> picForSeventhQuestion = new MutableLiveData<>();

    public MutableLiveData<SevnthQuestion> getPicSeventhQuestion(){

        picForSeventhQuestion = repository.getPicForSeventhQuestion();
        return picForSeventhQuestion;
    }

    public void setCurrectOrderSeventh(Boolean value)
    {
        currectOrderSeventh.setValue(value);
        repository.setCurrectPicOrder(currectOrderSeventh);
    }

    //region Eight Question Data Member
    private MutableLiveData<Boolean> currectOrderEighth = new MutableLiveData<>();
    private MutableLiveData<EightQuestion> picForEighthQuestion = new MutableLiveData<>();

    public MutableLiveData<EightQuestion> getPicEighthQuestion(){

        picForEighthQuestion = repository.getPicForEighthQuestion();
        return picForEighthQuestion;
    }

    public void setCurrectPicOrderEighth(Boolean value)
    {
        currectOrderEighth.setValue(value);
        repository.setCurrectPicOrderEighth(currectOrderEighth);
    }

    //region Ninth Question Data Member
    private MutableLiveData<SixthQuestion> sentenceForNinth = new MutableLiveData<>();

    public MutableLiveData<SixthQuestion> getSentenceNinthQuestion(){

        sentenceForNinth = repository.getSentenceForNinth();
        return sentenceForNinth;
    }

    public void setSentenceNinthQuestion(SixthQuestion sentenceNinthQuestion)
    {
        sentenceForNinth.setValue(sentenceNinthQuestion);
        repository.setSentenceForNinth(sentenceForNinth);
    }

    //region Tenth Question
    private MutableLiveData<TenthQuestion> picForTenth = new MutableLiveData<>();

    public MutableLiveData<TenthQuestion> getpicForTenthQuestion(){

        picForTenth = repository.getpicForTenth();
        return picForTenth;
    }

    public void setpicForTenthQuestion(TenthQuestion sentencepicForTenth)
    {
        picForTenth.setValue(sentencepicForTenth);
        repository.setpicForTenth(picForTenth);
    }



/*    //region Eight Question Data Member
    private MutableLiveData<Float> blackBallLocationX = new MutableLiveData<>();
    private MutableLiveData<Float> blackBallLocationY = new MutableLiveData<>();
    private MutableLiveData<Float> yellowBallLocationX = new MutableLiveData<>();
    private MutableLiveData<Float> yellowBallLocationY = new MutableLiveData<>();
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
    //endregion*/

/*
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
    }*/

}
