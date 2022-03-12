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
import androidx.navigation.Navigation;

import com.example.minimental.R;
import com.google.android.material.textfield.TextInputLayout;

public class CollectMissingDetails extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collect_missingdetails,container,false);

        EditText country = rootView.findViewById(R.id.Et_country);
        EditText city = rootView.findViewById(R.id.Et_city);
        EditText address = rootView.findViewById(R.id.Et_address);
        EditText floor = rootView.findViewById(R.id.Et_floor);
        EditText area = rootView.findViewById(R.id.Et_area);

        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_collectMissingDetails_to_secondQuestion);
            }
        });
        return rootView;
    }
}
