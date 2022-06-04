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
import com.example.minimental.Services.MediaPlayerService;
import com.example.minimental.ThirdQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class MathVersion extends Fragment  {
    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getAnswerObserver;
    private int numberOfAnswersGiven = 0;
    private TextView explainText;
    private TextView resultText;
    private ArrayList<String> FinalResult = new ArrayList<>();
    private ThirdQuestion math = new ThirdQuestion();

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
                    resultText.setText(result);
                    FinalResult.add(result);

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
        View rootView = inflater.inflate(R.layout.math_version,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        Button speechBtn = rootView.findViewById(R.id.mic_image_btn);
        ImageButton speakerButton = rootView.findViewById(R.id.math_istructions_speaker);
        nxtBtn.setEnabled(false);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speechBtn.startAnimation(animation);
        Button confirmAnswerbutton = rootView.findViewById(R.id.Button_finish_answer);
        math = sharedViewModel.getMathAnswerGiven().getValue();
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
        explainText = rootView.findViewById(R.id.explain_math);
        resultText = rootView.findViewById(R.id.math_version_question_textview);
        resultText.setText(math.getNumber());
        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechBtn.clearAnimation();
                startSpeechRecognition();
                explainText.setVisibility(View.GONE);
            }
        });

        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMediaService();
            }
        });

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel.setMathAnswerGiven(FinalResult);
                Navigation.findNavController(view).navigate(R.id.action_mathVersion_to_fourthQuestion);
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

    private void startMediaService()
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0917%D7%94%D7%95%D7%A8%D7%90%D7%AA%20%D7%97%D7%A9%D7%91%D7%95%D7%9F.mp3?alt=media&token=400739c2-46e8-4622-823f-02cd2baa83bb");
        getContext().startService(intent);
    }
/*    private void updateAnswer(String answer)
    {
        resultText.setText(answer);
        String adder;
        String[] spereatedWords = answer.split(" " , 5);
        ArrayList<String> listOfWords = new ArrayList<>(5);
        listOfWords.ensureCapacity(5);
        listOfWords.add(0 , spereatedWords[0]);
        listOfWords.add(1 , spereatedWords[1]);
        listOfWords.add(2 , spereatedWords[2]);
        listOfWords.add(3 , spereatedWords[3]);
        listOfWords.add(4 , spereatedWords[4]);
        sharedViewModel.setMathAnswerGiven(listOfWords);
    }*/
}
