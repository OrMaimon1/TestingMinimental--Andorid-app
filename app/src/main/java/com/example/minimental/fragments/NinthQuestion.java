package com.example.minimental.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.R;
import com.example.minimental.Services.MediaPlayerService;
import com.example.minimental.SixthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

public class NinthQuestion extends Fragment {

    private EditText currentSentence;
    private SharedViewModel sharedViewModel;
    private SixthQuestion nineQuestion = new SixthQuestion();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ninth_question,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        Button speakerButton = rootView.findViewById(R.id.ninth_question_speaker);
        currentSentence = rootView.findViewById(R.id.input_SentenceET);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speakerButton.startAnimation(animation);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                nineQuestion.setSentence(currentSentence.getText().toString());
                sharedViewModel.setSentenceNinthQuestion(nineQuestion);
                Navigation.findNavController(view).navigate(R.id.action_ninthQuestion_to_tenthQuestion);
            }
        });

        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakerButton.clearAnimation();
                startMediaService();
            }
        });
        return rootView ;
    }

    private void startMediaService()
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0526_1316%D7%9B%D7%AA%D7%99%D7%91%D7%AA%20%D7%9E%D7%A9%D7%A4%D7%98.mp3?alt=media&token=2a29ddb6-0020-4d5d-9aaf-d376e581fee8");
        getContext().startService(intent);
    }
}
