package com.example.gleative.workit;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    TextView workoutName;
    TextView workoutDesc;
    ListView customExercisesListView;
    WorkoutCustomExercisesListFragment workoutCustomExercisesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workout_info);

        selectedWorkout = getIntent().getParcelableExtra("workout");

        workoutName = findViewById(R.id.selected_workout_name);
        workoutDesc = findViewById(R.id.selected_workout_desc);
//        customExercisesListView = findViewById(R.id.custom_exercise_list);

        dbReference = FirebaseDatabase.getInstance().getReference().child("customExercises");

        // So we can access its methods
        workoutCustomExercisesListFragment = (WorkoutCustomExercisesListFragment) getSupportFragmentManager().findFragmentById(R.id.myWorkout_info_fragment);

        setDisplayedDetail(selectedWorkout);
    }

    public void setDisplayedDetail(Workout workout){
        workoutName.setText(workout.getWorkoutName());
        workoutDesc.setText(workout.getWorkoutDescription());
        workoutCustomExercisesListFragment.getCustomExercisesFromWorkout(workout); // Sends the custom exercises list to the adapter, so it can display workouts custom exercises
    }

    @Override
    public void onCustomExerciseSelected(CustomExercise customExercise) {

    }



    // When the user press back, they will be sendt to MyWorkoutsActivity, and not go through all the process they did when they created a workout
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyWorkoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // So MyWorkoutActivity wont overlap on eachother when user presses back
        startActivity(intent);
    }

    // When user presses the FAB button
    public void addNewCustomExerciseToWorkout(View view) {
        Intent intent = new Intent(this, AddExerciseToWorkoutActivity.class);
        intent.putExtra("workout", selectedWorkout);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // So MyWorkoutActivity wont overlap on eachother when user presses back
        startActivity(intent);
    }
}
