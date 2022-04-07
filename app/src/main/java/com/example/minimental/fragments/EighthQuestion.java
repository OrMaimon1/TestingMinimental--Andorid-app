package com.example.minimental.fragments;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.EightQuestion;
import com.example.minimental.R;
import com.example.minimental.ViewModels.SharedViewModel;

public class EighthQuestion extends Fragment {
    
    View blackCircle, yellowCircle, redSquare, blueCircle;
    private ConstraintLayout dragLayout;
    private float x = 0, y = 0;
    private SharedViewModel viewModel;
    private float blueCircleCenterX;
    private float blueCircleCenterY;
    private float redSquareCenterX;
    private float redSquareCenterY;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eighth_question,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        dragLayout = rootView.findViewById(R.id.eight_drag_layout);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);



        blackCircle = (View) rootView.findViewById(R.id.viewblackcircle);
        yellowCircle = (View) rootView.findViewById(R.id.viewYellowCircle);
        redSquare = (View) rootView.findViewById(R.id.square_view);
        blueCircle = (View) rootView.findViewById(R.id.circle_view);

        redSquareCenterX = redSquare.getX();
        redSquareCenterY = redSquare.getY();
        blueCircleCenterX = blueCircle.getX();
        blueCircleCenterY = blueCircle.getY();

        blackCircle.setOnTouchListener(new MyTouchListener());
        yellowCircle.setOnTouchListener(new MyTouchListener());

       // redSquare.setOnDragListener(new MyDragListener());
        //blueCircle.setOnDragListener(new MyDragListener());






       nxtBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_eighthQuestion_to_tenthQuestion);

            }

        });

        return rootView ;

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
                    ConstraintLayout.LayoutParams layout =  (ConstraintLayout.LayoutParams) view.getLayoutParams();
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(dragLayout.getLayoutParams());


                    float movedX, movedY;
                    movedX = event.getX();
                    movedY = event.getY();

                    boolean inBounds = true;
                    if(movedX < layoutParams.LEFT)
                    {
                        inBounds = false;
                    }
                    else if(movedX < layoutParams.RIGHT)
                    {
                        inBounds = false;
                    }
                    else if(movedY < layoutParams.TOP)
                    {
                        inBounds = false;
                    }
                    else if( movedY < layoutParams.BOTTOM)
                    {
                        inBounds = false;
                    }

                    if( inBounds )
                    {
                        float distanceX = movedX - x;
                        float distanceY = movedY - y;
                        view.setX(view.getX() + distanceX);
                        view.setY(view.getY() + distanceY);
                        view.setLayoutParams(layout);
                    }

                    if(blackCirecleInPlace(blackCircle.getX() , blackCircle.getY()))
                    {
                        //Toast.makeText(getActivity(), "balck circle in place", Toast.LENGTH_SHORT).show();
                        System.out.println("success");
                    }


                   break;
                case MotionEvent.ACTION_UP:
                    viewModel.setBlackBallLocationX(blackCircle.getX());
                    viewModel.setBlackBallLocationY(blackCircle.getY());
                    viewModel.setYellowBallLocationX(blackCircle.getX());
                    viewModel.setYellowBallLocationX(blackCircle.getY());

                    if(blackCirecleInPlace(blackCircle.getX() , blackCircle.getY()))
                    {
                        //Toast.makeText(getActivity(), "balck circle in place", Toast.LENGTH_SHORT).show();
                        System.out.println("success");
                    }
                   /* if(yellowCircleInPlace())
                    {

                    }*/
            }
            return true;
        }
    }

    public boolean blackCirecleInPlace(float locationX , float locationY)
    {
        boolean inPlace = true;
        int[] location = new int[2];
        blueCircle.getLocationOnScreen(location);
        int height = blueCircle.getBottom() - blueCircle.getTop();
        int radius = height / 2;
        int blueCircleX = location[0];
        int blueCircleY = location[1];
        if(!((blackCircle.getY() < blueCircleY + radius) && (blackCircle.getY() > blueCircleY - radius)) )
        {
            inPlace = false;
        }
        if(! ( (blackCircle.getX() > blueCircleX -radius) && (blackCircle.getX() < blueCircleX + radius) ))
        {
            inPlace = false;
        }
        return inPlace;
    }

    /*class MyDragListener implements View.OnDragListener {
        //Drawable enterShape = getResources().getDrawable(R.drawable.square_shape);
        //Drawable normalShape = getResources().getDrawable(R.drawable.square_shape);
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.invalidate();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.invalidate();
                    //v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    View owner = (View) view.getParent();
                    //owner.removeView(view);
                    //ConstraintLayout container = (ConstraintLayout) view;
                    //container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.invalidate();
                default:
                    break;
            }
            return true;
        }
    }*/
}
