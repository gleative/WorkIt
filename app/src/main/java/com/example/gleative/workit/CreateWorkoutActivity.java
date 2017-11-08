package com.example.gleative.workit;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateWorkoutActivity extends AppCompatActivity implements ExerciseListFragment.OnExerciseFragmentInteractionListener{

    DatabaseReference dbReference;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    Workout workout;
    EditText workoutNameText;
    EditText workoutDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
//        CreateWorkoutFragment createWorkoutFragment = (CreateWorkoutFragment) fragmentManager.findFragmentById(R.id.create_workout_fragment);
        // So we can access its methods
        ExerciseListFragment exerciseListFragment = (ExerciseListFragment) getSupportFragmentManager().findFragmentById(R.id.exercise_list_fragment);

        // Send with parameter 1 so it uses the exercise layout where the user can add a exercise
//        exerciseListFragment.useAddExerciseLayout(1);

        // Finds the EditText views
        workoutNameText = findViewById(R.id.workout_name);
        workoutDescriptionText = findViewById(R.id.workout_desc);

        setUpDrawer();

    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_myWorkouts);
    }

    @Override
    protected void onStart(){
        navigationDrawerFragment.updateCheckedItem(R.id.nav_myWorkouts);

        super.onStart();
    }


    @Override
    public void onExerciseSelected(Exercise selectedExercise) {
        // Her kan du ha at den går til vinduet hvor du kan definere hvor mange sets og reps du skal ha
        Intent intent = new Intent(this, CreateCustomExerciseActivity.class);
        intent.putExtra("exercise", selectedExercise);
        startActivity(intent);
    }

    // When user presses the add button
    public void addExerciseToWorkout(View view){
        workout = createWorkout(workoutNameText.getText().toString(), workoutDescriptionText.getText().toString());

        Intent intent = new Intent(this, AddExerciseToWorkoutActivity.class);
        intent.putExtra("workout", workout);
        startActivity(intent);
    }

    // Creates the workout with name and desc, sends to database, and returns the created object
    private Workout createWorkout(String workoutName, String workoutDescription){
        dbReference = FirebaseDatabase.getInstance().getReference("workouts");

        // Creates a user node, and returns a unique key value
        String workoutID = dbReference.push().getKey();

        Workout newWorkout = new Workout(workoutName,workoutDescription);

        // Adds the given values to the database
        dbReference.child(workoutID).setValue(newWorkout);

        return newWorkout;
    }

}
