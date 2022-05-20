package com.example.minimental.fragments;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.CustomView.MilkDragView;
import com.example.minimental.R;
import com.example.minimental.SevnthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.Calendar;

public class SeventhQuestion extends Fragment {

    ImageView milk;
    private SharedViewModel sharedViewModel;
    private SevnthQuestion sevnthQuestion = new SevnthQuestion();
    private Boolean sevnthCurrectOrder = false;
    private MilkDragView.DrawableProxy milkPicture;
    private MilkDragView milkDragView;
    private Rect borderRect;
    private Drawable clickOnFridge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.seventh_question,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        milkDragView = rootView.findViewById(R.id.milk_drag_view);
        milkPicture = milkDragView.getMilkDrawable();
        borderRect = milkDragView.getMilkBorderRect();
        clickOnFridge = milkDragView.getFridgeDrawble();
        milkDragView.setOnTouchListener(new milkDragTouchListener());
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //milk = rootView.findViewById(R.id.seventh_question_ImageView_milk);
        //milk.setOnTouchListener(new MyTouchListener());

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel.setCurrectOrderSeventh(sevnthCurrectOrder);
                Navigation.findNavController(view).navigate(R.id.action_SeventhQuestion_to_eighthQuestion);
            }
        });

        return rootView;
    }

    private class milkDragTouchListener implements View.OnTouchListener {
        private static final int MAX_CLICK_DURATION = 200;
        private long startClickTime;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (borderRect.contains((int)x , (int)y)) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_MOVE:

                        milkDragView.moveBorderRect((int)x , (int)y);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(milkDragView.isClickOnFridge((int) x, (int) y))
                        {
                            milkDragView.changeBorderColor();
                            milkDragView.setFridgeState(true);
                        }
                        if(milkDragView.isInPosition())
                        {
                            milkDragView.changeBorderColor();
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:

                        break;
                }
            }
                return true;
        }
    }





    /*final class MyTouchListener implements View.OnTouchListener{

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
    }*/
}
