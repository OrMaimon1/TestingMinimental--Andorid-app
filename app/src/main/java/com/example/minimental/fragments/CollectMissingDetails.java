package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.MainActivityViewModel;
import com.example.minimental.MissingDetail;
import com.example.minimental.R;
import com.example.minimental.ViewModels.SharedViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class CollectMissingDetails extends Fragment {

    private SharedViewModel sharedViewModel;
    private MissingDetail missingDetail = new MissingDetail();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collect_missingdetails,container,false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        EditText country = rootView.findViewById(R.id.input_country_missing_ET);
        EditText city = rootView.findViewById(R.id.input_city_missing_ET);
        EditText address = rootView.findViewById(R.id.input_address_missing_ET);
        EditText floor = rootView.findViewById(R.id.input_floor_misssingET);
        EditText area = rootView.findViewById(R.id.input_area_missing_ET);

//country.getText().toString(),city.getText().toString(),address.getText().toString(),
//                        floor.getText().toString(),area.getText().toString()
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
}
