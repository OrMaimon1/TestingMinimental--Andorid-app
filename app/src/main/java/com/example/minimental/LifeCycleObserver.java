package com.example.minimental;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.minimental.ViewModels.IFragment;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class LifeCycleObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry myRegistry;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private StringBuffer speechResult = new StringBuffer();
    private SharedViewModel fragmentToObserve;


    public LifeCycleObserver(ActivityResultRegistry registry)
    {
        myRegistry = registry;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        speechRecognizerLauncher = myRegistry.register("speechRecognitionAvtivity", owner, new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                Intent data = activityResult.getData();
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                for(String r:results)
                {
                    speechResult.append(r);
                }
                String result = speechResult.toString();
                fragmentToObserve.setAnswerGiven(result);
            }
        });
    }

    public void setMyFragmentViewModel(SharedViewModel fragment)
    {
        fragmentToObserve = fragment;
    }

    public void activateSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 5);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , "en");
        speechRecognizerLauncher.launch(speechIntent);
    }
    public StringBuffer getSpeechResult()
    {
        return speechResult;
    }
}
