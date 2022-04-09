package com.example.minimental.fragments;

import android.content.Context;
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
import com.google.android.material.textfield.TextInputLayout;

public class FifthQuestion extends Fragment implements IResultHandler {
    private SharedViewModel sharedViewModel;
    private LifeCycleObserver lifeCycleObserver;
    private Observer<String> getFirstItemDescription;
    private TextView text;
   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        lifeCycleObserver = new LifeCycleObserver(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(lifeCycleObserver);
        getFirstItemDescription = new Observer<String>() {
            @Override
            public void onChanged(String s) {
             text.setText(s);
            }
        };
        sharedViewModel.getFirstItemDescription().observe(this,getFirstItemDescription);
    }*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fifth_question,container,false);

        text = rootView.findViewById(R.id.fifth_txt_check);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        lifeCycleObserver = new LifeCycleObserver(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(lifeCycleObserver);
        getFirstItemDescription = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text.setText(s);
            }
        };
        sharedViewModel.getFirstItemDescription().observe(getViewLifecycleOwner(),getFirstItemDescription);


        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        ImageButton recordBtn = rootView.findViewById(R.id.fifth_question_mic_btn);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_fifthQuestion_to_sixth_question);
            }
        });
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lifeCycleObserver.activateSpeechRecognition();
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        lifeCycleObserver = new LifeCycleObserver(requireActivity().getActivityResultRegistry());
        getLifecycle().addObserver(lifeCycleObserver);
        getFirstItemDescription = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text.setText(s);
            }
        };
        sharedViewModel.getFirstItemDescription().observe(getViewLifecycleOwner(),getFirstItemDescription);
    }

    
    @Override
    public void handleResult(String result) {
        sharedViewModel.setFirstItemDescription(result);
    }
}
