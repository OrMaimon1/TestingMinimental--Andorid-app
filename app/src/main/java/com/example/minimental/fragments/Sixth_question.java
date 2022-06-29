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
import com.example.minimental.SixthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class Sixth_question extends Fragment implements MediaPlayerServiceBinder {


    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private SixthQuestion sixthQuestion = new SixthQuestion();
    private SixthQuestion sentence = new SixthQuestion();
    private Observer<String> getAnswerObserver;
    private String link;
    private Button speechBtn;
    private Button speakerButton;
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
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sixth_question,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sentence = sharedViewModel.getRepeatedSentence().getValue();
        TextView sentenceTv = rootView.findViewById(R.id.six_question_textview1);
        Log.d("firebase", sentence.getSentence());
        sentenceTv.setText(sentence.getSentence());
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        speechBtn = rootView.findViewById(R.id.image_of_microphone);
        speakerButton = rootView.findViewById(R.id.repeat_sentence_speaker);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speakerButton.startAnimation(animation);
        Integer Version = sharedViewModel.getVersion().getValue();
        if (Version == null || Version == 0) //only for now some users dosnt have version alredy asked to add
        {
            Version = 1;
        }
        if (Version == 1){ // for pic
            link = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Repeating%20Sentence%20Versions%2FMyRec_0525_0926%D7%97%D7%96%D7%A8%D7%94%20%D7%A2%D7%9C%20%D7%9E%D7%A9%D7%A4%D7%98.mp3?alt=media&token=5d65dbb1-9927-4035-85b0-c2cf90b2c594";
        }
        if (Version == 2){
            link = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Repeating%20Sentence%20Versions%2FMyRec_0526_1325%D7%92%D7%A8%D7%A1%D7%94%20%D7%A9%D7%A0%D7%99%D7%94%20%D7%97%D7%96%D7%A8%D7%94%20%D7%A2%D7%9C%20%D7%9E%D7%A9%D7%A4%D7%98.mp3?alt=media&token=d1319bd4-974a-4f09-b905-50b917ecf244";
        }
        if (Version == 3){
            link = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Repeating%20Sentence%20Versions%2FMyRec_0526_1325%D7%92%D7%A8%D7%A1%D7%94%20%D7%A9%D7%9C%D7%99%D7%A9%D7%99%D7%AA%20%D7%97%D7%96%D7%A8%D7%94%20%D7%A2%D7%9C%20%D7%9E%D7%A9%D7%A4%D7%98.mp3?alt=media&token=de0a9d59-c0ca-4d09-9db0-faf20a94c3a3";
        }
        if (Version == 4){
            link = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Repeating%20Sentence%20Versions%2FMyRec_0526_1326%D7%92%D7%A8%D7%A1%D7%94%20%D7%A8%D7%91%D7%99%D7%A2%D7%99%D7%AA%20%D7%97%D7%96%D7%A8%D7%94%20%D7%A2%D7%9C%20%D7%9E%D7%A9%D7%A4%D7%98.mp3?alt=media&token=5164aefd-2db1-4e53-8c7d-5731a1c39e02";
        }
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
                startMediaService();
            }
        });
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                sharedViewModel.setRepeatedSentence(sixthQuestion);
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
    private void startMediaService()
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        speakerButton.setClickable(false);
        MediaPlayerService.currentFragment = this;
        intent.putExtra("Link" , link);
        getContext().startService(intent);
    }

    @Override
    public void startSpeechButtonAnimation() {
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speechBtn.startAnimation(animation);
        speakerButton.setClickable(true);
    }

    private void updateAnswer(String answer)
    {
        sixthQuestion.setSentence(answer);
        sharedViewModel.setRepeatedSentence(sixthQuestion);
    }
}
