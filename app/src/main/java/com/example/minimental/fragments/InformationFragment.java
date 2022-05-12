package com.example.minimental.fragments;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.MainActivity;
import com.example.minimental.Question;
import com.example.minimental.R;
import com.example.minimental.Services.MediaPlayerService;
import com.example.minimental.ViewModels.SharedViewModel;
import com.example.minimental.informationQuestion;

import java.util.ArrayList;

public class  InformationFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private EditText currentStreetAnswerET;
    private EditText currentDayAnswerET;
    private EditText currentMonthAnswerET;
    private EditText currentDateAnswerET;
    private EditText currentYearAnswerET;
    private EditText currentSeasonAnswerET;
    private EditText currentCountryAnswerET;
    private EditText currentCityAnswerET;
    private EditText currentFloorAnswerET;
    private EditText currentAreaAnswerET;
    private ImageView currentQuestionAnswered;
    private informationQuestion informationQuestion = new informationQuestion();
    Context mainActivity;


    //Question currentQuestion;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    updateAnswer(result);
                }
            }
        });

    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.information_fragment,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        currentStreetAnswerET = rootView.findViewById(R.id.input_streetET);
        currentDayAnswerET = rootView.findViewById(R.id.input_dayET);
        currentMonthAnswerET = rootView.findViewById(R.id.input_monthET);
        currentDateAnswerET = rootView.findViewById(R.id.input_dateET);
        currentYearAnswerET = rootView.findViewById(R.id.input_yearET);
        currentSeasonAnswerET = rootView.findViewById(R.id.input_seasonET);
        currentCountryAnswerET = rootView.findViewById(R.id.input_countryET);
        currentCityAnswerET = rootView.findViewById(R.id.input_cityET);
        currentFloorAnswerET = rootView.findViewById(R.id.input_floorET);
        currentAreaAnswerET = rootView.findViewById(R.id.input_areaET);

        ImageView dayMicImageView = rootView.findViewById(R.id.day_mic_ImageView);
        ImageView monthMicImageView = rootView.findViewById(R.id.month_mic_image_view);
        ImageView dateMicImageView = rootView.findViewById(R.id.date_mic_image_view);
        ImageView yearMicImageView = rootView.findViewById(R.id.year_mic_image_view);
        ImageView seasonMicImageView = rootView.findViewById(R.id.season_mic_image_view);
        ImageView countryMicImageView = rootView.findViewById(R.id.country_mic_image_view);
        ImageView cityMicImageView = rootView.findViewById(R.id.city_mic_image_view);
        ImageView streetMicImageView = rootView.findViewById(R.id.street_mic_image_view);
        ImageView floormicImageView = rootView.findViewById(R.id.floor_mic_image_view);
        ImageView areaMicImageView = rootView.findViewById(R.id.area_mic_ImageView);

        dayMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        monthMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        dateMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        yearMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        seasonMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        countryMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        cityMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        streetMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        floormicImageView.setOnClickListener(new informationAnswerSpeechClickListner());
        areaMicImageView.setOnClickListener(new informationAnswerSpeechClickListner());


        ImageButton speakerBtn = rootView.findViewById(R.id.audio_test_btn);
        speakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Minimental%20test%20recording.mp4?alt=media&token=6f60dae1-3eff-42bb-9203-08e240e89c20";
                Intent intent = new Intent(getContext(), MediaPlayerService.class);
                intent.putExtra("Link" , link);
                getActivity().startService(intent);
            }
        });

        sharedViewModel.getInfoLiveData().observe(getViewLifecycleOwner(), new Observer<informationQuestion>() {
            @Override
            public void onChanged(informationQuestion informationQuestion) {
                //currentDayAnswerET.getText(informationQuestion.getDay().toString());
                //textInputUsername.getEditText().setText(s);
            }
        });



        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informationQuestion.setDay(currentDayAnswerET.getText().toString());
                informationQuestion.setMonth(currentMonthAnswerET.getText().toString());
                informationQuestion.setDate(currentDateAnswerET.getText().toString());
                informationQuestion.setYear(currentYearAnswerET.getText().toString());
                informationQuestion.setSeason(currentSeasonAnswerET.getText().toString());
                informationQuestion.setCountry(currentCountryAnswerET.getText().toString());
                informationQuestion.setCity(currentCityAnswerET.getText().toString());
                informationQuestion.setAddress(currentStreetAnswerET.getText().toString());
                informationQuestion.setFloor(currentFloorAnswerET.getText().toString());
                informationQuestion.setArea(currentAreaAnswerET.getText().toString());
                sharedViewModel.setInfoLiveData(informationQuestion);
                Navigation.findNavController(view).navigate(R.id.action_informationFragment_to_secondQuestion);
            }
        });
        return rootView;
    }


    /*
    Custom Click Listner
     */
    private class informationAnswerSpeechClickListner implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            currentQuestionAnswered = (ImageView) view;
            startSpeechRecognition();
        }
    }

    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechRecognizerLauncher.launch(speechIntent);
    }
    private void updateAnswer(String result)
    {
        switch(currentQuestionAnswered.getId())
        {
            case R.id.day_mic_ImageView:
                currentDayAnswerET.setText(result);
                break;
            case R.id.month_mic_image_view:
                currentMonthAnswerET.setText(result);
                break;
            case R.id.date_mic_image_view:
                currentDateAnswerET.setText(result);
                break;
            case R.id.year_mic_image_view:
                currentYearAnswerET.setText(result);
                break;
            case R.id.season_mic_image_view:
                currentSeasonAnswerET.setText(result);
                break;
            case R.id.country_mic_image_view:
                currentCountryAnswerET.setText(result);
                break;
            case R.id.city_mic_image_view:
                currentCityAnswerET.setText(result);
                break;
            case R.id.street_mic_image_view:
                currentStreetAnswerET.setText(result);
                break;
            case R.id.floor_mic_image_view:
                currentFloorAnswerET.setText(result);
                break;
            case R.id.area_mic_ImageView:
                currentAreaAnswerET.setText(result);
                break;
        }
    }

    /*enum Question
    {
        Day,
        Month,
        Date,
        Year,
        Season,
        Country,
        City,
        Street
    }*/

}

