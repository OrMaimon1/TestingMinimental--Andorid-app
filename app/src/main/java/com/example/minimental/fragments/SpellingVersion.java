package com.example.minimental.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

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

import com.example.minimental.R;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class SpellingVersion extends Fragment {
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getSpelledWordObserver;
    private SharedViewModel sharedViewModel;
    private int numberOfAnswersGiven = 0;
    private ArrayList<String> FinalResult = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        speechRecognizerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                Intent data = activityResult.getData();
                StringBuffer speechResult = new StringBuffer();
                if (data != null) {
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    for (String r : results) {
                        speechResult.append(r);
                    }
                    String result = speechResult.toString();
                    //updateAnswer(result);
                    FinalResult.add(result);
                }
            }
        });
        /*getSpelledWordObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                resultText.setText(s);
            }
        };*/
        //sharedViewModel.getMathAnswerGiven().observe(this , getAnswerObserver);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.spelling_version,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        Button speechBtn = rootView.findViewById(R.id.image_of_microphone);
        Button confirmAnswerbutton = rootView.findViewById(R.id.Button_finish_answer);
        nxtBtn.setEnabled(false);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speechBtn.startAnimation(animation);
        confirmAnswerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfAnswersGiven++;
                if(numberOfAnswersGiven == 5)
                {
                    nxtBtn.setEnabled(true);
                    Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                    nxtBtn.startAnimation(animation);
                    //confirmAnswerbutton.setEnabled(false);
                    confirmAnswerbutton.setAlpha(0.3f);
                    speechBtn.setEnabled(false);
                    speechBtn.setAlpha(0.3f);
                    //speechBtn.setImageAlpha(30);
                }
            }
        });
        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechBtn.clearAnimation();
                startSpeechRecognition();
            }
        });
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel.setSpelledWord(FinalResult);
                Navigation.findNavController(view).navigate(R.id.action_spellingVersion_to_fourthQuestion);
            }
        });
        return rootView;
    }
    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechRecognizerLauncher.launch(speechIntent);
    }

    private void updateAnswer(String answer)
    {
        //resultText.setText(answer);
        String adder;
        String[] spereatedWords = answer.split(" " , 5);
        ArrayList<String> listOfWords = new ArrayList<>(5);
        listOfWords.ensureCapacity(5);
        listOfWords.add(0 , spereatedWords[0]);
        listOfWords.add(1 , spereatedWords[1]);
        listOfWords.add(2 , spereatedWords[2]);
        listOfWords.add(3 , spereatedWords[3]);
        listOfWords.add(4 , spereatedWords[4]);
    }
}
