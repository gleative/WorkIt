package com.example.gleative.workit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gleative.workit.R;
import com.example.gleative.workit.model.Workout;

/**
 * Created by glenn on 17.10.2017.
 */

public class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView workoutName;
    TextView workoutAmountExercises;
    OnWorkoutSelectedListener onWorkoutSelectedListener;

    public WorkoutViewHolder(View itemView) {
        super(itemView);
        workoutName = itemView.findViewById(R.id.workout_title);
        workoutAmountExercises = itemView.findViewById(R.id.workout_exercise_amount);
    }

    public void bind(Workout workout, OnWorkoutSelectedListener listener){
        this.workoutName.setText(workout.getWorkoutName());
        this.workoutAmountExercises.setText("Exercises: " + workout.getAmountExercises());
        this.onWorkoutSelectedListener = listener;
    }

    @Override
    public void onClick(View view) {
        onWorkoutSelectedListener.workoutSelected(getAdapterPosition());
    }
}
