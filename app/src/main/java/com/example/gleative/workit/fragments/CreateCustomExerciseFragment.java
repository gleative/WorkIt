package com.example.gleative.workit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gleative.workit.CreateCustomExerciseActivity;
import com.example.gleative.workit.R;
import com.example.gleative.workit.model.Exercise;

/**
 * Created by gleative on 07.11.2017.
 */

public class CreateCustomExerciseFragment extends Fragment{

    private TextView exerciseName;
    private TextView exerciseBodyPart;

    public CreateCustomExerciseFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//        exerciseIndex = savedInstanceState == null? DEFAULT_EXERCISE_INDEX : savedInstanceState.getInt(EXERCISE_INDEX, DEFAULT_EXERCISE_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_create_custom_exercise, container, false); // Layout file

        exerciseName = fragmentView.findViewById(R.id.selected_exercise_name);
        exerciseBodyPart = fragmentView.findViewById(R.id.selected_exercise_body_part);

        return fragmentView;
    }

    public void setDisplayedDetail(Exercise exercise){

        exerciseName.setText(exercise.getExerciseName());
        exerciseBodyPart.setText(exercise.getBodyPart());
    }
}
