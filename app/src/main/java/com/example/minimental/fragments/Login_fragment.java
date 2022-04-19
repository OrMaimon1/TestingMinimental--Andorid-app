package com.example.minimental.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minimental.MainActivityViewModel;
import com.example.minimental.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_fragment extends Fragment {

    private MainActivityViewModel viewModel;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment,container,false);
        //where to put the viewmodel of the fragment
        textInputUsername = rootView.findViewById(R.id.input_name);
        textInputPassword = rootView.findViewById(R.id.input_password);
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
                String username = textInputUsername.getEditText().getText().toString();
                String password = textInputPassword.getEditText().getText().toString();
                viewModel.setUserName(textInputUsername.getEditText().getText().toString());
                viewModel.setPassword(textInputPassword.getEditText().getText().toString());
                if (username.length() > 0 && password.length() > 0) {
                    firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Navigation.findNavController(view).navigate(R.id.action_login_fragment_to_collectMissingDetails);
                            }
                            else{
                                Toast.makeText(getContext(), "Wrong username or password try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else   {
                    Toast.makeText(getContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }


            }
        });

       // authStateListener = new FirebaseAuth.AuthStateListener() {
        //    @Override
       //     public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

         //   }
       // };

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
