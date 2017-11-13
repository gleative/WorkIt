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

import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

import java.util.List;

public class StartWorkoutActivity extends AppCompatActivity {

    private Workout workout;
    private List<CustomExercise> exercisesList;
    private CustomExercise customExercise;
    private int currentSet = 1; // 1 because you always start at set number 1
    private int sets, reps;
    private int position = 0; // 0 So it starts at the start of the workouts list.

    TextView currentExerciseNameView, setsView, repsView;
    ImageView currentExercisePicture;
    Button processWorkoutButton;

    Toolbar toolbar;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentExerciseNameView = findViewById(R.id.current_exercise_name);
        currentExercisePicture = findViewById(R.id.current_exercise_picture);
        setsView = findViewById(R.id.current_exercise_sets);
        repsView = findViewById(R.id.current_exercise_reps);
        processWorkoutButton = findViewById(R.id.continue_button);

        // Shows info of the current exercise when user presses on the image
        currentExercisePicture.setClickable(true);
        currentExercisePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentExerciseInfo();
            }
        });


        workout = getIntent().getParcelableExtra("workout");
        toolbar.setTitle(workout.getWorkoutName());

        exercisesList = workout.getCustomExercises();

        setUpExercise();

        setUpDrawer();
    }

    private void setUpDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setUpDrawer(drawerLayout, toolbar, R.id.nav_exercises);
    }

    @Override
    protected void onStart(){
        navigationDrawerFragment.updateCheckedItem(R.id.nav_exercises);

        super.onStart();
    }

    private void setUpExercise(){
        // When position is the same as the amount of exercises, it means the workout is done.
        if(position == exercisesList.size()){
            Intent intent = new Intent(this, MainActivity.class);
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
        currentExerciseNameView.setText(exercise.getExerciseName());
//        currentExercisePicture
        repsView.setText(Integer.toString(customExercise.getReps()));
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
