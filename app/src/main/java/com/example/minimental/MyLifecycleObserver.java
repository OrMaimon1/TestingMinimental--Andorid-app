package com.example.minimental;

import android.content.Intent;
import android.net.Uri;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.transform.Result;

class MyLifecycleObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<Intent> mSpeechRecogntionResult;

    MyLifecycleObserver(@NonNull ActivityResultRegistry registry) {
        mRegistry = registry;
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        // ...

        mSpeechRecogntionResult = mRegistry.register("key", owner, new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Result_OK){
                            Intent data =result.getData();
                            ArrayList<String> results =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            sb = new StringBuffer();
                            for (String result: results)
                            {
                                sb.append(result +",");
                            }

                        }
                    }

                });
    }

    public void Speech_Recqniton() {
        // Open the activity to select an image
        mSpeechRecogntionResult.launch("");
    }

}

