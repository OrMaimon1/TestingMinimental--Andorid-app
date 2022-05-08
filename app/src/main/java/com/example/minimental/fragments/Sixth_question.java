package com.example.minimental.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.MainActivityViewModel;
import com.example.minimental.R;
import com.example.minimental.SixthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

public class Sixth_question extends Fragment {


    private SharedViewModel sharedViewModel;
    private SixthQuestion sixthQuestion = new SixthQuestion();
    private EditText currentSentence;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sixth_question,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        currentSentence = rootView.findViewById(R.id.input_SentenceET);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sixthQuestion.setSentence(currentSentence.getText().toString());
                //sharedViewModel.setRepeatedSentence(sixthQuestion);
                Navigation.findNavController(view).navigate(R.id.action_sixth_question_to_SeventhQuestion);
            }
        });
        return rootView;

    }
}
