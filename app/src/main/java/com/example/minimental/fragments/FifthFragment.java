package com.example.minimental.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.FifthQuestion;
import com.example.minimental.R;
import com.example.minimental.ViewModels.SharedViewModel;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FifthFragment extends Fragment {
    public SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getFirstItemDescription;
    private TextView text;
    private EditText currentFirstPicET;
    private EditText currentSecondPicET;
    private ImageView firstImage;
    private Bitmap firstImageBitmap;
    private FifthQuestion fifthQuestion = new FifthQuestion();
    private Thread imageProcessThread;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageProcessThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Minimental%20test%20image%201.jpg?alt=media&token=df0aa36e-4b89-4914-ac9e-e236dcd9a91a");
                    firstImageBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        imageProcessThread.start();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        speechRecognizerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                Intent data = activityResult.getData();
                StringBuffer speechResult = new StringBuffer();
                if (data != null) {
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    for (String r : results) {
                        speechResult.append(r);
                    }
                    String result = speechResult.toString();
                    //sharedViewModel.setFirstItemDescription(result);
                }
            }
        });
        /*getAnswerObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                resultText.setText(s);
            }
        };
        sharedViewModel.getMathAnswerGiven().observe(this , getAnswerObserver);*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fifth_question,container,false);

        currentFirstPicET = rootView.findViewById(R.id.input_FirstPicET);
        currentSecondPicET = rootView.findViewById(R.id.input_SecondPicET);
        firstImage = rootView.findViewById(R.id.first_Image);
        try {
            imageProcessThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        firstImage.setImageBitmap(firstImageBitmap);
        text = rootView.findViewById(R.id.input_FirstPicET);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

/*        getFirstItemDescription = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text.setText(s);
            }
        };
        sharedViewModel.getFirstItemDescription().observe(getViewLifecycleOwner(),getFirstItemDescription);*/


        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        ImageButton recordBtn = rootView.findViewById(R.id.pict1_mic_image_view);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fifthQuestion.setFirstpic(currentFirstPicET.getText().toString());
                fifthQuestion.setSecoundpic(currentSecondPicET.getText().toString());
                sharedViewModel.setFifthQuestionLiveData(fifthQuestion);
                Navigation.findNavController(view).navigate(R.id.action_fifthQuestion_to_sixth_question);
            }
        });
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechRecognition();
            }
        });
        return rootView;
    }
    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechRecognizerLauncher.launch(speechIntent);
    }

}
