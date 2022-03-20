package com.example.minimental.fragments;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.minimental.R;

public class EighthQuestion extends Fragment {
    
    View blackCircle, yellowCircle, redSquare, blueCircle;


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

        blackCircle.setOnLongClickListener(longclickListener);
        yellowCircle.setOnLongClickListener(longclickListener);

        redSquare.setOnDragListener(dragListener);
        blueCircle.setOnDragListener(dragListener);


        nxtBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_eighthQuestion_to_ninthQuestion);

            }

        });

        return rootView ;
    }
    View.OnLongClickListener longclickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data= ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowbuilder = new View.DragShadowBuilder(v);
            v.startDrag(data,myShadowbuilder,v,0);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent event) {

            int dragEvent = event.getAction();

            switch (dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    final  View view1 = (View) event.getLocalState();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    view.animate()
                            .x(redSquare.getX())
                            .y(redSquare.getY())
                            .x(blueCircle.getX())
                            .y(blueCircle.getY())
                            .setDuration(700)
                            .start();
                    break;

            }

            return true;
        }
    };
}
