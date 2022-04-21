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
import com.google.android.material.textfield.TextInputLayout;

public class CollectMissingDetails extends Fragment {

    private MainActivityViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collect_missingdetails,container,false);

        EditText country = rootView.findViewById(R.id.Et_country);
        EditText city = rootView.findViewById(R.id.Et_city);
        EditText address = rootView.findViewById(R.id.Et_address);
        EditText floor = rootView.findViewById(R.id.Et_floor);
        EditText area = rootView.findViewById(R.id.Et_area);
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        viewModel.getMissingDetail().observe(getViewLifecycleOwner(), new Observer<MissingDetail>() {
            @Override
            public void onChanged(MissingDetail missingDetail) {

            }
        });
//country.getText().toString(),city.getText().toString(),address.getText().toString(),
//                        floor.getText().toString(),area.getText().toString()
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).popBackStack(R.id.login_fragment , false);
                //viewModel.setMissingDetail(country.getText().toString());
                Navigation.findNavController(view).navigate(R.id.action_collectMissingDetails_to_informationFragment);

            }
        });
        return rootView;
    }
}
