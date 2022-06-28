package com.example.minimental.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.minimental.ViewModels.SharedViewModel;
import com.example.minimental.secoundQuestion;

import java.util.ArrayList;

public class FourthQuestion extends Fragment implements MediaPlayerServiceBinder {

    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getWordSpokenObserver;
    private ArrayList<String> wordsToRemember;
    private int currentwordIndex = 0;
    private EditText resultText;
    private TextView feedBack;
    private secoundQuestion fourthQuestion = new secoundQuestion();
    private secoundQuestion secoundquestion = new secoundQuestion();
    private String word1;
    private Button listenBtn;
    private Button recordButton;
    private ArrayList<String> listOfWords;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //wordsToRemember = sharedViewModel.getRepeatedWords().getValue();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fourth_question,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        Button confirmWordButton = rootView.findViewById(R.id.confirm_word_btn);
        resultText = rootView.findViewById(R.id.word_spoken_ET);
        feedBack = rootView.findViewById(R.id.feedback_text);
        secoundquestion = sharedViewModel.getObjectdata().getValue();
        recordButton = rootView.findViewById(R.id.fourth_question_mic);
        listenBtn = rootView.findViewById(R.id.repeat_words_instruction_speaker);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        listenBtn.startAnimation(animation);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentwordIndex <=2) {
                    recordButton.clearAnimation();
                    startSpeechRecognition();

                }
            }
        });
        confirmWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                confirmWordButton.startAnimation(animation);
                checkIfWordIsCorrect();
                Log.d("firebase", String.valueOf(secoundquestion.getObject1()));
                Log.d("firebase word", String.valueOf(word1));
            }
        });

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                Navigation.findNavController(view).navigate(R.id.action_fourthQuestion_to_fifthQuestion);
            }
        });
        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenBtn.clearAnimation();
                startMediaService();
            }
        });


        return rootView;
    }
    private void checkIfWordIsCorrect()
    {
        if (listOfWords == null) {
            listOfWords = new ArrayList<>(3);
        }
        if (listOfWords.size() < 3)
        {
            feedBack.setText(R.string.Enter_ForthQuestion);
            Toast.makeText(getContext(), R.string.ThreeWord_secondQuestion, Toast.LENGTH_LONG).show();

        }
        else {
            if (secoundquestion.getObject1().equals(listOfWords.get(0))) {
                if (secoundquestion.getObject2().equals(listOfWords.get(1))){

                    if (secoundquestion.getObject3().equals(listOfWords.get(2))){
                        feedBack.setText(R.string.Correct_fourthQuestion);

                    }
                    else {
                        feedBack.setText(R.string.VeryClose_fourthQuestion);

                    }
                }
                else{
                    feedBack.setText(R.string.Close_fourthQuestion);
                }
            } else {
                feedBack.setText(R.string.InCorrect_fourthQuestion);
            }
        }

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
        MediaPlayerService.currentFragment = this;
        listenBtn.setClickable(false);
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0920%D7%96%D7%99%D7%9B%D7%A8%D7%95%D7%9F%20%D7%90%D7%A8%D7%95%D7%9A.mp3?alt=media&token=4ddd5c80-77fd-49b4-a71a-17a26c52d025");
        getContext().startService(intent);
    }

    @Override
    public void startSpeechButtonAnimation() {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        recordButton.startAnimation(animation);
        listenBtn.setClickable(true);
    }

    private void updateAnswer(String answer)
    {
        resultText.setText(answer);
        String adder;
        String[] spereatedWords = answer.split(" " , 3);
        listOfWords = new ArrayList<>(3);
        if (spereatedWords.length < 3)
        {
            Toast.makeText(getContext(), R.string.ThreeWord_secondQuestion, Toast.LENGTH_LONG).show();
        }
        else {
            listOfWords.ensureCapacity(3);
            listOfWords.add(0, spereatedWords[0]);
            listOfWords.add(1, spereatedWords[1]);
            listOfWords.add(2, spereatedWords[2]);
            fourthQuestion.setObject1(listOfWords.get(0));
            fourthQuestion.setObject2(listOfWords.get(1));
            fourthQuestion.setObject3(listOfWords.get(2));
            sharedViewModel.setFourthQuestionLiveData(fourthQuestion);
        }
    }
}
