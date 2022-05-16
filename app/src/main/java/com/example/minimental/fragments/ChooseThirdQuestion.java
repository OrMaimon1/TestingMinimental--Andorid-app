package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.minimental.R;

public class ChooseThirdQuestion extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
    }
    private CardView mathCard, spellingCard;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.choose_third_question,container,false);
         mathCard = rootView.findViewById(R.id.math_card);
        mathCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_chooseThirdQuestion_to_mathVersion);
            }
        });
        spellingCard = rootView.findViewById(R.id.spelling_card);
        spellingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_chooseThirdQuestion_to_spellingVersion);
            }
        });

        return rootView;
    }
}
