package com.example.minimental.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.minimental.Services.MediaPlayerService;
import com.example.minimental.ViewModels.SharedViewModel;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FifthFragment extends Fragment {
    public SharedViewModel sharedViewModel;
    private ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private Observer<String> getFirstItemDescription;
    private TextView text;
    private EditText firstPicET;
    private EditText secondPicET;
    private ImageView firstImage;
    private Bitmap firstImageBitmap;
    private FifthQuestion fifthQuestion = new FifthQuestion();
    private Thread imageProcessThread;
    private EditText currentPictureDescribedEditText;
    private String pic1;
    private String pic2;


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
                    currentPictureDescribedEditText.setText(result);
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

        //fifthQuestion = sharedViewModel.getFifthQuestionLiveData().getValue();
        firstPicET = rootView.findViewById(R.id.input_FirstPicET);
        secondPicET = rootView.findViewById(R.id.input_SecondPicET);
        firstImage = rootView.findViewById(R.id.first_Image);
        try {
            imageProcessThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        firstImage.setImageBitmap(firstImageBitmap);
        text = rootView.findViewById(R.id.input_FirstPicET);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Integer Version = sharedViewModel.getVersion().getValue();
        if (Version == null) //only for now some users dosnt have version alredy asked to add
        {
            Version = 1;
        }
        if (Version == 1){ // for pic
            pic1 = "";
            pic2 = "";
        }
        if (Version == 2){
            pic1 = "";
            pic2 = "";
        }
        if (Version == 3){
            pic1 = "";
            pic2 = "";
        }
        if (Version == 4){
            pic1 = "";
            pic2 = "";
        }

/*        getFirstItemDescription = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text.setText(s);
            }
        };
        sharedViewModel.getFirstItemDescription().observe(getViewLifecycleOwner(),getFirstItemDescription);*/


        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        ImageButton recordBtnForImageOne = rootView.findViewById(R.id.pict1_mic_image_view);
        ImageButton recordButtonForImageTwo = rootView.findViewById(R.id.pict2_mic_image_view);
        Button speakerButton = rootView.findViewById(R.id.describe_instructions_speaker);
        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        speakerButton.startAnimation(animation);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                fifthQuestion.setFirstpic(firstPicET.getText().toString());
                fifthQuestion.setSecoundpic(secondPicET.getText().toString());
                sharedViewModel.setFifthQuestionLiveData(fifthQuestion);
                Navigation.findNavController(view).navigate(R.id.action_fifthQuestion_to_sixth_question);
            }
        });
        recordBtnForImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPictureDescribedEditText = firstPicET;
                startSpeechRecognition();
            }
        });
        recordButtonForImageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPictureDescribedEditText = secondPicET;
                startSpeechRecognition();
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
    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechRecognizerLauncher.launch(speechIntent);
    }

    private void startMediaService()
    {
        Intent intent = new Intent(getContext() , MediaPlayerService.class);
        intent.putExtra("Link" , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Questions%20Instructions%2FMyRec_0525_0925%D7%94%D7%95%D7%A8%D7%90%D7%AA%20%D7%A9%D7%99%D7%95%D7%9D.mp3?alt=media&token=b9d3dd38-a837-4708-b00b-2125faad4548");
        getContext().startService(intent);
    }

}
