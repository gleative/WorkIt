package com.example.gleative.workit;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    WorkoutCustomExercisesListFragment workoutCustomExercisesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workout_info);

        selectedWorkout = getIntent().getParcelableExtra("workout");

        workoutName = findViewById(R.id.selected_workout_name);
        workoutDesc = findViewById(R.id.selected_workout_desc);

        dbReference = FirebaseDatabase.getInstance().getReference().child("customExercises");



        setDisplayedDetail(selectedWorkout);
    }

    public void setDisplayedDetail(Workout workout){
        workoutName.setText(workout.getWorkoutName());
        workoutDesc.setText(workout.getWorkoutDescription());



//        List<CustomExercise> customExerciseList = new ArrayList<>();

//        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot customExerciseSnapshot : dataSnapshot.getChildren()){
//                    CustomExercise customExercise = customExerciseSnapshot.getValue(CustomExercise.class);
//
//                    if(customExercise.getWorkoutID().equals(workout.getWorkoutID())){
//                        customExerciseList.add(customExercise);
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onCustomExerciseSelected(CustomExercise customExercise) {

    }
}
