package com.example.minimental.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

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
}
