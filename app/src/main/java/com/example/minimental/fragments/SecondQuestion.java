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

import com.example.minimental.LifeCycleObserver;
import com.example.minimental.R;
import com.example.minimental.Services.MediaPlayerService;
import com.example.minimental.ViewModels.SharedViewModel;
import com.example.minimental.secoundQuestion;

import java.util.ArrayList;

public class SecondQuestion extends Fragment {
    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    //private LifeCycleObserver speechRecognitionObserver;
    private Observer<ArrayList<String>> getSpeechRecognistionDataResultObserver;
    private TextView resultText;
    private secoundQuestion secoundQuestion = new secoundQuestion();

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
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        Button listenBtn = rootView.findViewById(R.id.memory_instruction_speaker);
        Button speechBtn = rootView.findViewById(R.id.image_of_microphone);
        resultText = rootView.findViewById(R.id.check_txt);

        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        listenBtn.startAnimation(animation);

        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenBtn.clearAnimation();
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
                speechBtn.startAnimation(animation);
                startMediaService();
            }
        });



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
                speechBtn.clearAnimation();
            }
        });
        //sharedViewModel.getRepeatedWords().observe(getViewLifecycleOwner(),getSpeechRecognistionDataResultObserver);
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
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0913%D7%94%D7%95%D7%A8%D7%90%D7%94%20%D7%96%D7%99%D7%9B%D7%A8%D7%95%D7%9F.mp3?alt=media&token=f6bc06fc-114d-4a62-9ffb-0592c25b5d31");
        getContext().startService(intent);

    }
    private void updateAnswer(String answer)
    {
        resultText.setText(answer);
        String adder;
        String[] spereatedWords = answer.split(" " , 3);
        ArrayList<String> listOfWords = new ArrayList<>(3);
        if (spereatedWords.length < 3)
        {
            Toast.makeText(getContext(), "please enter 3 word!!", Toast.LENGTH_LONG).show();

        }
        else{
            listOfWords.ensureCapacity(3);
            listOfWords.add(0 , spereatedWords[0]);
            listOfWords.add(1 , spereatedWords[1]);
            listOfWords.add(2 , spereatedWords[2]);
            secoundQuestion.setObject1(listOfWords.get(0));
            secoundQuestion.setObject2(listOfWords.get(1));
            secoundQuestion.setObject3(listOfWords.get(2));
            sharedViewModel.setObjectData(secoundQuestion);
        }


    }
}
