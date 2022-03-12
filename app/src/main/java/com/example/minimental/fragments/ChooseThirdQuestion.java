package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.minimental.R;

public class ChooseThirdQuestion extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.choose_third_question,container,false);
        Button nxtMathBtn = rootView.findViewById(R.id.button_of_mathQuestion);
        nxtMathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_chooseThirdQuestion_to_mathVersion);
            }
        });
        Button nxtSpellBtn = rootView.findViewById(R.id.button_of_spellingQuestion);
        nxtSpellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_chooseThirdQuestion_to_spellingVersion);
            }
        });
        return rootView;
    }
}
