package com.example.gleative.workit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WorkoutDoneActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView workoutTimeView, fastestExerciseTimeView, longestExerciseTimeView, fastestExerciseNameView, longestExerciseNameView;

    String fastestExerciseName, longestExerciseName;
    long hours, minutes, seconds, fastestExercise, longestExercise, workoutTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_done);

        toolbar = findViewById(R.id.workout_done_toolbar);
        setSupportActionBar(toolbar);

        workoutTimeView = findViewById(R.id.workout_time);
        fastestExerciseTimeView = findViewById(R.id.fastest_exercise_time);
        longestExerciseTimeView = findViewById(R.id.longest_exercise_time);
        fastestExerciseNameView = findViewById(R.id.fastest_exercise_name);
        longestExerciseNameView = findViewById(R.id.longest_exercise_name);


        Bundle extras = getIntent().getExtras();
        workoutTime = extras.getLong("workoutTime");
        fastestExercise = extras.getLong("fastestExercise");
        longestExercise = extras.getLong("longestExercise");
        fastestExerciseName = extras.getString("fastestExerciseName");
        longestExerciseName = extras.getString("longestExerciseName");

        // Creates format so it always displays two decimals
        NumberFormat displayTime = new DecimalFormat("00");

        calculateTime(workoutTime);
        workoutTimeView.setText(displayTime.format(hours) + ":" + displayTime.format(minutes) + ":" + displayTime.format(seconds));

        calculateTime(fastestExercise);
        fastestExerciseNameView.setText(fastestExerciseName);
        fastestExerciseTimeView.setText(displayTime.format(hours) + ":" + displayTime.format(minutes) + ":" + displayTime.format(seconds));

        calculateTime(longestExercise);
        longestExerciseNameView.setText(longestExerciseName);
        longestExerciseTimeView.setText(displayTime.format(hours) + ":" + displayTime.format(minutes) + ":" + displayTime.format(seconds));

    }

    // Converts the long value, to amount of hours, minutes and seconds
    private void calculateTime(long time){
        hours = (time / 3600000) % 24;
        minutes = (time / 60000) % 60;
        seconds = (time / 1000) % 60;
    }

    //
    public void finishWorkout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
