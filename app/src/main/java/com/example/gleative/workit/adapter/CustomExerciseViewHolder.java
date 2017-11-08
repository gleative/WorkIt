package com.example.gleative.workit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gleative.workit.R;
import com.example.gleative.workit.model.CustomExercise;
import com.example.gleative.workit.model.Exercise;

import org.w3c.dom.Text;

/**
 * Created by gleative on 08.11.2017.
 */

public class CustomExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView customExerciseName;
    TextView customExerciseSets;
    TextView customExerciseReps;
    OnExerciseSelectedListener onExerciseSelectedListener;

    public CustomExerciseViewHolder(View itemView){
        super(itemView);

        customExerciseName = itemView.findViewById(R.id.custom_exercise_title);
        customExerciseSets = itemView.findViewById(R.id.custom_exercise_sets);
        customExerciseReps = itemView.findViewById(R.id.custom_exercise_reps);

    }

    public void bind(CustomExercise customExercise, Exercise exercise, OnExerciseSelectedListener listener){
        this.customExerciseName.setText(exercise.getExerciseName());
        this.customExerciseSets.setText(customExercise.getSets());
        this.customExerciseReps.setText(customExercise.getReps());
        this.onExerciseSelectedListener = listener;
    }

    @Override
    public void onClick(View v) {
        onExerciseSelectedListener.exerciseSelected(getAdapterPosition());
    }
}
