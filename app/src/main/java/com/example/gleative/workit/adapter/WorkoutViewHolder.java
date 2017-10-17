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
    OnWorkoutSelectedListener onWorkoutSelectedListener;

    public WorkoutViewHolder(View itemView) {
        super(itemView);
        workoutName = itemView.findViewById(R.id.workout_title);
    }

    public void bind(Workout workout, OnWorkoutSelectedListener listener){
        this.workoutName.setText(workout.getWorkoutName());
        this.onWorkoutSelectedListener = listener;
    }

    @Override
    public void onClick(View view) {
        onWorkoutSelectedListener.workoutSelected(getAdapterPosition());

    }
}
