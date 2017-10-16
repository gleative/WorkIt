package com.example.gleative.workit.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gleative.workit.R;
import com.example.gleative.workit.model.Exercise;

import java.util.List;

/**
 * Created by gleative on 12.10.2017.
 *
 * Gets data from selected exercise, and sets the data into the correct views
 */

public class ExerciseInfoFragment extends Fragment{
    private List<Exercise> exerciseList;

    public final static String EXERCISE_INDEX = "exerciseIndex";
    private static final int DEFAULT_EXERCISE_INDEX = 1;

    private Toolbar exerciseNameToolbar;
    private TextView exerciseNameView;
    private TextView exerciseDescView;
    private int exerciseIndex;

    public ExerciseInfoFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        exerciseIndex = savedInstanceState == null? DEFAULT_EXERCISE_INDEX : savedInstanceState.getInt(EXERCISE_INDEX, DEFAULT_EXERCISE_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        exerciseList = Exercise.getData();

        View fragmentView = inflater.inflate(R.layout.fragment_exercise_info, container, false); // Layout file

        // Finds referance to the diffrent views in "fragment_exercise_info" layout
        exerciseNameToolbar = fragmentView.findViewById(R.id.toolbar);
//        exerciseNameView = fragmentView.findViewById(R.id.exercise_title);
        exerciseDescView = fragmentView.findViewById(R.id.exercise_description);

        setDisplayedDetail(exerciseIndex);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXERCISE_INDEX, exerciseIndex);
    }

    public void setDisplayedDetail(int exerciseDescIndex){
        exerciseIndex = exerciseDescIndex;

        Exercise exercise = exerciseList.get(exerciseIndex);

        exerciseNameToolbar.setTitle(exercise.getExerciseName());
        exerciseDescView.setText(exercise.getExerciseDescription());
    }
}
