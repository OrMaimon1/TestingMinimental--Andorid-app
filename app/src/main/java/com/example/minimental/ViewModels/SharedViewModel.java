package com.example.minimental.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    //region Information Question Data Members
    private MutableLiveData<String> dayAnswer = new MutableLiveData<>();
    private MutableLiveData<String> monthAnswer = new MutableLiveData<>();
    private MutableLiveData<String> yearAnswer = new MutableLiveData<>();
    private MutableLiveData<String> dateAnswer = new MutableLiveData<>();
    private MutableLiveData<String> seasonAnswer = new MutableLiveData<>();
    private MutableLiveData<String> countryAnswer = new MutableLiveData<>();
    private MutableLiveData<String> cityAnswer = new MutableLiveData<>();
    private MutableLiveData<String> streetAnswer = new MutableLiveData<>();
    //endregion

    //region Second Question Data Members
    private MutableLiveData<String> repeatedWords = new MutableLiveData<>();
    private String firstWord = "";
    private String secondWord = "";
    private String thirdWord = "";
    //endregion

    //region Third Question Math Version Data Member
    private MutableLiveData<String> answerGiven = new MutableLiveData<>();
    //endregion

    //region Third Question spelling Version Data Member
    private MutableLiveData<String> spelledWord = new MutableLiveData<>();
    //endregion

    //region Fourth Question Data Members
    private MutableLiveData<String> firstWordInFourthQuestion = new MutableLiveData<>();
    private MutableLiveData<String> secondWordInFourthQuestion = new MutableLiveData<>();
    private MutableLiveData<String> thirdWordInFourthQuestion = new MutableLiveData<>();
    //endregion

    //region Fifth Question Data Members
    private MutableLiveData<String> firstItemDescription = new MutableLiveData<>();
    private MutableLiveData<String> secondItemDescription = new MutableLiveData<>();
    //endregion

    //region Sixth Question Data member
    private MutableLiveData<String> repeatedSentence = new MutableLiveData<>();
    //endregion

    //region Eight Question Data Member
    private MutableLiveData<Float> blackBallLocationX = new MutableLiveData<>();
    private MutableLiveData<Float> blackBallLocationY = new MutableLiveData<>();
    private MutableLiveData<Float> yellowBallLocationX = new MutableLiveData<>();
    private MutableLiveData<Float> yellowBallLocationY = new MutableLiveData<>();
    //endregion


    //region Information Question Methods
    public void setDayAnswer(String value)
    {
        dayAnswer.setValue(value);
    }
    public void setMonthAnswer(String value)
    {
        monthAnswer.setValue(value);
    }
    public void setYearAnswerAnswer(String value)
    {
        yearAnswer.setValue(value);
    }
    public void setDateAnswer(String value)
    {
        dateAnswer.setValue(value);
    }
    public void setSeasonAnswer(String value)
    {
        seasonAnswer.setValue(value);
    }
    public void setCountryAnswer(String value)
    {
        countryAnswer.setValue(value);
    }
    public void setCityAnswer(String value)
    {
        cityAnswer.setValue(value);
    }
    public void setStreetAnswer(String value)
    {
        streetAnswer.setValue(value);
    }

    public MutableLiveData<String> getDayAnswer() {
        return dayAnswer;
    }

    public MutableLiveData<String> getMonthAnswer() {
        return monthAnswer;
    }

    public MutableLiveData<String> getYearAnswer() {
        return yearAnswer;
    }

    public MutableLiveData<String> getDateAnswer() {
        return dateAnswer;
    }

    public MutableLiveData<String> getSeasonAnswer() {
        return seasonAnswer;
    }

    public MutableLiveData<String> getCountryAnswer() {
        return countryAnswer;
    }

    public MutableLiveData<String> getCityAnswer() {
        return cityAnswer;
    }

    public MutableLiveData<String> getStreetAnswer() {
        return streetAnswer;
    }
    //endregion

    //region Second Question Methods
    public void setRepeatedWords(String value)
    {
        repeatedWords.setValue(value);
    }

    public MutableLiveData<String> getRepeatedWords()
    {
        return repeatedWords;
    }

    private void seperateSentenceToWords()
    {
        String[] seperatedSentence;
        seperatedSentence = repeatedWords.toString().split(" " , 3);
    }
    //endregion

    //region Third Question Math Version Methods
    public void setAnswerGiven(String value)
    {
        answerGiven.setValue(value);
    }
    public MutableLiveData<String> getAnswerGiven()
    {
        return answerGiven;
    }
    //endregion

    //region Third Question Spelling Version Methods
    public void setSpelledWord(String value)
    {
        spelledWord.setValue(value);
    }

    public MutableLiveData<String> getSpelledWord()
    {
        return spelledWord;
    }

    public void seperateSpelledWord()
    {
        String[] word = spelledWord.toString().split(" " , 5);
    }
    //endregion

    //region Fourth Question Methods
    public void setFirstWordInFourthQuestion(String value)
    {
        firstWordInFourthQuestion.setValue(value);
    }

    public void setSecondWordInFourthQuestion(String value)
    {
        secondWordInFourthQuestion.setValue(value);
    }
    public void setThirdWordInFourthQuestion(String value)
    {
        thirdWordInFourthQuestion.setValue(value);
    }
    public MutableLiveData<String> getFirstWordInFourthQuestion()
    {
        return firstWordInFourthQuestion;
    }
    public MutableLiveData<String> getSecondWordInFourthQuestion()
    {
        return secondWordInFourthQuestion;
    }
    public MutableLiveData<String> getThirdWordInFourthQuestion()
    {
        return thirdWordInFourthQuestion;
    }
    //endregion

    //region Fifth Question Methods
    public void setFirstItemDescription(String value)
    {
        firstItemDescription.setValue(value);
    }
    public void setSecondItemDescription(String value)
    {
        secondWordInFourthQuestion.setValue(value);
    }

    public MutableLiveData<String> getFirstItemDescription() {
        return firstItemDescription;
    }

    public MutableLiveData<String> getSecondItemDescription() {
        return secondItemDescription;
    }
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
}
