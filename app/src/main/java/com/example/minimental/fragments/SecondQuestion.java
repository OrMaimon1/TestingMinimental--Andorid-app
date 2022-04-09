package com.example.minimental.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.LifeCycleObserver;
import com.example.minimental.R;
import com.example.minimental.ViewModels.IResultHandler;
import com.example.minimental.ViewModels.SharedViewModel;

public class SecondQuestion extends Fragment implements IResultHandler {
    private SharedViewModel sharedViewModel;
    private LifeCycleObserver speechRecognitionObserver;
    private Observer<String> getSpeechRecognistionDataResultObserver;
    private TextView resultText;

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speechRecognitionObserver = new LifeCycleObserver(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(speechRecognitionObserver);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        getSpeechRecognistionDataResultObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                resultText.setText(s);
            }
        };
        sharedViewModel.getRepeatedWords().observe(this,getSpeechRecognistionDataResultObserver);
        //speechRecognitionObserver.setMyFragmentViewModel(sharedViewModel);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.second_question,container,false);

        speechRecognitionObserver = new LifeCycleObserver(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(speechRecognitionObserver);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        getSpeechRecognistionDataResultObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                resultText.setText(s);
            }
        };
        sharedViewModel.getRepeatedWords().observe(getViewLifecycleOwner(),getSpeechRecognistionDataResultObserver);


        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        ImageButton speechBtn = rootView.findViewById(R.id.image_of_microphone);
        resultText = rootView.findViewById(R.id.check_txt);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_secondQuestion_to_chooseThirdQuestion);
            }
        });
        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechRecognitionObserver.activateSpeechRecognition();
            }
        });
        return rootView;
    }


    @Override
    public void handleResult(String result) {
        sharedViewModel.setRepeatedWords(result);
    }
}
