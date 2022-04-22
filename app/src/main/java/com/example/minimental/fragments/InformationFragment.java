package com.example.minimental.fragments;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.minimental.Question;
import com.example.minimental.R;

import java.util.ArrayList;

public class  InformationFragment extends Fragment {

    ActivityResultLauncher<Intent> speechRecognizerLauncher;
    EditText currentStreetAnswerET;
    EditText currentDayAnswerET;
    EditText currentMonthAnswerET;
    EditText currentDateAnswerET;
    EditText currentYearAnswerET;
    EditText currentSeasonAnswerET;
    EditText currentCountryAnswerET;
    EditText currentCityAnswerET;
    Question currentQuestion;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speechRecognizerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                Intent data = activityResult.getData();
                StringBuffer speechResult = new StringBuffer();
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                for(String r:results)
                {
                    speechResult.append(r);
                }
                String result = speechResult.toString();
                updateAnswer(currentQuestion , result);
            }
        });

    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.information_fragment,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        currentStreetAnswerET = rootView.findViewById(R.id.input_streetET);
        currentDayAnswerET = rootView.findViewById(R.id.input_dayET);
        currentMonthAnswerET = rootView.findViewById(R.id.input_monthET);
        currentDateAnswerET = rootView.findViewById(R.id.input_dateET);
        currentYearAnswerET = rootView.findViewById(R.id.input_yearET);
        currentSeasonAnswerET = rootView.findViewById(R.id.input_seasonET);
        currentCountryAnswerET = rootView.findViewById(R.id.input_countryET);
        currentCityAnswerET = rootView.findViewById(R.id.input_cityET);

        ImageView dayMicImageView = rootView.findViewById(R.id.day_mic_ImageView);
        ImageView monthMicImageView = rootView.findViewById(R.id.month_mic_image_view);
        ImageView dateMicImageView = rootView.findViewById(R.id.date_mic_image_view);
        ImageView yearMicImageView = rootView.findViewById(R.id.year_mic_image_view);
        ImageView seasonMicImageView = rootView.findViewById(R.id.season_mic_image_view);
        ImageView countryMicImageView = rootView.findViewById(R.id.country_mic_image_view);
        ImageView cityMicImageView = rootView.findViewById(R.id.city_mic_image_view);
        ImageView streetMicImageView = rootView.findViewById(R.id.street_mic_image_view);

        dayMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.Day));
        monthMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.Month));
        dateMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.Date));
        yearMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.Year));
        seasonMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.Season));
        countryMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.Country));
        cityMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.City));
        streetMicImageView.setOnClickListener(new informationAnswerSpeechClickListner(Question.Street));

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        private informationAnswerSpeechClickListner(Question question)
        {
            currentQuestion = question;
        }
        @Override
        public void onClick(View view) {
            startSpeechRecognition();
        }
    }

    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , "en");
        speechRecognizerLauncher.launch(speechIntent);
    }
    private void updateAnswer(Question question , String result)
    {
        switch(question)
        {
            case Day:
                currentDayAnswerET.setText(result);
                break;
            case Month:
                currentMonthAnswerET.setText(result);
                break;
            case Date:
                currentDateAnswerET.setText(result);
                break;
            case Year:
                currentYearAnswerET.setText(result);
                break;
            case Season:
                currentSeasonAnswerET.setText(result);
                break;
            case Country:
                currentSeasonAnswerET.setText(result);
                break;
            case City:
                currentCityAnswerET.setText(result);
                break;
            case Street:
                currentStreetAnswerET.setText(result);
                break;
        }
    }

    enum Question
    {
        Day,
        Month,
        Date,
        Year,
        Season,
        Country,
        City,
        Street
    }

}

