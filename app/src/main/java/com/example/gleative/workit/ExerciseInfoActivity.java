package com.example.gleative.workit;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.gleative.workit.adapter.ExercisePicturesAdapter;
import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class ExerciseInfoActivity extends AppCompatActivity {

    Exercise selectedExercise;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    ExercisePicturesAdapter exercisePicturesAdapter;

//    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);




        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("jajajaj");
        setSupportActionBar(toolbar);
        // Adds back button, check manifest and under ExerciseInfoActivity tag android:parentActivityName=".ExerciseActivity", so back button works
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // But, title bar gets pushed if you use this so.... dunno

//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        setUpViewPager();

        int id = getIntent().getIntExtra("exercise_id", 1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ExerciseInfoFragment exerciseInfoFragment = (ExerciseInfoFragment) fragmentManager.findFragmentById(R.id.exercise_info_fragment);

        exerciseInfoFragment.setDisplayedDetail(id);

    }

    // Denne type vindu har ikke navigasjon drawer!

//
//    private void setUpViewPager(){
//        ExercisePicturesAdapter adapter = new ExercisePicturesAdapter(this);
//        adapter
//    }

    @Override
    protected void onStart(){
        super.onStart();
    }

}
