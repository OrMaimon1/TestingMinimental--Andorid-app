package com.example.minimental.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.minimental.Question;
import com.example.minimental.R;

public class  InformationFragment extends Fragment {

    public static InformationFragment newInstance(int questionNumber)
    {
        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("questionNumber" , questionNumber);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.information_fragment,container,false);
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_informationFragment_to_secondQuestion);
            }
        });
        return rootView;
    }
}

