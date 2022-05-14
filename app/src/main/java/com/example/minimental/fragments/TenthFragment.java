package com.example.minimental.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.Painter;
import com.example.minimental.R;
import com.example.minimental.TenthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class TenthFragment extends Fragment {


    private SharedViewModel sharedViewModel;
    private TenthQuestion tenthQuestion = new TenthQuestion();
    //ConstraintLayout constraintlayout;
    Painter painter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tenth_question,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        ImageView imageView = rootView.findViewById(R.id.tenth_question_ImageView);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        painter = rootView.findViewById(R.id.painter_view);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        painter.init(metrics);
        //setHasOptionsMenu(true);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                painter.setDrawingCacheEnabled(true);
                Bitmap img = painter.getDrawingCache();
                tenthQuestion.setPicToCopy(img);
                //tenthQuestion.setPicToCopy(currentSentence.getText().toString());
                sharedViewModel.setpicForTenthQuestion(tenthQuestion);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                String datetime = dateformat.format(c.getTime());
                sharedViewModel.setDatelast(datetime);
                //imageView.setImageBitmap(img);
                Navigation.findNavController(view).navigate(R.id.action_tenthQuestion_to_login_fragment);
            }
        });

        return rootView;
    }




}
