package com.example.minimental;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<CharSequence> userName = new MutableLiveData<>();
    private MutableLiveData<CharSequence> password = new MutableLiveData<>();
    private MutableLiveData<MissingDetail> missingDetail = new MutableLiveData<>();


    public void setUserName(CharSequence text) {
        userName.setValue(text);
    }

    public LiveData<CharSequence> getUserName(){
        return userName;
    }
    public void setPassword(CharSequence text) {
        password.setValue(text);
    }

    public LiveData<CharSequence> getPassword(){
        return password;
    }

    public void setMissingDetail(MissingDetail data){
        missingDetail.setValue(data);
    }

    public LiveData<MissingDetail> getMissingDetail(){
        return  missingDetail;
    }


}
