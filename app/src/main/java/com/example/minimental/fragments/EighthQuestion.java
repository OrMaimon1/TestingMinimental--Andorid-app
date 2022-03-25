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
import androidx.navigation.Navigation;

import com.example.minimental.R;

public class EighthQuestion extends Fragment {
    
    View blackCircle, yellowCircle, redSquare, blueCircle;
    float x,y;
    float dx , dy;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eighth_question,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);


        blackCircle = (View) rootView.findViewById(R.id.viewblackcircle);
        yellowCircle = (View) rootView.findViewById(R.id.viewYellowCircle);
        redSquare = (View) rootView.findViewById(R.id.square_view);
        blueCircle = (View) rootView.findViewById(R.id.circle_view);

        blackCircle.setOnTouchListener(new MyTouchListener());
        yellowCircle.setOnTouchListener(new MyTouchListener());

       // redSquare.setOnDragListener(new MyDragListener());
        //blueCircle.setOnDragListener(new MyDragListener());






       // nxtBtn.setOnClickListener(new View.OnClickListener() {

         //   @Override
           // public void onClick(View view) {

             //   Navigation.findNavController(view).navigate(R.id.action_eighthQuestion_to_ninthQuestion);

            //}

        //});

        return rootView ;

    }


    final class MyTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                x = motionEvent.getX();
                y = motionEvent.getY();
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE)
            {
                dx = motionEvent.getX() - x;
                dy = motionEvent.getY() - y;

                view.setX(view.getX() + dx);
                view.setY(view.getY() + dy);

                x = motionEvent.getX();
                y = motionEvent.getY();
            }
            return true;
        }
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
