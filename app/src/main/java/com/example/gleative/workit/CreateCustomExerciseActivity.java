package com.example.gleative.workit;

import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

    Exercise selectedExercise;
    Workout workout;
    CustomExercise customExercise;

    EditText sets;
    EditText reps;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_exercise);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);

        selectedExercise = getIntent().getParcelableExtra("exercise");
        workout = getIntent().getParcelableExtra("workout");

        FragmentManager fragmentManager = getSupportFragmentManager();
        CreateCustomExerciseFragment createCustomExerciseFragment = (CreateCustomExerciseFragment) fragmentManager.findFragmentById(R.id.create_custom_exercise_fragment);

        Toast.makeText(this, "Add " + selectedExercise.getExerciseName() + " to: " + workout.getWorkoutName() + workout.getWorkoutID(), Toast.LENGTH_SHORT).show();

        // Displays the exercise information on the layout
        createCustomExerciseFragment.setDisplayedDetail(selectedExercise);

    }


    public void addCustomExerciseToWorkout(View view) {
        customExercise = createCustomExercise(Integer.parseInt(sets.getText().toString()), Integer.parseInt(reps.getText().toString()));
    }

    private CustomExercise createCustomExercise(int sets, int reps){
        dbReference = FirebaseDatabase.getInstance().getReference("customExercises");

        // Creates a user node with random generated ID, and returns a unique key value (ID)
        String customExerciseID = dbReference.push().getKey();

        CustomExercise newCustomExercise = new CustomExercise(workout, selectedExercise, sets, reps);

        // Adds the given values to the database
        dbReference.child(customExerciseID).setValue(newCustomExercise);

        return newCustomExercise;
    }
}
