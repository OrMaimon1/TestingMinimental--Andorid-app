package com.example.minimental.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import com.example.minimental.R;
import com.example.minimental.Services.MediaPlayerService;
import com.example.minimental.Services.MediaPlayerServiceBinder;
import com.example.minimental.ViewModels.SharedViewModel;
import com.example.minimental.informationQuestion;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import com.google.android.material.textfield.TextInputLayout;
import com.google.type.DateTime;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;


public class  InformationFragment extends Fragment implements MediaPlayerServiceBinder {

    private SharedViewModel sharedViewModel;
    ActivityResultLauncher<Intent> speechRecognizerLauncher;
    AutoCompleteTextView autoCompleteTextView;
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
    private ImageView currentQuestionHeared;
    private MediaPlayerService mediaPlayerService = new MediaPlayerService();
    private Hashtable speakerAndLinkMap = new Hashtable<ImageView , String>();
    private informationQuestion informationQuestion = new informationQuestion();
    private DateTime dateTime;
    private boolean mediaIsPlaying = false;
    Context mainActivity;
    private TextInputLayout address_hint;
    private TextInputLayout floor_hint;

    ImageView daySpeakerImageView;
    ImageView monthSpeakerImageView;
    ImageView dateSpeakerImageView;
    ImageView yearSpeakerImageView;
    ImageView seasonSpeakerImageView;
    ImageView countrySpeakerImageView;
    ImageView citySpeakerImageView;
    ImageView streetSpeakerImageView;
    ImageView floorSpeakerImageView;
    ImageView areaSpeakerImageView;
    ImageView[] speakerButtons;






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
        address_hint = rootView.findViewById(R.id.street_quetion);
        floor_hint = rootView.findViewById(R.id.floor_quetion);


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

        daySpeakerImageView = rootView.findViewById(R.id.day_speaker);
        monthSpeakerImageView = rootView.findViewById(R.id.month_speaker);
        dateSpeakerImageView = rootView.findViewById(R.id.date_speaker);
        yearSpeakerImageView= rootView.findViewById(R.id.year_speaker);
        seasonSpeakerImageView = rootView.findViewById(R.id.season_speaker);
        countrySpeakerImageView = rootView.findViewById(R.id.country_speaker);
        citySpeakerImageView = rootView.findViewById(R.id.city_speaker);
        streetSpeakerImageView = rootView.findViewById(R.id.street_speaker);
        floorSpeakerImageView = rootView.findViewById(R.id.floor_speaker);
        areaSpeakerImageView = rootView.findViewById(R.id.area_speaker);
        speakerButtons = new ImageView[]{daySpeakerImageView , monthSpeakerImageView , dateSpeakerImageView , yearSpeakerImageView , seasonSpeakerImageView,
                countrySpeakerImageView , citySpeakerImageView , streetSpeakerImageView , floorSpeakerImageView , areaSpeakerImageView};
        initializeSpeakerLink();

        if (sharedViewModel.getMissingDetailMutableLiveData().getValue().isIs_in_hospital()){
            address_hint.setHint(R.string.hospital_address);
            floor_hint.setHint(R.string.hospital_floor);
        }
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

        daySpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        monthSpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        dateSpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        yearSpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        seasonSpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        countrySpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        citySpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        streetSpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        floorSpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());
        areaSpeakerImageView.setOnClickListener(new informationQuestionSpeakerClickListener());

        //number 1 -dropdown
        autoCompleteTextView = rootView.findViewById(R.id.input_areaET);
        ArrayList<String> Areas = new ArrayList<String>();
        Areas.add(getString(R.string.center));
        Areas.add(getString(R.string.north));
        Areas.add(getString(R.string.south));
        Areas.add(getString(R.string.west));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.location_item, Areas);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String area = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), " " + area, Toast.LENGTH_SHORT).show();
            }
        });

        /*ImageButton speakerBtn = rootView.findViewById(R.id.audio_test_btn);
        speakerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Minimental%20test%20recording.mp4?alt=media&token=6f60dae1-3eff-42bb-9203-08e240e89c20";
                Intent intent = new Intent(getContext(), MediaPlayerService.class);
                intent.putExtra("Link" , link);
                getActivity().startService(intent);
            }
        });*/



        new TapTargetSequence((Activity) rootView.getContext())
                .targets(
                        TapTarget.forView(daySpeakerImageView,getContext().getString(R.string.speaker_button_title), getContext().getString(R.string.speaker_button_desc))
                                .outerCircleColor(R.color.teal_200)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(20)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.black)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .cancelable(false)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(60),
                        TapTarget.forView(dayMicImageView, getContext().getString(R.string.mic_button_title), getContext().getString(R.string.mic_button_desc))
                                .outerCircleColor(R.color.teal_200)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(20)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.black)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .cancelable(false)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(60)).listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                Toast.makeText(getContext(),"Sequence Finished",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                Toast.makeText(getContext(),"GREAT!",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {

            }
        }).start();






        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
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
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                String datetime = dateformat.format(c.getTime());
                sharedViewModel.setDateFirst(datetime);
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

    private class informationQuestionSpeakerClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            currentQuestionHeared = (ImageView) view;
            disableAllButtons();
            if(!mediaIsPlaying) {
                mediaIsPlaying = true;
                startSpeakerService();
                mediaIsPlaying =false;
            }
        }
    }

    private void disableAllButtons()
    {
        for(ImageView button : speakerButtons )
        {
            button.setEnabled(false);
        }
    }

    private void enableAllButtons()
    {
        for(ImageView button : speakerButtons)
        {
            button.setEnabled(true);
        }
    }

    @Override
    public void startSpeechButtonAnimation() {
        enableAllButtons();
    }

    private void startSpeechRecognition()
    {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_RESULTS , 1);
        speechRecognizerLauncher.launch(speechIntent);
    }

    private void startSpeakerService()
    {
        Intent speakerServiceIntent = new Intent(getContext() , MediaPlayerService.class);
        MediaPlayerService.currentFragment = this;
        speakerServiceIntent.putExtra("Link" , (String)speakerAndLinkMap.get(currentQuestionHeared));
        getContext().startService(speakerServiceIntent);
    }

    private void initializeSpeakerLink()
    {
        speakerAndLinkMap.put( daySpeakerImageView, "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0914%D7%99%D7%95%D7%9D.mp3?alt=media&token=f45afd45-4013-4d3f-889f-8d0fb006ac3e");
        speakerAndLinkMap.put(monthSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0915%D7%97%D7%95%D7%93%D7%A9.mp3?alt=media&token=062b1fcf-e165-4cbe-93b8-ec7b0860edc5");
        speakerAndLinkMap.put(seasonSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0916%D7%A2%D7%95%D7%A0%D7%94.mp3?alt=media&token=ed477063-f21b-44d4-b446-1dea2f6e44a4");
        speakerAndLinkMap.put(yearSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0916%D7%A9%D7%A0%D7%94.mp3?alt=media&token=ae1933ea-c549-44d0-a1bc-4ceb85017aba");
        speakerAndLinkMap.put(dateSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0916%D7%AA%D7%90%D7%A8%D7%99%D7%9A.mp3?alt=media&token=47502b0b-7015-47e9-bc06-e8f68aa20e71");
        speakerAndLinkMap.put(countrySpeakerImageView, "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0921%D7%9E%D7%93%D7%99%D7%A0%D7%94.mp3?alt=media&token=68b437d5-1c57-4aad-8f2e-ac4ef9ae8d56");
        speakerAndLinkMap.put(citySpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0921%D7%A2%D7%99%D7%A8.mp3?alt=media&token=be0eb38d-da28-4e94-9d5b-903bb6e244bc");
        speakerAndLinkMap.put(streetSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0922%D7%A8%D7%97%D7%95%D7%91.mp3?alt=media&token=707123e4-7049-47b9-9a8f-4ecafa2d1334");
        speakerAndLinkMap.put(floorSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0923%D7%A7%D7%95%D7%9E%D7%94.mp3?alt=media&token=625420a0-643a-4fae-8e31-d51abfd7d946");
        speakerAndLinkMap.put(areaSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/MiniMental%20Location%20Home%20Version%2FMyRec_0522_0924%D7%90%D7%99%D7%96%D7%95%D7%A8.mp3?alt=media&token=20d9505e-cf0d-4a55-8876-d7932c2641aa");
        if (sharedViewModel.getMissingDetailMutableLiveData().getValue().isIs_in_hospital()){
            speakerAndLinkMap.put(streetSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Three%20Phase%20Instruction%20Versions%2FLocation%20Hospital%20Version%2FMyRec_0522_0935%D7%9E%D7%A7%D7%95%D7%9D%20%D7%91%D7%99%D7%97.mp3?alt=media&token=1707f782-40d1-4c18-a79f-1ef0d58568e9");
            speakerAndLinkMap.put(floorSpeakerImageView , "https://firebasestorage.googleapis.com/v0/b/minimental-hit.appspot.com/o/Three%20Phase%20Instruction%20Versions%2FLocation%20Hospital%20Version%2FMyRec_0522_0937%D7%A7%D7%95%D7%9E%D7%94%20%D7%91%D7%99%D7%97.mp3?alt=media&token=bc46f104-bc76-4d16-9142-7ff9b4707b87");
        }
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

