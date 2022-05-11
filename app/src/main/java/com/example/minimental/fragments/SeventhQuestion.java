package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.R;
import com.example.minimental.SevnthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

public class SeventhQuestion extends Fragment {

    ImageView milk;
    float x = 0, y = 0;
    private SharedViewModel sharedViewModel;
    private SevnthQuestion sevnthQuestion = new SevnthQuestion();
    private Boolean sevnthCurrectOrder = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.seventh_question,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        milk = rootView.findViewById(R.id.seventh_question_ImageView_milk);
        milk.setOnTouchListener(new MyTouchListener());

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel.setCurrectOrderSeventh(sevnthCurrectOrder);
                Navigation.findNavController(view).navigate(R.id.action_SeventhQuestion_to_eighthQuestion);
            }
        });

        return rootView;
    }

    final class MyTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float movedX, movedY;
                    movedX = event.getX();
                    movedY = event.getY();

                    float distanceX = movedX - x;
                    float distanceY = movedY - y;

                    view.setX(view.getX()+distanceX);
                    view.setY(view.getY()+distanceY);

                    break;
                }

            return true;
        }
    }
}
