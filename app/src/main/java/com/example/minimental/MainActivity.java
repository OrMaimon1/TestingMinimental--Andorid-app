package com.example.minimental;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.minimental.fragments.InformationFragment;
import com.example.minimental.fragments.SecondQuestion;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

    }

    /*final String REGISTER_FRAGMNET_TAG = "register_fragment";
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    MainActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        textInputUsername = findViewById(R.id.input_name);
        textInputPassword = findViewById(R.id.input_password);
        Button Nextbtn = findViewById(R.id.next_Btn);
        Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.root_container,new SecondQuestion(),REGISTER_FRAGMNET_TAG);
                //transaction.add(R.id.root_container,new SecondQuestion(),REGISTER_FRAGMNET_TAG);
                transaction.commit();
                //getSupportFragmentManager().beginTransaction().add(R.id.root_container,new SecondQuestion(),REGISTER_FRAGMNET_TAG).commit();
            }
        });
        //Toolbar toolbar = findViewById(R.id.toolBar);
        //toolbar.setTitle("");
        //setSupportActionBar(toolbar);

        //ViewPager2 questionsPager = findViewById(R.id.questionsViewPager);
        //QuestionAdapter questionAdapter = new QuestionAdapter(getSupportFragmentManager() , this.getLifecycle());
        //questionsPager.setAdapter(questionAdapter);
    }

    private class QuestionAdapter extends FragmentStateAdapter
    {
        public QuestionAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @Override
        public Fragment createFragment(int position) {
            return InformationFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return Question.values().length;
        }
    }*/
}