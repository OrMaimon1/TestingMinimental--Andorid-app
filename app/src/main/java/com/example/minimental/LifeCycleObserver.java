package com.example.minimental;
import android.content.Intent;
import android.speech.RecognizerIntent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.minimental.ViewModels.IResultHandler;

import java.util.ArrayList;

public class LifeCycleObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry myRegistry;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;



    final String TAG = "lifeCycleOwner";


    public LifeCycleObserver(ActivityResultRegistry registry)
    {
        myRegistry = registry;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LifecycleOwner lifecycleOwner = owner;
        speechRecognizerLauncher = myRegistry.register("speechRecognitionActivity", owner, new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                Intent data = activityResult.getData();
                StringBuffer speechResult = new StringBuffer();
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                for(String r:results)
                {
                    speechResult.append(r);
                }
                String result = speechResult.toString();
                ((IResultHandler)owner).handleResult(result);

                //myFragmentViewModel.setSpeechRecognizerData(result);
            }
        });
    }


    /*@Override
    public void onResume(@NonNull LifecycleOwner owner) {
        lifecycleOwner = owner;
    }*/

    /*public void setMyFragmentViewModel(FragmentOneViewModel fragment)
    {
        myFragmentViewModel = fragment;
    }*/

    public void activateSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , "en");
        speechRecognizerLauncher.launch(speechIntent);
    }
}
