package com.example.gleative.workit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

public class AddExerciseToWorkoutActivity extends AppCompatActivity implements  ExerciseListFragment.OnExerciseFragmentInteractionListener {

    Workout workout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Finner toolbaren i Activity
//        SJEKK MANIFEST OG PÅ EXERCISES, VI KAN DEFINERE NAVN PÅ ACTIONBAR DER!
//        toolbar.setTitle("Exercises"); // Setter title på actionbar
        setSupportActionBar(toolbar);

        // Gets the workout object
        workout = getIntent().getParcelableExtra("workout");

        Toast.makeText(this, "Add exercises to: " + workout.getWorkoutName() + workout.getWorkoutID(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onExerciseSelected(Exercise exercise) {
        Intent intent = new Intent(this, CreateCustomExerciseActivity.class);
        intent.putExtra("workout", workout);
        intent.putExtra("exercise", exercise);
        startActivity(intent);
    }
}
