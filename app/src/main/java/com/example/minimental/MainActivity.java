package com.example.minimental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}