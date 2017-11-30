package com.example.gleative.workit;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gleative.workit.fragments.CreateCustomExerciseFragment;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

public class CreateCustomExerciseActivity extends AppCompatActivity {

    CreateCustomExerciseFragment createCustomExerciseFragment;

    Exercise selectedExercise;
    Workout workout;

    ImageView exercisePicture1, exercisePicture2;
    EditText sets, reps;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_exercise);


        toolbar = findViewById(R.id.toolbar_execise_name);
        setSupportActionBar(toolbar);

        selectedExercise = getIntent().getParcelableExtra("exercise"); // Exercise object
        workout = getIntent().getParcelableExtra("workout"); // Workout object

        // Had to use this method for it to change title on toolbar
        getSupportActionBar().setTitle(selectedExercise.getExerciseName());

        exercisePicture1 = findViewById(R.id.selected_exercise_picture1);
        exercisePicture2 = findViewById(R.id.selected_exercise_picture2);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);

        FragmentManager fragmentManager = getSupportFragmentManager();
        createCustomExerciseFragment = (CreateCustomExerciseFragment) fragmentManager.findFragmentById(R.id.create_custom_exercise_fragment);

        // Displays the exercise information on the layout
        createCustomExerciseFragment.setDisplayedDetail(selectedExercise);

    }

    // Adds the custom exercise to the created workout, unless the fields are empty.
    public void addCustomExerciseToWorkout(View view) {
        createCustomExerciseFragment.addCustomExerciseToWorkout(workout);
    }
}
