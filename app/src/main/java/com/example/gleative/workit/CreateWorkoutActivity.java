package com.example.gleative.workit;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateWorkoutActivity extends AppCompatActivity{

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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        workoutNameText = findViewById(R.id.workout_name);
        workoutDescriptionText = findViewById(R.id.workout_desc);

        setUpDrawer();

    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_myWorkouts);
    }

    @Override
    protected void onStart(){
        navigationDrawerFragment.updateCheckedItem(R.id.nav_myWorkouts);
        super.onStart();
    }

    // When user presses the add button
    public void addExerciseToWorkout(View view){
        // If any of the edit text's are empty
        if(workoutNameText.getText().toString().isEmpty() || workoutDescriptionText.getText().toString().isEmpty()){
            Toast.makeText(this, "You have to fill out both fields!", Toast.LENGTH_SHORT).show();
        }
        else{
            workout = createWorkout(workoutNameText.getText().toString(), workoutDescriptionText.getText().toString());

            Intent intent = new Intent(this, ExerciseActivity.class);
            intent.putExtra("workout", workout);
            startActivity(intent);
        }

    }

    // Creates the workout with name and desc, sends to database, and returns the created object
    private Workout createWorkout(String workoutName, String workoutDescription){
        try{
            dbReference = FirebaseDatabase.getInstance().getReference("workouts");

            // Creates a user node, and returns a unique key value
            String workoutID = dbReference.push().getKey();

            Workout newWorkout = new Workout(workoutName,workoutDescription);
            newWorkout.setWorkoutID(workoutID); // Adds the unique ID genereated by firebase to the workout object so can find it here

            // Adds the given values to the database
            dbReference.child(workoutID).setValue(newWorkout);

            return newWorkout;
        } catch(Exception e){
            Toast.makeText(this, "Failed to create workout.", Toast.LENGTH_SHORT).show();
        }

        return null; // Return nothing, this means it failed to create a custom exercise

    }

}
