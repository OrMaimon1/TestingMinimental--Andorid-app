package com.example.minimental;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    //private MainActivityViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        //viewModel.getUserName().observe(this,);
        //viewModel.getMissingDetail().observe(this,missingDetail -> );
        //NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

    }

}