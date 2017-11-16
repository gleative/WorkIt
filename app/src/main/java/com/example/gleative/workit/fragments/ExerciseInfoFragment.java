package com.example.gleative.workit.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gleative.workit.R;
import com.example.gleative.workit.adapter.ExercisePicturesAdapter;
import com.example.gleative.workit.model.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleative on 12.10.2017.
 *
 * Gets data from selected exercise, and sets the data into the correct views
 */

public class ExerciseInfoFragment extends Fragment{
    DatabaseReference dbReference;

    private List<Exercise> exerciseList;
    private List<Exercise> eList;

    public final static String EXERCISE_INDEX = "exerciseIndex";
    private static final int DEFAULT_EXERCISE_INDEX = 1;

    private Toolbar exerciseNameToolbar;
    private TextView exerciseNameView;
    private TextView exerciseDescView;
    private ImageView exercisePicture;
    private int exerciseIndex;
    private ViewPager exerciseViewPager; // Holds the pictures

    public ExerciseInfoFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        exerciseIndex = savedInstanceState == null? DEFAULT_EXERCISE_INDEX : savedInstanceState.getInt(EXERCISE_INDEX, DEFAULT_EXERCISE_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getData(exerciseIndex);
//        exerciseList = Exercise.getData();

        View fragmentView = inflater.inflate(R.layout.fragment_exercise_info, container, false); // Layout file

        // Finds referance to the diffrent views in "fragment_exercise_info" layout
        exerciseNameToolbar = fragmentView.findViewById(R.id.exercise_name); // Couldnt have toolbar as ID or else it wouldnt change name on toolbar for some reason....
        exercisePicture = fragmentView.findViewById(R.id.exercise_picture);
        exerciseDescView = fragmentView.findViewById(R.id.exercise_description);

//        setDisplayedDetail(exerciseIndex);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXERCISE_INDEX, exerciseIndex);
    }

    public void setDisplayedDetail(Exercise exercise){
//        exerciseIndex = exerciseDescIndex;

//        Exercise exercise = exerciseList.get(exerciseIndex);

        exerciseNameToolbar.setTitle(exercise.getExerciseName());
        Glide.with(getActivity()).load(exercise.getImageThumb1()).into(exercisePicture);

        exerciseDescView.setText(exercise.getExerciseDescription());
    }
}
