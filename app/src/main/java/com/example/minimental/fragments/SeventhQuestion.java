package com.example.minimental.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.CustomView.MilkDragView;
import com.example.minimental.R;
import com.example.minimental.Services.MediaPlayerService;
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
        Button listenBtn = rootView.findViewById(R.id.seventh_question_instruction_speaker);
        milkDragView = rootView.findViewById(R.id.milk_drag_view);
        milkPicture = milkDragView.getMilkDrawable();
        borderRect = milkDragView.getMilkBorderRect();
        clickOnFridge = milkDragView.getFridgeDrawble();
        milkDragView.setOnTouchListener(new milkDragTouchListener());
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        listenBtn.startAnimation(animation);
        //milk = rootView.findViewById(R.id.seventh_question_ImageView_milk);
        //milk.setOnTouchListener(new MyTouchListener());
        Integer Version = sharedViewModel.getVersion().getValue(); // this is the test version
        if (Version == null) //only for now some users dosnt have version alredy asked to add
        {
            Version = 1;
        }


        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                sharedViewModel.setCurrectOrderSeventh(sevnthCurrectOrder);
                Navigation.findNavController(view).navigate(R.id.action_SeventhQuestion_to_eighthQuestion);
            }
        });

        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenBtn.clearAnimation();
                startMediaService();
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
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_MOVE:
                    if(borderRect.contains((int)x , (int)y)) {
                        milkDragView.moveBorderRect((int) x, (int) y);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(milkDragView.isInPosition())
                    {
                        milkDragView.changeBorderColor();
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    if(!(borderRect.contains((int)x , (int)y)))
                    {
                        if(milkDragView.isClickOnFridge((int) x, (int) y))
                        {
                            milkDragView.setFridgeState(true);
                        }
                    }
                    break;
            }
                return true;
        }
    }

    private void startMediaService()
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Three%20Phase%20Instruction%20Versions%2FMyRec_0526_1313%D7%94%D7%95%D7%A8%D7%90%D7%94%20%D7%AA%D7%9C%D7%AA%20%D7%A9%D7%9C%D7%91%D7%99%D7%AA.mp3?alt=media&token=3c76d7dc-b264-4bdb-83fe-4ce16eaa5e44");
        getContext().startService(intent);
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
