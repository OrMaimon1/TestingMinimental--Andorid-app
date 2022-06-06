package com.example.minimental.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.Painter;
import com.example.minimental.R;
import com.example.minimental.Services.MediaPlayerService;
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
        ImageButton speakerButton = rootView.findViewById(R.id.tenth_question_speaker);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        painter = rootView.findViewById(R.id.painter_view);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        painter.init(metrics);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speakerButton.startAnimation(animation);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
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
                Boolean permission = Boolean.FALSE;
                //sharedViewModel.setPermission(permission);
            }
        });

        speakerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakerButton.clearAnimation();
                startMediaService();
            }
        });

        return rootView;
    }

    private void startMediaService()
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0526_1317%D7%94%D7%A2%D7%AA%D7%A7%D7%AA%20%D7%A6%D7%95%D7%A8%D7%94.mp3?alt=media&token=3d8d3aa1-34e5-4b3b-b6ba-0fb734383502");
        getContext().startService(intent);
    }




}
