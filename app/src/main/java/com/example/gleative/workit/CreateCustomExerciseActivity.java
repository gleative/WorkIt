package com.example.gleative.workit;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.gleative.workit.fragments.CreateCustomExerciseFragment;
import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class CreateCustomExerciseActivity extends AppCompatActivity {

    Exercise selectedExercise;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedExercise = getIntent().getParcelableExtra("exercise");

        FragmentManager fragmentManager = getSupportFragmentManager();
        CreateCustomExerciseFragment createCustomExerciseFragment = (CreateCustomExerciseFragment) fragmentManager.findFragmentById(R.id.create_custom_exercise_fragment);

        // Displays the exercise information on the layout
        createCustomExerciseFragment.setDisplayedDetail(selectedExercise);

    }
}
