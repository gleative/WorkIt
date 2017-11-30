package com.example.gleative.workit;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gleative.workit.adapter.ExercisePicturesAdapter;
import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;

public class ExerciseInfoActivity extends AppCompatActivity {

    Exercise selectedExercise;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    ExercisePicturesAdapter exercisePicturesAdapter;
    ExerciseInfoFragment exerciseInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Gets the exercise object, by getting the key name og the value
        selectedExercise = getIntent().getParcelableExtra("exercise");

        FragmentManager fragmentManager = getSupportFragmentManager();
        exerciseInfoFragment = (ExerciseInfoFragment) fragmentManager.findFragmentById(R.id.exercise_info_fragment);

        // Displays the exercise information on the layout
        exerciseInfoFragment.setDisplayedDetail(selectedExercise);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    // If exercise already starred, unstar it, else star it
    public void starrExercise(View view) {
        // Converted to string because to find the child it has to be string value
        String exerciseID = String.valueOf(selectedExercise.getExerciseID());

        if(selectedExercise.getStarred().equals("1")){
            selectedExercise.setStarred("0"); // So object created in app is also updated
            exerciseInfoFragment.starrExercise(exerciseID, "0");
        }
        else{
            selectedExercise.setStarred("1"); // So object created in app is also updated
            exerciseInfoFragment.starrExercise(exerciseID, "1");
        }
    }
}
