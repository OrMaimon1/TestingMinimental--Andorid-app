package com.example.minimental.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

import com.example.minimental.MissingDetail;
import com.example.minimental.R;
import com.example.minimental.ViewModels.SharedViewModel;

import java.util.ArrayList;

public class CollectMissingDetails extends Fragment {

    private SharedViewModel sharedViewModel;
    private MissingDetail missingDetail = new MissingDetail();
    ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private ImageView currentQuestionAnswered;
    private EditText country;
    private EditText city;
    private EditText address;
    private EditText floor;
    private EditText area ;


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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collect_missingdetails,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        country = rootView.findViewById(R.id.input_country_missing_ET);
        city = rootView.findViewById(R.id.input_city_missing_ET);
        address = rootView.findViewById(R.id.input_address_missing_ET);
        floor = rootView.findViewById(R.id.input_floor_misssingET);
        area = rootView.findViewById(R.id.input_area_missing_ET);
        ImageView countryMicImageView = rootView.findViewById(R.id.country_missing_mic_ImageView);
        ImageView cityMicImageView = rootView.findViewById(R.id.city_missing_mic_image_view);
        ImageView streetMicImageView = rootView.findViewById(R.id.address_missing_mic_image_view);
        ImageView floormicImageView = rootView.findViewById(R.id.floor_missing_mic_image_view);
        ImageView areaMicImageView = rootView.findViewById(R.id.area_missing_mic_image_view);
        countryMicImageView.setOnClickListener(new CollectMissingDetails.MissingDetailSpeechClickListner());
        cityMicImageView.setOnClickListener(new CollectMissingDetails.MissingDetailSpeechClickListner());
        streetMicImageView.setOnClickListener(new CollectMissingDetails.MissingDetailSpeechClickListner());
        floormicImageView.setOnClickListener(new CollectMissingDetails.MissingDetailSpeechClickListner());
        areaMicImageView.setOnClickListener(new CollectMissingDetails.MissingDetailSpeechClickListner());


        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                missingDetail.setCountry(country.getText().toString());
                missingDetail.setCity(city.getText().toString());
                missingDetail.setAddress(address.getText().toString());
                missingDetail.setFloor(floor.getText().toString());
                missingDetail.setArea(area.getText().toString());

                //sharedViewModel.setMissingDetailMutableLiveData(missingDetail);
                Navigation.findNavController(view).navigate(R.id.action_collectMissingDetails_to_informationFragment);

            }
        });
        return rootView;
    }
    /*
  Custom Click Listner
   */
    private class MissingDetailSpeechClickListner implements View.OnClickListener
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
            case R.id.country_missing_mic_ImageView:
                country.setText(result);
                break;
            case R.id.city_missing_mic_image_view:
                city.setText(result);
                break;
            case R.id.address_missing_mic_image_view:
                address.setText(result);
                break;
            case R.id.floor_missing_mic_image_view:
                floor.setText(result);
                break;
            case R.id.area_missing_mic_image_view:
                area.setText(result);
                break;
        }
    }

}
