package com.example.gleative.workit;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.WorkoutCustomExercisesListFragment;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyWorkoutInfoActivity extends AppCompatActivity implements WorkoutCustomExercisesListFragment.OnCustomExerciseFragmentInteractionListener{

    DatabaseReference dbReference;

    Workout selectedWorkout;
    WorkoutCustomExercisesListFragment workoutCustomExercisesListFragment;

    Toolbar toolbar;
    EditText workoutNameView, workoutDescView;
    Button updateWorkoutButton, cancelUpdateWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workout_info);

        selectedWorkout = getIntent().getParcelableExtra("workout");

        toolbar = findViewById(R.id.toolbar_workout_name);
        setSupportActionBar(toolbar);

        workoutNameView = findViewById(R.id.selected_workout_name);
        workoutDescView = findViewById(R.id.selected_workout_desc);
        updateWorkoutButton = findViewById(R.id.update_workout_button);
        cancelUpdateWorkoutButton = findViewById(R.id.cancel_update_workout_button);

        // Displays the update and cancel button if their is input from the user
        workoutNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // Displays the update and cancel button if their is input from the user
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!(selectedWorkout.getWorkoutName().equals(workoutNameView.getText().toString()))){
                    setVisibilityOnUpdateAndCancel(true);
                }
                else{
                    setVisibilityOnUpdateAndCancel(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Displays the update and cancel button if their is input from the user
        workoutDescView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // Displays the update and cancel button if their is input from the user
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!(selectedWorkout.getWorkoutDescription().equals(workoutDescView.getText().toString()))){
                    setVisibilityOnUpdateAndCancel(true);
                }
                else{
                    setVisibilityOnUpdateAndCancel(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        dbReference = FirebaseDatabase.getInstance().getReference().child("workouts");

        // So we can access its methods
        workoutCustomExercisesListFragment = (WorkoutCustomExercisesListFragment) getSupportFragmentManager().findFragmentById(R.id.myWorkout_info_fragment);

        setDisplayedDetail(selectedWorkout);
    }

    // Displays the information about the workout to the user
    public void setDisplayedDetail(Workout workout){
        workoutNameView.setText(workout.getWorkoutName());
        workoutDescView.setText(workout.getWorkoutDescription());
        workoutCustomExercisesListFragment.getCustomExercisesFromWorkout(workout); // Sends the custom exercises list to the adapter, so it can display workouts custom exercises
    }

    // Displays the information about the chosen customExercise
    @Override
    public void onCustomExerciseSelected(CustomExercise customExercise) {
        Exercise exercise = customExercise.getExercise();

        Intent intent = new Intent(this, ExerciseInfoActivity.class);
        intent.putExtra("exercise", exercise);
        startActivity(intent);
    }

    // Updates the workout with the new info to the database
    public void updateWorkout(View view) {
        String workoutID = selectedWorkout.getWorkoutID();

        try{
            dbReference.child(workoutID).child("workoutName").setValue(workoutNameView.getText().toString());
            dbReference.child(workoutID).child("workoutDescription").setValue(workoutDescView.getText().toString());
            Toast.makeText(this, "Workout successfully updated", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to update workout!", Toast.LENGTH_SHORT).show();
        }

        workoutNameView.clearFocus();
        workoutDescView.clearFocus();
        setVisibilityOnUpdateAndCancel(false);

        hideKeyboard(view);

    }

    // Reverts the input to the origin name and description of the workout
    public void cancelUpdateWorkout(View view) {
        workoutNameView.setText(selectedWorkout.getWorkoutName());
        workoutDescView.setText(selectedWorkout.getWorkoutDescription());

        workoutNameView.clearFocus();
        workoutDescView.clearFocus();

        hideKeyboard(view);
    }

    // Displays the two buttons if true, or else removes them from the view
    private void setVisibilityOnUpdateAndCancel(boolean value){
        if(value == true){
            updateWorkoutButton.setVisibility(View.VISIBLE);
            cancelUpdateWorkoutButton.setVisibility(View.VISIBLE);
        }
        else{
            updateWorkoutButton.setVisibility(View.GONE);
            cancelUpdateWorkoutButton.setVisibility(View.GONE);
        }
    }

    // Hides the keyboard
    private void hideKeyboard(View view){
        view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // When the user press back, they will be sendt to MyWorkoutsActivity, and not go through all the process they did when they created a workout
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyWorkoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // So MyWorkoutActivity wont overlap on eachother when user presses back
        startActivity(intent);
    }

    // When user presses the FAB button
    public void addNewCustomExerciseToWorkout(View view) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("workout", selectedWorkout);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // So MyWorkoutActivity wont overlap on eachother when user presses back
        startActivity(intent);
    }
}
