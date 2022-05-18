package com.example.minimental.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.minimental.ViewModels.SharedViewModel;
import com.example.minimental.secoundQuestion;

import java.util.ArrayList;

public class FourthQuestion extends Fragment {

    private SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getWordSpokenObserver;
    private ArrayList<String> wordsToRemember;
    private int currentwordIndex = 0;
    private EditText resultText;
    private TextView feedBack;
    private secoundQuestion fourthQuestion = new secoundQuestion();
    private secoundQuestion secoundQuestion = new secoundQuestion();
    private String word1;


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
        secoundQuestion.setObject1(sharedViewModel.objectLoad().getValue().getObject1());
        secoundQuestion.setObject2(sharedViewModel.objectLoad().getValue().getObject2());
        secoundQuestion.setObject3(sharedViewModel.objectLoad().getValue().getObject3());
        Button recordButton = rootView.findViewById(R.id.fourth_question_mic);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentwordIndex <=2) {
                    startSpeechRecognition();
                }
            }
        });
        confirmWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfWordIsCorrect();
            }
        });

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fourthQuestion_to_fifthQuestion);
            }
        });
        return rootView;
    }
    private void checkIfWordIsCorrect()
    {
        if (secoundQuestion.getObject1() == null)
        {
            feedBack.setText("Enter a object");

        }
        else {
            if (secoundQuestion.getObject1().equals(word1)) {
                currentwordIndex++;
                feedBack.setText("Correct");
            } else {
                feedBack.setText("InCorrect");
            }
        }
    }



    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechRecognizerLauncher.launch(speechIntent);
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
        else {
            listOfWords.ensureCapacity(3);
            listOfWords.add(0, spereatedWords[0]);
            listOfWords.add(1, spereatedWords[1]);
            listOfWords.add(2, spereatedWords[2]);
            word1 = listOfWords.get(0);
            fourthQuestion.setObject1(listOfWords.get(0));
            fourthQuestion.setObject2(listOfWords.get(1));
            fourthQuestion.setObject3(listOfWords.get(2));
            sharedViewModel.setFourthQuestionLiveData(fourthQuestion);
        }
    }
}
