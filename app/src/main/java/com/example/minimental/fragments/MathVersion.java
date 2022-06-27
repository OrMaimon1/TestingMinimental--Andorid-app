package com.example.minimental.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
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

public class MathVersion extends Fragment implements MediaPlayerServiceBinder {
    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getAnswerObserver;
    private int numberOfAnswersGiven = 0;
    private TextView explainText;
    private TextView resultText;
    private ArrayList<String> FinalResult = new ArrayList<>();
    private ThirdQuestion math = new ThirdQuestion();
    private String result;
    private Button speechBtn;
    private Button[] speakerButtons;
    private String instructionsLink = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0917%D7%94%D7%95%D7%A8%D7%90%D7%AA%20%D7%97%D7%A9%D7%91%D7%95%D7%9F.mp3?alt=media&token=400739c2-46e8-4622-823f-02cd2baa83bb";
    private String continueCommandLink = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0918%D7%AA%D7%9E%D7%A9%D7%99%D7%9A.mp3?alt=media&token=24c994a7-8bb9-4238-88c5-dc621fad5763";
    private String stopCommandLink = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0919%D7%A2%D7%A6%D7%95%D7%A8.mp3?alt=media&token=9fb58cb6-836a-4a50-930c-0cc5604e7584";

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
                    result = speechResult.toString();
                    resultText.setText(result);
                    //FinalResult.add(result); //check if working
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
        speechBtn = rootView.findViewById(R.id.mic_image_btn);
        Button speakerButton = rootView.findViewById(R.id.math_istructions_speaker);
        nxtBtn.setEnabled(false);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speakerButton.startAnimation(animation);
        Button confirmAnswerbutton = rootView.findViewById(R.id.Button_finish_answer);
        speakerButtons = new Button[]{speakerButton , confirmAnswerbutton};
        math = sharedViewModel.getMathAnswerGiven().getValue();
        confirmAnswerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                confirmAnswerbutton.startAnimation(animation1);
                numberOfAnswersGiven++;
                if(numberOfAnswersGiven<5)
                {
                    startMediaService(continueCommandLink);
                }
                FinalResult.add(result); //check if working
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
                speakerButton.clearAnimation();
                startMediaService(instructionsLink);
            }
        });

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                sharedViewModel.setMathAnswerGiven(FinalResult);
                Navigation.findNavController(view).navigate(R.id.action_mathVersion_to_fourthQuestion);
                //numberOfAnswersGiven = 0;
                //FinalResult.clear();
            }
        });
        return rootView;
    }
    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        disableAllSpeakerButtons();
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechRecognizerLauncher.launch(speechIntent);
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
