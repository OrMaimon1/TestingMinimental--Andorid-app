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
import android.widget.EditText;
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

import com.example.minimental.R;
import com.example.minimental.SixthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class Sixth_question extends Fragment {


    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private SixthQuestion sixthQuestion = new SixthQuestion();
    private SixthQuestion sentence = new SixthQuestion();
    private Observer<String> getAnswerObserver;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    updateAnswer(result);
                    }
            }
        });
/*        getAnswerObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                resultText.setText(s);
            }
        };
        sharedViewModel.getMathAnswerGiven().observe(this , getAnswerObserver);*/
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sixth_question,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sentence = sharedViewModel.getRepeatedSentence().getValue();
        TextView sentenceTv = rootView.findViewById(R.id.six_question_textview1);
        sentenceTv.setText(sentence.getSentence());
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        Button speechBtn = rootView.findViewById(R.id.image_of_microphone);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speechBtn.startAnimation(animation);
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
                //sharedViewModel.setRepeatedSentence(sixthQuestion);
                Navigation.findNavController(view).navigate(R.id.action_sixth_question_to_SeventhQuestion);
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
        sixthQuestion.setSentence(answer);
        sharedViewModel.setRepeatedSentence(sixthQuestion);
    }
}
