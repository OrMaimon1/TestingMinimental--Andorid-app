package com.example.minimental.fragments;

import android.content.Intent;
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

import com.example.minimental.MissingDetail;
import com.example.minimental.R;
import com.example.minimental.ViewModels.SharedViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CollectMissingDetails extends Fragment {

    private SharedViewModel sharedViewModel;
    private MissingDetail missingDetail = new MissingDetail();
    AutoCompleteTextView autoCompleteTextView;
    ActivityResultLauncher<Intent> speechRecognizerLauncher;
    private ImageView currentQuestionAnswered;
    private EditText country;
    private EditText city;
    private EditText address;
    private EditText floor;
    private EditText area ;
    private TextInputLayout address_hint;
    private TextInputLayout floor_hint;


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
        area = rootView.findViewById(R.id.input_areamissingET);
        address_hint = rootView.findViewById(R.id.area_missing_missing_quetion);
        floor_hint = rootView.findViewById(R.id.floor_missing_quetion);
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
        missingDetail = sharedViewModel.getMissingDetailMutableLiveData().getValue();
        if (missingDetail.isIs_in_hospital()){
            address_hint.setHint(R.string.hospital_address);
            floor_hint.setHint(R.string.hospital_floor);
        }
        country.setText(missingDetail.getCountry());
        city.setText(missingDetail.getCity());
        address.setText(missingDetail.getAddress());
        floor.setText(missingDetail.getFloor());
        area.setText(missingDetail.getArea());

        //number 1 -dropdown
        autoCompleteTextView = rootView.findViewById(R.id.input_areamissingET);
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




        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                nxtBtn.startAnimation(animation);
                missingDetail.setCountry(country.getText().toString());
                missingDetail.setCity(city.getText().toString());
                missingDetail.setAddress(address.getText().toString());
                missingDetail.setFloor(floor.getText().toString());
                missingDetail.setArea(area.getText().toString());

                sharedViewModel.setMissingDetailMutableLiveData(missingDetail);
                Navigation.findNavController(view).navigate(R.id.action_collectMissingDetails_to_startTest);

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
