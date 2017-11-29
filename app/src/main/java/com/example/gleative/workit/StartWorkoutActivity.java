package com.example.gleative.workit;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.fragments.NavigationDrawerFragment;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import pl.droidsonroids.gif.GifImageView;

public class StartWorkoutActivity extends AppCompatActivity {

    private Workout workout;
    private List<CustomExercise> exercisesList;
    private CustomExercise customExercise;
    private int currentSet = 1; // 1 because you always start at set number 1
    private int sets;
    private int position = 0; // 0 So it starts at the start of the workouts list.

    private CountDownTimer timer;
    private int counter = 0;
    private long seconds = 0;
    private long minutes = 0;
    private long hours = 0;

    private CountDownTimer timerExercise;
    private String fastestExerciseName, longestExerciseName;
    private long fastestExercise = 0;
    private long longestExercise = 0;
    private long workoutTime, exerciseTime;

    private long savedWorkoutTime, savedExerciseTime;

    TextView currentExerciseNameView, setsView, repsView, exercisesDoneView, timerView;
    GifImageView currentExerciseGif;
    Button processWorkoutButton;
    Toolbar toolbar;

    private NotificationCreator notificationCreator;

    // True if workout is done, or when we dont want the notification to display
    private boolean workoutDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        toolbar = findViewById(R.id.currently_playing_workout_name);
        setSupportActionBar(toolbar);

        currentExerciseNameView = findViewById(R.id.current_exercise_name);
        currentExerciseGif = findViewById(R.id.current_exercise_gif);
        setsView = findViewById(R.id.current_exercise_sets);
        repsView = findViewById(R.id.current_exercise_reps);
        exercisesDoneView = findViewById(R.id.exercises_done);
        processWorkoutButton = findViewById(R.id.continue_button);

        timerView = findViewById(R.id.timer);

        // Shows info of the current exercise when user presses on the image
        currentExerciseGif.setClickable(true);
        currentExerciseGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentExerciseInfo();
            }
        });

        notificationCreator = new NotificationCreator(this);

        workout = getIntent().getParcelableExtra("workout");

        exercisesList = workout.getCustomExercises();

        // For some reason have to set title like this, or else it wouldnt apply the value
        getSupportActionBar().setTitle("Playing: " + workout.getWorkoutName());
        setUpExercise();

        startWorkoutTimer();
    }

    // Starts the timer for the workout
    private void startWorkoutTimer(){
        timer = new CountDownTimer(3600000, 1000){

            @Override
            public void onTick(long millisecondsLeft) {
                counter++;

                // Holds the amount of milliseconds
                workoutTime = 3600000 - millisecondsLeft;

                NumberFormat displayTime = new DecimalFormat("00");
                hours = (workoutTime / 3600000) % 24;
                minutes = (workoutTime / 60000) % 60;
                seconds = (workoutTime / 1000) % 60;

                timerView.setText(displayTime.format(hours) + ":" + displayTime.format(minutes) + ":" + displayTime.format(seconds) + ":" + counter);

            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

    private void startExerciseTimer(){
        timerExercise = new CountDownTimer(3600000, 1000) {
            @Override
            public void onTick(long millisecondsLeft) {
                exerciseTime = 3600000 - millisecondsLeft;
            }

            @Override
            public void onFinish() {}
        }.start();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    // Saves the needed values so the user can continue the work out from where he/she left off, after changing orientationW
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        try{
            outState.putParcelable("exercise", customExercise);
            outState.putInt("sets", currentSet);
            outState.putInt("exercisesDone", position);
            outState.putLong("workoutTime", workoutTime);
            outState.putLong("exerciseTime", exerciseTime);
            outState.putLong("longestExercise", longestExercise);
            outState.putLong("fastestExercise", fastestExercise);
            outState.putString("longestExerciseName", longestExerciseName);
            outState.putString("fastestExerciseName", fastestExerciseName);
        } catch (Exception e){
            Toast.makeText(this, "Unable to save the data!", Toast.LENGTH_SHORT).show();
        }

    }

    // Gets the values from the bundle saved before terminating the activity, and adds the correct values so the user can start from where they left off
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try{
            customExercise = savedInstanceState.getParcelable("exercise");
            currentSet = savedInstanceState.getInt("sets");
            position = savedInstanceState.getInt("exercisesDone");
            longestExercise = savedInstanceState.getLong("longestExercise");
            fastestExercise = savedInstanceState.getLong("fastestExercise");
            longestExerciseName = savedInstanceState.getString("longestExerciseName");
            fastestExerciseName = savedInstanceState.getString("fastestExerciseName");
        } catch (Exception e){
            Toast.makeText(this, "Unable to retrieve the saved values!", Toast.LENGTH_SHORT).show();
        }


        // Append it, in case user changes orientation multiple times
        workoutTime = savedInstanceState.getLong("workoutTime");
        savedWorkoutTime += workoutTime;

        // Appends the exercise time, so it doesnt reset
        savedExerciseTime = savedInstanceState.getLong("exerciseTime");
        exerciseTime += savedExerciseTime;

        // So it displays the correct exercise for the user
        setUpExercise();
    }

    private void setUpExercise(){
        // Shows the user how many exercises are done
        exercisesDoneView.setText("Exercises done: " + position + "/" + workout.getAmountExercises());

        // When position is the same as the amount of exercises, it means the workout is done.
        if(position == exercisesList.size()){
            workoutDone = true; // Sets it to true, so notification dont show up
            timer.cancel(); // Stops the timer
            Intent intent = new Intent(this, WorkoutDoneActivity.class);

            // Adds the extra milliseconds lost when user changes orientation
            workoutTime += savedWorkoutTime;

            intent.putExtra("workoutTime", workoutTime);
            intent.putExtra("longestExercise", longestExercise);
            intent.putExtra("fastestExercise", fastestExercise);
            intent.putExtra("longestExerciseName", longestExerciseName);
            intent.putExtra("fastestExerciseName", fastestExerciseName);
            startActivity(intent);
        }
        else{
            // Gets the current exercise
            customExercise = exercisesList.get(position);
            sets = customExercise.getSets(); // Gets the amount of sets. This holds the sets needed to be done to go to next exercise
            displayExercise(customExercise);
            startExerciseTimer(); // Starts timer for current exercise
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

        // If last set of current exercise is done, go to next exercise
        if(currentSet > sets){
            timerExercise.cancel(); // Stops the timer for current exercise

            // If they have no value
            if(fastestExercise == 0 && longestExercise == 0){
                fastestExercise = exerciseTime;
                longestExercise = exerciseTime;

                String exerciseName = customExercise.getExercise().getExerciseName();

                // Gets rid of the chance of the exercise name to become null, by giving it value of the first exercise
                fastestExerciseName = exerciseName;
                longestExerciseName = exerciseName;
            }
            else{
                // Check if this exercise was faster or slower than any of the other
                if(exerciseTime < fastestExercise){
                    fastestExercise = exerciseTime;
                    fastestExerciseName = customExercise.getExercise().getExerciseName();
                }
                else if(exerciseTime > longestExercise){
                    longestExercise = exerciseTime;
                    longestExerciseName = customExercise.getExercise().getExerciseName();
                }
            }

            exerciseTime = 0; // Resets the exercise time so it starts from 0 on next exercise


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
        workoutDone = true; // Sets it to true, so notification dont show up

        Intent intent = new Intent(this, ExerciseInfoActivity.class);
        intent.putExtra("exercise", customExercise.getExercise());
        startActivity(intent);
    }

    // Called when user go out of app
    @Override
    protected void onPause() {
        super.onPause();
        if(!workoutDone){
            NotificationCompat.Builder notificationBuilder = notificationCreator.getChannel1Notification("Current exercise: " + customExercise.getExercise().getExerciseName(), "LOOOL");
            notificationCreator.getManager().notify(1, notificationBuilder.build());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        workoutDone = false; // Set to false so the notification can come back again
        // Deletes the notification, when the user is back on the activity
        notificationCreator.getManager().cancelAll();
    }

    // When user presses back, the workout is canceled. Sets workout done to true. So notification doesnt show up
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        workoutDone = true;
    }
}
