package com.example.gleative.workit;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class ExerciseInfoActivity extends AppCompatActivity {

    Exercise selectedExercise;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("jajajaj");
        setSupportActionBar(toolbar);

        int id = getIntent().getIntExtra("exercise_id", 1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ExerciseInfoFragment exerciseInfoFragment = (ExerciseInfoFragment) fragmentManager.findFragmentById(R.id.exercise_info_fragment);

        exerciseInfoFragment.setDisplayedDetail(id);

    }

    // Denne type vindu har ikke navigasjon drawer!

    @Override
    protected void onStart(){
        super.onStart();
    }

}
