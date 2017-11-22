package com.example.gleative.workit;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gleative.workit.fragments.CreateCustomExerciseFragment;
import com.example.gleative.workit.fragments.ExerciseInfoFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCustomExerciseActivity extends AppCompatActivity {

    DatabaseReference dbReference;
    CreateCustomExerciseFragment createCustomExerciseFragment;

    Exercise selectedExercise;
    Workout workout;
    CustomExercise customExercise;

    ImageView exercisePicture1;
    ImageView exercisePicture2;
    EditText sets;
    EditText reps;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_exercise);


        toolbar = (Toolbar) findViewById(R.id.toolbar_execise_name);
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

    // Adds the custome exercise to the created workout, unless the fields are empty.
    public void addCustomExerciseToWorkout(View view) {
        createCustomExerciseFragment.addCustomExerciseToWorkout(workout);
    }
}
