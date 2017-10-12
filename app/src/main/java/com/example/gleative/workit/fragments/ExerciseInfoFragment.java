package com.example.gleative.workit.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

    private TextView exerciseTitleView;
    private TextView exerciseDescView;
    private ImageView exerciseImageView;
    private int exerciseIndex;

    // Empty constructor required
    public ExerciseInfoFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the index to 0, if there is no savedInstanceState
        exerciseIndex = savedInstanceState == null? DEFAULT_EXERCISE_INDEX : savedInstanceState.getInt(EXERCISE_INDEX, DEFAULT_EXERCISE_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        exerciseList = Exercise.getData();

        View fragmentView = inflater.inflate(R.layout.fragment_exercise_info, container, false); // Layout file

        // Finds referance to the diffrent views in "fragment_exercise_info" layout
        exerciseTitleView = fragmentView.findViewById(R.id.exercise_title);
        exerciseDescView = fragmentView.findViewById(R.id.exercise_description);
        exerciseImageView = fragmentView.findViewById(R.id.exercise_picture);

        setDisplayDetail(exerciseIndex);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXERCISE_INDEX, exerciseIndex);
    }

    // Sets values of the exercise data to the correct views
    public void setDisplayDetail(int exerciseDescriptionIndex){
        exerciseIndex = exerciseDescriptionIndex;

        Exercise exercise = exerciseList.get(exerciseIndex);

        exerciseTitleView.setText(exercise.getExerciseName());
        exerciseDescView.setText(exercise.getExerciseDescription());
//        exerciseImageView

        // Sets the main image
//        Drawable image = ContextCompat.getDrawable(getActivity(), exercise.getImageID());
//        if(image != null){
//            exerciseImageView.setImageDrawable(image);
//        }
    }
}
