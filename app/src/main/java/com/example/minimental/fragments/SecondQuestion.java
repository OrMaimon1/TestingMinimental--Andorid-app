package com.example.minimental.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.util.ArrayList;

public class SecondQuestion extends Fragment {
    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private LifeCycleObserver speechRecognitionObserver;
    private Observer<ArrayList<String>> getSpeechRecognistionDataResultObserver;
    private TextView resultText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speechRecognizerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
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
                updateAnswer(result);
            }
        });
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //sharedViewModel.setSecondQuestionLiveData();
        getSpeechRecognistionDataResultObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                StringBuffer sb = new StringBuffer();
                for(String result:strings)
                {
                    sb.append(result + ",");
                }
                resultText.setText(sb);
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.second_question,container,false);
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
                startSpeechRecognition();
            }
        });
        sharedViewModel.getRepeatedWords().observe(getViewLifecycleOwner(),getSpeechRecognistionDataResultObserver);
        return rootView;
    }
    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , "en");
        speechRecognizerLauncher.launch(speechIntent);
    }
    private void updateAnswer(String answer)
    {
        resultText.setText(answer);
        String[] spereatedWords = answer.split(" " , 3);
        ArrayList<String> listOfWords = new ArrayList<>(3);
        listOfWords.ensureCapacity(3);
        listOfWords.add(0 , spereatedWords[0]);
        listOfWords.add(1 , spereatedWords[1]);
        listOfWords.add(2 , spereatedWords[2]);
        sharedViewModel.setRepeatedWords(listOfWords);
    }
}
