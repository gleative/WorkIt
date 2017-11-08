package com.example.gleative.workit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gleative.workit.fragments.ExerciseListFragment;
import com.example.gleative.workit.fragments.WorkoutCustomExercisesListFragment;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;
import com.example.gleative.workit.model.Workout;

public class MyWorkoutInfoActivity extends AppCompatActivity implements WorkoutCustomExercisesListFragment.OnCustomExerciseFragmentInteractionListener{

    Workout selectedWorkout;

    TextView workoutName;
    TextView workoutDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workout_info);

        selectedWorkout = getIntent().getParcelableExtra("workout");

        workoutName = findViewById(R.id.selected_workout_name);
        workoutDesc = findViewById(R.id.selected_workout_desc);

        setDisplayedDetail(selectedWorkout);
    }

    public void setDisplayedDetail(Workout workout){
        workoutName.setText(workout.getWorkoutName());
        workoutDesc.setText(workout.getWorkoutDescription());
    }

    @Override
    public void onCustomExerciseSelected(CustomExercise customExercise) {

    }
}
