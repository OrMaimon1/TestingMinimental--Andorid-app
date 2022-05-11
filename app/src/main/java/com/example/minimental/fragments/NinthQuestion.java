package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.R;
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
        currentSentence = rootView.findViewById(R.id.input_SentenceET);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nineQuestion.setSentence(currentSentence.getText().toString());
                sharedViewModel.setSentenceNinthQuestion(nineQuestion);
                Navigation.findNavController(view).navigate(R.id.action_ninthQuestion_to_tenthQuestion);
            }
        });
        return rootView ;
    }
}
