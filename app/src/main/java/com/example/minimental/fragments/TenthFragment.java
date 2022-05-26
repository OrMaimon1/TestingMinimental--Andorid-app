package com.example.minimental.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.Painter;
import com.example.minimental.R;
import com.example.minimental.TenthQuestion;
import com.example.minimental.ViewModels.SharedViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TenthFragment extends Fragment {


    private SharedViewModel sharedViewModel;
    private TenthQuestion tenthQuestion = new TenthQuestion();
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
                Navigation.findNavController(view).navigate(R.id.action_tenthQuestion_to_fileFragment);
            }
        });

        return rootView;
    }




}
