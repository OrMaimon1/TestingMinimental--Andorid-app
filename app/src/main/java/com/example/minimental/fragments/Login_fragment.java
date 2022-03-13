package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.MainActivityViewModel;
import com.example.minimental.R;
import com.google.android.material.textfield.TextInputLayout;

public class Login_fragment extends Fragment {

    private MainActivityViewModel viewModel;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment,container,false);
        //where to put the viewmodel of the fragment
        textInputUsername = rootView.findViewById(R.id.input_name);
        textInputPassword = rootView.findViewById(R.id.input_name);
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        viewModel.getUserName().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence s) {
                textInputUsername.getEditText().setText(s);
            }
        });
        Button nxtBtn = rootView.findViewById(R.id.next_Btn);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setUserName(textInputUsername.getEditText().getText().toString());
                viewModel.setPassword(textInputPassword.getEditText().getText().toString());
                Navigation.findNavController(view).navigate(R.id.action_login_fragment_to_collectMissingDetails);
            }
        });
        return rootView;
    }

}
