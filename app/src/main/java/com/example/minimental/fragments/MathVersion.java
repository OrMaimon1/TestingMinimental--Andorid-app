package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.LifeCycleObserver;
import com.example.minimental.R;
import com.example.minimental.ViewModels.IResultHandler;
import com.example.minimental.ViewModels.SharedViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MathVersion extends Fragment implements IResultHandler {
    private SharedViewModel sharedViewModel;
    private LifeCycleObserver speechRecognitionObserver;
    private Observer<String> getAnswerObserver;
    private TextView resultText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        speechRecognitionObserver = new LifeCycleObserver(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(speechRecognitionObserver);
        //speechRecognitionObserver.setMyFragmentViewModel(sharedViewModel);
        getAnswerObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                resultText.setText(s);
            }
        };
        sharedViewModel.getAnswerGiven().observe(this , getAnswerObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.math_version,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        ImageButton speechBtn = rootView.findViewById(R.id.mic_image_btn);
        resultText = rootView.findViewById(R.id.result_txt_math);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_mathVersion_to_fourthQuestion);
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
        sharedViewModel.setAnswerGiven(result);
    }
}
