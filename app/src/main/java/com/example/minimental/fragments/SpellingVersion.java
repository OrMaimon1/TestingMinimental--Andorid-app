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
import com.example.minimental.Services.MediaPlayerServiceBinder;
import com.example.minimental.ThirdQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class SpellingVersion extends Fragment implements MediaPlayerServiceBinder {
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getSpelledWordObserver;
    private SharedViewModel sharedViewModel;
    private int numberOfAnswersGiven = 0;
    private ArrayList<String> FinalResult = new ArrayList<>();
    private ThirdQuestion spell = new ThirdQuestion();
    private Button speechBtn;
    private Button[] speakerButtons;
    private String instructionsLink = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0918%D7%94%D7%95%D7%A8%D7%90%D7%AA%20%D7%90%D7%99%D7%95%D7%AA.mp3?alt=media&token=2e34cbbf-8131-4cc2-8e37-d71cc03296ce";
    private String continueCommandLink = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0918%D7%AA%D7%9E%D7%A9%D7%99%D7%9A.mp3?alt=media&token=24c994a7-8bb9-4238-88c5-dc621fad5763";
    private String stopCommandLink = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0919%D7%A2%D7%A6%D7%95%D7%A8.mp3?alt=media&token=9fb58cb6-836a-4a50-930c-0cc5604e7584";



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
        speechBtn = rootView.findViewById(R.id.image_of_microphone);
        Button confirmAnswerbutton = rootView.findViewById(R.id.Button_finish_answer);
        Button speakerButton = rootView.findViewById(R.id.spelling_instructions_speaker);
        TextView spellTv = rootView.findViewById(R.id.spelling_version_textview);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speakerButton.startAnimation(animation);
        spell = sharedViewModel.getSpelledWord().getValue();
        speakerButtons = new Button[]{confirmAnswerbutton , speakerButton};
        spellTv.setText(spell.getObjectforspelling());
        nxtBtn.setEnabled(false);
        confirmAnswerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                confirmAnswerbutton.startAnimation(animation1);
                numberOfAnswersGiven++;
                if(numberOfAnswersGiven < 5)
                {
                    startMediaService(continueCommandLink);
                }
                if(numberOfAnswersGiven == 5)
                {
                    startMediaService(stopCommandLink);
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

        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakerButton.clearAnimation();
                startMediaService(instructionsLink);
            }
        });
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
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

    private void startMediaService(String link)
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        disableAllSpeakerButtons();
        MediaPlayerService.currentFragment = this;
        intent.putExtra("Link" , link);
        getContext().startService(intent);
    }

    @Override
    public void startSpeechButtonAnimation() {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speechBtn.startAnimation(animation);
        enabaleAllSpeakerButtons();
    }

    private void disableAllSpeakerButtons()
    {
        for(Button button : speakerButtons)
        {
            button.setClickable(false);
        }
    }

    private void enabaleAllSpeakerButtons()
    {
        for(Button button : speakerButtons)
        {
            button.setClickable(true);
        }
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
