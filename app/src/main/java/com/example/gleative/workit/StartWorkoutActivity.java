package com.example.gleative.workit;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class StartWorkoutActivity extends AppCompatActivity {

    private Workout workout;
    private List<CustomExercise> exercisesList;
    private CustomExercise customExercise;
    private int currentSet = 1; // 1 because you always start at set number 1
    private int sets, reps;
    private int position = 0; // 0 So it starts at the start of the workouts list.

    TextView currentExerciseNameView, setsView, repsView, exercisesDoneView;
    ImageView currentExercisePicture;
    GifImageView currentExerciseGif;
    Button processWorkoutButton;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        toolbar = (Toolbar) findViewById(R.id.currently_playing_workout_name);
        setSupportActionBar(toolbar);

        currentExerciseNameView = findViewById(R.id.current_exercise_name);
        currentExerciseGif = findViewById(R.id.current_exercise_gif);
        setsView = findViewById(R.id.current_exercise_sets);
        repsView = findViewById(R.id.current_exercise_reps);
        exercisesDoneView = findViewById(R.id.exercises_done);
        processWorkoutButton = findViewById(R.id.continue_button);

        // Shows info of the current exercise when user presses on the image
        currentExerciseGif.setClickable(true);
        currentExerciseGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentExerciseInfo();
            }
        });

        workout = getIntent().getParcelableExtra("workout");

        exercisesList = workout.getCustomExercises();

        // For some reason have to set title like this, or else it wouldnt apply the value
        getSupportActionBar().setTitle("Playing: " + workout.getWorkoutName());
        setUpExercise();

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    // Saves the needed values so the user can continue the work out from where he/she left off, after changing orientationW
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("exercise", customExercise);
        outState.putInt("sets", currentSet);
        outState.putInt("exercisesDone", position);
    }

    // Gets the values from the bundle saved before terminating the activity, and adds the correct values so the user can start from where they left off
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        customExercise = savedInstanceState.getParcelable("exercise");
        currentSet = savedInstanceState.getInt("sets");
        position = savedInstanceState.getInt("exercisesDone");

        // So it displays the correct exercise for the user
        setUpExercise();
    }

    private void setUpExercise(){
        // Shows the user how many exercises are done
        exercisesDoneView.setText("Exercises done: " + position + "/" + workout.getAmountExercises());

        // When position is the same as the amount of exercises, it means the workout is done.
        if(position == exercisesList.size()){
            Intent intent = new Intent(this, WorkoutDoneActivity.class);
            startActivity(intent);
        }
        else{
            // Gets the current exercise
            customExercise = exercisesList.get(position);
            sets = customExercise.getSets(); // Gets the amount of sets. This holds the sets needed to be done to go to next exercise
            displayExercise(customExercise);
        }

    }

    private void displayExercise(CustomExercise customExercise){
        Exercise exercise = customExercise.getExercise();

        // Finds the path to the gif in drawable
        int resID = getResources().getIdentifier(exercise.getGifImage(), "drawable", getPackageName());

        currentExerciseGif.setImageResource(resID); // Sets the gif image of the exercise
        currentExerciseNameView.setText(exercise.getExerciseName());
        repsView.setText(Integer.toString(customExercise.getReps())); // Get error if it isnt converted to string
        setsView.setText(currentSet + "/" + customExercise.getSets());
    }

    // When user presses continue button
    public void proceedInWorkout(View view) {
        currentSet++; // Next set

        // If last set is done, go to next exercise
        if(currentSet > sets){
            // Increase position so it goes to next exercise in the workouts list
            position++;
            currentSet = 1; // Reset current set
            setUpExercise();
        }
        else{
            // Increase the set value
            setsView.setText(currentSet + "/" + customExercise.getSets());
        }
    }

    private void showCurrentExerciseInfo(){
        Intent intent = new Intent(this, ExerciseInfoActivity.class);
        intent.putExtra("exercise", customExercise.getExercise());
        startActivity(intent);
    }
}
