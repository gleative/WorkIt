package com.example.gleative.workit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gleative.workit.R;
import com.example.gleative.workit.model.Exercise;

/**
 * Created by gleative on 14.10.2017.
 */

public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView exerciseName;
    TextView exerciseBodyPart;
    OnExerciseSelectedListener onExerciseSelectedListener;

    public ExerciseViewHolder(View itemView){
        super(itemView);

        // Sets the value to the given xml item
        exerciseName = itemView.findViewById(R.id.exercise_title);
        exerciseBodyPart = itemView.findViewById(R.id.exercise_body_part);
    }

    public void bind(Exercise exercise, OnExerciseSelectedListener listener){ // Setter titelen på øvelslen
        this.exerciseName.setText(exercise.getExerciseName());
        this.exerciseBodyPart.setText(exercise.getBodyPart());
        this.onExerciseSelectedListener = listener;
    }

    @Override
    public void onClick(View v) {
        onExerciseSelectedListener.exerciseSelected(getAdapterPosition());
    }
}
